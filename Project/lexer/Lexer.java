package lexer;

import java.io.*;

public class Lexer {
	public int line = 1;
	private char peek = ' ';
	public static final char EOF = (char) -1;

	private void readch(BufferedReader br) {
		try {
			peek = (char) br.read();
		} catch (IOException exc) {
			peek = EOF; // ERROR
		}
	}

	private Token toNumberToken(BufferedReader br) {
		int val = peek - '0';
		readch(br);

		// CHECK NO 0n
		if (val == 0 && Character.isDigit(peek)) {
			System.err.println("\n\nErroneous character after 0 : " + peek);
			return null;
		}

		// OBTAIN NUMBER
		while (Character.isDigit(peek)) {
			val = val * 10 + peek - '0';
			readch(br);
		}

		// CHECK NO NUMBER-CHAR OR NUM_
		if (Character.isLetter(peek) || peek == '_') {
			System.err.println("\n\nErroneous character after number : '" + peek + "'");
			return null;
		}

		// Else don't clean peek
		return new NumberTok(val);
	}

	// ... gestire il caso degli identificatori e delle parole chiave //
	private Token toWordToken(BufferedReader br) {
		String s = "";
		boolean onlySlash = true;

		// OBTAIN STRING
		do {
			s += "" + peek;
			if (peek != '_')
				onlySlash = false;
			readch(br);
		} while (Character.isLetterOrDigit(peek) || peek == '_');

		// CHECK FOR _
		if (s.charAt(0) == '_') {
			if (onlySlash) {
				System.err.println("\n\nErroneous identification (only '_') in: " + s);
				return null;
			}
		}
		// KEY WORD
		else if (s.equals("assign"))
			return Word.assign;
		else if (s.equals("to"))
			return Word.to;
		else if (s.equals("conditional"))
			return Word.conditional;
		else if (s.equals("option"))
			return Word.option;
		else if (s.equals("do"))
			return Word.dotok;
		else if (s.equals("else"))
			return Word.elsetok;
		else if (s.equals("while"))
			return Word.whiletok;
		else if (s.equals("begin"))
			return Word.begin;
		else if (s.equals("end"))
			return Word.end;
		else if (s.equals("print"))
			return Word.print;
		else if (s.equals("read"))
			return Word.read;

		// IDENTIFIER
		// don't clean peek
		return new Word(Tag.ID, s);
	}

	public Token lexical_scan(BufferedReader br) {
		while (true) { // For empty token like comments
			while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
				if (peek == '\n')
					line++;
				readch(br);
			}

			switch (peek) {
				// ... gestire i casi di ( ) [ ] { } + - * / ; , ... //
				case '!':
					peek = ' ';
					return Token.not;
				case '(':
					peek = ' ';
					return Token.lpt;
				case ')':
					peek = ' ';
					return Token.rpt;
				case '[':
					peek = ' ';
					return Token.lpq;
				case ']':
					peek = ' ';
					return Token.rpq;
				case '{':
					peek = ' ';
					return Token.lpg;
				case '}':
					peek = ' ';
					return Token.rpg;
				case '+':
					peek = ' ';
					return Token.plus;
				case '-':
					peek = ' ';
					return Token.minus;
				case '*':
					peek = ' ';
					return Token.mult;
				case '/':
					readch(br);
					if (peek == '/') {
						do {
							readch(br);
						} while (peek != EOF && peek != '\n');
					} else if (peek == '*') {
						char old;
						peek = '\0';
						do {
							old = peek;
							readch(br);
						} while (peek != EOF && !(old == '*' && peek == '/'));
						if (peek == EOF) {
							System.err.println(
									"\n\nError, never closed /* */ type comment : ");
							return null;
						}
						peek = ' ';
					} else
						return Token.div; // no new peek
					break;
				case ';':
					peek = ' ';
					return Token.semicolon;
				case ',':
					peek = ' ';
					return Token.comma;
				// fine gestire i casi di ( ) [ ] { } + - * / ; , ... //
				case '&':
					readch(br);
					if (peek == '&') {
						peek = ' ';
						return Word.and;
					} else {
						System.err.println("\n\nErroneous character after & : " + peek);
						return null;
					}
					// ... gestire i casi di || < > <= >= == <> ... //
				case '|':
					readch(br);
					if (peek == '|') {
						peek = ' ';
						return Word.or;
					} else {
						System.err.println("\n\nErroneous character after | : " + peek);
						return null;
					}
				case '<':
					readch(br);
					if (peek == ' ') {
						return Word.lt;
					} else if (peek == '=') {
						peek = ' ';
						return Word.le;
					} else if (peek == '>') {
						peek = ' ';
						return Word.ne;
					} else {
						System.err.println("\n\nErroneous character after < : " + peek);
						return null;
					}
				case '>':
					readch(br);
					if (peek == ' ') {
						return Word.gt;
					} else if (peek == '=') {
						peek = ' ';
						return Word.ge;
					} else {
						System.err.println("\n\nErroneous character after > : " + peek);
						return null;
					}
				case '=':
					readch(br);
					if (peek == '=') {
						peek = ' ';
						return Word.eq;
					} else {
						System.err.println("\n\nErroneous character after = : " + peek);
						return null;
					}
					// fine gestire i casi di || < > <= >= == <> ... //
				case EOF:
					return new Token(Tag.EOF);
				default:
					if (Character.isLetter(peek) || peek == '_') {
						return toWordToken(br);
					} else if (Character.isDigit(peek)) {
						return toNumberToken(br);
					} else {
						System.err.println("\n\nErroneous character: " + peek);
						return null;
					}
			}
		}
	}

	private static void main(String args[]){
		Lexer lex = new Lexer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader("Project/lexer/input-lexer.txt"));

			Token tok;
			do {
				tok = lex.lexical_scan(br);
				if (tok == null)
					return;
				System.out.print(tok + " ");
			} while (tok.tag != Tag.EOF);
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