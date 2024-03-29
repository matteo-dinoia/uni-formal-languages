package _1_dfa;

public class _10_ContainsComments {

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
			case 0: // Start (or outside comment)
				if (ch == '/')
					state = 1;
				else if (ch == '*' || ch == 'a')
					state = 0;
				else
					state = -1;
				break;
			case 1: // Found /
				if (ch == '*')
					state = 2;
				else if (ch == '/' || ch == 'a')
					state = 0;
				else
					state = -1;
				break;
			case 2: // Found /* (in comment)
				if (ch == '*')
					state = 3;
				else if (ch == '/' || ch == 'a')
					state = 2;
				else
					state = -1;
				break;
			case 3: // Found closing *
				if (ch == 'a')
					state = 2;
				else if (ch == '*')
					state = 3;
				else if (ch == '/')
					state = 0;
				else
					state = -1;
				break;
			}
		}
		return state == 0 || state == 1;
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
