package _1_dfa;

public class _08_FloatingPoint {

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
			case 0: // Start
				if (ch == '+' || ch == '-')
					state = 1;
				else if (ch >= '0' && ch <= '9')
					state = 2;
				else if (ch == '.')
					state = 3;
				else if (ch == 'e')
					state = 10; //pit
				else
					state = -1;
				break;
			case 1: // Sign found
				if (ch >= '0' && ch <= '9')
					state = 2;
				else if (ch == '.')
					state = 3;
				else if (ch == 'e' || ch == '+' || ch == '-')
					state = 10; //pit
				else
					state = -1;
				break;
			case 2: // number before dot
				if (ch >= '0' && ch <= '9')
					state = 2;
				else if (ch == '.')
					state = 3;
				else if (ch == 'e')
					state = 5;
				else if (ch == '+' || ch == '-')
					state = 10; //pit
				else
					state = -1;
				break;
			case 3: // Found dot
				if (ch >= '0' && ch <= '9')
					state = 4;
				else if (ch == 'e' || ch == '+' || ch == '-' || ch == '.')
					state = 10; //pit
				else
					state = -1;
				break;
			case 4: // Number after dot
				if (ch >= '0' && ch <= '9')
					state = 4;
				else if (ch == 'e')
					state = 5;
				else if (ch == '+' || ch == '-' || ch == '.')
					state = 10; //pit
				else
					state = -1;
				break;
			case 5: // 'e', start
				if (ch == '+' || ch == '-')
					state = 6;
				else if (ch >= '0' && ch <= '9')
					state = 7;
				else if (ch == '.')
					state = 8;
				else if (ch == 'e')
					state = 10; //pit
				else
					state = -1;
				break;
			case 6: // 'e', sign found
				if (ch >= '0' && ch <= '9')
					state = 7;
				else if (ch == '.')
					state = 8;
				else if (ch == 'e' || ch == '+' || ch == '-')
					state = 10; //pit
				else
					state = -1;
				break;
			case 7: // 'e', number before dot
				if (ch >= '0' && ch <= '9')
					state = 7;
				else if (ch == '.')
					state = 8;
				else if (ch == 'e' || ch == '+' || ch == '-')
					state = 10; //pit
				else
					state = -1;
				break;
			case 8: // 'e', found dot
				if (ch >= '0' && ch <= '9')
					state = 9;
				else if (ch == 'e' || ch == '+' || ch == '-' || ch == '.')
					state = 10; //pit
				else
					state = -1;
				break;
			case 9: // 'e', number after dot
				if (ch >= '0' && ch <= '9')
					state = 9;
				else if (ch == 'e' || ch == '+' || ch == '-' || ch == '.')
					state = 10; //pit
				else
					state = -1;
				break;
			case 10: // Pit
				if ((ch >= '0' && ch <= '9') || ch == 'e' || ch == '-' || ch == '+' || ch == '.')
					state = 10;
				else
					state = -1;
				break;
			}
		}

		return state == 2 || state == 4 || state == 7 || state == 9;
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
