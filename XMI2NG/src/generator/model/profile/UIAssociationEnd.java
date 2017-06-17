package generator.model.profile;

public class UIAssociationEnd extends UIElement {
	protected Boolean jsonIgnore = false;
	
	public UIAssociationEnd(String label, Boolean visible, UIElementType uIType) {
		super(label, visible, uIType);
	}

	public Boolean getJsonIgnore() {
		return jsonIgnore;
	}

	public void setJsonIgnore(Boolean jsonIgnore) {
		this.jsonIgnore = jsonIgnore;
	}

}
