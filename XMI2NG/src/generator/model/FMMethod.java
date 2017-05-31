package generator.model;

import java.util.ArrayList;
import java.util.List;

public class FMMethod extends FMNamedElement {

	private String visibility;
	
	private FMParameter returnType = new FMParameter(null, "void");
	
	private List<FMParameter> parameters = new ArrayList<>();
	
	public FMMethod(String name, String visibility, FMParameter returnType) {
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

	public String getReturnType() {
		return returnType.getType();
	}
	
	public String getReturnTypeId() {
		return returnType.getTypeId();
	}
	
	public List<FMParameter> getParameters() {
		return parameters;
	}
	
	public void addParameter(FMParameter parameter) {
		// TODO : chech parameter direction
		if (parameter.getName() == null) {
			this.returnType = parameter;
		} else {
			this.parameters.add(parameter);
		}
	}

}
