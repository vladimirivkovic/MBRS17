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
import generator.model.profile.UIElement;
import generator.model.profile.UIProperty;
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
	
	private UIProperty uIProperty = new UIProperty();
	
	//anotacije
	private List<FMAnnotation> annotations = new ArrayList<FMAnnotation>();
	
	private String associationId = null;

	private FMProperty inverseProperty = null;
	
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
	
	public FMClass getFMClass() {
		System.out.println(ParserEngine.getClass(type).getName());
		return ParserEngine.getClass(type);
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

	public UIProperty getuIProperty() {
		return uIProperty;
	}
	
	public Boolean getIsClass(){
		return ParserEngine.getElementMap().get(type) instanceof FMClass;
		
	}

	@Override
	public void addStereotype(Stereotype st) {
		super.addStereotype(st);
		
		
		if (st instanceof Id) {
			id = (Id) st;
			setUIElementAttributes((UIElement) id);
			setUIPropertyAttributes((UIProperty) id);

			
		} else if(st instanceof Lookup) {
			lookup = (Lookup) st;
			setUIElementAttributes((UIElement) lookup);
			setUIPropertyAttributes((UIProperty) lookup);

			
		} else if(st instanceof ReadOnly) {
			readOnly = (ReadOnly) st;
			setUIElementAttributes((UIElement) readOnly);
			setUIPropertyAttributes((UIProperty) readOnly);
			
		} else if(st instanceof NoInsert) {
			noInsert = (NoInsert) st;
			setUIElementAttributes((UIElement) noInsert);
			setUIPropertyAttributes((UIProperty) noInsert);
			
		} else if(st instanceof Next) {
			next = (Next) st;
			setUIElementAttributes((UIElement) next);
			
		} else if(st instanceof Zoom) {
			zoom = (Zoom) st;
			setUIElementAttributes((UIElement) zoom);
			
		} else if(st instanceof Calculated) {
			calculated = (Calculated) st;
			setUIElementAttributes((UIElement) calculated);
			setUIPropertyAttributes((UIProperty) calculated);
			
		} else if(st instanceof Tab) {
			tab = (Tab) st;
			setUIElementAttributes((UIElement) tab);
			
		} else if(st instanceof UIProperty) {
			UIProperty uip = (UIProperty) st;
			setUIElementAttributes((UIElement) uip);
			setUIPropertyAttributes(uip);
			
		}
		
	}
	
	private void setUIElementAttributes(UIElement uie){
		if(this.uIProperty.getVisible() && !uie.getVisible()){	//menja u slucaju da ga niko nije postavio na false...
			this.uIProperty.setRequired(false);
		}
		
		if(this.uIProperty.getLabel().equals("") && !uie.getLabel().equals("")){ //menja label u slucaju da ga niko nije postavio na razlicito od ""
			this.uIProperty.setLabel(uie.getLabel());
			
		}
		
		//TODO: implementirati za component!
		
	}
	
	private void setUIPropertyAttributes(UIProperty uip){
		if(!this.uIProperty.getShowColumn() && uip.getShowColumn()){ //menja showColumn ako ga niko nije postavio na true
			this.uIProperty.setShowColumn(true);
			
		}
		
		if(!this.uIProperty.getToolTip().equals("") && !uip.getToolTip().equals("")){
			this.uIProperty.setToolTip(uip.getToolTip());
			
		}
		
		if(!this.uIProperty.getCopyable() && uip.getCopyable()){  //menja u slucaju da ga niko nije postavio na true...
			this.uIProperty.setCopyable(true);
			
		}
		
		if(!this.uIProperty.getSearchable() && uip.getSearchable()){ //menja u slucaju da ga niko nije postavio na true...
			this.uIProperty.setSearchable(true);
			
		}
		
		if(!this.uIProperty.getRequired() && uip.getRequired()){ //menja u slucaju da ga niko nije postavio na true...
			this.uIProperty.setRequired(true);
			
		}
		
		if(!this.uIProperty.getUnique() && uip.getUnique()){ //menja u slucaju da ga niko nije postavio na true...
			this.uIProperty.setUnique(true);
			
		}
	}
	
	public FMProperty getInverseProperty() {
		return inverseProperty;
	}
	
	public void setInverseProperty(FMProperty inverseProperty) {
		this.inverseProperty = inverseProperty;
	}
	
}
