import java.util.ArrayList;

public class Variable extends Symbol{
	private ArrayList<Production> productions=new ArrayList<>();
	private Set<Terminal> first=null, follow=null;
	private Boolean nullable=null;

	public Variable(String name) {
		super(name);
	}

	public Set<Terminal> getFirst(){
		return first;
	}
	public Set<Terminal> getFollow(){
		return follow;
	}


	public boolean addFirst(Set<Terminal> set){
		if(first==null){
			first=new Set<Terminal>();
			return true;
		}
		return first.addSet(set);
	}
	public boolean  addFollow(Set<Terminal> set){
		if(follow==null){
			follow=new Set<Terminal>();
		}
		return follow.addSet(set);
	}

	public void addProduction(Symbol productionBody[]){
		productions.add(new Production(this, productionBody));
	}

	public ArrayList<Production> getProductions(){
		return productions;
	}

	@Override
	public boolean isVariable() {
		return true;
	}

	@Override
	public Boolean isNullable() {
		return nullable;
	}

	public void setNullable(Boolean b) {
		this.nullable=b;
	}

}