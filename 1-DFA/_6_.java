
public class _6_ {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);
			if (!(ch == 'a' || ch == 'b'))
				state = -1; // In case is not in the alphabets

			switch (state) {
				case 0: // Start
					if (ch == 'a')
						state = 3;
					break;
				case 1:
				case 2:
				case 3:
					if (ch == 'a')
						state = 3;
					else
						state--;
					break;
			}
		}
		return state != -1 && (state != 0);
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
