package generator.model.profile;

public class UIClass extends UIElement {
	
	private Boolean create = true;
	private Boolean update = true;
	private Boolean copy = true;
	private Boolean delete = true;
	private int rowsPerPage;
	private String entityGroup = "Other entities";
	
	public UIClass() {
		// TODO Auto-generated constructor stub
	}
	
	public UIClass(Boolean create, Boolean update, Boolean copy, Boolean delete, int displayNumber) {
		super();
		this.create = create;
		this.update = update;
		this.copy = copy;
		this.delete = delete;
		this.rowsPerPage = displayNumber;
	}

	public UIClass(String label, Boolean visible, UIElementType uIType) {
		super(label, visible, uIType);
	}
	
	public String getEntityGroup() {
		return entityGroup;
	}

	public void setEntityGroup(String entityGroup) {
		this.entityGroup = entityGroup;
	}

	public Boolean getCreate() {
		return create;
	}

	public void setCreate(Boolean create) {
		this.create = create;
	}

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public Boolean getCopy() {
		return copy;
	}

	public void setCopy(Boolean copy) {
		this.copy = copy;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}
	
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
	

}
