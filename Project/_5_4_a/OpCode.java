package _5_4_a;

public enum OpCode {
	ldc(true, "ldc "),
	invokestatic(true, "invokestatic "),
	istore(true, "istore "),
	iload(true, "iload "),

	ifne(true, "ifne L"),
	if_icmpeq(true, "if_icmpeq L"),
	if_icmple(true, "if_icmple L"),
	if_icmplt(true, "if_icmplt L"),
	if_icmpne(true, "if_icmpne L"),
	if_icmpge(true, "if_icmpge L"),
	if_icmpgt(true, "if_icmpgt L"),
	GOto(true, "goto L"),
	label(true, "L", ":"),

	ior(false, "ior"),
	iand(false, "iand"),
	iadd(false, "iadd"),
	imul(false, "imul"),
	idiv(false, "idiv"),
	isub(false, "isub"),
	ineg(false, "ineg"),
	dup(false, "dup");

	private String prefix, postfix;
	private final boolean needArg;
	private OpCode(boolean needArg, String command){
		this(needArg, command, "");
	}
	private OpCode(boolean needArg, String prefix, String postfix){
		this.needArg = needArg;
		this.prefix = prefix;
		this.postfix = postfix;
	}

	public static final int READ = 0, WRITE = 1;
	private String argInvokestatic(int arg) {
		switch(arg){
			case READ:
				return "Output/read()I";
			case WRITE:
				return "Output/print(I)V";
			default:
				throw new Error("Error: not valid name of methods");
		}
	}
	public void checkValue(Integer arg){
		if( (arg != null ? 1 : 0) != (needArg ? 1 : 0)){
			throw new Error("Error: mismatch in "+ this.prefix + " of argument number ("
					+ (arg != null ? "1" : "0") + "["+arg+"] instead of " + (needArg ? "1" : "0") + ")");
		}
	}
	public String toJasmin(Integer arg){
		this.checkValue(arg);

		String strArg;
		if(arg == null) strArg = "";
		else if(this == invokestatic) strArg = argInvokestatic(arg);
		else strArg = "" + arg;

		return prefix + strArg + postfix;
	}


	//PARSING FROM LEXER
	public static OpCode getCodeFromRelop(int tag){
		switch(tag){
		case Tag.LT:  return if_icmplt;
		case Tag.LE: return if_icmple;
		case Tag.EQ: return if_icmpeq;
		case Tag.NE: return if_icmpne;
		case Tag.GE: return if_icmpge;
		case Tag.GT:  return if_icmpgt;
		default:   return null;
		}
	}
}

