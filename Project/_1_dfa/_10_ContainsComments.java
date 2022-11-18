package _1_dfa;

public class _10_ContainsComments {
	public static final int POZZO = 4;

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);
			if (!(ch == 'a' || ch == '/' || ch == '*'))
				state = -1; // In case is not in the alphabets

			switch (state) {
				case 0:
					if (ch == '/')
						state = 1;
					break;
				case 1:
					if (ch == '*')
						state = 2;
					else
						state = 0;
					break;
				case 2:
					if (ch == '*')
						state = 3;
					break;
				case 3:
					if (ch == 'a')
						state = 2;
					else if (ch == '*')
						state = 3;
					else
						state = 0;
					break;
				case POZZO:
					break;
			}
		}
		return state != -1 && (state == 0);
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
