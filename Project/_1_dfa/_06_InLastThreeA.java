package _1_dfa;

public class _06_InLastThreeA {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
			case 0: // Start
				if (ch == 'a')
					state = 3;
				else if (ch == 'b')
					state = 0;
				else
					state = -1;
				break;
			case 1:
				if (ch == 'a')
					state = 3;
				else if (ch == 'b')
					state = 0;
				else
					state = -1;
				break;
			case 2:
				if (ch == 'a')
					state = 3;
				else if (ch == 'b')
					state = 1;
				else
					state = -1;
				break;
			case 3:
				if (ch == 'a')
					state = 3;
				else if (ch == 'b')
					state = 2;
				else
					state = -1;
				break;
			}
		}
		return state == 1 || state == 2 || state == 3;
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
