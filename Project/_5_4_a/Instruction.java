package _5_4_a;

public class Instruction {
	OpCode operation;
	Integer argument;

	//COSTRUCTOR
	public Instruction(OpCode operation) { this(operation, null); }
	public Instruction(OpCode operation, int argument) { this(operation, (Integer)argument); }
	public Instruction(OpCode operation, Integer argument){
		operation.checkValue(argument);

		this.operation = operation;
		this.argument = argument;
	}

	public String toJasmin () {
		String prefix = "\t", postfix = "\n";

		if(this.operation == OpCode.label){
			prefix = "";
		}

		return prefix + operation.toJasmin(argument) + postfix;
	}
    }
