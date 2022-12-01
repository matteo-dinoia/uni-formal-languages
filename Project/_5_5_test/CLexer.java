package _5_5_test;

import _5_4_a.Tag;
import _5_4_a.Token;
import _5_4_a.TokenNumber;
import _5_4_a.TokenWord;

public class CLexer extends Lexer{
	public CLexer(String pathName){ super(pathName); }

	@Override protected Token lexicalScan() {
		switch (readChar()) {

		case '\n':
			increaseLine();
		case ' ':
		case '\t':
		case '\r': return null;

		case EOF:  return new Token(Tag.EOF);
		case '!':  return Token.not;
		case '(':  return Token.lpt;
		case ')':  return Token.rpt;
		case '[':  return Token.lpq;
		case ']':  return Token.rpq;
		case '{':  return Token.lpg;
		case '}':  return Token.rpg;
		case '+':  return Token.plus;
		case '-':  return Token.minus;
		case '*':  return Token.mult;
		case ';':  return Token.semicolon;
		case ',':  return Token.comma;

		case '&':
			if (readChar() == '&') return TokenWord.and;
			throw lexError("erroneous character");
		case '|':
			if (readChar() == '|') return TokenWord.or;
			throw lexError("erroneous character");
		case '=':
			if (readChar() == '=') return TokenWord.eq;
			throw lexError("erroneous character");
		case '<':
			readChar();
			if (actualChar() == ' ') return TokenWord.lt;
			else if (actualChar() == '=') return TokenWord.le;
			else if (actualChar() == '>') return TokenWord.ne;
			throw lexError("erroneous character");
		case '>':
			readChar();
			if (actualChar() == ' ') return TokenWord.gt;
			else if (actualChar() == '=') return TokenWord.ge;
			throw lexError("erroneous character");

		case '/':
			readChar();
			if (actualChar() == '/') { // line comments
				while (actualChar() != EOF && actualChar() != '\n'){ readChar(); }
				return null;
			} else if (actualChar() == '*') { //multiline comments
				do{
					readChar();
				}while (actualChar() != EOF && !(olderChar() == '*' && actualChar() == '/'));

				if (actualChar() == EOF)
					throw lexError("never closed /* */ type comment");
				return null;
			} else{
				revertLastChar();
				return Token.div; // no new peek
			}

		case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
			return toNumberToken();
		case '_':
		case 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I','i',
				 'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q',
				'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z':
			return toWordToken();

		default:  throw lexError("not valid character");
		}
	}

	private TokenNumber toNumberToken() {
		int val = actualChar() - '0';
		readChar();

		// CHECK NO 0n
		if (val == 0 && Character.isDigit(actualChar()))
			throw lexError("erroneous character");

		// OBTAIN NUMBER
		while (Character.isDigit(actualChar())) {
			val = val * 10 + actualChar() - '0';
			readChar();
		}

		// CHECK NO NUMBER-CHAR OR NUM_
		if (Character.isLetter(actualChar()) || actualChar() == '_')
			throw lexError("erroneous character");

		revertLastChar();
		return new TokenNumber(val);
	}

	private Token toWordToken() {
		String res = "";
		boolean onlySlash = true;

		// OBTAIN STRING
		do {
			res += "" + actualChar();
			if (actualChar() != '_') onlySlash = false;
			readChar();
		} while (Character.isLetterOrDigit(actualChar()) || actualChar() == '_');

		// CHECK FOR _
		if (onlySlash)
			throw lexError("erroneous identification (only '_') in: " + res);

		revertLastChar();
		return TokenWord.parseString(res);
	}


	public static void main(String args[]){
		Lexer lex = new CLexer("_5_4_test/input.txt");
		while (lex.getNextToken().tag != Tag.EOF);
		lex.close();
	}
}