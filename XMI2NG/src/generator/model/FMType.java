package generator.model;

public class FMType extends FMNamedElement {	
	
	private String namespace;

	public String getNamespace() {
		return namespace;
	}

	/**
	 * 
	 * @param name naziv tipa
	 * @param typePackage kvalifikovano ime paketa koji treba ukljuciti, 
				prazan string ako je tip podataka iz stadnardne biblioteke
	 */
	public FMType(String name, String namespace) {
		super(name);
		this.namespace = namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	//kvalifikovano ime paketa koji treba ukljuciti, 
	//prazan string ako je tip podataka iz stadnardne biblioteke
	

}
