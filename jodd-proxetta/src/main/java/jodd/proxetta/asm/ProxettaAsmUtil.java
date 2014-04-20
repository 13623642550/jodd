// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.proxetta.asm;

import jodd.asm.AsmUtil;
import jodd.proxetta.MethodInfo;
import jodd.util.StringBand;
import jodd.asm5.Label;
import jodd.asm5.MethodVisitor;
import jodd.asm5.Type;
import static jodd.asm5.Opcodes.*;
import jodd.proxetta.ProxyAdvice;
import jodd.proxetta.ProxettaException;
import static jodd.JoddProxetta.*;
import jodd.util.StringPool;
import static jodd.util.StringPool.COLON;

/**
 * Various ASM utilities used by {@link jodd.proxetta.Proxetta}.
 * For more generic ASM tools, see {@link jodd.asm.AsmUtil}.
 */
public class ProxettaAsmUtil {

	public static final String INIT = "<init>";
	public static final String CLINIT = "<clinit>";
	public static final String DESC_VOID = "()V";

	// ---------------------------------------------------------------- misc

	/**
	 * Pushes int value in an optimal way.
	 */
	public static void pushInt(MethodVisitor mv, int value) {
		if (value <= 5) {
			mv.visitInsn(ICONST_0 + value);
		} else if (value <= Byte.MAX_VALUE) {
			mv.visitIntInsn(BIPUSH, value);
		}  else {
			mv.visitIntInsn(SIPUSH, value);
		}
	}

	/**
	 * Changes method access to private and final.
	 */
	public static int makePrivateFinalAccess(int access) {
		return (access & 0xFFFFFFF0) | AsmUtil.ACC_PRIVATE | AsmUtil.ACC_FINAL;
	}

	/**
	 * Validates argument index.
	 */
	public static void checkArgumentIndex(MethodSignatureVisitor msign, int argIndex, Class<? extends ProxyAdvice> advice) {
		if ((argIndex < 1) || (argIndex > msign.getArgumentsCount())) {
			throw new ProxettaException("Invalid argument index: '" + argIndex + "' used in advice: " + advice.getName());
		}
	}

	/**
	 * Builds advice field name.
	 */
	public static String adviceFieldName(String name, int index) {
		return fieldPrefix + name + fieldDivider + index;
	}

	/**
	 * Builds advice method name.
	 */
	public static String adviceMethodName(String name, int index) {
		return methodPrefix + name + methodDivider + index;
	}



	// ---------------------------------------------------------------- load

	public static void loadMethodArgumentClass(MethodVisitor mv, MethodSignatureVisitor msign, int index) {
		loadClass(mv, msign.getArgumentOpcodeType(index), msign.getArgumentTypeName(index));
	}

	public static void loadMethodReturnClass(MethodVisitor mv, MethodSignatureVisitor msign) {
		loadClass(mv, msign.getReturnOpcodeType(), msign.getReturnTypeName());
	}

