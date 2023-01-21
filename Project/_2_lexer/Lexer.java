package _2_lexer;

import java.io.*;

public class Lexer {
	public int line = 1;
	private char peek = ' ';
	public static final char EOF = (char) -1;

	private void readch(BufferedReader br) {
		try {
			peek = (char) br.read();
		} catch (IOException exc) { peek = EOF; }
	}

	private NumberTok toNumberToken(BufferedReader br) {
		int val = peek - '0';
		readch(br);

		// CHECK FOR 0N
		if (val == 0 && Character.isDigit(peek))
			throw new Error("\n\nErroneous character after 0 : " + peek);

		while (Character.isDigit(peek)) {
			val = val * 10 + peek - '0';
			readch(br);
		}

		// CHECK FOR FOLLOWED BY _ OR LETTER
		if (Character.isLetter(peek) || peek == '_')
			throw new Error("\n\nErroneous character after number : '" + peek + "'");

		return new NumberTok(val); // Don't clear peek
	}

	private Token toWordToken(BufferedReader br) {
		String s = "";
		boolean onlySlash = true;

		// OBTAIN STRING
		do {
			s += "" + peek;
			if (peek != '_') onlySlash = false;
			readch(br);
		} while (Character.isLetterOrDigit(peek) || peek == '_');

		// CHECK FOR _
		if (onlySlash)
			throw new Error("\n\nErroneous identification (only '_') in: " + s);

		// KEY WORD or IDENTIFIER (don't clear peek)
		switch(s){
		case "assign": return Word.assign;
		case "to": return Word.to;
		case "conditional": return Word.conditional;
		case "option": return Word.option;
		case "do": return Word.dotok;
		case "else": return Word.elsetok;
		case "while": return Word.whiletok;
		case "begin": return Word.begin;
		case "end": return Word.end;
		case "print": return Word.print;
		case "read": return Word.read;
		default: return new Word(Tag.ID, s);
		}
	}

	public Token lexical_scan(BufferedReader br) {
		while (true) { // For empty token like comments
			while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
				if (peek == '\n')
					line++;
				readch(br);
			}

			switch (peek) {
			case '!': peek = ' '; return Token.not;
			case '(': peek = ' '; return Token.lpt;
			case ')': peek = ' '; return Token.rpt;
			case '[': peek = ' '; return Token.lpq;
			case ']': peek = ' '; return Token.rpq;
			case '{': peek = ' '; return Token.lpg;
			case '}': peek = ' '; return Token.rpg;
			case '+': peek = ' '; return Token.plus;
			case '-': peek = ' '; return Token.minus;
			case '*': peek = ' '; return Token.mult;
			case ';': peek = ' '; return Token.semicolon;
			case ',': peek = ' '; return Token.comma;
			case EOF: return new Token(Tag.EOF);
			// Possible double letter symbols
			case '&':
				readch(br);
				if (peek == '&') { peek = ' '; return Word.and; }
				else throw new Error("\n\nErroneous character after & : " + peek);
			case '|':
				readch(br);
				if (peek == '|') { peek = ' '; return Word.or; }
				else throw new Error("\n\nErroneous character after | : " + peek);
			case '<':
				readch(br);
				if (peek == ' ') { peek = ' '; return Word.lt; }
				else if (peek == '=') { peek = ' '; return Word.le; }
				else if (peek == '>') { peek = ' '; return Word.ne; }
				else throw new Error("\n\nErroneous character after < : " + peek);
			case '>':
				readch(br);
				if (peek == ' ')  { peek = ' '; return Word.gt; }
				else if (peek == '=') { peek = ' '; return Word.ge; }
				else throw new Error("\n\nErroneous character after > : " + peek);
			case '=':
				readch(br);
				if (peek == '=') { peek = ' '; return Word.eq; }
				else throw new Error("\n\nErroneous character after = : " + peek);
			case '/':
				readch(br);
				if (peek == '/') {
					while (peek != EOF && peek != '\n') readch(br);
				} else if (peek == '*') {
					char old;
					peek = ' ';
					do {
						old = peek;
						readch(br);
					} while (peek != EOF && !(old == '*' && peek == '/'));
					if (peek == EOF)
						throw new Error("\n\nError, never closed /* */ type comment : ");
					peek = ' ';
				} else
					return Token.div; // no new peek
				break;
			// Longer symbols
			default:
				if (Character.isLetter(peek) || peek == '_') {
					return toWordToken(br);
				} else if (Character.isDigit(peek)) {
					return toNumberToken(br);
				} else throw new Error("\n\nErroneous character: " + peek);
			}
		}
	}

	public static void main(String args[]) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("_2_lexer/input.txt"));
		Lexer lex = new Lexer();
		Token tok;

		do {
			tok = lex.lexical_scan(br);
			System.out.print(tok + " ");
		} while (tok.tag != Tag.EOF);
		System.out.println();

		if(br!=null) br.close();
	}
}