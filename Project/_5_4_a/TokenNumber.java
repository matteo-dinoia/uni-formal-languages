package _5_4_a;

public class TokenNumber extends Token {
	public final int value;

	public TokenNumber(int value) {
		super(Tag.NUM);
		this.value = value;
	}

	public String toString() {
		return "<" + tag + ", " + value + ">";
	}
}
