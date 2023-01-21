package _1_dfa;

public class _07_MyName {

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
			case 0: // START
				if (ch == 'M')
					state = 1;
				else
					state = 7;
				break;
			case 1:// M
				if (ch == 'a')
					state = 2;
				else
					state = 8;
				break;
			case 2:// Ma
				if (ch == 't')
					state = 3;
				else
					state = 9;
				break;
			case 3:// Mat
				if (ch == 't')
					state = 4;
				else
					state = 10;
				break;
			case 4:// Matt
				if (ch == 'e')
					state = 5;
				else
					state = 11;
				break;
			case 5:// Matte

				if (ch == 'o')
					state = 6;
				else
					state = 12;
				break;
			case 6:// Matteo
				state = 13; //pit
				break;
			case 7:// M with 1 wrong char
				if (ch == 'a')
					state = 8;
				else
					state = 13; //pit
				break;
			case 8:// Ma with 1 wrong char
				if (ch == 't')
					state = 9;
				else
					state = 13; //pit
				break;
			case 9:// Mat with 1 wrong char
				if (ch == 't')
					state = 10;
				else
					state = 13; //pit
				break;
			case 10:// Matt with 1 wrong char
				if (ch == 'e')
					state = 11;
				else
					state = 13; //pit
				break;
			case 11:// Matte with 1 wrong char

				if (ch == 'o')
					state = 12;
				else
					state = 13; //pit
				break;
			case 12:// Matteo with 1 wrong char
				state = 13;
				break;
			case 13: // Pit
				state = 13;
				break;
			}
		}

		return state != -1 && (state == 6 || state == 12);
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
