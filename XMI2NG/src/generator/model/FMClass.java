package generator.model;

import generator.ParserEngine;
import generator.model.profile.Stereotype;
import generator.model.profile.UIClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FMClass extends FMType {

	private String visibility;

	private List<FMProperty> properties = new ArrayList<FMProperty>();

	private List<String> importedPackages = new ArrayList<String>();

	private List<FMMethod> methods = new ArrayList<FMMethod>();
	
	private List<FMConstraint> constraints = new ArrayList<FMConstraint>();

	private String parentId = null;
	
	private ArrayList<String> interfaceIds = new ArrayList<>();

	private UIClass uIClass;

	public FMClass(String name, String classPackage, String visibility) {
		super(name, classPackage);
		this.visibility = visibility;
	}

	public List<FMProperty> getProperties() {
		return properties;
	}

	public Iterator<FMProperty> getPropertyIterator() {
		return properties.iterator();
	}

	public void addProperty(FMProperty property) {
		properties.add(property);
	}

	public int getPropertyCount() {
		return properties.size();
	}

	public List<String> getImportedPackages() {
		return importedPackages;
	}

	public Iterator<String> getImportedIterator() {
		return importedPackages.iterator();
	}

	public void addImportedPackage(String importedPackage) {
		importedPackages.add(importedPackage);
	}

	public int getImportedCount() {
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

	public String getParent() {
		return ParserEngine.getType(parentId);
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLowerName() {
		String s = this.getName();
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	public UIClass getUIClass() {
		return uIClass;

	}
	
	public void addConstraint(FMConstraint constraint)
	{
		constraints.add(constraint);
	}

	public List<FMConstraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<FMConstraint> constraints) {
		this.constraints = constraints;
	}
	
	public void addInterfaceId(String interfaceId) {
		interfaceIds.add(interfaceId);
	}
	
	public ArrayList<String> getInterfaceIds() {
		return interfaceIds;
	}
	
	public ArrayList<String> getInterfaces() {
		ArrayList<String> interfaces = new ArrayList<String>();
		
		for (String interfaceId : interfaceIds) {
			interfaces.add(ParserEngine.getType(interfaceId));
		}
		
		return interfaces;
	}

	@Override
	public void addStereotype(Stereotype st) {
		super.addStereotype(st);
		if (st instanceof UIClass) {
			uIClass = (UIClass) st;
		}

	}

	public List<FMProperty> getLookupProperties() {
		ArrayList<FMProperty> lookups = new ArrayList<FMProperty>();

		for (FMProperty p : properties) {
			if (p.getLookup() != null) {
				lookups.add(p);
			}
		}

		return lookups;
	}

}
