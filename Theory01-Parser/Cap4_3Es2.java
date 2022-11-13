public class Cap4_3Es2 extends Parser {

	@Override
	protected void S() {
		E();
	}

	private void E(){
		switch(peek()){
		case '(':
		case '~':
		case 't':
		case 'f':
			F();
			E2();
			break;
		default:
			throw guideError("E");
		}
	}

	private void E2(){
		switch(peek()){
		case '&':
			match('&');
			E();
		case ')':
		case '$':
			break;
		default:
			throw guideError("E2");
		}
	}

	private void F(){
		switch(peek()){
		case '(':
		case '~':
		case 't':
		case 'f':
			T();
			F2();
			break;
		default:
			throw guideError("F");
		}
	}

	private void F2(){
		switch(peek()){
		case '|':
			match('|');
			F();
		case ')':
		case '$':
		case '&':
			break;
		default:
			throw guideError("E2");
		}
	}

	private void T(){
		switch(peek()){
		case '(':
			match('(');
			E();
			match(')');
			break;
		case '~':
			match('~');
			E();
			break;
		case 't':
			match('t');
			break;
		case 'f':
			match('f');
			break;
		default:
			throw guideError("T");
		}
	}

	public static void main(String args[]){
		new Cap4_3Es2().executeTest();
	}
}
