public abstract class Symbol{
	private String name;
	public Symbol(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}
	public abstract boolean isVariable();
	public abstract Set<Terminal> getFirst();
	public abstract Set<Terminal> getFollow();
	public abstract Boolean isNullable();
}
