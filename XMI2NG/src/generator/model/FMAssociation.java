package generator.model;

public class FMAssociation extends FMNamedElement {
	private FMProperty firstEnd = null;
	private FMProperty secondEnd = null;

	public FMAssociation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public FMProperty getFirstEnd() {
		return firstEnd;
	}

	public void setFirstEnd(FMProperty firstEnd) {
		this.firstEnd = firstEnd;
	}

	public FMProperty getSecondEnd() {
		return secondEnd;
	}

	public void setSecondEnd(FMProperty secondEnd) {
		this.secondEnd = secondEnd;
	}
	
	

}
