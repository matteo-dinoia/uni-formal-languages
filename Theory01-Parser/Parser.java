import java.io.*;

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

	private Error error() { // emette errore e interrompe
		return new Error("Syntax error");
	}

	public Error guideError(String functionName){
		return new Error("Error in guide of function: "
			+functionName+" character found: "+peek());
	}

	public void executeTest(){
		BufferedReader br=null;
		try {
			final String relativePath="input.txt";
			final String absolutePath=this.getClass().getResource(relativePath).toString().substring("file:".length());
			br = new BufferedReader(new FileReader(absolutePath));
			String line=br.readLine();
			while(line!=null){
				try{
					this.parse(line);
					System.out.println("Linea \""+line+"\" riconoscituta");
				}
				catch(Error e){
					System.out.println("Linea \""+line+"\" non riconoscituta:\n\t"+e.getMessage());
				}
				line=br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
