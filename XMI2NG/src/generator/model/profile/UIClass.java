package generator.model.profile;

public class UIClass extends UIElement {
	
	private Boolean create;
	private Boolean update;
	private Boolean copy;
	private Boolean delete;
	private int displayNumber;
	
	public UIClass() {
		// TODO Auto-generated constructor stub
	}
	
	public UIClass(Boolean create, Boolean update, Boolean copy, Boolean delete, int displayNumber) {
		super();
		this.create = create;
		this.update = update;
		this.copy = copy;
		this.delete = delete;
		this.displayNumber = displayNumber;
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

	public int getDisplayNumber() {
		return displayNumber;
	}

	public void setDisplayNumber(int displayNumber) {
		this.displayNumber = displayNumber;
	}
	
	

}
