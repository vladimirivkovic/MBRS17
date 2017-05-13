package generator.model;

import java.util.ArrayList;
import java.util.List;

import example.ParserEngine2;



public class FMProperty extends FMNamedElement  {
	private String type;
	
	private String visibility; 
	
	private Integer lower = 0;
	
	private Integer upper = 1;
	
	//anotacije
	private List<FMAnnotation> annotations = new ArrayList<FMAnnotation>();
	
	private String associationId = null;

	
	//TODO Dodati duzinu, preciznost, indikator da li se radi o kljucu itd...
	
	public FMProperty(String name, String type, 
			String visibility, int lower, int upper) {
		super(name);

		this.type = type;
		this.visibility = visibility;

		this.lower = lower;
		this.upper = upper;	
	}

	public FMProperty(String name, String type, 
			String visibility, int lower, int upper, String associationId) {
		super(name);

		this.type = type;
		this.visibility = visibility;

		this.lower = lower;
		this.upper = upper;	
		this.associationId = associationId;
	}
	
	public String getType() {
		return ParserEngine2.getType(type);
		//return type;
	}
	public String getTypeId() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
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
	
	public List<FMAnnotation> getAnnotations() {
		return annotations;
	}
	
	public void addAnnotation(FMAnnotation annotation) {
		this.annotations.add(annotation);
	}
	
	public String getCapName() {
		String s = this.getName();
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public boolean getPrimitive() {
		return ParserEngine2.isPrimitive(type);
	}
	
	public String getAssociationId() {
		return associationId;
	}
	
	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}
}
