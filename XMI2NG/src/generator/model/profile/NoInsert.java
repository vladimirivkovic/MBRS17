package generator.model.profile;

public class NoInsert extends UIProperty {
	
	public class Calculated extends NoInsert {
		protected String formula;
		
		public void setFormula(String formula) {
			this.formula = formula;
		}
		
		public String getFormula() {
			return formula;
		}
	}

}
