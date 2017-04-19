package generator.model;

public class FMParameter extends FMNamedElement {
	
	private String type;
	private Boolean out;
	private Boolean ref;
	
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
	
	

}
