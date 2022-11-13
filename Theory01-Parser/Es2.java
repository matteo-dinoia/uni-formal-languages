public class Es2 extends Parser {
	protected void S() {
		switch (peek()) {
			case '[': // S -> [S]S
				match('[');
				S();
				match(']');
				break;
			case ']':
			case '$': // S -> epsilon
				break;
			default:
				throw guideError("S");
		}
	}

	public static void main(String args[]){
		new Es2().executeTest();
	}
}
