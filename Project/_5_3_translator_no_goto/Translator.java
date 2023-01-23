package _5_3_translator_no_goto;

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

	public Translator(Lexer l, BufferedReader br) {
		lex = l;
		pbr = br;
		move();
	}

	private void move() {
		look = lex.lexical_scan(pbr);
		System.out.println("token = " + look);
	}

	private Error error(String s) {
		throw new Error("near line " + lex.line + ": " + s);
	}

	private void match(int t) {
		if (look.tag == t) {
			if (look.tag != Tag.EOF) move();
		} else throw error("syntax error");
	}

	// VARIABLES
	public void prog() throws IOException{
		switch(look.tag){
		case Tag.ASSIGN:
		case Tag.PRINT:
		case Tag.READ:
		case Tag.WHILE:
		case Tag.COND:
		case '{':
			statlist();
			match(Tag.EOF);

			//PRINT FILES
			code.toJasmin();
			break;
		default: throw error("in statlist");
		}
	}

	private void statlist(){
		switch(look.tag){
		case Tag.ASSIGN:
		case Tag.PRINT:
		case Tag.READ:
		case Tag.WHILE:
		case Tag.COND:
		case '{':
			stat();
			statlistp();
			break;
		default: throw error("in statlist");
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
		default: throw error("in statlistp");
		}
	}

	private void stat() {
		int lstart, lend;
		switch(look.tag){
		case Tag.ASSIGN:
			match(Tag.ASSIGN);
			expr();
			match(Tag.TO);
			idlist(OpCode.dup, -1, false);
			break;
		case Tag.PRINT:
			match(Tag.PRINT);
			match('[');
			exprlist(OpCode.invokestatic, 1, true);
			match(']');
			break;
		case Tag.READ:
			match(Tag.READ);
			match('[');
			idlist(OpCode.invokestatic, 0, true);
			match(']');
			break;
		case Tag.WHILE:
			lstart=code.newLabel(); lend=code.newLabel();
			match(Tag.WHILE);
			match('(');

			code.emitLabel(lstart);
			bexpr(lend, false);

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
		default: throw error("in stat");
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
		default: throw error("in conditionalp");
		}
	}

	private void idlist(OpCode opCode, int parameter, boolean alsoExecuteLastTime){
		switch(look.tag){
		case Tag.ID:
			int id_addr = st.lookupOrNewAddress(((Word)look).lexeme);
			match(Tag.ID);

			if(alsoExecuteLastTime || look.tag==',') //if not last or execute also in last
				code.emit(opCode, parameter);
			code.emit(OpCode.istore, id_addr);
			idlistp(opCode, parameter, alsoExecuteLastTime);
			break;
		default: throw error("in idlist");
		}
	}
	private void idlistp(OpCode opCode, int parameter, boolean alsoExecuteLastTime){
		switch(look.tag){
		case ',':
			match(',');
			int id_addr = st.lookupOrNewAddress(((Word)look).lexeme);
			match(Tag.ID);

			if(alsoExecuteLastTime || look.tag==',') //if not last or execute also in last
				code.emit(opCode, parameter);
			code.emit(OpCode.istore, id_addr);
			idlistp(opCode, parameter, alsoExecuteLastTime);
			break;
		case ']':
		case '}':
		case Tag.OPTION:
		case Tag.END:
		case Tag.EOF:
		case ';':
			break;
		default: throw error("in idlistp");
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
		default: throw error("in optlist");
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
		default: throw error("in optlistp");
		}
	}

	private void optitem(int next, int lafter_else){
		switch(look.tag){
		case Tag.OPTION:
			match(Tag.OPTION);
			match('(');
			bexpr(next, false);
			match(')');

			match(Tag.DO);
			stat();
			code.emit(OpCode.GOto, lafter_else);
			break;
		default: throw error("in optitem");
		}
	}

	private void bexpr(int ljump, boolean jumpIfTrue){
		switch(look.tag){
		case Tag.RELOP:
			String type=((Word)look).lexeme;
			match(Tag.RELOP);
			expr();
			expr();

			OpCode operCode=OpCode.getCodeFromRelop(type);
			if(!jumpIfTrue) operCode=operCode.getOpposite();
			code.emit(operCode, ljump);
			break;
		default: throw error("in bexpr");
		}
	}

	private void expr(){
		switch(look.tag){
		case '+':
			match('+');
			match('(');
			exprlist(OpCode.iadd, -1, false);
			match(')');
			break;
		case '*':
			match('*');
			match('(');
			exprlist(OpCode.imul, -1, false);
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
		default: throw error("in expr");
		}
	}

	private void exprlist(OpCode operationCode, int operand, boolean alsoExecuteFirstTime){
		switch(look.tag){
		case '+':
		case '-':
		case '*':
		case '/':
		case Tag.NUM:
		case Tag.ID:
			expr();
			if(alsoExecuteFirstTime)
				code.emit(operationCode, operand);
			exprlistp(operationCode, operand, alsoExecuteFirstTime);
			break;
		default: throw error("in exprlist");
		}
	}
	private void exprlistp(OpCode operationCode, int operand, boolean alsoExecuteFirstTime){
		switch(look.tag){
		case ',':
			match(',');
			expr();
			code.emit(operationCode, operand);
			exprlistp(operationCode, operand, alsoExecuteFirstTime);
			break;
		case ']':
		case ')':
			break;
		default: throw error("in exprlistp");
		}
	}

	public static void main(String args[]) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("_5_3_translator_no_goto/input.lft"));
		Translator translator = new Translator(new Lexer(), br);

		translator.prog();
		System.out.println("Input OK  (parsed correctly)");

		if(br!=null)br.close();
	}
}

