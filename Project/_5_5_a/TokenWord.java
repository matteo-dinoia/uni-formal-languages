package _5_5_a;

public class TokenWord extends Token {
	public String lexeme = "";



	private TokenWord(int tag, String s) {
		super(tag);
		lexeme = s;
	}

	@Override
	public String toString() {
		return "<" + tag + ", " + lexeme + ">";
	}

	public static TokenWord parseString(String s){
		switch(s){
		case "assign": return assign;
		case "to": return to;
		case "conditional": return conditional;
		case "option": return option;
		case "do": return dotok;
		case "else": return elsetok;
		case "while": return whiletok;
		case "begin": return begin;
		case "end": return end;
		case "print": return print;
		case "read": return read;
		default: return new TokenWord(Tag.ID, s);
		}
	}
	public static final TokenWord assign = new TokenWord(Tag.ASSIGN, "assign"),
			to = new TokenWord(Tag.TO, "to"),
			conditional = new TokenWord(Tag.COND, "conditional"),
			option = new TokenWord(Tag.OPTION, "option"),
			dotok = new TokenWord(Tag.DO, "do"),
			elsetok = new TokenWord(Tag.ELSE, "else"),
			whiletok = new TokenWord(Tag.WHILE, "while"),
			begin = new TokenWord(Tag.BEGIN, "begin"),
			end = new TokenWord(Tag.END, "end"),
			print = new TokenWord(Tag.PRINT, "print"),
			read = new TokenWord(Tag.READ, "read"),
			or = new TokenWord(Tag.OR, "||"),
			and = new TokenWord(Tag.AND, "&&"),
			lt = new TokenWord(Tag.LT, "<"),
			gt = new TokenWord(Tag.GT, ">"),
			eq = new TokenWord(Tag.EQ, "=="),
			le = new TokenWord(Tag.LE, "<="),
			ne = new TokenWord(Tag.NE, "<>"),
			ge = new TokenWord(Tag.GE, ">=");
}
