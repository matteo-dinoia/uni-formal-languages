package _5_5_z;

import _5_4_a.*;
import _5_4_test.*;

public class CTranslator extends Translator{

	//COSTRUCTOR
	public CTranslator(String pathName){
		super(new CLexer(pathName));
	}


	protected void prog(){
		switch(look().tag){
		case Tag.ASSIGN:
		case Tag.PRINT:
		case Tag.READ:
		case Tag.WHILE:
		case Tag.COND:
		case '{':
			statlist();
			match(Tag.EOF);
			break;
		default: throw error("in statlist");
		}
	}

	private void statlist(){
		switch(look().tag){
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
		switch(look().tag){
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
		switch(look().tag){
		case Tag.ASSIGN:
			match(Tag.ASSIGN);
			expr();
			match(Tag.TO);
			idlist(OpCode.dup, null);
			break;
		case Tag.PRINT:
			match(Tag.PRINT);
			match('[');
			exprlist(OpCode.invokestatic, OpCode.WRITE);
			match(']');
			break;
		case Tag.READ:
			match(Tag.READ);
			match('[');
			idlist(OpCode.invokestatic, OpCode.READ);
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
		switch(look().tag){
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

	private void idlist(OpCode opCode, Integer parameter){
		switch(look().tag){
		case Tag.ID:
			int id_addr = code.getVariableOrCreate(((TokenWord)look()).lexeme);
			match(Tag.ID);

			if(opCode!=OpCode.dup || look().tag==',') //if assign and is last element
				code.emit(opCode, parameter);
			code.emit(OpCode.istore, id_addr);
			idlistp(opCode, parameter);
			break;
		default:
			throw error("in idlist");
		}
	}
	private void idlistp(OpCode opCode, Integer parameter){
		switch(look().tag){
		case ',':
			match(',');
			int id_addr = code.getVariableOrCreate(((TokenWord)look()).lexeme);
			match(Tag.ID);

			if(opCode!=OpCode.dup || look().tag==',') //if assign and is last element
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
		switch(look().tag){
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
		switch(look().tag){
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
		switch(look().tag){
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
		switch(look().tag){
		case Tag.LE:
		case Tag.LT:
		case Tag.GE:
		case Tag.GT:
		case Tag.NE:
		case Tag.EQ:
			int type = look().tag;
			match(type); //ex RELOP
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
		switch(look().tag){
		case '+':
			match('+');
			match('(');
			exprlist(OpCode.iadd, null);
			match(')');
			break;
		case '*':
			match('*');
			match('(');
			exprlist(OpCode.imul, null);
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
			code.emit(OpCode.ldc, ((TokenNumber)look()).value);
			match(Tag.NUM);
			break;
		case Tag.ID:
			code.emit(OpCode.iload, code.getExistingVariable(((TokenWord)look()).lexeme));
			match(Tag.ID);

			break;
		default:
			throw error("in expr");
		}
	}
	private void exprlist(OpCode operationCode, Integer operand){
		switch(look().tag){
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
	private void exprlistp(OpCode operationCode, Integer operand){
		switch(look().tag){
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


	//TESTING
	public static void main(String args[]){
		CTranslator translator = new CTranslator("_5_4_z/input.lft");
		translator.parse();
		translator.close();
	}
}

