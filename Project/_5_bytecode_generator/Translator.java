package _5_bytecode_generator;

import _2_3_lexer.*;
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
	public void prog(){ //JUST ADDED GUIDE
		switch(look.tag){
		case Tag.ASSIGN:
		case Tag.PRINT:
		case Tag.READ:
		case Tag.WHILE:
		case Tag.COND:
		case '{':
			/*int lnext_prog = code.newLabel();
			statlist(lnext_prog);
			code.emitLabel(lnext_prog);*/
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
		int lstart, lblock, lend;
		switch(look.tag){
		case Tag.ASSIGN:
			match(Tag.ASSIGN);
			expr();
			match(Tag.TO);
			int id=idlist();
			code.emit(OpCode.istore, id);
			break;
		case Tag.PRINT:
			match(Tag.PRINT);
			match('[');
			exprlist();
			match(']');
			code.emit(OpCode.invokestatic, 1);
			break;
		case Tag.READ:
			match(Tag.READ);
			match('[');
			int id2=idlist();
			match(']');

			code.emit(OpCode.invokestatic, 0);
			code.emit(OpCode.istore, id2);
			break;
		case Tag.WHILE:
			lstart=code.newLabel(); lblock=code.newLabel(); lend=code.newLabel();
			match(Tag.WHILE);
			match('(');

			code.emitLabel(lstart);
			bexpr(lblock, lend);

			code.emitLabel(lblock);
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
			throw error("in idlist");
		}
	}

	private int idlist(){ //TODO REMOVE -> rendo lista
		switch(look.tag){
		case Tag.ID:
			//ADDED
			int id_addr = st.lookupAddress(((Word)look).lexeme);
			if (id_addr==-1) {
				id_addr = count;
				st.insert(((Word)look).lexeme,count++);
			}
			//ADDED
			match(Tag.ID);
			idlistp();
			return id_addr;
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
			int block=code.newLabel();
			bexpr(block, next);
			match(')');

			match(Tag.DO);
			code.emitLabel(block);
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
			//Token lookRelop=look;
			match(Tag.RELOP);
			expr();
			expr();
			code.emit(OpCode.if_icmple, ltrue);
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
			exprlist();
			match(')');
			code.emit(OpCode.iadd); //Operation
			//manca
			break;
		case '*':
			match('*');
			match('(');
			exprlist();
			match(')');
			code.emit(OpCode.imul); //Operation
			//manca
			break;
		case '-':
			match('-');
			expr();
			expr();
			code.emit(OpCode.isub); //Operation
			break;
		case '/':
			match('/');
			expr();
			expr();
			code.emit(OpCode.idiv); //Operation
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
			br = new BufferedReader(new FileReader("_5_bytecode_generator/input.txt"));

			Translator translator = new Translator(new Lexer(), br);
			translator.prog();

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

