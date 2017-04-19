package generator.model;

public abstract class FMNamedElement {
	
	private String name;
	
	public FMNamedElement(String name) {
		this.name = name;
		clearName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}
