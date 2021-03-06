package generator.model;

import java.util.ArrayList;
import java.util.List;

import generator.model.profile.Report;
import generator.model.profile.Stereotype;
import generator.model.profile.Transaction;

public class FMMethod extends FMNamedElement {

	private String visibility;
	
	private FMParameter returnType = new FMParameter(null, "void");
	
	private Boolean isStatic = false;
	private Boolean isAbstract = false;
	
	private List<FMParameter> parameters = new ArrayList<>();
	
	private Report report;
	private Transaction transaction;
	
	public FMMethod(String name, String visibility, FMParameter returnType, Boolean isStatic) {
		super(name);
		
		this.visibility = visibility;
		this.returnType = returnType;
		this.isStatic = isStatic;
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
	
	public boolean getVoid() {
		return "void".equals(returnType.getTypeId());
	}
	
	public String getReturnTypeId() {
		return returnType.getTypeId();
	}
	
	public List<FMParameter> getParameters() {
		return parameters;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}
	
	public Report getReport() {
		return report;
	}
	
	public boolean getStatic() {
		return isStatic;
	}
	
	public boolean getAbstract() {
		return isAbstract;
	}
	
	public void addParameter(FMParameter parameter) {
		// TODO : chech parameter direction
		if (parameter.getName() == null) {
			this.returnType = parameter;
		} else {
			this.parameters.add(parameter);
		}
	}
	
	@Override
	public void addStereotype(Stereotype st) {
		super.addStereotype(st);
		
		if (st instanceof Transaction) {
			transaction = (Transaction) st;
		} else if(st instanceof Report) {
			report = (Report) st;
		}
	}

}
