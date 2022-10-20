import java.io.*;

public class Lexer {
	public static int line = 1;
	private char peek = ' ';

	private void readch(BufferedReader br) {
		try {
			peek = (char) br.read();
		} catch (IOException exc) {
			peek = (char) -1; // ERROR
		}
	}

	private Token toNumberToken(BufferedReader br) {
		int val = peek - '0';
		readch(br);

		// CHECK NO 0n
		if (val == 0 && Character.isDigit(peek)) {
			System.err.println("Erroneous character after 0 : " + peek);
			return null;
		}

		// OBTAIN NUMBER
		while (Character.isDigit(peek)) {
			val = val * 10 + peek - '0';
			readch(br);
		}

		// CHECK NO NUMBER-CHAR
		if (Character.isLetter(peek)) {
			System.err.println("Erroneous character after number : " + peek);
			return null;
		}

		// Else don't clean peek
		return new NumberTok(val);
	}

	// ... gestire il caso degli identificatori e delle parole chiave //
	private Token toWordToken(BufferedReader br) {
		String s = "";

		// OBTAIN STRING
		do {
			s += "" + peek;
			readch(br);
		} while (Character.isLetterOrDigit(peek));

		// KEY WORD
		if (s.equals("assign"))
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
		while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
			if (peek == '\n')
				line++;
			readch(br);
		}

		switch (peek) {
			// ... gestire i casi di ( ) [ ] { } + - * / ; , ... //
			case '!': // peek = ' '; return Token.not;
			case '(':
			case ')':
			case '[':
			case ']':
			case '{':
			case '}':
			case '+':
			case '-':
			case '*':
			case '/':
			case ';':
			case ',':
				Token res = new Token(peek);
				peek = ' ';
				return res;
			// fine gestire i casi di ( ) [ ] { } + - * / ; , ... //
			case '&':
				readch(br);
				if (peek == '&') {
					peek = ' ';
					return Word.and;
				} else {
					System.err.println("Erroneous character"
							+ " after & : " + peek);
					return null;
				}
				// ... gestire i casi di || < > <= >= == <> ... //
			case '|':
				readch(br);
				if (peek == '|') {
					peek = ' ';
					return Word.or;
				} else {
					System.err.println("Erroneous character"
							+ " after & : " + peek);
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
					System.err.println("Erroneous character"
							+ " after & : " + peek);
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
					System.err.println("Erroneous character"
							+ " after & : " + peek);
					return null;
				}
			case '=':
				readch(br);
				if (peek == '=') {
					peek = ' ';
					return Word.eq;
				} else {
					System.err.println("Erroneous character"
							+ " after & : " + peek);
					return null;
				}
				// fine gestire i casi di || < > <= >= == <> ... //
			case (char) -1:
				return new Token(Tag.EOF);
			default:
				if (Character.isLetter(peek)) {
					return toWordToken(br);
				} else if (Character.isDigit(peek)) {
					return toNumberToken(br);
				} else {
					System.err.println("Erroneous character: " + peek);
					return null;
				}
		}
	}

	public static final String path = "Input.txt"; // il percorso del file da leggere

	public static void main(String[] args) {
		Lexer lex = new Lexer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			Token tok;
			do {
				tok = lex.lexical_scan(br);
				System.out.println("Scan: " + tok);
			} while (tok.tag != Tag.EOF);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}