	public static void loadClass(MethodVisitor mv, int type, String typeName) {
		switch (type) {
			case 'V':
				mv.visitFieldInsn(GETSTATIC, AsmUtil.SIGNATURE_JAVA_LANG_VOID, "TYPE", AsmUtil.L_SIGNATURE_JAVA_LANG_CLASS);
				break;
			case 'B':
				mv.visitFieldInsn(GETSTATIC, AsmUtil.SIGNATURE_JAVA_LANG_BYTE, "TYPE", AsmUtil.L_SIGNATURE_JAVA_LANG_CLASS);
				break;
			case 'C':
				mv.visitFieldInsn(GETSTATIC, AsmUtil.SIGNATURE_JAVA_LANG_CHARACTER, "TYPE", AsmUtil.L_SIGNATURE_JAVA_LANG_CLASS);
				break;
			case 'S':
				mv.visitFieldInsn(GETSTATIC, AsmUtil.SIGNATURE_JAVA_LANG_SHORT, "TYPE", AsmUtil.L_SIGNATURE_JAVA_LANG_CLASS);
				break;
			case 'I':
				mv.visitFieldInsn(GETSTATIC, AsmUtil.SIGNATURE_JAVA_LANG_INTEGER, "TYPE", AsmUtil.L_SIGNATURE_JAVA_LANG_CLASS);
				break;
			case 'Z':
				mv.visitFieldInsn(GETSTATIC, AsmUtil.SIGNATURE_JAVA_LANG_BOOLEAN, "TYPE", AsmUtil.L_SIGNATURE_JAVA_LANG_CLASS);
				break;
			case 'J':
				mv.visitFieldInsn(GETSTATIC, AsmUtil.SIGNATURE_JAVA_LANG_LONG, "TYPE", AsmUtil.L_SIGNATURE_JAVA_LANG_CLASS);
				break;
			case 'F':
				mv.visitFieldInsn(GETSTATIC, AsmUtil.SIGNATURE_JAVA_LANG_FLOAT, "TYPE", AsmUtil.L_SIGNATURE_JAVA_LANG_CLASS);
				break;
			case 'D':
				mv.visitFieldInsn(GETSTATIC, AsmUtil.SIGNATURE_JAVA_LANG_DOUBLE, "TYPE", AsmUtil.L_SIGNATURE_JAVA_LANG_CLASS);
				break;
			default:
				mv.visitLdcInsn(Type.getType(typeName));
				break;
		}

	}

	/**
	 * Loads all method arguments before INVOKESPECIAL call.
	 */
	public static void loadSpecialMethodArguments(MethodVisitor mv, MethodSignatureVisitor msign) {
		mv.visitVarInsn(ALOAD, 0);
		for (int i = 1; i <= msign.getArgumentsCount(); i++) {
			loadMethodArgument(mv, msign, i);
		}
	}

	/**
	 * Loads all method arguments before INVOKESTATIC call.
	 */
	public static void loadStaticMethodArguments(MethodVisitor mv, MethodSignatureVisitor msign) {
		for (int i = 0; i < msign.getArgumentsCount(); i++) {
			loadMethodArgument(mv, msign, i);
		}
	}

	/**
	 * Loads all method arguments before INVOKEVIRTUAL call.
	 */
	public static void loadVirtualMethodArguments(MethodVisitor mv, MethodSignatureVisitor msign) {
		for (int i = 1; i <= msign.getArgumentsCount(); i++) {
			loadMethodArgument(mv, msign, i);
		}
	}

	/**
	 * Loads one argument. Index is 1-based. No conversion occurs.
	 */
	public static void loadMethodArgument(MethodVisitor mv, MethodSignatureVisitor msign, int index) {
		int offset = msign.getArgumentOffset(index);
		int type = msign.getArgumentOpcodeType(index);
		switch (type) {
			case 'V':
				break;
			case 'B':
			case 'C':
			case 'S':
			case 'I':
			case 'Z':
				mv.visitVarInsn(ILOAD, offset);
				break;
			case 'J':
				mv.visitVarInsn(LLOAD, offset);
				break;
			case 'F':
				mv.visitVarInsn(FLOAD, offset);
				break;
			case 'D':
				mv.visitVarInsn(DLOAD, offset);
				break;
			default:
				mv.visitVarInsn(ALOAD, offset);
		}
	}


	public static void loadMethodArgumentAsObject(MethodVisitor mv, MethodInfo methodInfo, int index) {
		int offset = methodInfo.getArgumentOffset(index);
		int type = methodInfo.getArgumentOpcodeType(index);
		switch (type) {
			case 'V':
				break;
			case 'B':
				mv.visitVarInsn(ILOAD, offset);
				AsmUtil.valueOfByte(mv);
				break;
			case 'C':
				mv.visitVarInsn(ILOAD, offset);
				AsmUtil.valueOfCharacter(mv);
				break;
			case 'S':
				mv.visitVarInsn(ILOAD, offset);
				AsmUtil.valueOfShort(mv);
				break;
			case 'I':
				mv.visitVarInsn(ILOAD, offset);
				AsmUtil.valueOfInteger(mv);
				break;
			case 'Z':
				mv.visitVarInsn(ILOAD, offset);
				AsmUtil.valueOfBoolean(mv);
				break;
			case 'J':
				mv.visitVarInsn(LLOAD, offset);
				AsmUtil.valueOfLong(mv);
				break;
			case 'F':
				mv.visitVarInsn(FLOAD, offset);
				AsmUtil.valueOfFloat(mv);
				break;
			case 'D':
				mv.visitVarInsn(DLOAD, offset);
				AsmUtil.valueOfDouble(mv);
				break;
			default:
				mv.visitVarInsn(ALOAD, offset);
		}
	}

