package generator.model;

import java.util.ArrayList;
import java.util.List;

public class FMMethod extends FMNamedElement {

	private String visibility;
	
	private FMType returnType;
	
	private List<FMParameter> parameters = new ArrayList<>();
	
	public FMMethod(String name, String visibility, FMType returnType) {
		super(name);
		
		this.visibility = visibility;
		this.returnType = returnType;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public FMType getReturnType() {
		return returnType;
	}

	public void setReturnType(FMType returnType) {
		this.returnType = returnType;
	}
	
	public List<FMParameter> getParameters() {
		return parameters;
	}
	
	public void addParameter(FMParameter parameter) {
		this.parameters.add(parameter);
	}

}
