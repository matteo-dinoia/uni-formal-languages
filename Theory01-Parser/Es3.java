public class Es3 extends Parser {
	protected void S() {
		switch (peek()) {
			case 'a': // S → XC
			case 'b':
			case 'c':
			case '$':
				X();
				C();
				break;
			default:
				throw error();
		}
	}

	protected void X() {
		switch (peek()) {
			case 'a': // S → aXb
				match('a');
				X();
				match('b');
				break;
			case 'b': // X → ε
			case 'c':
			case '$':
				break;
			default:
				throw error();
		}
	}

	protected void C() {
		switch (peek()) {
			case 'c': // S → cC
				match('c');
				C();
				break;
			case '$':  // C → ε
				break;
			default:
				throw error();
		}
	}
}
