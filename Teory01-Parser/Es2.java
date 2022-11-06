public class Es2 extends Parser {
	protected void S() {
		switch (peek()) {
			case '[': // S → [S]S
				match('[');
				S();
				match(']');
				break;
			case ']':
			case '$': // S → epsilon
				break;
			default:
				throw error();
		}
	}
}
