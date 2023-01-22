package _5_1_translator_generator;

import _2_lexer.*;
import _5_0_bytecode.*;

import java.io.*;

public class Translator {
	private Lexer lex;
	private BufferedReader pbr;
	private Token look;

	SymbolTable st = new SymbolTable();
	CodeGenerator code = new CodeGenerator();
	int count=0;

	//MAIN METHODS
	public Translator(Lexer l, BufferedReader br) {
		lex = l;
		pbr = br;
		move();
	}

	void move() {
		look = lex.lexical_scan(pbr);
		System.out.println("token = " + look);
	}

	Error error(String s) {
		throw new Error("near line " + lex.line + ": " + s);
	}

	void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF) move();
		} else
			throw error("syntax error");
	}


	// DA PARSER
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

		//PRINT FILES
		try { code.toJasmin(); }
		catch(java.io.IOException e) {
			System.out.println("IO error\n");
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
		int lstart, lend, lcontinue;
		switch(look.tag){
		case Tag.ASSIGN:
			match(Tag.ASSIGN);
			expr();
			match(Tag.TO);
			idlist(OpCode.dup, -1);
			break;
		case Tag.PRINT:
			match(Tag.PRINT);
			match('[');
			exprlist(OpCode.invokestatic, 1);
			match(']');
			break;
		case Tag.READ:
			match(Tag.READ);
			match('[');
			idlist(OpCode.invokestatic, 0);
			match(']');
			break;
		case Tag.WHILE:
			lstart=code.newLabel(); lend=code.newLabel(); lcontinue=code.newLabel();
			match(Tag.WHILE);
			match('(');

			code.emitLabel(lstart);
			bexpr(lcontinue, lend);
			code.emitLabel(lcontinue);

			match(')');
			stat();
			code.emit(OpCode.GOto, lstart);
			code.emitLabel(lend);
			break;
		case Tag.COND:
			lend=code.newLabel(); //dopo else
			match(Tag.COND);

			match('[');
			optlist(lend);
			match(']');

			conditionalp();
			code.emitLabel(lend);
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

	private void idlist(OpCode opCode, int parameter){
		switch(look.tag){
		case Tag.ID:
			int id_addr = st.lookupAddress(((Word)look).lexeme);
			if (id_addr==-1) {
				id_addr = count;
				st.insert(((Word)look).lexeme,count++);
			}
			match(Tag.ID);

			if(opCode!=OpCode.dup || look.tag==',') //if assign and is last element
				code.emit(opCode, parameter);
			code.emit(OpCode.istore, id_addr);
			idlistp(opCode, parameter);
			break;
		default:
			throw error("in idlist");
		}
	}
	private void idlistp(OpCode opCode, int parameter){
		switch(look.tag){
		case ',':
			match(',');
			int id_addr = st.lookupAddress(((Word)look).lexeme);
			if (id_addr==-1) {
				id_addr = count;
				st.insert(((Word)look).lexeme,count++);
			}
			match(Tag.ID);

			if(opCode!=OpCode.dup || look.tag==',') //if assign and is last element
				code.emit(opCode, parameter);
			code.emit(OpCode.istore, id_addr);
			idlistp(opCode, parameter);
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

	private void optlist(int lafter_else){
		switch(look.tag){
		case Tag.OPTION:
			int next_optitem=code.newLabel();
			optitem(next_optitem, lafter_else);
			code.emitLabel(next_optitem);
			optlistp(lafter_else);
			break;
		default:
			throw error("in optlist");
		}
	}
	private void optlistp(int lafter_else){
		switch(look.tag){
		case Tag.OPTION:
			int next_optitem=code.newLabel();
			optitem(next_optitem, lafter_else);
			code.emitLabel(next_optitem);
			optlistp(lafter_else);
			break;
		case ']':
			break;
		default:
			throw error("in optlistp");
		}
	}

	private void optitem(int next, int lafter_else){
		switch(look.tag){
		case Tag.OPTION:
			match(Tag.OPTION);
			match('(');
			int lcontinue=code.newLabel();
			bexpr(lcontinue, next);
			code.emitLabel(lcontinue);
			match(')');

			match(Tag.DO);
			stat();
			code.emit(OpCode.GOto, lafter_else);
			break;
		default:
			throw error("in optitem");
		}
	}

	private void bexpr(int ltrue, int lfalse){
		switch(look.tag){
		case Tag.RELOP:
			String type=((Word)look).lexeme;
			match(Tag.RELOP);
			expr();
			expr();

			code.emit(OpCode.getCodeFromRelop(type), ltrue);
			code.emit(OpCode.GOto, lfalse);
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
			exprlist(OpCode.iadd, -1);
			match(')');
			break;
		case '*':
			match('*');
			match('(');
			exprlist(OpCode.imul, -1);
			match(')');
			break;
		case '-':
			match('-');
			expr();
			expr();
			code.emit(OpCode.isub);
			break;
		case '/':
			match('/');
			expr();
			expr();
			code.emit(OpCode.idiv);
			break;
		case Tag.NUM:
			code.emit(OpCode.ldc, ((NumberTok)look).getValue());
			match(Tag.NUM);
			break;
		case Tag.ID:
			code.emit(OpCode.iload, st.lookupAddress(((Word)look).lexeme));
			match(Tag.ID);

			break;
		default:
			throw error("in expr");
		}
	}

	private void exprlist(OpCode operationCode, int operand){
		switch(look.tag){
		case '+':
		case '-':
		case '*':
		case '/':
		case Tag.NUM:
		case Tag.ID:
			expr();
			if(operationCode==OpCode.invokestatic) // if is print
				code.emit(operationCode, operand);
			exprlistp(operationCode, operand);
			break;
		default:
			throw error("in exprlist");
		}
	}
	private void exprlistp(OpCode operationCode, int operand){
		switch(look.tag){
		case ',':
			match(',');
			expr();
			code.emit(operationCode, operand);
			exprlistp(operationCode, operand);
			break;
		case ']':
		case ')':
			break;
		default:
			throw error("in exprlistp");
		}
	}

	public static void main(String args[]) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("_5_1_translator_generator/input.lft"));
		Translator translator = new Translator(new Lexer(), br);

		translator.prog();
		System.out.println("Input OK  (parsed correctly)");

		if(br!=null)br.close();
	}
}

