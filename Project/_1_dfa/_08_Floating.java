package _1_dfa;

public class _08_Floating {
	public static final int POZZO = 10;

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			if (!((ch >= '0' && ch <= '9') || ch == 'e' || ch == '-' || ch == '+' || ch == '.'))
				state = -1;

			switch (state) {
				case 0: // start
				case 5: // e found and start
					if (ch == '+' || ch == '-')
						state++;
					else if (ch >= '0' && ch <= '9')
						state += 2;
					else if (ch == '.')
						state += 3;
					else
						state = POZZO;
					break;
				case 1: // segno trovato
				case 6: // e found and segno trovato
					if (ch >= '0' && ch <= '9')
						state += 1;
					else if (ch == '.')
						state += 2;
					else
						state = POZZO;
					break;
				case 2:// numero prima virgola
				case 7:// e found and numero prima virgola
					if (ch >= '0' && ch <= '9')
						;
					else if (ch == '.')
						state += 1;
					else if (ch == 'e')
						state = state < 5 ? 5 : 13;
					else
						state = POZZO;
					break;
				case 3:// virgola
				case 8:// e found and virgola
					if (ch >= '0' && ch <= '9')
						state += 1;
					else
						state = POZZO;
					break;
				case 4:// numero dopo virgola
				case 9:// e found and numero dopo virgola
					if (ch >= '0' && ch <= '9')
						;
					else if (ch == 'e')
						state = state < 5 ? 5 : 13;
					else
						state = POZZO;
					break;
				case POZZO:
					break;
			}
		}

		return state != -1 && (state == 2 || state == 7 || state == 4 || state == 9);
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
