package _1_dfa;

public class _5_ {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);
			if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')))
				state = -1; // In case is not in the alphabets

			switch (state) {
				case 0: // Start
					if (ch >= '0' && ch <= '9')
						state = 7; // pit
					else if ((ch >= 'a' && ch <= 'k') || (ch >= 'A' && ch <= 'K'))
						state = 1;
					else if ((ch >= 'l' && ch <= 'z') || (ch >= 'L' && ch <= 'Z'))
						state = 2;
					break;
				case 1: // Corso A (A-K)
					if (ch >= '0' && ch <= '9') {
						if ((ch - '0') % 2 == 0)
							state = 4;
						else
							state = 3;
					}
					break;
				case 2: // Corso B (L-Z)
					if (ch >= '0' && ch <= '9') {
						if ((ch - '0') % 2 == 0)
							state = 6;
						else
							state = 5;
					}
					break;
				case 3: // T1
					if (ch >= '0' && ch <= '9') {
						if ((ch - '0') % 2 == 0)
							state = 4;
					} else
						state = 7;
					break;
				case 4: // T2
					if (ch >= '0' && ch <= '9') {
						if ((ch - '0') % 2 != 0)
							state = 3;
					} else
						state = 7;
					break;
				case 5: // T3
					if (ch >= '0' && ch <= '9') {
						if ((ch - '0') % 2 == 0)
							state = 6;
					} else
						state = 7;
					break;
				case 6: // T4
					if (ch >= '0' && ch <= '9') {
						if ((ch - '0') % 2 != 0)
							state = 5;
					} else
						state = 7;
					break;
				case 7: // Pit -> nothing
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
