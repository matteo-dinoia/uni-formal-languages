package _5_1_bytecode_generator;

public enum OpCode {
	ldc, imul, ineg, idiv, iadd,
	isub, istore, ior, iand, iload,
	if_icmpeq, if_icmple, if_icmplt, if_icmpne, if_icmpge,
	if_icmpgt, ifne, GOto, invokestatic, label,
	dup, pop //added by me
}