	// ---------------------------------------------------------------- store

	/**
	 * Stores one argument. Index is 1-based. No conversion occurs.
	 */
	public static void storeMethodArgument(MethodVisitor mv, MethodSignatureVisitor msign, int index) {
		int offset = msign.getArgumentOffset(index);
		int type = msign.getArgumentOpcodeType(index);
		switch (type) {
			case 'V':
				break;
			case 'B':
			case 'C':
			case 'S':
			case 'I':
			case 'Z':
				mv.visitVarInsn(ISTORE, offset); break;
			case 'J':
				mv.visitVarInsn(LSTORE, offset); break;
			case 'F':
				mv.visitVarInsn(FSTORE, offset); break;
			case 'D':
				mv.visitVarInsn(DSTORE, offset); break;
			default:
				mv.visitVarInsn(ASTORE, offset);
		}
	}

	/**
	 * Returns <code>true</code> if opcode is xSTORE.
	 */
	public static boolean isStoreOpcode(int opcode) {
		return (opcode == ISTORE)
				|| (opcode == LSTORE)
				|| (opcode == FSTORE)
				|| (opcode == DSTORE)
				|| (opcode == ASTORE);
	}


	public static void storeMethodArgumentFromObject(MethodVisitor mv, MethodSignatureVisitor msign, int index) {
		int type = msign.getArgumentOpcodeType(index);
		int offset = msign.getArgumentOffset(index);
		storeValue(mv, offset, type);
	}

	public static void storeValue(MethodVisitor mv, int offset, int type) {
		switch (type) {
			case 'V':
				break;
			case 'B':
				AsmUtil.byteValue(mv);
				mv.visitVarInsn(ISTORE, offset);
				break;
			case 'C':
				AsmUtil.charValue(mv);
				mv.visitVarInsn(ISTORE, offset);
				break;
			case 'S':
				AsmUtil.shortValue(mv);
				mv.visitVarInsn(ISTORE, offset);
				break;
			case 'I':
				AsmUtil.intValue(mv);
				mv.visitVarInsn(ISTORE, offset);
				break;
			case 'Z':
				AsmUtil.booleanValue(mv);
				mv.visitVarInsn(ISTORE, offset);
				break;
			case 'J':
				AsmUtil.longValue(mv);
				mv.visitVarInsn(LSTORE, offset);
				break;
			case 'F':
				AsmUtil.floatValue(mv);
				mv.visitVarInsn(FSTORE, offset);
				break;
			case 'D':
				AsmUtil.doubleValue(mv);
				mv.visitVarInsn(DSTORE, offset);
				break;
			default:
				mv.visitVarInsn(ASTORE, offset);
		}
	}

	// ---------------------------------------------------------------- return

