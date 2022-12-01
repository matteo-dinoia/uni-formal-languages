package _5_5_test;

import java.io.*;
import _5_4_a.Token;

public abstract class Lexer {
	public static final char EOF = (char) -1;
	private BufferedReader bufferedReader=null;

	//GETTER AND SETTER
	private int line = 1;
	public int getLine(){
		return line;
	}
	protected void increaseLine(){
		line++;
	}

	private char oldPeek=' ', peek = ' ';
	protected char readChar() {
		if(reverted){
			reverted = false;
			//doesn't change
		}
		else {
			oldPeek = peek;
			try {
				peek = (char) bufferedReader.read();
			} catch (IOException exc) {
				peek = EOF; // ERROR
			}
		}

		return actualChar();
	}
	protected char actualChar(){
		return peek;
	}
	protected char olderChar(){
		return oldPeek;
	}

	private boolean reverted=false;
	protected void revertLastChar(){
		reverted = true;
	}

	//CONSTRUCTOR AND CLOSING
	public Lexer(String pathName){
		try{
			this.bufferedReader = new BufferedReader(new FileReader(pathName));
		}catch(IOException er){
			throw new Error("Coudn't open the file");
		}
	}
	public void close(){
		try{
			bufferedReader.close();
		}catch(IOException | NullPointerException er){
			throw new Error("Couldn't close the file");
		}
	}

	//ERRORS
	protected Error lexError(String strError){
		return new Error("ERROR ('" + oldPeek + "' followed by '" + peek + "'): " + strError);
	}

	//TO IMPLEMENTS
	public Token getNextToken(){
		Token res;
		while ((res = lexicalScan()) == null);

		System.out.println("Token read = " + res);
		return res;
	}
	protected abstract Token lexicalScan();
}