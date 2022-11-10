
public class GuideSet {

	public static void main(String[] args) {
		Variable A=new Variable("A");
		Symbol symbols[]={
			A
		};

		A.addProduction(
			new Symbol[]{

			}
		);

		new GuideSet(symbols).calculateAndPrintGuide();
	}

	private Symbol symbols[]=null;
	public GuideSet(Symbol[] terminalAndVariableWithProduction){

		this.symbols=terminalAndVariableWithProduction;
	}

	private void calculateAndPrintGuide() {
		if(symbols==null){
			return;
		}

		for(Symbol tmp: symbols)
			calculateNull(tmp);

		for(Symbol tmp: symbols)
			calculateFirst(tmp);

		for(Symbol tmp: symbols)
			calculateFollow(tmp);

		for(Symbol tmp: symbols){
			if(!tmp.isVariable())
				continue;

			for(Production prod : ((Variable)tmp).getProductions()){
				calculateGuide(prod);
			}

		}


		//PRINT
	}



	private Boolean calculateNulls(Symbol element[]){

	}

	private Set<Terminal> calculateFirst(Symbol list[]) {
		Set<Terminal> res=new Set();

		for(Symbol el:list){
			res.addSet(el.getFirst());
			if(el.)
		}


		return res;
	}

	private Set<Terminal> calculateFollow(Symbol element[]) {
		Set<Terminal> res=new Set();
	}

	private Set<Terminal> calculateGuide(Production element[]) {
		Set<Terminal> res=new Set();
	}

	private Boolean calculateNull(Symbol element){
		if(element.isNullable()!=null)
			return element.isNullable();

	}
	private Set<Terminal> calculateFirst(Symbol element) {
		if(element.getFirst()!=null)
			return element.getFirst();
	}
	private Set<Terminal> calculateFollow(Symbol element) {
		if(element.getFollow()!=null)
			return element.getFollow();
	}
	private Set<Terminal> calculateGuide(Production element) {
		if(element.getGuide()!=null)
			return element.getGuide();

	}

	/*
	private Boolean calculateNull(Symbol element){
		if(element.isNullable()!=null)
			return element.isNullable();

		Variable var=(Variable) element;
		var.setNullable(true);
		boolean isUnknown=false;
		for(Production prod : var.getProductions()){
			for(Symbol symbolInProd: prod.getBody()){
				if(calculateNull(symbolInProd)==false){
					var.setNullable(false);
					return false;
				}
				else if(calculateNull(symbolInProd)==null){
					isUnknown=true;
				}

			}

		}

		return isUnknown ? null : true;

	}

	private Set<Terminal> calculateFirst(Symbol element) {
		if(element.getFirst()!=null)
			return element.getFirst();

		Variable var=(Variable) element;
		var.addFirst(null);
		for(Production prod : var.getProductions()){
			for(Symbol symbolInProd: prod.getBody()){
				var.addFirst(calculateFirst(symbolInProd));
				if(symbolInProd.isNullable()==false){
					break;
				}
			}

		}

		return var.getFirst();
	}

	private Set<Terminal> calculateFollow(Symbol element) {
		if(element.getFollow()!=null)
			return element.getFollow();

		Variable var=(Variable) element;
		var.addFollow(null);
		for(Production prod : var.getProductions()){

			for(Symbol symbolInProd: prod.getBody()){
				var.addFirst(calculateFirst(symbolInProd));
				if(symbolInProd.isNullable()==false){
					nullableAll=true;
					break;
				}
			}

		}

		return element.getFollow(); //FIX
	}

	private Set<Terminal> calculateGuide(Production element) {
		if(element.getGuide()!=null)
			return element.getGuide();

		if(element.getHead().isNullable())
			element.addGuide(element.getHead().getFollow());
		else element.addGuide(null);
		for(Symbol symbolInProd: element.getBody()){
			if(!symbolInProd.isVariable()){
				element.addGuide(symbolInProd.getFirst());
				break;
			}
			else{
				for(Production prod : ((Variable)symbolInProd).getProductions())
					calculateGuide(prod);
			}

			if(symbolInProd.isNullable()==false){
				break;
			}
		}

		return element.getGuide();
	}*/


	//STOP INFINITE LOOP



}
