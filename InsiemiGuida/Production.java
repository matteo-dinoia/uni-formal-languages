import java.util.ArrayList;

public class Production{
	private Variable head=null;
	private Symbol[] body =null;
	private Set<Terminal> guide=null;
	public Production(Variable head, Symbol[] body){
		this.head=head;
		this.body=body;
	}

	public Variable getHead(){
		return head;
	}

	public Symbol[] getBody(){
		return body;
	}

	public boolean  addGuide(Set<Terminal> set){
		if(guide==null){
			if(set.isEmpty())
				return false;
			guide=new Set<Terminal>();
			return true;
		}
		else return guide.addSet(set);
	}

	public Set<Terminal> getGuide(){
		return guide;
	}
}
