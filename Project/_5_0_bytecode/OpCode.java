package _5_0_bytecode;

public enum OpCode {
	ldc, imul, ineg, idiv, iadd,
	isub, istore, ior, iand, iload,
	if_icmpeq, if_icmple, if_icmplt, if_icmpne, if_icmpge,
	if_icmpgt, ifne, GOto, invokestatic, label,
	dup; //added by me

	public static OpCode getCodeFromRelop(String strRelop){

		switch(strRelop){
			case "<":  return if_icmplt;
			case "<=": return if_icmple;
			case "==": return if_icmpeq;
			case "<>": return if_icmpne;
			case ">=": return if_icmpge;
			case ">":  return if_icmpgt;
			default:   return null;
		}
	}

	public OpCode getOpposite(){
		switch(this){
			case if_icmpne: return if_icmpeq;
			case if_icmpeq: return if_icmpne;

			case if_icmpge:  return if_icmplt;
			case if_icmplt: return if_icmpge;

			case if_icmpgt: return if_icmple;
			case if_icmple:  return if_icmpgt;

			default:   return null;
		}
	}
}

