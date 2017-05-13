package generator.model.profile;

public class UIProperty extends UIElement {
	protected Boolean showColumn;
	protected String toolTip;
	protected Boolean copyable;
	protected Boolean searchable;
	protected Boolean required;
	protected Boolean unique;
	
	public UIProperty() {
		// TODO Auto-generated constructor stub
	}

	public UIProperty(Boolean showColumn, String toolTip, Boolean copyable,
			Boolean searchable, Boolean required, Boolean unique) {
		super();
		this.showColumn = showColumn;
		this.toolTip = toolTip;
		this.copyable = copyable;
		this.searchable = searchable;
		this.required = required;
		this.unique = unique;
	}

	public Boolean getShowColumn() {
		return showColumn;
	}

	public void setShowColumn(Boolean showColumn) {
		this.showColumn = showColumn;
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public Boolean getCopyable() {
		return copyable;
	}

	public void setCopyable(Boolean copyable) {
		this.copyable = copyable;
	}

	public Boolean getSearchable() {
		return searchable;
	}

	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

}
