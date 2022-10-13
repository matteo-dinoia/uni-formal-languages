
public class Es1p7 {
	public static final int POZZO = 13;

	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);

			switch (state) {
				case 0: // START
					if (ch == 'M')
						state++;
					else
						state = 7;
					break;
				case 1:// M
				case 7:// M con * usato
					if (ch == 'a')
						state++;
					else
						state = state < 7 ? state + 7 : POZZO;
					break;
				case 2:// Ma
				case 8:// Ma con * usato
					if (ch == 't')
						state++;
					else
						state = state < 7 ? state + 7 : POZZO;
					break;
				case 3:// Mat
				case 9:// Mat con * usato
					if (ch == 't')
						state++;
					else
						state = state < 7 ? state + 7 : POZZO;
					break;
				case 4:// Matt
				case 10:// Matt con * usato
					if (ch == 'e')
						state++;
					else
						state = state < 7 ? state + 7 : POZZO;
					break;
				case 5:// Matte
				case 11:// Matte con * usato

					if (ch == 'o')
						state++;
					else
						state = state < 7 ? state + 7 : POZZO;
					break;
				case 6:// Matteo
				case 12:// Matteo con * usato
					state = POZZO;
					break;
				case POZZO: // Pozzo
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
