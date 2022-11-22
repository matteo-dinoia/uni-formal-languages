public class Cap5_1Es1 extends Parser{
	@Override protected void start(){
		int res=S();
		match('$');
		System.out.print("(Value="+res+")");
	}

	private int S() {
		switch (peek()) {
			case '[': // S -> [S]S
				match('[');
				int t1=S();
				match(']');
				int t2=S();
				return (t1+1) >= t2 ? (t1+1) : t2;
			case ']':
			case '$': // S -> epsilon
				return 0;
			default:
				throw guideError("S");
		}
	}

	public static void main(String args[]){
		new Cap5_1Es1().executeTest(args);
	}
}