	/**
	 * Visits return opcodes.
	 */
	public static void visitReturn(MethodVisitor mv, MethodSignatureVisitor msign, boolean isLast) {
		switch (msign.getReturnOpcodeType()) {
			case 'V':
				if (isLast == true) {
					mv.visitInsn(POP);
				}
				mv.visitInsn(RETURN);
				break;

			case 'B':
				if (isLast == true) {
					mv.visitInsn(DUP);
					Label label = new Label();
					mv.visitJumpInsn(IFNONNULL, label);
						mv.visitInsn(POP);
						mv.visitInsn(ICONST_0);
						mv.visitInsn(IRETURN);
					mv.visitLabel(label);

					AsmUtil.byteValue(mv);
				}
				mv.visitInsn(IRETURN);
				break;

			case 'C':
				if (isLast == true) {
					mv.visitInsn(DUP);
					Label label = new Label();
					mv.visitJumpInsn(IFNONNULL, label);
						mv.visitInsn(POP);
						mv.visitInsn(ICONST_0);
						mv.visitInsn(IRETURN);
					mv.visitLabel(label);

					AsmUtil.charValue(mv);
				}
				mv.visitInsn(IRETURN);
				break;

			case 'S':
				if (isLast == true) {
					mv.visitInsn(DUP);
					Label label = new Label();
					mv.visitJumpInsn(IFNONNULL, label);
						mv.visitInsn(POP);
						mv.visitInsn(ICONST_0);
						mv.visitInsn(IRETURN);
					mv.visitLabel(label);

					AsmUtil.shortValue(mv);
				}
				mv.visitInsn(IRETURN);
				break;

			case 'I':
				if (isLast == true) {
					mv.visitInsn(DUP);
					Label label = new Label();
					mv.visitJumpInsn(IFNONNULL, label);
						mv.visitInsn(POP);
						mv.visitInsn(ICONST_0);
						mv.visitInsn(IRETURN);
					mv.visitLabel(label);

					AsmUtil.intValue(mv);
				}
				mv.visitInsn(IRETURN);
				break;

			case 'Z':
				if (isLast == true) {
					mv.visitInsn(DUP);
					Label label = new Label();
					mv.visitJumpInsn(IFNONNULL, label);
						mv.visitInsn(POP);
						mv.visitInsn(ICONST_0);
						mv.visitInsn(IRETURN);
					mv.visitLabel(label);

					AsmUtil.booleanValue(mv);
				}
				mv.visitInsn(IRETURN);
				break;

			case 'J':
				if (isLast == true) {
					mv.visitInsn(DUP);
					Label label = new Label();
					mv.visitJumpInsn(IFNONNULL, label);
						mv.visitInsn(POP);
						mv.visitInsn(LCONST_0);
						mv.visitInsn(LRETURN);
					mv.visitLabel(label);

					AsmUtil.longValue(mv);
				}
				mv.visitInsn(LRETURN);
				break;

			case 'F':
				if (isLast == true) {
					mv.visitInsn(DUP);
					Label label = new Label();
					mv.visitJumpInsn(IFNONNULL, label);
						mv.visitInsn(POP);
						mv.visitInsn(FCONST_0);
						mv.visitInsn(FRETURN);
					mv.visitLabel(label);

					AsmUtil.floatValue(mv);
				}
				mv.visitInsn(FRETURN);
				break;

			case 'D':
				if (isLast == true) {
					mv.visitInsn(DUP);
					Label label = new Label();
					mv.visitJumpInsn(IFNONNULL, label);
						mv.visitInsn(POP);
						mv.visitInsn(DCONST_0);
						mv.visitInsn(DRETURN);
					mv.visitLabel(label);

					AsmUtil.doubleValue(mv);
				}
				mv.visitInsn(DRETURN);
				break;

			default:
				mv.visitInsn(ARETURN);
				break;
		}
	}

	/**
	 * Prepares return value.
	 */
	public static void prepareReturnValue(MethodVisitor mv, MethodSignatureVisitor msign, int varOffset) {
		varOffset += msign.getAllArgumentsSize();
		switch (msign.getReturnOpcodeType()) {
			case 'V':
				mv.visitInsn(ACONST_NULL);
				break;
			case 'B':
				AsmUtil.valueOfByte(mv);
				break;
			case 'C':
				AsmUtil.valueOfCharacter(mv);
				break;
			case 'S':
				AsmUtil.valueOfShort(mv);
				break;
			case 'I':
				AsmUtil.valueOfInteger(mv);
				break;
			case 'Z':
				AsmUtil.valueOfBoolean(mv);
				break;
			case 'J':
				AsmUtil.valueOfLong(mv);
				break;
			case 'F':
				AsmUtil.valueOfFloat(mv);
				break;
			case 'D':
				AsmUtil.valueOfDouble(mv);
				break;

		}
	}

