package _3_2_parser;

import java.io.*;

import _2_lexer.*;

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

	private void move() { /* lex a term */
		look = lex.lexical_scan(pbr);
		if(!silentParser)
			System.out.println("token = " + look);
	}

	private Error error(String s) { /* print error messange */
		return new Error("near line " + lex.line + ", at symbol "+look.tag+ ": " + s);
	}

	private void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF)
				move();
		} else
			throw error("syntax error");
	}

	//VARIABLE
	public void prog(){
		switch(look.tag){
		case Tag.ASSIGN:
		case Tag.PRINT:
		case Tag.READ:
		case Tag.WHILE:
		case Tag.COND:
		case '{':
			statlist();
			match(Tag.EOF);
			break;
		default:
			throw error("in statlist");
		}
	}

	private void statlist(){
		switch(look.tag){
		case Tag.ASSIGN:
		case Tag.PRINT:
		case Tag.READ:
		case Tag.WHILE:
		case Tag.COND:
		case '}':
			stat();
			statlistp();
			break;
		default:
			throw error("in statlist");
		}
	}

	private void statlistp() {
		switch(look.tag){
		case ';':
			match(';');
			stat();
			statlistp();
			break;
		case Tag.EOF:
		case '}':
			break;
		default:
			throw error("in statlistp");
		}
	}

	private void stat() {
		switch(look.tag){
		case Tag.ASSIGN:
			match(Tag.ASSIGN);
			expr();
			match(Tag.TO);
			idlist();
			break;
		case Tag.PRINT:
			match(Tag.PRINT);
			match('[');
			exprlist();
			match(']');
			break;
		case Tag.READ:
			match(Tag.READ);
			match('[');
			idlist();
			match(']');
			break;
		case Tag.WHILE:
			match(Tag.WHILE);
			match('(');
			bexpr();
			match(')');
			stat();
			break;
		case Tag.COND:
			match(Tag.COND);
			match('[');
			optlist();
			match(']');
			conditionalp();
			match(Tag.END);
			break;
		case '{':
			match('{');
			statlist();
			match('}');
			break;
		default:
			throw error("in stat");
		}
	}

	private void conditionalp(){
		switch(look.tag){
		case Tag.ELSE:
			match(Tag.ELSE);
			stat();
			break;
		case Tag.END:
			break;
		default:
			throw error("in conditionalp");
		}
	}

	private void idlist(){
		switch(look.tag){
		case Tag.ID:
			match(Tag.ID);
			idlistp();
			break;
		default:
			throw error("in idlist");
		}
	}

	private void idlistp(){
		switch(look.tag){
		case ',':
			match(',');
			match(Tag.ID);
			idlistp();
			break;
		case ']':
		case '}':
		case Tag.OPTION:
		case Tag.END:
		case Tag.EOF:
		case ';':
			break;
		default:
			throw error("in idlistp");
		}
	}

	private void optlist(){
		switch(look.tag){
		case Tag.OPTION:
			optitem();
			optlistp();
			break;
		default:
			throw error("in optlist");
		}
	}

	private void optlistp(){
		switch(look.tag){
		case Tag.OPTION:
			optitem();
			optlistp();
			break;
		case ']':
			break;
		default:
			throw error("in optlistp");
		}
	}

	private void optitem(){
		switch(look.tag){
		case Tag.OPTION:
			match(Tag.OPTION);
			match('(');
			bexpr();
			match(')');
			match(Tag.DO);
			stat();
			break;
		default:
			throw error("in optitem");
		}
	}

	private void bexpr(){
		switch(look.tag){
		case Tag.RELOP:
			match(Tag.RELOP);
			expr();
			expr();
			break;
		default:
			throw error("in bexpr");
		}
	}

	private void expr(){
		switch(look.tag){
		case '+':
			match('+');
			match('(');
			exprlist();
			match(')');
			break;
		case '*':
			match('*');
			match('(');
			exprlist();
			match(')');
			break;
		case '-':
			match('-');
			expr();
			expr();
			break;
		case '/':
			match('/');
			expr();
			expr();
			break;
		case Tag.NUM:
			match(Tag.NUM);
			break;
		case Tag.ID:
			match(Tag.ID);
			break;
		default:
			throw error("in expr");
		}
	}

	private void exprlist(){
		switch(look.tag){
		case '+':
		case '-':
		case '*':
		case '/':
		case Tag.NUM:
		case Tag.ID:
			expr();
			exprlistp();
			break;
		default:
			throw error("in exprlist");
		}
	}

	private void exprlistp(){
		switch(look.tag){
		case ',':
			match(',');
			expr();
			exprlistp();
			break;
		case ']':
		case ')':
			break;
		default:
			throw error("in exprlistp");
		}
	}
	public static void main(String args[]){
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader("Project/_3_2_parser/input.txt"));

			Parser parser = new Parser(new Lexer(), br);
			parser.prog();

			System.out.println("Input OK  (parsed correctly)");
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br==null)
				return;
			try {
				br.close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
}
