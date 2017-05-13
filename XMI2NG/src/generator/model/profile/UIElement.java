package generator.model.profile;

public class UIElement implements Stereotype {
	
	public enum UIElementType {
		TEXT_FIELD, RADIO_BUTTON, COMBO_BOX, DATE_PICKERM, CHECK_BOX, BUTTON, LABEL, MENU, MENU_ITEM
	}
	
	protected String label;
	protected Boolean visible = true;
	protected UIElementType component;
	

}
