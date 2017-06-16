package generator.model;

import generator.ParserEngine;

import java.util.ArrayList;
import java.util.List;

public class FMInterface extends FMType {
	
	private ArrayList<String> parentIds = new ArrayList<>();
	
	private List<FMMethod> methods = new ArrayList<FMMethod>();

	public FMInterface(String name, String namespace) {
		super(name, namespace);
		// TODO Auto-generated constructor stub
	}
	
	public void addParentId(String parentId) {
		parentIds.add(parentId);
	}
	
	public ArrayList<String> getParentIds() {
		return parentIds;
	}
	
	public ArrayList<String> getParents() {
		ArrayList<String> interfaces = new ArrayList<String>();
		
		for (String interfaceId : parentIds) {
			interfaces.add(ParserEngine.getType(interfaceId));
		}
		
		return interfaces;
	}
	
	public List<FMMethod> getMethods() {
		return methods;
	}

	public void addMethod(FMMethod method) {
		methods.add(method);
	}

}
