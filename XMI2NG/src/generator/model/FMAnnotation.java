package generator.model;

import java.util.ArrayList;
import java.util.List;

public class FMAnnotation extends FMNamedElement {
	
	private List<FMParameter> parameters = new ArrayList<>();
	
	public FMAnnotation(String name) {
		super(name);
	}
	
	public List<FMParameter> getParameters() {
		return parameters;
	}
	
	public void addParameter(FMParameter parameter) {
		this.parameters.add(parameter);
	}
	
	public int getSize() {
		return this.parameters.size();
	}

}
