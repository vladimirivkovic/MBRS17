package generator.model;

public abstract class FMNamedElement {
	
	private String name;
	
	public FMNamedElement(String name) {
		this.name = name;
	}

	public String getName() {
		return name.replace(' ', '_');
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean hasName() {
		return name != null;
	}

	
}
