package generator;

import generator.model.FMAssociation;
import generator.model.FMClass;
import generator.model.FMNamedElement;
import generator.model.FMProperty;
import generator.model.FMType;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;

public class ParserEngine {

	private static Map<String, FMType> types = new HashMap<String, FMType>();
	private static Map<String, FMNamedElement> elementMap = new HashMap<>();
	private static Stack<FMNamedElement> elementStack = new Stack<FMNamedElement>();

	public enum STATE {
		IDLE, CLASS, ATTRIBUTE, LOWER, LOWERX, UPPER, UPPERX, TYPE, CLASSIFIER, DATATYPE, ASSOCIATION, AEND, NAVIGABLE
	}

	private static STATE current = STATE.IDLE;

	public static Stack<String> keys = new Stack<>();

	public static void handleStart(STATE state, Attributes attributes) {
		String name = null, id = null, visibility = null;

		switch (state) {
		case CLASS:
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi.id")) {
					id = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("visibility")) {
					visibility = attributes.getValue(i);
				}
			}

			FMClass c = new FMClass(name, "default", visibility);
			elementMap.put(id, c);
			elementStack.push(c);
			System.out.println("Class " + name);
			current = STATE.ATTRIBUTE;

			types.put(id, c);

			break;

		case DATATYPE:

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi.id")) {
					id = attributes.getValue(i);
				}
			}

			types.put(id, new FMType(name, ""));

			break;

		case ATTRIBUTE:

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi.id")) {
					id = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("visibility")) {
					visibility = attributes.getValue(i);
				}
			}
			//System.out.println("Attribute " + name);
			FMProperty p = new FMProperty(name, null, visibility, 0, 1);
			((FMClass) elementStack.peek()).addProperty(p);
			elementMap.put(id, p);
			elementStack.push(p);

			// current = STATE.LOWER;
			break;

		case LOWER:
			if (current == STATE.ATTRIBUTE || current == STATE.AEND) {
				// waiting for lower value element
				current = STATE.LOWERX;
			}
			break;

		case UPPER:
			if (current == STATE.UPPER) {
				// waiting for upper value element
				current = STATE.UPPERX;
			}
			break;

		case TYPE:
			// waiting for classifier element
			current = STATE.CLASSIFIER;
			break;

		case CLASSIFIER:
			if (current == STATE.CLASSIFIER) {
				FMNamedElement e = elementStack.peek();
				String value = "";

				for (int i = 0; i < attributes.getLength(); i++) {
					if (attributes.getQName(i).equals("xmi.idref")) {
						value = attributes.getValue(i);
					}
				}
				
				if (e instanceof FMProperty) {
					((FMProperty) e).setType(value);
					current = STATE.ATTRIBUTE;
				} else if (e instanceof FMAssociation) {
					FMAssociation aso = (FMAssociation) e;
					
					if (aso.getSecondEnd() == null) {
						aso.getFirstEnd().setType(value);
					} else {
						aso.getSecondEnd().setType(value);
					}
					current = STATE.AEND;
				}
			}
			break;

		case ASSOCIATION:
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi.id")) {
					id = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("visibility")) {
					visibility = attributes.getValue(i);
				}
			}

			FMAssociation a = new FMAssociation(name, null);
			elementMap.put(id, a);
			elementStack.push(a);
			System.out.println("Association " + name);
			current = STATE.AEND;

			break;

		case AEND:

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi.id")) {
					id = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("visibility")) {
					visibility = attributes.getValue(i);
				}
			}

			FMAssociation as = (FMAssociation) elementStack.peek();
			
			if (as.getFirstEnd() == null) {
				// System.out.println("first end");
				as.setFirstEnd(new FMProperty(name, null, visibility, 0, 0));
			} else {
				// System.out.println("second end");
				as.setSecondEnd(new FMProperty(name, null, visibility, 0, 0));
			}

			// current = STATE.TYPE;
			break;
		
		case NAVIGABLE:
			if (current == STATE.AEND) {
				FMNamedElement e = elementStack.peek();
				String value = "";

				for (int i = 0; i < attributes.getLength(); i++) {
					if (attributes.getQName(i).equals("xmi.value")) {
						value = attributes.getValue(i);
					}
				}
				
				if (e instanceof FMAssociation) {
					FMAssociation aso = (FMAssociation) e;
					
					if (aso.getSecondEnd() == null) {
						aso.setFirstNavigable(value.equals("true"));
						//System.out.println("first navigable " + value);
					} else {
						aso.setSecondNavigable(value.equals("true"));
						//System.out.println("second navigable " + value);
					}
				}
			}
			break;
		default:
			break;
		}

	}

	public static void handleCharacters(String characters) {
		FMNamedElement el;
		FMAssociation asoc = null;
		int value;

		switch (current) {
		case UPPERX:
			el = elementStack.peek();
			value = Integer.parseInt(characters);
			
			if (el instanceof FMProperty) {
				((FMProperty) el).setUpper(value);
				current = STATE.ATTRIBUTE;
			} else if (el instanceof FMAssociation) {
				asoc = (FMAssociation) el;
				
				if (asoc.getSecondEnd() == null) {
					asoc.getFirstEnd().setUpper(value);
				} else {
					asoc.getSecondEnd().setUpper(value);
				}
				current = STATE.AEND;
			}

			break;

		case LOWERX:
			el = elementStack.peek();
			value = Integer.parseInt(characters);
			
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
			
			current = STATE.UPPER;
			break;

		default:
			// if (!"".equals(characters))System.out.println(characters);
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
