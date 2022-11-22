import java.util.ArrayList;

public class Cap5_1Es2 extends Parser{

	@Override protected void start(){
		ArrayList<Integer> l=S();
		match('$');

		String res="(Value= ";
		for(Integer num : l) res+=num+"; ";
		System.out.print(res+")");
	}

	private ArrayList<Integer> S() {
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
				int nV=peeked-'0';
				return L(nV);
			default:
				throw guideError("S");
		}

	}

	private ArrayList<Integer> L(int e){
		switch (peek()) {
			case ';': // L -> ;nL
				match(';');
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
						int nV=peeked-'0';
						ArrayList<Integer> res=L(e);
						res.add(nV-e);
						return res;
					default:
						throw guideError("L_2");
				}
			case '$': // S -> epsilon
				return new ArrayList<>();
			default:
				throw guideError("L");
		}
	}

	public static void main(String args[]){
		new Cap5_1Es2().executeTest(args);
	}
}
