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
		IDLE, MODEL, PACKED_ELEMENT, CLASS, ASSOCIATION, OWNED_ATTRIBUTE, TYPE, OWNED_END, LOWER, UPPER
	}

	private static STATE current = STATE.IDLE;

	public static Stack<String> keys = new Stack<>();

	public static void handleStart(STATE state, Attributes attributes) {
		String name = null, visibility = "public", type = null;
		String xmiId = null, xmiType = null, href = null;
		String associationId = null;
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
				} else if (attributes.getQName(i).equals("xmi:id")) {
					xmiId = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("visibility")) {
					visibility = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi:type")) {
					xmiType = attributes.getValue(i);
				}
			}

			if (xmiType == null) {
				current = STATE.IDLE;
				break;
			}
			// System.out.println("X : " + name);
			if (xmiType.equals("uml:Class")) {
				if (name == null) {
					current = STATE.IDLE;
					break;
				}

				FMClass c = new FMClass(name, "default", visibility);
				elementMap.put(xmiId, c);
				elementStack.push(c);

				current = STATE.OWNED_ATTRIBUTE;
				System.out.println("########Class : " + name);
				types.put(xmiId, c);

			} else if (xmiType.equals("uml:Association")) {
				FMAssociation a = new FMAssociation(name, xmiId);
				elementMap.put(xmiId, a);
				elementStack.push(a);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Association "
						+ name);
				current = STATE.OWNED_END;
			} else if (xmiType.equals("uml:Enumeration")) {
				// TODO : handle enumeration
			} else {
				current = STATE.IDLE;
			}

			break;

		case OWNED_ATTRIBUTE:
			// System.out.println(current);
			if (current != STATE.OWNED_ATTRIBUTE)
				break;

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi:id")) {
					xmiId = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("visibility")) {
					visibility = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("type")) {
					type = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("association")) {
					associationId = attributes.getValue(i);
				}

			}

			if (type != null && name == null) {
				name = "attr" + xmiId.substring(30);
			}

			if (name != null) {
				System.out.println("Attribute " + name);
				FMProperty p = new FMProperty(name, type, visibility, 0, 1,
						associationId);
				((FMClass) elementStack.peek()).addProperty(p);
				elementMap.put(xmiId, p);
				elementStack.push(p);
			}

			// current = STATE.LOWER;
			break;

		case LOWER:
			if (current != STATE.OWNED_ATTRIBUTE)
				break;

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
			// current = STATE.UPPER;
			break;

		case UPPER:
			if (current != STATE.OWNED_ATTRIBUTE && current != STATE.OWNED_END)
				break;

			el = elementStack.peek();
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("value")) {
					try {
						value = Integer.parseInt(attributes.getValue(i));
					} catch (NumberFormatException nfe) {
						if ("*".equals(attributes.getValue(i))) {
							value = -1;
						}
					}
				}
			}

			if (el instanceof FMProperty) {
				((FMProperty) el).setUpper(value);
				// current = STATE.OWNED_ATTRIBUTE;
			} else if (el instanceof FMAssociation) {
				asoc = (FMAssociation) el;

				if (asoc.getSecondEnd() == null) {
					asoc.getFirstEnd().setUpper(value);
				} else {
					asoc.getSecondEnd().setUpper(value);
				}
				current = STATE.OWNED_END;
			}
			System.out.println("upper : " + value);
			break;

		case TYPE:
			if (current != STATE.OWNED_ATTRIBUTE)
				break;

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("referentPath")) {
					href = attributes.getValue(i);
				}
			}
			String[] words = href.split("::");
			// System.out.println("Type: " + type);
			((FMProperty) elementStack.peek()).setType(words[words.length - 1]);

			break;

		case OWNED_END:

			if (current != STATE.OWNED_END)
				break;

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi:id")) {
					xmiId = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("type")) {
					type = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("visibility")) {
					visibility = attributes.getValue(i);
				}
			}

			FMAssociation as = (FMAssociation) elementStack.peek();

			if (as.getFirstEnd() == null) {
				// System.out.println("first end");
				as.setFirstEnd(new FMProperty(name, type, visibility, 0, 1,
						associationId));
			} else {
				// System.out.println("second end");
				as.setSecondEnd(new FMProperty(name, type, visibility, 0, 1,
						associationId));
			}

			// current = STATE.TYPE;
			break;
		default:
			break;
		}

	}

	public static void handleEnd(STATE state) {
		switch (state) {
		case PACKED_ELEMENT:
			if (current == STATE.IDLE) {
				break;
			}

			FMNamedElement e = elementStack.pop();

			if (e instanceof FMAssociation) {
				FMAssociation a = (FMAssociation) e;

				// connect second end
				if (a.getFirstEnd() != null && a.getSecondEnd() == null) {
					FMClass firstEndClass = (FMClass) elementMap.get(a
							.getFirstEnd().getTypeId());

					for (FMProperty prop : firstEndClass.getProperties()) {
						if (a.getId().equals(prop.getAssociationId())) {
							FMClass secondEndClass = (FMClass) elementMap
									.get(prop.getTypeId());

							secondEndClass.addProperty(new FMProperty(a
									.getName() == null ? firstEndClass.getName(): a.getName(), a
									.getFirstEnd().getTypeId(), "private", 0,
									prop.getUpper() == -1 ? 1 : -1));

							System.out.println(firstEndClass.getName()
									+ " added in " + secondEndClass.getName());
							current = STATE.IDLE;
							break;
						}
					}

				}
			}

			current = STATE.IDLE;

			break;

		case OWNED_ATTRIBUTE:
			if (current == STATE.OWNED_ATTRIBUTE) {
				elementStack.pop();
			}
			break;

		default:
			break;
		}
	}

	public static String getType(String typeId) {
		if (types.containsKey(typeId))
			return types.get(typeId).getName();
		else
			return typeId;
	}

	public static boolean isPrimitive(String typeId) {
		return !(types.get(typeId) instanceof FMClass);
	}

	public static Map<String, FMNamedElement> getElementMap() {
		return elementMap;
	}

	public static void handleCharacters(String characters) {
		// TODO Auto-generated method stub

	}
}
