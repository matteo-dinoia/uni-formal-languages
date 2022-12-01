package _5_5_z;

import java.util.*;

import _5_4_a.Instruction;
import _5_4_a.OpCode;

import java.io.*;

public class CodeGenerator {
	private LinkedList <Instruction> instructions = new LinkedList <Instruction>();
	private int label=0;

	//CODE EMISSIONS
	public void emit(OpCode opCode) {
		instructions.add(new Instruction(opCode));
	}
	public void emit(OpCode opCode , Integer operand) {
		instructions.add(new Instruction(opCode, operand));
	}

	//LABEL
	public void emitLabel(int operand) {
		emit(OpCode.label, operand);
	}
	public int newLabel() {
		return label++;
	}

	//VARIABLES
	int countVariable = 0;
	Map <String, Integer> OffsetMap = new HashMap <String,Integer>();
	public int getVariableOrCreate(String name){
		Integer id_addr = getExistingVariable(name);
		if (id_addr != null)
			return id_addr;


		initializeVariable(name, countVariable);
		return countVariable++;
	}
	private void initializeVariable(String name, int address) {
		if(OffsetMap.containsValue(address))
			throw new Error("Error:reference to a memory location already occupied by another variable");

		OffsetMap.put(name, address);
	}
	public Integer getExistingVariable (String name) {
		if(OffsetMap.containsKey(name))
			return OffsetMap.get(name);

		return null;
	}


	//PARSING
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
