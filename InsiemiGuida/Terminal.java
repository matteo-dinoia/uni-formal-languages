
public class Terminal extends Symbol{

	public Terminal(String name) {
		super(name);
	}


	public Set<Terminal> getFirst(){
		return new Set<Terminal>(this);
	}
	public Set<Terminal> getFollow() {
		return new Set<Terminal>();
	}


	@Override
	public boolean isVariable() {
		return false;
	}


	@Override
	public Boolean isNullable() {
		return false;
	}
}