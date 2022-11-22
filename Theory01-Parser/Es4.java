public class Es4 extends Parser {
	@Override protected void start(){S(); match('$');}

	private void S() {
		char peeked=peek();
		switch (peeked) {
			case '0': // S -> 0...9
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				match(peeked);
				break;
			case '+': // S -> +SS
				match('+');
				S();
				S();
				break;
			case '*': // S -> *SS
				match('*');
				S();
				S();
				break;
			default:
				throw guideError("S");
		}
	}

	public static void main(String args[]){
		new Es4().executeTest(args);
	}
}
