package generator.model.profile;

public class UIElement implements Stereotype {
	
	public enum UIElementType {
		TEXT_FIELD, RADIO_BUTTON, COMBO_BOX, DATE_PICKERM, CHECK_BOX, BUTTON, LABEL, MENU, MENU_ITEM
	}
	
	protected String label;
	protected Boolean visible = true;
	protected UIElementType component;
	
	public UIElement(String label, Boolean visible, UIElementType component) {
		super();
		this.label = label;
		this.visible = visible;
		this.component = component;
	}

	public UIElement() {
		super();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public UIElementType getComponent() {
		return component;
	}

	public void setComponent(UIElementType component) {
		this.component = component;
	}
	
}