	public static void castToReturnType(MethodVisitor mv, MethodSignatureVisitor msign) {
		final String returnType;

		char returnOpcodeType = msign.getReturnOpcodeType();

		switch (returnOpcodeType) {
			case 'I':
				returnType = AsmUtil.SIGNATURE_JAVA_LANG_INTEGER;
				break;
			case 'J':
				returnType = AsmUtil.SIGNATURE_JAVA_LANG_LONG;
				break;
			case 'S':
				returnType = AsmUtil.SIGNATURE_JAVA_LANG_SHORT;
				break;
			case 'B':
				returnType = AsmUtil.SIGNATURE_JAVA_LANG_BYTE;
				break;
			case 'Z':
				returnType = AsmUtil.SIGNATURE_JAVA_LANG_BOOLEAN;
				break;
			case 'F':
				returnType = AsmUtil.SIGNATURE_JAVA_LANG_FLOAT;
				break;
			case 'D':
				returnType = AsmUtil.SIGNATURE_JAVA_LANG_DOUBLE;
				break;
			case 'C':
				returnType = AsmUtil.SIGNATURE_JAVA_LANG_CHARACTER;
				break;
			case '[':
				returnType = msign.getReturnTypeName();
				break;
			default:
				returnType = msign.getReturnType().replace('.', '/');
				break;
		}

		mv.visitTypeInsn(CHECKCAST, returnType);
	}

	// ---------------------------------------------------------------- method signature


	/**
	 * Creates unique key for method signatures map.
	 */
	public static String createMethodSignaturesKey(int access, String methodName, String description, String className) {
		return new StringBand(7)
			.append(access)
			.append(COLON)
			.append(description)
			.append(StringPool.UNDERSCORE)
			.append(className)
			.append(StringPool.HASH)
			.append(methodName)
			.toString();
	}


	// ---------------------------------------------------------------- detect advice macros

	public static boolean isInvokeMethod(String name, String desc) {
		if (name.equals("invoke")) {
			if (desc.equals("()Ljava/lang/Object;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isArgumentsCountMethod(String name, String desc) {
		if (name.equals("argumentsCount")) {
			if (desc.equals("()I")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isArgumentTypeMethod(String name, String desc) {
		if (name.equals("argumentType")) {
			if (desc.equals("(I)Ljava/lang/Class;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isArgumentMethod(String name, String desc) {
		if (name.equals("argument")) {
			if (desc.equals("(I)Ljava/lang/Object;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSetArgumentMethod(String name, String desc) {
		if (name.equals("setArgument")) {
			if (desc.equals("(Ljava/lang/Object;I)V")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isCreateArgumentsArrayMethod(String name, String desc) {
		if (name.equals("createArgumentsArray")) {
			if (desc.equals("()[Ljava/lang/Object;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isCreateArgumentsClassArrayMethod(String name, String desc) {
		if (name.equals("createArgumentsClassArray")) {
			if (desc.equals("()[Ljava/lang/Class;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isReturnTypeMethod(String name, String desc) {
		if (name.equals("returnType")) {
			if (desc.equals("()Ljava/lang/Class;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTargetMethod(String name, String desc) {
		if (name.equals("target")) {
			if (desc.equals("()Ljava/lang/Object;")) {
				return true;
			}
		}
		return false;
	}


	public static boolean isTargetClassMethod(String name, String desc) {
		if (name.equals("targetClass")) {
			if (desc.equals("()Ljava/lang/Class;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTargetMethodNameMethod(String name, String desc) {
		if (name.equals("targetMethodName")) {
			if (desc.equals("()Ljava/lang/String;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTargetMethodSignatureMethod(String name, String desc) {
		if (name.equals("targetMethodSignature")) {
			if (desc.equals("()Ljava/lang/String;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTargetMethodDescriptionMethod(String name, String desc) {
		if (name.equals("targetMethodDescription")) {
			if (desc.equals("()Ljava/lang/String;")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isReturnValueMethod(String name, String desc) {
		if (name.equals("returnValue")) {
			if (desc.equals("(Ljava/lang/Object;)Ljava/lang/Object;")) {
				return true;
			}
		}
		return false;
	}

}
