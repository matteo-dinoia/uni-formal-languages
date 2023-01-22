package _3_1_parser_math;

import java.io.*;

import _2_lexer.*;

public class Parser {
	private Lexer lex;
	private BufferedReader pbr;
	private Token look;

	public Parser(Lexer l, BufferedReader br) {
		lex = l;
		pbr = br;
		move(); // read first term
	}

	private void move() { // lex a term
		look = lex.lexical_scan(pbr);
		System.out.println("token = " + look);
	}

	private Error error(String s) { // return new error
		return new Error("near line " + lex.line + ": " + s);
	}

	private void match(int t) {
		if (look.tag == t){
			if (look.tag != Tag.EOF) move();
		} else throw error("syntax error");
	}

	// VARIABLES
	public void start() {
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				expr();
				match(Tag.EOF);
				break;
			default: throw error("found '"+look+"' in start with guide {(, NUM}");
		}
	}

	private void expr() {
		switch (look.tag){
			case '(':
			case Tag.NUM:
				term();
				exprp();
				break;
			default: throw error("found '"+look+"' in expr with guide {(, NUM}");
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
			default: throw error("found '"+look+"' in exprp with guide {+,-,9,EOF}");
		}
	}

	private void term() {
		switch (look.tag) {
			case '(':
			case Tag.NUM:
				fact();
				termp();
				break;
			default: throw error("found '"+look+"' in term with guide {(, NUM}");
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
			default: throw error("found '"+look+"' in termp with guide {*, /, ), EOF}");
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
			default: throw error("found '"+look+"' in fact with guide {(, NUM}");
		}
	}

	public static void main(String args[]) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("_3_1_parser_math/input.txt"));
		Parser parser = new Parser(new Lexer(), br);

		parser.start();
		System.out.println("Input OK (parsed correctly)");

		if(br!=null) br.close();
	}
}