package _5_bytecode_generator;

import java.util.LinkedList;
import java.io.*;

public class CodeGenerator {

	LinkedList <Instruction> instructions = new LinkedList <Instruction>();

	int label=0;

	public void emit(OpCode opCode) {
		instructions.add(new Instruction(opCode));
	}

	public void emit(OpCode opCode , int operand) {
		instructions.add(new Instruction(opCode, operand));
	}

	public void emitLabel(int operand) {
		emit(OpCode.label, operand);
	}

	public int newLabel() {
		return label++;
	}

	public void toJasmin() throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter("Output.j"));
		String temp = "";
		temp = temp + header;
		while(instructions.size() > 0){
			Instruction tmp = instructions.remove();
			temp = temp + tmp.toJasmin();
		}
		temp = temp + footer;
		out.println(temp);
		out.flush();
		out.close();
	}

	private static final String header = ".class public Output \n"
		+ ".super java/lang/Object\n"
		+ "\n"
		+ ".method public <init>()V\n"
		+ "\taload_0\n"
		+ "\tinvokenonvirtual java/lang/Object/<init>()V\n"
		+ "\treturn\n"
		+ ".end method\n"
		+ "\n"
		+ ".method public static print(I)V\n"
		+ "\t.limit stack 2\n"
		+ "\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n"
		+ "\tiload_0 \n"
		+ "\tinvokestatic java/lang/Integer/toString(I)Ljava/lang/String;\n"
		+ "\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n"
		+ "\treturn\n"
		+ ".end method\n"
		+ "\n"
		+ ".method public static read()I\n"
		+ "\t.limit stack 3\n"
		+ "\tnew java/util/Scanner\n"
		+ "\tdup\n"
		+ "\tgetstatic java/lang/System/in Ljava/io/InputStream;\n"
		+ "\tinvokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V\n"
		+ "\tinvokevirtual java/util/Scanner/next()Ljava/lang/String;\n"
		+ "\tinvokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I\n"
		+ "\tireturn\n"
		+ ".end method\n"
		+ "\n\n\n"
		+ ".method public static run()V\n"
		+ "\t.limit stack 1024\n"
		+ "\t.limit locals 256\n";

	private static final String footer = "\treturn\n"
		+ ".end method"
		+ "\n\n\n"
		+ ".method public static main([Ljava/lang/String;)V\n"
		+ "\tinvokestatic Output/run()V\n"
		+ "\treturn\n"
		+ ".end method\n";
}
