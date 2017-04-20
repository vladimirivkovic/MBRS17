package example;

import generator.model.FMAssociation;
import generator.model.FMClass;
import generator.model.FMNamedElement;
import generator.model.FMProperty;
import generator.model.FMType;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;

public class ParserEngine2 {

	private static Map<String, FMType> types = new HashMap<String, FMType>();
	private static Map<String, FMNamedElement> elementMap = new HashMap<>();
	private static Stack<FMNamedElement> elementStack = new Stack<FMNamedElement>();

	public enum STATE {
		IDLE, MODEL, PACKED_ELEMENT, CLASS, ASSOCIATION, OWNED_ATTRIBUTE, TYPE, MEMBER_END, LOWER, UPPER
		//CLASS, ATTRIBUTE, LOWER, LOWERX, UPPER, UPPERX, TYPE, CLASSIFIER, DATATYPE, ASSOCIATION, AEND, NAVIGABLE
	}

	private static STATE current = STATE.IDLE;

	public static Stack<String> keys = new Stack<>();

	public static void handleStart(STATE state, Attributes attributes) {
		String name = null, visibility = null;
		String xmiId = null, xmiType = null, href = null;
		FMNamedElement el;
		FMAssociation asoc = null;
		int value = 0;

		switch (state) {
		case MODEL:
			current = STATE.PACKED_ELEMENT;
			break;
		
		case PACKED_ELEMENT:
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi.id")) {
					xmiId = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("visibility")) {
					visibility = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi:type")) {
					xmiType = attributes.getValue(i);
				}
			}
			
			if (xmiType == null) {
				break;
			}
			//System.out.println("X : " + name);
			if (xmiType.contentEquals("uml:Class")) {
				FMClass c = new FMClass(name, "default", visibility);
				elementMap.put(xmiId, c);
				elementStack.push(c);

				current = STATE.OWNED_ATTRIBUTE;
				System.out.println("Class : " + name);
				types.put(xmiId, c);
				
			} else if (xmiType.contentEquals("uml:Association")) {
				FMAssociation a = new FMAssociation(name);
				elementMap.put(xmiId, a);
				elementStack.push(a);
				System.out.println("Association " + name);
				current = STATE.MEMBER_END;
			}

			

			break;

		case OWNED_ATTRIBUTE:
			if (current != STATE.OWNED_ATTRIBUTE) break;

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi.id")) {
					xmiId = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("visibility")) {
					visibility = attributes.getValue(i);
				}
			}
			System.out.println("Attribute " + name);
			FMProperty p = new FMProperty(name, null, visibility, 0, 1);
			((FMClass) elementStack.peek()).addProperty(p);
			elementMap.put(xmiId, p);
			elementStack.push(p);

			current = STATE.LOWER;
			break;

		case LOWER:
			if (current != STATE.LOWER) break;
			
			el = elementStack.peek();
			
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("value")) {
					value = Integer.parseInt(attributes.getValue(i));
				}
			}
			
			if (el instanceof FMProperty) {
				((FMProperty) el).setLower(value);
			} else if (el instanceof FMAssociation) {
				asoc = (FMAssociation) el;
				
				if (asoc.getSecondEnd() == null) {
					asoc.getFirstEnd().setLower(value);
				} else {
					asoc.getSecondEnd().setLower(value);
				}
			}
			System.out.println("lower : " + value);
			current = STATE.UPPER;
			break;

		case UPPER:
			if (current != STATE.UPPER) break;
			
			el = elementStack.peek();
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("value")) {
					value = Integer.parseInt(attributes.getValue(i));
				}
			}
			
			if (el instanceof FMProperty) {
				((FMProperty) el).setUpper(value);
				current = STATE.OWNED_ATTRIBUTE;
			} else if (el instanceof FMAssociation) {
				asoc = (FMAssociation) el;
				
				if (asoc.getSecondEnd() == null) {
					asoc.getFirstEnd().setUpper(value);
				} else {
					asoc.getSecondEnd().setUpper(value);
				}
				current = STATE.MEMBER_END;
			}
			System.out.println("upper : " + value + current);
			break;

		case TYPE:
			if (current != STATE.OWNED_ATTRIBUTE) break;
			
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("href")) {
					href = attributes.getValue(i);
				}
			}
			System.out.println("Type : " + href.split("#")[1]);
			break;

		case MEMBER_END:

//			for (int i = 0; i < attributes.getLength(); i++) {
//				if (attributes.getQName(i).equals("name")) {
//					name = attributes.getValue(i);
//				} else if (attributes.getQName(i).equals("xmi.id")) {
//					id = attributes.getValue(i);
//				} else if (attributes.getQName(i).equals("visibility")) {
//					visibility = attributes.getValue(i);
//				}
//			}
//
//			FMAssociation as = (FMAssociation) elementStack.peek();
//			
//			if (as.getFirstEnd() == null) {
//				// System.out.println("first end");
//				as.setFirstEnd(new FMProperty(name, null, visibility, 0, 0));
//			} else {
//				// System.out.println("second end");
//				as.setSecondEnd(new FMProperty(name, null, visibility, 0, 0));
//			}

			// current = STATE.TYPE;
			break;
		default:
			break;
		}

	}

	public static void handleEnd(STATE state) {
		FMNamedElement e = elementStack.pop();
		//current = STATE.CLASS;

		if (e instanceof FMAssociation) {
			FMAssociation a = (FMAssociation) e;
			FMProperty end1 = a.getFirstEnd();
			FMProperty end2 = a.getSecondEnd();

			String type1 = end1.getTypeId();
			String type2 = end2.getTypeId();

			FMClass c1 = (FMClass) types.get(type1);
			FMClass c2 = (FMClass) types.get(type2);

			if (a.isSecondNavigable())
				c2.addProperty(new FMProperty(a.getName(), end1.getTypeId(), end1
					.getVisibility(), end1.getLower(), end1.getUpper()));
		
			if (a.isFirstNavigable())
				c1.addProperty(new FMProperty(a.getName(), end2.getTypeId(), end2
					.getVisibility(), end2.getLower(), end2.getUpper()));
		}
	}

	public static String getType(String typeId) {
		return types.get(typeId).getName();
	}
	
	public static boolean isPrimitive(String typeId) {
		return !(types.get(typeId) instanceof FMClass);
	}
	
	public static Map<String, FMNamedElement> getElementMap() {
		return elementMap;
	}
}
