package generator.model.profile;

public class Calculated extends NoInsert {
	private String formula;
	
	public Calculated(String label, Boolean visible, UIElementType uIType) {
		super(label, visible, uIType);
		
	}

	public Calculated(String formula) {
		super();
		this.formula = formula;
		
	}

	public String getFormula() {
		return formula;
		
	}

	public void setFormula(String formula) {
		this.formula = formula;
		
	}
	
}
