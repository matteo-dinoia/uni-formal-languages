public abstract class Parser {
	private String w; // stringa da riconoscere
	private int i; // indice del prossimo simbolo

	protected char peek(){ // legge il simbolo corrente
		return w.charAt(i);
	}

	protected void match(char a){ // controlla il simbolo corrente
		if (peek() == a) i++; else throw error();
	}

	public final void parse(String v) { // avvia il parsing di v
		w = v + "$";
		i = 0;
		start();
	}

	protected abstract void start(); // simbolo iniziale della grammatica

	private Error error() { // emette errore e interrompe
		return new Error("Syntax error");
	}

	public Error guideError(String functionName){
		return new Error("Error in guide of function: "
			+functionName+" character found: '"+peek()+"'");
	}

	public void executeTest(String[] lineToTest){
		if(lineToTest==null)
			return;

		String line;
		for(int i=0; i<lineToTest.length; i++){
			line=lineToTest[i];
			try{
				this.parse(line);
				System.out.println("Linea \""+line+"\" riconoscituta");
			}
			catch(Error e){
				System.out.println("Linea \""+line+"\" non riconoscituta:\n\t"+e.getMessage());
			}
		}

	}
}
