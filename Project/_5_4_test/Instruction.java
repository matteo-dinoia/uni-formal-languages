package _5_4_;

public class Instruction {
	OpCode opCode;
	int operand;

	public Instruction(OpCode opCode) {
		this.opCode = opCode;
	}

	public Instruction(OpCode opCode, int operand) {
		this.opCode = opCode;
		this.operand = operand;
	}

	public String toJasmin () {
		String temp="";
		switch (opCode) {
			case ldc : temp = "\tldc " + operand + "\n"; break;
			case invokestatic :
			if( operand == 1)
			temp = "\tinvokestatic " + "Output/print(I)V" + "\n";
			else
			temp = "\tinvokestatic " + "Output/read()I" + "\n"; break;
			case iadd : temp = "\tiadd " + "\n"; break;
			case imul : temp = "\timul " + "\n"; break;
			case idiv : temp = "\tidiv " + "\n"; break;
			case isub : temp = "\tisub " + "\n"; break;
			case ineg : temp = "\tineg " + "\n"; break;
			case istore : temp = "\tistore " + operand + "\n"; break;
			case ior : temp = "\tior " + "\n"; break;
			case iand : temp = "\tiand " + "\n"; break;
			case iload : temp = "\tiload " + operand + "\n"; break;
			case if_icmpeq : temp = "\tif_icmpeq L" + operand + "\n"; break;
			case if_icmple : temp = "\tif_icmple L" + operand + "\n"; break;
			case if_icmplt : temp = "\tif_icmplt L" + operand + "\n"; break;
			case if_icmpne : temp = "\tif_icmpne L" + operand + "\n"; break;
			case if_icmpge : temp = "\tif_icmpge L" + operand + "\n"; break;
			case if_icmpgt : temp = "\tif_icmpgt L" + operand + "\n"; break;
			case ifne : temp = "\tifne L" + operand + "\n"; break;
			case GOto : temp = "\tgoto L" + operand + "\n" ; break;
			case label : temp = "  L" + operand + ":\n"; break;
			//mine
			case dup: temp = "\tdup"+"\n"; break;
		}
		return temp;
	}
    }
