package generator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FMClass extends FMType {	
	
	private String visibility;
	

	//Obelezja klase
	private List<FMProperty> properties = new ArrayList<FMProperty>();
	
	//Spisak paketa (za import deklaraciju klase) 
	private List<String> importedPackages = new ArrayList<String>();
	
	//spisak metoda
	private List<FMMethod> methods = new ArrayList<FMMethod>();
	
	//Omoguciti definisanje klase-pretka!
	private FMClass parent;
	//TODO Interfejsi
	
	public FMClass(String name, String classPackage, String visibility) {
		super(name, classPackage);		
		this.visibility = visibility;
	}	
	
	public List<FMProperty> getProperties(){
		return properties;
	}
	
	public Iterator<FMProperty> getPropertyIterator(){
		return properties.iterator();
	}
	
	public void addProperty(FMProperty property){
		properties.add(property);		
	}
	
	public int getPropertyCount(){
		return properties.size();
	}
	
	public List<String> getImportedPackages(){
		return importedPackages;
	}

	public Iterator<String> getImportedIterator(){
		return importedPackages.iterator();
	}
	
	public void addImportedPackage(String importedPackage){
		importedPackages.add(importedPackage);		
	}
	
	public int getImportedCount(){
		return properties.size();
	}
	
	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	public List<FMMethod> getMethods() {
		return methods;
	}
	
	public void addMethod(FMMethod method) {
		methods.add(method);
	}
	
	public FMClass getParent() {
		return parent;
	}
	
	public void setParent(FMClass parent) {
		this.parent = parent;
	}
	
	public String getLowerName() {
		String s = this.getName();
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
	
}
