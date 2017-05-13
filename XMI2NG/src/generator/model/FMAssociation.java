package generator.model;

public class FMAssociation extends FMNamedElement {
	private FMProperty firstEnd = null;
	private FMProperty secondEnd = null;
	private boolean firstNavigable;
	private boolean secondNavigable;
	private String id;

	public FMAssociation(String name, String id) {
		super(name);
		this.id = id;
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

	public boolean isFirstNavigable() {
		return firstNavigable;
	}

	public void setFirstNavigable(boolean firstNavigable) {
		this.firstNavigable = firstNavigable;
	}

	public boolean isSecondNavigable() {
		return secondNavigable;
	}

	public void setSecondNavigable(boolean secondNavigable) {
		this.secondNavigable = secondNavigable;
	}
	
	public String getId() {
		return id;
	}

}
