public abstract class Parser {
	private String w; // stringa da riconoscere
	private int i; // indice del prossimo simbolo

	protected char peek(){ // legge il simbolo corrente
		return w.charAt(i);
	}

	protected void match(char a){ // controlla il simbolo corrente
		if (peek() == a) i++; else throw error();
	}

	public void parse(String v) { // avvia il parsing di v
		w = v + "$";
		i = 0;
		S();
		match('$');
	}

	protected abstract void S(); // simbolo iniziale della grammatica

	protected SyntaxError error() { // emette errore e interrompe
		return new Error("Syntax error");
	}
}
