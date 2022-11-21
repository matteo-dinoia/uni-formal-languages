package _2_1_lexer_old;

public class NumberTok extends Token {
	private int value;

	public NumberTok(int value) {
		super(Tag.NUM);
		this.value = value;
	}

	public String toString() {
		return "<" + tag + ", " + value + ">";
	}

	public int getValue(){
		return value;
	}
}
