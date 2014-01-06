// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.proxetta.asm;

import jodd.asm.AsmUtil;
import jodd.proxetta.ProxyAspect;
import jodd.asm4.ClassVisitor;
import jodd.asm4.FieldVisitor;
import jodd.asm4.MethodVisitor;
import jodd.asm4.Opcodes;

import java.util.List;

import static jodd.proxetta.asm.ProxettaAsmUtil.CLINIT;
import static jodd.proxetta.asm.ProxettaAsmUtil.INIT;
import static jodd.proxetta.asm.ProxettaAsmUtil.loadVirtualMethodArguments;
import static jodd.proxetta.asm.ProxettaAsmUtil.visitReturn;
import static jodd.asm4.Opcodes.ACC_ABSTRACT;
import static jodd.asm4.Opcodes.ACC_NATIVE;
import static jodd.asm4.Opcodes.ALOAD;
import static jodd.asm4.Opcodes.GETFIELD;
import static jodd.asm4.Opcodes.INVOKEINTERFACE;
import static jodd.asm4.Opcodes.INVOKEVIRTUAL;

public class ProxettaWrapperClassBuilder extends ProxettaClassBuilder {

	protected final Class targetClassOrInterface;
	protected final Class targetInterface;
	protected final String targetFieldName;

	public ProxettaWrapperClassBuilder(
			Class targetClassOrInterface,
			Class targetInterface,
			String targetFieldName,
			ClassVisitor dest,
			ProxyAspect[] aspects,
			String suffix,
			String reqProxyClassName,
			TargetClassInfoReader targetClassInfoReader) {

		super(dest, aspects, suffix, reqProxyClassName, targetClassInfoReader);
		this.targetClassOrInterface = targetClassOrInterface;
		this.targetInterface = targetInterface;
		this.targetFieldName = targetFieldName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

		wd.init(name, superName, this.suffix, this.reqProxyClassName);

		// no superclass
		wd.superName = AsmUtil.SIGNATURE_JAVA_LANG_OBJECT;

		// change access of destination
		access &= ~AsmUtil.ACC_ABSTRACT;
		access &= ~AsmUtil.ACC_INTERFACE;

		// write destination class
		if (targetClassOrInterface.isInterface()) {
			// target is interface
			wd.wrapInterface = true;

			interfaces = new String[] {targetClassOrInterface.getName().replace('.', '/')};
		} else {
			// target is class
			wd.wrapInterface = false;

			if (targetInterface != null) {
				// interface provided
				interfaces = new String[] {targetInterface.getName().replace('.', '/')};
			} else {
				// no interface provided, use all
				//interfaces = null;
			}
		}
		wd.dest.visit(version, access, wd.thisReference, signature, wd.superName, interfaces);

		wd.proxyAspects = new ProxyAspectData[aspects.length];
		for (int i = 0; i < aspects.length; i++) {
			wd.proxyAspects[i] = new ProxyAspectData(wd, aspects[i], i);
		}

		// create new field wrapper field and store it's reference into work-data
		wd.wrapperRef = targetFieldName;
		wd.wrapperType = 'L' + name + ';';
		FieldVisitor fv  = wd.dest.visitField(AsmUtil.ACC_PUBLIC, wd.wrapperRef, wd.wrapperType, null, null);
		fv.visitEnd();

		createEmptyCtor();
	}

	/**
	 * Created empty default constructor.
	 */
	protected void createEmptyCtor() {
		MethodVisitor mv = wd.dest.visitMethod(AsmUtil.ACC_PUBLIC, INIT, "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, AsmUtil.SIGNATURE_JAVA_LANG_OBJECT, INIT, "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodSignatureVisitor msign = targetClassInfo.lookupMethodSignatureVisitor(access, name, desc, wd.superReference);
		if (msign == null) {
			return null;
		}

		// ignore all destination constructors
		if (name.equals(INIT) == true) {
			return null;
		}
		// ignore all destination static block
		if (name.equals(CLINIT) == true) {
			return null;
		}

		return applyProxy(msign);
	}

	@Override
	protected ProxettaMethodBuilder applyProxy(MethodSignatureVisitor msign) {
		List<ProxyAspectData> aspectList = matchMethodPointcuts(msign);

		if (aspectList == null) {
			wd.proxyApplied = true;
			createSimpleMethodWrapper(msign);
			return null;
		}

		wd.proxyApplied = true;
		return new ProxettaMethodBuilder(msign, wd, aspectList);

	}

	/**
	 * Creates simple method wrapper without proxy.
	 */
	protected void createSimpleMethodWrapper(MethodSignatureVisitor msign) {

		int access = msign.getAccessFlags();

		access &= ~ACC_ABSTRACT;
		access &= ~ACC_NATIVE;

		MethodVisitor mv = wd.dest.visitMethod(
				access, msign.getMethodName(), msign.getDescription(), msign.getRawSignature(), msign.getExceptionsArray());
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, wd.thisReference, wd.wrapperRef, wd.wrapperType);
		loadVirtualMethodArguments(mv, msign);
		if (wd.wrapInterface) {
			mv.visitMethodInsn(INVOKEINTERFACE, wd.wrapperType.substring(1, wd.wrapperType.length() - 1), msign.getMethodName(), msign.getDescription());
		} else {
			mv.visitMethodInsn(INVOKEVIRTUAL, wd.wrapperType.substring(1, wd.wrapperType.length() - 1), msign.getMethodName(), msign.getDescription());
		}
		ProxettaAsmUtil.prepareReturnValue(mv, msign, 0);
		visitReturn(mv, msign, true);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}

	@Override
	public void visitEnd() {
		makeStaticInitBlock();

		processSuperMethods();

		wd.dest.visitEnd();

	}
}
