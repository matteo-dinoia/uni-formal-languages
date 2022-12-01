package _5_4_z;

import _5_4_a.*;
import _5_4_test.*;

public abstract class Translator {
	private Lexer lex;
	private Token look;

	CodeGenerator code = new CodeGenerator();
	int count=0;

	//CONSTRUCTOR AND CLOSING
	public Translator(Lexer lex) {
		this.lex = lex;
		move();
	}
	public void close(){ lex.close(); }

	protected void move() { look = lex.getNextToken(); }
	protected Token look(){ return look; }
	public Error error(String s) {
		throw new Error("near line " + lex.getLine() + ": " + s);
	}
	protected void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF) move();
		} else
			throw error("syntax error");
	}
	public void parse(){
		prog();

		//PRINT FILES
		try { code.toJasmin(); }
		catch(java.io.IOException e) {
			System.out.println("IO error\n");
		}
	}

	protected abstract void prog();
}
