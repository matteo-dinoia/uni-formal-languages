public class Es2 extends Parser {
	@Override protected void start(){S(); match('$');}

	private void S() {
		switch (peek()) {
			case '[': // S -> [S]S
				match('[');
				S();
				match(']');
				S();
				break;
			case ']':
			case '$': // S -> epsilon
				break;
			default:
				throw guideError("S");
		}
	}

	public static void main(String args[]){
		new Es2().executeTest(args);
	}
}
