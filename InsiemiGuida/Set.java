import java.util.ArrayList;

public class Set<T>{
	private ArrayList<T> elements =new ArrayList<>();
	public Set(){
		addSet(null);
	}
	public Set(Set<T> setToAdd){
		addSet(setToAdd);
	}
	public Set(T toAdd){
		addElement(toAdd);
	}

	public boolean addSet(Set<T> setToAdd){
		if(setToAdd==null || setToAdd.isEmpty())
			return false;

		boolean somethingChanged=false;
		for(T tmp:setToAdd.elements)
			somethingChanged=somethingChanged||addElement(tmp);

		return somethingChanged;
	}

	public boolean addElement(T toAdd){
		if(elements==null)
			return false;
		else if(!elements.contains(toAdd)){
			elements.add(toAdd);
			return true;
		}
		return false;
	}

	public boolean isEmpty(){
		return elements.isEmpty();
	}

}