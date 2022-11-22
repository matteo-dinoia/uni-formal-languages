public class Es3 extends Parser {
	@Override protected void start(){S(); match('$');}

	private void S() {
		switch (peek()) {
			case 'a': // S -> XC
			case 'b':
			case 'c':
			case '$':
				X();
				C();
				break;
			default:
				throw guideError("S");
		}
	}

	private void X() {
		switch (peek()) {
			case 'a': // S -> aXb
				match('a');
				X();
				match('b');
				break;
			case 'b': // X ->
			case 'c':
			case '$':
				break;
			default:
				throw guideError("X");
		}
	}

	private void C() {
		switch (peek()) {
			case 'c': // S -> cC
				match('c');
				C();
				break;
			case '$':  // C ->
				break;
			default:
				throw guideError("C");
		}
	}

	public static void main(String args[]){
		new Es3().executeTest(args);
	}
}
