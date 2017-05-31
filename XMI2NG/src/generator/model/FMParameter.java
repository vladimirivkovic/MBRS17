package generator.model;

import generator.ParserEngine;

public class FMParameter extends FMNamedElement {
	
	private String type;
	private Boolean out = false;
	private Boolean ref = false;
	
	private Integer lower = 0;
	private Integer upper = 1;
	
	public FMParameter(String name, String type, 
			Boolean out, Boolean ref) {
		super(name);
		
		this.type = type;
		this.out = out;
		this.ref = ref;
	}
	
	public FMParameter(String name, String type) {
		super(name);
		
		this.type = type;
	}
	
	public String getType() {
		return ParserEngine.getType(type);
	}

	public String getTypeId() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public Boolean getOut() {
		return out;
	}

	public void setOut(Boolean out) {
		this.out = out;
	}

	public Boolean getRef() {
		return ref;
	}

	public void setRef(Boolean ref) {
		this.ref = ref;
	}
	
	public Integer getLower() {
		return lower;
	}
	
	public void setLower(Integer lower) {
		this.lower = lower;
	}
	
	public Integer getUpper() {
		return upper;
	}
	
	public void setUpper(Integer upper) {
		this.upper = upper;
	}

}
