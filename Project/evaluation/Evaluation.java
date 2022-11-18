package evaluation;

import java.io.*;
import lexer.Lexer;
import lexer.NumberTok;
import lexer.Tag;
import lexer.Token;

public class Evaluation {
	private Lexer lex;
	private BufferedReader pbr;
	private Token look;
	public boolean silentParser=false;

	/* BASE FUNCTIONS */
	public Evaluation(Lexer l, BufferedReader br) {
		this(l, br, false);
	}

	public Evaluation(Lexer l, BufferedReader br, boolean silentParser) {
		this.silentParser=silentParser;

		this.lex = l;
		this.pbr = br;
		this.move(); // read first term

	}

	void move() { /* lex a term */
		look = lex.lexical_scan(pbr);
		if(!silentParser)
			System.out.println("token = " + look);
	}

	private Error error(String s) { /* print error messange */
		return new Error("near line " + lex.line + ": " + s);
	}

	void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF)
				move();
		} else
			error("syntax error");
	}


	/* VARIABLES */
	public void start() {
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				/*REAL*/
				int val=expr();
				match(Tag.EOF);
				System.out.println("Result:"+val);
				break;
			default:
				throw error("wrong caracter: "+look+" in start with guide {(, NUM}");
		}
	}

	private int expr() {
		switch (look.tag){
			case '(':
			case Tag.NUM:
				/*REAL*/
				int termVal=term();
				int val=exprp(termVal);
				return val;
			default:
				throw error("wrong caracter: "+look+" in expr with guide {(, NUM}");
		}
	}

	private int exprp(int i) {
		int termVal, val;
		switch (look.tag) {
			case '+':
				match('+');
				termVal=term();
				val=exprp(i+termVal);
				return val;
			case '-':
				match('-');
				termVal=term();
				val=exprp(i-termVal);
				return val;
			case ')':
			case Tag.EOF:
				return i;
			default:
				throw error("wrong caracter: "+look+" in exprp with guide {+,-,9,EOF}");
		}
	}

	private int term() {
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				/*REAL*/
				int factVal=fact();
				int val=termp(factVal);
				return val;
			default:
				throw error("wrong caracter: "+look+" in term with guide {(, NUM}");
		}
	}

	private int termp(int i) {
		int factVal, val;
		switch (look.tag) {
			case '*':
				match('*');
				factVal=fact();
				val=termp(i*factVal);
				return val;
			case '/':
				match('/');
				factVal=fact();
				val=termp(i/factVal);
				return val;
			case ')':
			case Tag.EOF:
			case '+':
			case '-':
				return i;
			default:
				throw error("wrong caracter: "+look+" in termp with guide {*, /, ), EOF}");
		}
	}

	private int fact() {
		switch (look.tag) {
			case '(':
				match('(');
				int exprVal=expr();
				match(')');
				return exprVal;
			case Tag.NUM:
				int numValue=((NumberTok)look).getValue();
				match(Tag.NUM);
				return numValue;
			default:
				throw error("wrong caracter: "+look+" in fact with guide {(, NUM}");
		}
	}

	/* MAIN */
	public static void main(String args[]){
		Lexer lex = new Lexer();
		BufferedReader br=null;
		try {
			//FILE READING
			final String relativePath="input.txt";
			final String absolutePath="Project/evaluation/"+relativePath;
			br = new BufferedReader(new FileReader(absolutePath));

			//EXECUTION
			Evaluation eval = new Evaluation(lex, br, true);
			try{
				eval.start();
			}
			catch(Error e){
				System.out.println("Linea \""+"\" non riconoscituta:\n\t"+e.getMessage());
			}
		} catch (Exception e) {e.printStackTrace();
		}finally{
			if(br==null) return;
			try {
				br.close();
			} catch (IOException e) {}
		}
	}
}