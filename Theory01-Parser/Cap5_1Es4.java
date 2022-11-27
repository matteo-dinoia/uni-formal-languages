

public class Cap5_1Es4 extends Parser{

	@Override protected void start(){
		E();
		match('$');
		System.out.println();
	}

	private void E() {
		switch(peek()){
			case '(':
			case '0':case '1':case '2':case '3':case '4':
			case '5':case '6':case '7':case '8':case '9':
				T();
				E2();

				break;
			default:
				throw guideError("in E");

		}
	}

	private void E2() {
		switch(peek()){
			case '+':
				match('+');
				T();
				E2();
				System.out.print('+');
				break;
			case ')':
			case '$':
					break;
			default:
				throw guideError("in E2");

		}
	}

	private void T() {
		switch(peek()){
			case '(':
			case '0':case '1':case '2':case '3':case '4':
			case '5':case '6':case '7':case '8':case '9':
				F();
				T2();
				break;
			default:
				throw guideError("in E");

		}
	}

	private void T2() {
		switch(peek()){
			case '*':
				match('*');
				F();
				T2();
				System.out.print('*');
				break;
			case ')':
			case '+':
			case '$':
				break;
			default:
				throw guideError("in T2");

		}
	}

	private void F() {
		switch(peek()){
			case '(':
				match('(');
				System.out.print('(');
				E();
				match(')');
				System.out.print(')');
				break;
			case '0':case '1':case '2':case '3':case '4':
			case '5':case '6':case '7':case '8':case '9':
				System.out.print(peek());
				match(peek());
				break;
			default:
				throw guideError("in E");

		}
	}

	public static void main(String args[]){
		new Cap5_1Es4().executeTest(args);
	}
}
