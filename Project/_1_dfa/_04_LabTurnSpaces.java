package _1_dfa;

public class _04_LabTurnSpaces {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
			case 0: // Start
				if (ch == ' ')
					state = 0;
				else if (ch >= '0' && ch <= '9') {
					if ((ch - '0') % 2 == 0)
						state = 1;
					else
						state = 2;
				} else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
					state = 4;
				else
					state = -1;
				break;
			case 1: // Even
				if (ch == ' ')
					state = 5;
				else if (ch >= '0' && ch <= '9') {
					if ((ch - '0') % 2 == 0)
						state = 1;
					else
						state = 2;
				} else if (ch >= 'A' && ch <= 'K')
					state = 3;
				else if ((ch >= 'a' && ch <= 'z') || (ch >= 'L' && ch <= 'Z'))
					state = 4;
				else
					state = -1;
				break;
			case 2: // Odd
				if (ch == ' ')
					state = 6;
				else if (ch >= '0' && ch <= '9') {
					if ((ch - '0') % 2 == 0)
						state = 1;
					else
						state = 2;
				} else if (ch >= 'L' && ch <= 'Z')
					state = 3;
				else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'K'))
					state = 4;
				else
					state = -1;
				break;
			case 3: // T2 o T3
				if (ch == ' ')
					state = 7;
				else if (ch >= 'a' && ch <= 'z')
					state = 3;
				else if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z'))
					state = 4; // Second condition for avoiding surname with capitalized letter in the middle
				else
					state = -1;
				break;
			case 4: // pit
				if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
						|| (ch >= '0' && ch <= '9') || ch == ' ')
					state = 4;
				else
					state = -1;
				break;
			case 5: // Even with space after
				if (ch == ' ')
					state = 5;
				else if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z'))
					state = 4;
				else if (ch >= 'A' && ch <= 'K')
					state = 3;
				else if (ch >= 'L' && ch <= 'Z')
					state = 4;
				else
					state = -1;
				break;
			case 6: // Odd with space after
				if (ch == ' ')
					state = 6;
				else if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z'))
					state = 4;
				else if (ch >= 'A' && ch <= 'K')
					state = 4;
				else if (ch >= 'L' && ch <= 'Z')
					state = 3;
				else
					state = -1;
				break;
			case 7: // t2 o t3 spazio
				if (ch == ' ')
					state = 7;
				else if (ch >= 'A' && ch <= 'Z')
					state = 3;
				else if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z')) {
					state = 4;
				} else
					state = -1;
			}
		}
		return state == 3 || state == 7;
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
