package parserold;

import java.io.*;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class ParserOld {
	private Lexer lex;
	private BufferedReader pbr;
	private Token look;
	public boolean silentParser=false;

	public ParserOld(Lexer l, BufferedReader br) {
		this(l, br, false);
	}

	public ParserOld(Lexer l, BufferedReader br, boolean silentParser) {
		this.silentParser=silentParser;

		lex = l;
		pbr = br;
		move(); // read first term

	}

	void move() { /* lex a term */
		look = lex.lexical_scan(pbr);
		if(!silentParser)
			System.out.println("token = " + look);
	}

	void error(String s) { /* print error messange */
		throw new Error("near line " + lex.line + ": " + s);
	}

	void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF)
				move();
		} else
			error("syntax error");
	}

	/* VARIABLE */
	public void start() {
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				/*REAL*/
				expr();
				match(Tag.EOF);
				break;
			default:
				error("wrong caracter: "+look+" in start with guide {(, NUM}");
		}
	}

	private void expr() {
		switch (look.tag){
			case '(':
			case Tag.NUM:
				/*REAL*/
				term();
				exprp();
				break;
			default:
				error("wrong caracter: "+look+" in expr with guide {(, NUM}");
		}
	}

	private void exprp() {
		switch (look.tag) {
			case '+':
				match('+');
				term();
				exprp();
				break;
			case '-':
				match('-');
				term();
				exprp();
				break;
			case ')':
			case Tag.EOF:
				break;
			default:
				error("wrong caracter: "+look+" in exprp with guide {+,-,9,EOF}");
		}
	}

	private void term() {
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				/*REAL*/
				fact();
				termp();
				break;
			default:
				error("wrong caracter: "+look+" in term with guide {(, NUM}");
		}
	}

	private void termp() {
		switch (look.tag) {
			case '*':
				match('*');
				fact();
				termp();
				break;
			case '/':
				match('/');
				fact();
				termp();
				break;
			case ')':
			case Tag.EOF:
			case '+':
			case '-':
				break;
			default:
				error("wrong caracter: "+look+" in termp with guide {*, /, ), EOF}");
		}
	}

	private void fact() {
		switch (look.tag) {
			case '(':
				match('(');
				expr();
				match(')');
				break;
			case Tag.NUM:
				match(Tag.NUM);
				break;
			default:
				error("wrong caracter: "+look+" in fact with guide {(, NUM}");
		}
	}

	public static void main(String args[]){
		Lexer lex = new Lexer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader("Project/lexer/input-parser.txt"));
			ParserOld parser = new ParserOld(lex, br);
			parser.start();
			System.out.println("Input OK");
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}