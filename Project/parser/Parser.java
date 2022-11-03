package parser;

import java.io.BufferedReader;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class Parser {
	private Lexer lex;
	private BufferedReader pbr;
	private Token look;
	public boolean silentParser=false;

	public Parser(Lexer l, BufferedReader br) {
		this(l, br, false);
	}

	public Parser(Lexer l, BufferedReader br, boolean silentParser) {
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


}