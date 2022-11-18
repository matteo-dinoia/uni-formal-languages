package _1_dfa;

public class _2_Identificatori {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);
			switch (state) {
				case 0: // Start
					if (ch >= '0' && ch <= '9')
						state = 3;
					else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
						state = 2;
					else if (ch == '_')
						state = 1;
					else
						state = -1;
					break;
				case 1: // _
					if (ch == '_')
						state = 1;
					else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
							|| (ch >= '0' && ch <= '9'))
						state = 2;
					else
						state = -1;
					break;
				case 2: // final
					if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
							|| (ch >= '0' && ch <= '9') || ch == '_')
						state = 2;
					else
						state = -1;
					break;
				case 3: // pit
					if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
							|| (ch >= '0' && ch <= '9') || ch == '_')
						state = 3;
					else
						state = -1;
					break;
			}
		}
		return state == 2;
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			System.out.println("'" + args[i] + "':" + scan(args[i]));
	}
}
