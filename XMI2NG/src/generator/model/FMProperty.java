package generator.model;

import java.util.ArrayList;
import java.util.List;

import generator.ParserEngine;
import generator.model.profile.Calculated;
import generator.model.profile.Id;
import generator.model.profile.Lookup;
import generator.model.profile.Next;
import generator.model.profile.NoInsert;
import generator.model.profile.ReadOnly;
import generator.model.profile.Stereotype;
import generator.model.profile.Tab;
import generator.model.profile.Zoom;



public class FMProperty extends FMNamedElement  {
	private String type;
	private String visibility; 
	private Integer lower = 0;
	private Integer upper = 1;
	
	//Stereotypes
	private Id id;
	private Lookup lookup;
	private ReadOnly readOnly;
	private NoInsert noInsert;
	private Next next;
	private Zoom zoom;
	private Calculated calculated;
	private Tab tab;
	
	//anotacije
	private List<FMAnnotation> annotations = new ArrayList<FMAnnotation>();
	
	private String associationId = null;

	
	
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
		return ParserEngine.getType(type);
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
		return ParserEngine.isPrimitive(type);
	}
	
	public String getAssociationId() {
		return associationId;
	}
	
	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}
	
	
	public Id getId() {
		return id;
	}

	public Lookup getLookup() {
		return lookup;
	}

	public ReadOnly getReadOnly() {
		return readOnly;
	}

	public NoInsert getNoInsert() {
		return noInsert;
	}

	public Next getNext() {
		return next;
	}

	public Zoom getZoom() {
		return zoom;
	}

	public Calculated getCalculated() {
		return calculated;
	}

	public Tab getTab() {
		return tab;
	}

	@Override
	public void addStereotype(Stereotype st) {
		super.addStereotype(st);
		
		if (st instanceof Id) {
			id = (Id) st;
			
		} else if(st instanceof Lookup) {
			lookup = (Lookup) st;
			
		} else if(st instanceof ReadOnly) {
			readOnly = (ReadOnly) st;
			
		} else if(st instanceof NoInsert) {
			noInsert = (NoInsert) st;
			
		} else if(st instanceof Next) {
			next = (Next) st;
			
		} else if(st instanceof Zoom) {
			zoom = (Zoom) st;
			
		} else if(st instanceof Calculated) {
			calculated = (Calculated) st;
			
		} else if(st instanceof Tab) {
			tab = (Tab) st;
			
		}
		
	}
}
