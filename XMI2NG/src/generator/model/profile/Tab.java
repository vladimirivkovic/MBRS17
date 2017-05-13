package generator.model.profile;

public class Tab extends UIAssociationEnd {
	protected Boolean dependant;
	
	public void setDependant(boolean dependant) {
		this.dependant = dependant;
	}
	
	public boolean getDependant() {
		return dependant;
	}
}
