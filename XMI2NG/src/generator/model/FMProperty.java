package generator.model;

import java.util.ArrayList;
import java.util.List;

import example.ParserEngine;



public class FMProperty extends FMNamedElement  {
	//Tip obelezja
	//TODO Razmislisti da li je bolje da type bude string ili referenca na FMType
	private String type;
	
	//kadrinalitet (kompletan string)
	//TODO Razmisliti o enum
	private String visibility; 
	
	//donja granica
	private Integer lower;
	
	//gornja granica 
	//konvencija: -1 znači *
	private Integer upper;
	
	//anotacije
	private List<FMAnnotation> annotations = new ArrayList<FMAnnotation>();

	
	//TODO Dodati duzinu, preciznost, indikator da li se radi o kljucu itd...

	
	/**
	 * 
	 * @param name 
	 * @param type
	 * @param visibility private, protected, public, ?
	 * @param lower Donja granica kardinaliteta
	 * @param upper Gornja granica kardinaliteta. -1 znači *
	 */
	public FMProperty(String name, String type, 
			String visibility, int lower, int upper) {
		super(name);

		this.type = type;
		this.visibility = visibility;

		this.lower = lower;
		this.upper = upper;	
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
		return ParserEngine.isPrimitive(type);
	}
}
