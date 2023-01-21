package _1_dfa;

public class _05_LabTurnInverted {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
			case 0: // Start
				if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z'))
					state = 7; // pit
				else if (ch >= 'A' && ch <= 'K')
					state = 1;
				else if (ch >= 'L' && ch <= 'Z')
					state = 2;
				else
					state = -1;
				break;
			case 1: // Course A (A-K)
				if (ch >= '0' && ch <= '9') {
					if ((ch - '0') % 2 == 0)
						state = 4;
					else
						state = 3;
				} else if (ch >= 'a' && ch <= 'z')
					state = 1;
				else if (ch >= 'A' && ch <= 'Z')
					state = 7; // pit
				else
					state = -1;
				break;
			case 2: // Course B (L-Z)
				if (ch >= '0' && ch <= '9') {
					if ((ch - '0') % 2 == 0)
						state = 6;
					else
						state = 5;
				} else if (ch >= 'a' && ch <= 'z')
					state = 2;
				else if (ch >= 'A' && ch <= 'Z')
					state = 7; // pit
				else
					state = -1;
				break;
			case 3: // T1
			case 4: // T2
				if (ch >= '0' && ch <= '9') {
					if ((ch - '0') % 2 == 0)
						state = 4;
					else
						state = 3;
				} else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
					state = 7; //pit
				else
					state = -1;
				break;
			case 5: // T3
			case 6: // T4
				if (ch >= '0' && ch <= '9') {
					if ((ch - '0') % 2 == 0)
						state = 6;
					else
						state = 5;
				} else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
					state = 7; //pit
				else
					state = -1;
				break;
			case 7: // Pit
				if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9'))
					state = 7; //pit
				else
					state = -1;
				break;
			}
		}
		return state == 4 || state == 5;
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
