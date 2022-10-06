import java.util.TreeMap;

public class ThreeConsecutiveZero {
	private class State {
		private TreeMap<Character, State> destinations = new TreeMap<>();
		public boolean isFinal = false;

		public void addBranch(State dest, char val) {
			destinations.put(val, dest);
		}

		public boolean execute(String s) {
			if (s == null || s.equals(""))
				return isFinal;
			return destinations.get(s.charAt(0))
					.execute(s.substring(1));
		}
	}

	private State q0;

	public ThreeConsecutiveZero() {
		q0 = new State();
		State q1 = new State();
		State q2 = new State();
		State q3 = new State();

		// Forward route
		q0.addBranch(q1, '0');
		q1.addBranch(q2, '0');
		q2.addBranch(q3, '0');

		// Return route
		q0.addBranch(q0, '1');
		q1.addBranch(q0, '1');
		q2.addBranch(q0, '1');

		// End route
		q3.addBranch(q3, '0');
		q3.addBranch(q3, '1');
		q3.isFinal = true;
	}

	public boolean execute(String s) {
		return q0.execute(s);
	}

	public static void main(String[] args) {
		ThreeConsecutiveZero automa = new ThreeConsecutiveZero();
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + automa.execute(args[i]));
	}
}