public class NumberTok extends Token {
	private int value;

	// TODO ADDO AUTO CAST

	public NumberTok(int value) {
		super(Tag.NUM);
		this.value = value;
	}

	public String toString() {
		return "<" + tag + ", " + value + ">";
	}
}
