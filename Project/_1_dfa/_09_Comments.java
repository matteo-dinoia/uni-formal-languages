package _1_dfa;

public class _09_Comments {

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
			case 0: // Start
				if (ch == '/')
					state = 1;
				else if (ch == 'a' || ch == '*')
					state = 5; //pit
				else
					state = -1;
				break;
			case 1: // Found /
				if (ch == '*')
					state = 2;
				else if (ch == 'a' || ch == '/')
					state = 5; //pit
				else
					state = -1;
				break;
			case 2: // Found /*
				if (ch == '*')
					state = 3;
				else if (ch == 'a' || ch == '/')
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
					state = 4;
				else
					state = -1;
				break;
			case 4: // End comment
			case 5: // Pit
				if (ch == 'a' || ch == '/' || ch == '*')
					state = 5; //pit
				else
					state = -1;
				break;
			}
		}
		return state == 4;
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}