package generator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FMEnumeration extends FMType {

	private List<String> literals = new ArrayList<String>();
	
	public FMEnumeration(String name, String namespace) {
		super(name, namespace);
		
		// TODO Auto-generated constructor stub
	}
	
	public void addLiteral(String literal){
		literals.add(literal);
		
	}
	
	public List<String> getLiterals(){
		return literals;
		
	}
	
	public Iterator<String> getLiteralsIterator(){
		return literals.iterator();
		
	}

}
