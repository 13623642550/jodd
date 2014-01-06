// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.asm;

import jodd.util.StringUtil;
import jodd.asm4.Opcodes;
import jodd.asm4.signature.SignatureVisitor;

/**
 * A {@link SignatureVisitor} that prints a disassembled view of the signature
 * it visits.

 * Changes made by igor (jodd):
 * <ul>
 * <li>all private scopes made protected</li>
 * <li>getExceptionsArray added</li>
 * <li>constructor commented</li>
 * </ul>
 *
 * @author Eugene Kuleshov
 * @author Eric Bruneton
 * @author Igor Spasic (jodd)
 */
public class TraceSignatureVisitor extends SignatureVisitor {

	protected final StringBuffer declaration;       // jodd

    protected boolean isInterface;                  // jodd

    protected boolean seenFormalParameter;          // jodd

    protected boolean seenInterfaceBound;           // jodd

    protected boolean seenParameter;                // jodd

    protected boolean seenInterface;                // jodd

    protected StringBuffer returnType;              // jodd

    protected StringBuffer exceptions;              // jodd

    /**
     * Stack used to keep track of class types that have arguments. Each element
     * of this stack is a boolean encoded in one bit. The top of the stack is
     * the lowest order bit. Pushing false = *2, pushing true = *2+1, popping =
     * /2.
     */
    protected int argumentStack;                    // jodd

    /**
     * Stack used to keep track of array class types. Each element of this stack
     * is a boolean encoded in one bit. The top of the stack is the lowest order
     * bit. Pushing false = *2, pushing true = *2+1, popping = /2.
     */
    protected int arrayStack;                       // jodd

    protected String separator = "";                // jodd

/*
    public TraceSignatureVisitor(final int access) {	// jodd
		super(Opcodes.ASM4);
        isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
        this.declaration = new StringBuffer();
    }
*/

    protected TraceSignatureVisitor(final StringBuffer buf) {       // jodd
		super(Opcodes.ASM4);
        this.declaration = buf;
    }

    public void visitFormalTypeParameter(final String name) {
        declaration.append(seenFormalParameter ? ", " : "<").append(name);
        seenFormalParameter = true;
        seenInterfaceBound = false;
    }

    public SignatureVisitor visitClassBound() {
        separator = " extends ";
        startType();
        return this;
    }

    public SignatureVisitor visitInterfaceBound() {
        separator = seenInterfaceBound ? ", " : " extends ";
        seenInterfaceBound = true;
        startType();
        return this;
    }

    public SignatureVisitor visitSuperclass() {
        endFormals();
        separator = " extends ";
        startType();
        return this;
    }

    public SignatureVisitor visitInterface() {
        separator = seenInterface ? ", " : isInterface
                ? " extends "
                : " implements ";
        seenInterface = true;
        startType();
        return this;
    }

    public SignatureVisitor visitParameterType() {
        endFormals();
        if (seenParameter) {
            declaration.append(", ");
        } else {
            seenParameter = true;
            declaration.append('(');
        }
        startType();
        return this;
    }

    public SignatureVisitor visitReturnType() {
        endFormals();
        if (seenParameter) {
            seenParameter = false;
        } else {
            declaration.append('(');
        }
        declaration.append(')');
        returnType = new StringBuffer();
        return new TraceSignatureVisitor(returnType);
    }

    public SignatureVisitor visitExceptionType() {
        if (exceptions == null) {
            exceptions = new StringBuffer();
        } else {
            exceptions.append(", ");
        }
        // startType();
        return new TraceSignatureVisitor(exceptions);
    }

    public void visitBaseType(final char descriptor) {
        switch (descriptor) {
            case 'V':
                declaration.append("void");
                break;
            case 'B':
                declaration.append("byte");
                break;
            case 'J':
                declaration.append("long");
                break;
            case 'Z':
                declaration.append("boolean");
                break;
            case 'I':
                declaration.append("int");
                break;
            case 'S':
                declaration.append("short");
                break;
            case 'C':
                declaration.append("char");
                break;
            case 'F':
                declaration.append("float");
                break;
            // case 'D':
            default:
                declaration.append("double");
                break;
        }
        endType();
    }

    public void visitTypeVariable(final String name) {
        declaration.append(name);
        endType();
    }

    public SignatureVisitor visitArrayType() {
        startType();
        arrayStack |= 1;
        return this;
    }

    public void visitClassType(final String name) {
        if (AsmUtil.SIGNATURE_JAVA_LANG_OBJECT.equals(name)) {
            // Map<java.lang.Object,java.util.List>
            // or
            // abstract public V get(Object key); (seen in Dictionary.class)
            // should have Object
            // but java.lang.String extends java.lang.Object is unnecessary
            boolean needObjectClass = argumentStack % 2 != 0 || seenParameter;
            if (needObjectClass) {
                declaration.append(separator).append(name.replace('/', '.'));
            }
        } else {
            declaration.append(separator).append(name.replace('/', '.'));
        }
        separator = "";
        argumentStack *= 2;
    }

    public void visitInnerClassType(final String name) {
        if (argumentStack % 2 != 0) {
            declaration.append('>');
        }
        argumentStack /= 2;
        declaration.append('.');
        declaration.append(separator).append(name.replace('/', '.'));
        separator = "";
        argumentStack *= 2;
    }

    public void visitTypeArgument() {
        if (argumentStack % 2 == 0) {
            ++argumentStack;
            declaration.append('<');
        } else {
            declaration.append(", ");
        }
        declaration.append('?');
    }

    public SignatureVisitor visitTypeArgument(final char tag) {
        if (argumentStack % 2 == 0) {
            ++argumentStack;
            declaration.append('<');
        } else {
            declaration.append(", ");
        }

        if (tag == EXTENDS) {
            declaration.append("? extends ");
        } else if (tag == SUPER) {
            declaration.append("? super ");
        }

        startType();
        return this;
    }

    public void visitEnd() {
        if (argumentStack % 2 != 0) {
            declaration.append('>');
        }
        argumentStack /= 2;
        endType();
    }

    public String getDeclaration() {
        return declaration.toString();
    }

    public String getReturnType() {
        return returnType == null ? null : returnType.toString();
    }

    public String getExceptions() {
        return exceptions == null ? null : exceptions.toString();
    }

	public String[] getExceptionsArray() {		 // jodd
		if (exceptions == null) {
			return null;
		}
		String[] result = StringUtil.splitc(exceptions.toString(), ',');

		StringUtil.trimAll(result);

		return result;
	}

    // -----------------------------------------------

    private void endFormals() {
        if (seenFormalParameter) {
            declaration.append('>');
            seenFormalParameter = false;
        }
    }

    private void startType() {
        arrayStack *= 2;
    }

    private void endType() {
        if (arrayStack % 2 == 0) {
            arrayStack /= 2;
        } else {
            while (arrayStack % 2 != 0) {
                arrayStack /= 2;
                declaration.append("[]");
            }
        }
    }
}
