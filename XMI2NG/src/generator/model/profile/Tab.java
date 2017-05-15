package generator.model.profile;

public class Tab extends UIAssociationEnd {
	protected Boolean dependant;
	
	public Tab(String label, Boolean visible, UIElementType uIType) {
		super(label, visible, uIType);
	}

	public void setDependant(boolean dependant) {
		this.dependant = dependant;
	}
	
	public boolean getDependant() {
		return dependant;
	}
}
