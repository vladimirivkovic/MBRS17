package generator.model;

import generator.model.profile.Stereotype;

import java.util.ArrayList;
import java.util.List;

public abstract class FMNamedElement {
	
	private String name;
	private String originName;
	
	protected List<Stereotype> stereotypes = new ArrayList<Stereotype>();
	
	public FMNamedElement(String name) {
		this.name = name;
		this.originName = name;
		clearName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.originName = name;
		clearName();
	}
	
	public Boolean hasName() {
		return name != null;
	}

	private void clearName() {
		if (name != null) {
			int i = 0;
			while (i < name.length()) {
				if (!Character.isJavaIdentifierPart(name.charAt(i))) {
					name = name.replace(name.charAt(i), '_');
				}
				i++;
			}
		}
	}
	
	public String getOriginName() {
		return originName;
	}
	
	public List<Stereotype> getStereotypes() {
		return stereotypes;
	}
	
	public void addStereotype(Stereotype st) {
		stereotypes.add(st);
	}
	
}
