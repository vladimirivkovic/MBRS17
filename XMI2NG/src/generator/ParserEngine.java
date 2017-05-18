package generator;

import generator.model.FMAssociation;
import generator.model.FMClass;
import generator.model.FMEnumeration;
import generator.model.FMNamedElement;
import generator.model.FMProperty;
import generator.model.FMType;
import generator.model.profile.BusinessOperation;
import generator.model.profile.Calculated;
import generator.model.profile.Id;
import generator.model.profile.Lookup;
import generator.model.profile.Next;
import generator.model.profile.NoInsert;
import generator.model.profile.ReadOnly;
import generator.model.profile.Tab;
import generator.model.profile.UIAssociationEnd;
import generator.model.profile.UIClass;
import generator.model.profile.UIElement;
import generator.model.profile.UIElement.UIElementType;
import generator.model.profile.UIGroup;
import generator.model.profile.UIProperty;
import generator.model.profile.Zoom;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;

public class ParserEngine {

	private static Map<String, FMType> types = new HashMap<String, FMType>();
	private static Map<String, FMNamedElement> elementMap = new HashMap<>();
	private static Stack<FMNamedElement> elementStack = new Stack<FMNamedElement>();

	public enum STATE {
		IDLE, MODEL, PACKED_ELEMENT, CLASS, ASSOCIATION, OWNED_ATTRIBUTE, TYPE, OWNED_END, MEMBER_END, LOWER, UPPER, STEREOTYPE, OWNED_LITERAL
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
				System.out.println("###################Class : " + name);
				types.put(xmiId, c);

			} else if (xmiType.equals("uml:Association")) {
				FMAssociation a = new FMAssociation(name, xmiId);
				elementMap.put(xmiId, a);
				elementStack.push(a);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@Association "
						+ name);
				current = STATE.OWNED_END;
			} else if (xmiType.equals("uml:Enumeration")) {
				if (name == null) {
					current = STATE.IDLE;
					break;
				}

				FMEnumeration e = new FMEnumeration(name, "default");
				elementMap.put(xmiId, e);
				elementStack.push(e);

				current = STATE.OWNED_LITERAL;
				System.out.println("###################Enumeration : " + name);
				types.put(xmiId, e);
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

			System.out.println("Attribute " + name);
			FMProperty p = new FMProperty(name, type, visibility, 0, 1,
					associationId);
			((FMClass) elementStack.peek()).addProperty(p);
			elementMap.put(xmiId, p);
			elementStack.push(p);

			// current = STATE.LOWER;
			break;

		case OWNED_LITERAL:
			// System.out.println(current);
			if (current != STATE.OWNED_LITERAL)
				break;

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("name")) {
					name = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("xmi:id")) {
					xmiId = attributes.getValue(i);
				} else if (attributes.getQName(i).equals("type")) {
					type = attributes.getValue(i);
				}

			}

			System.out.println("Literal " + name);

			((FMEnumeration) elementStack.peek()).addLiteral(name);

			break;

		case LOWER:
			if (current != STATE.OWNED_ATTRIBUTE && current != STATE.OWNED_END)
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
		//TODO: VLADIMIREEEE BOTH_END_NAVIGABLE
		// case MEMBER_END: 
		//
		// if (current != STATE.OWNED_END)
		// break;
		//
		// for (int i = 0; i < attributes.getLength(); i++) {
		// if (attributes.getQName(i).equals("xmi:idref")) {
		// name = attributes.getValue(i);
		// }
		// }
		//
		// FMAssociation as = (FMAssociation) elementStack.peek();

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

			FMProperty endProperty = new FMProperty(
					name == null ? as.getName() == null ? elementMap.get(type)
							.getName() : as.getName() : name, type, visibility,
					0, 1, associationId);
			elementMap.put(xmiId, endProperty);

			if (as.getFirstEnd() == null) {
				// System.out.println("first end");
				as.setFirstEnd(endProperty);
			} else {
				// System.out.println("second end");
				as.setSecondEnd(endProperty);
			}

			// current = STATE.TYPE;
			break;

		case STEREOTYPE:

			break;

		default:
			break;
		}

	}

	public static void handleEnd(STATE state) {
		switch (state) {
		case MODEL:
			// add names for noname attributes
			for (FMNamedElement el : elementMap.values()) {
				if (el instanceof FMProperty) {
					FMProperty p = (FMProperty) el;

					if (p.getName() == null) {
						p.setName(p.getType());
					}
				}
			}

			break;
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

							a.getFirstEnd().setUpper(
									prop.getUpper() == -1 ? 1 : -1);

							secondEndClass.addProperty(a.getFirstEnd());
							
							a.setSecondEnd(prop);
							a.getFirstEnd().setInverseProperty(a.getSecondEnd());
							a.getSecondEnd().setInverseProperty(a.getFirstEnd());

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

	}

	public static void handleStereotype(String qName, Attributes attributes) {
		String baseClass = null, baseProperty = null, baseElement = null, baseOperation = null;

		for (int i = 0; i < attributes.getLength(); i++) {
			if (attributes.getQName(i).equals("base_Class")) {
				baseClass = attributes.getValue(i);
			} else if (attributes.getQName(i).equals("base_Property")) {
				baseProperty = attributes.getValue(i);
			} else if (attributes.getQName(i).equals("base_Element")) {
				baseElement = attributes.getValue(i);
			} else if (attributes.getQName(i).equals("base_Operation")) {
				baseOperation = attributes.getValue(i);
			}
		}

		String label = "";
		Boolean visible = true;
		UIElementType uIElementType = null;

		for (int i = 0; i < attributes.getLength(); i++) {
			if (attributes.getQName(i).equals("label")) {
				label = attributes.getValue(i);

			} else if (attributes.getQName(i).equals("visible")) {
				visible = "true".equals(attributes.getValue(i));

			} else if (attributes.getQName(i).equals("component")) {
				uIElementType = UIElementType.valueOf(attributes.getValue(i));

			}

		}

		// System.out.println(qName);

		switch (qName) {
		case "_:UIElement":
			if (elementMap.containsKey(baseElement)) {
				UIElement e = new UIElement(label, visible, uIElementType);

				elementMap.get(baseElement).addStereotype(e);

			}
			break;
		case "_:UIClass":
			UIClass c = new UIClass(label, visible, uIElementType);
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("create")) {
					c.setCreate("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("update")) {
					c.setUpdate("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("delete")) {
					c.setDelete("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("copy")) {
					c.setCopy("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("rowsPerPage")) {
					c.setRowsPerPage(Integer.parseInt(attributes.getValue(i)));

				}
			}
			// System.out.println("UICLASS na " +
			// elementMap.get(baseClass).getName());
			elementMap.get(baseClass).addStereotype(c);

			break;

		case "_:UIProperty":
			if (elementMap.containsKey(baseProperty)) {
				UIProperty p = new UIProperty(label, visible, uIElementType);

				for (int i = 0; i < attributes.getLength(); i++) {
					if (attributes.getQName(i).equals("showColumn")) {
						p.setShowColumn("true".equals(attributes.getValue(i)));

					} else if (attributes.getQName(i).equals("toolTip")) {
						p.setToolTip(attributes.getValue(i));

					} else if (attributes.getQName(i).equals("copyable")) {
						p.setCopyable("true".equals(attributes.getValue(i)));

					} else if (attributes.getQName(i).equals("searchable")) {
						p.setSearchable("true".equals(attributes.getValue(i)));

					} else if (attributes.getQName(i).equals("required")) {
						p.setRequired("true".equals(attributes.getValue(i)));

					} else if (attributes.getQName(i).equals("unique")) {
						p.setUnique("true".equals(attributes.getValue(i)));

					}

				}
				elementMap.get(baseProperty).addStereotype(p);

			}

			break;

		case "_:UIAssociationEnd":
			elementMap.get(baseProperty).addStereotype(
					new UIAssociationEnd(label, visible, uIElementType));

			break;

		case "_:Lookup":
			elementMap.get(baseProperty).addStereotype(
					new Lookup(label, visible, uIElementType));

			break;

		case "_:ReadOnly":
			ReadOnly r = new ReadOnly(label, visible, uIElementType);

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("showColumn")) {
					r.setShowColumn("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("toolTip")) {
					r.setToolTip(attributes.getValue(i));

				} else if (attributes.getQName(i).equals("copyable")) {
					r.setCopyable("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("searchable")) {
					r.setSearchable("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("required")) {

					r.setRequired("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("unique")) {
					r.setUnique("true".equals(attributes.getValue(i)));

				}
			}
			elementMap.get(baseProperty).addStereotype(r);

			break;
		case "_:NoInsert":
			NoInsert n = new NoInsert(label, visible, uIElementType);

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("showColumn")) {
					n.setShowColumn("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("toolTip")) {
					n.setToolTip(attributes.getValue(i));

				} else if (attributes.getQName(i).equals("copyable")) {
					n.setCopyable("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("searchable")) {
					n.setSearchable("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("required")) {
					n.setRequired("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("unique")) {
					n.setUnique("true".equals(attributes.getValue(i)));

				}
			}
			elementMap.get(baseProperty).addStereotype(n);

			break;

		case "_:Calculated":
			if (elementMap.containsKey(baseProperty)) {
				Calculated cal = new Calculated(label, visible, uIElementType);

				for (int i = 0; i < attributes.getLength(); i++) {
					if (attributes.getQName(i).equals("showColumn")) {
						cal.setShowColumn("true".equals(attributes.getValue(i)));

					} else if (attributes.getQName(i).equals("toolTip")) {
						cal.setToolTip(attributes.getValue(i));

					} else if (attributes.getQName(i).equals("copyable")) {
						cal.setCopyable("true".equals(attributes.getValue(i)));

					} else if (attributes.getQName(i).equals("searchable")) {
						cal.setSearchable("true".equals(attributes.getValue(i)));

					} else if (attributes.getQName(i).equals("required")) {
						cal.setRequired("true".equals(attributes.getValue(i)));

					} else if (attributes.getQName(i).equals("unique")) {
						cal.setUnique("true".equals(attributes.getValue(i)));

					}
				}
				elementMap.get(baseProperty).addStereotype(cal);

			}
			break;

		case "_:Id":
			Id id = new Id(label, visible, uIElementType);

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("showColumn")) {
					id.setShowColumn("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("toolTip")) {
					id.setToolTip(attributes.getValue(i));

				} else if (attributes.getQName(i).equals("copyable")) {
					id.setCopyable("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("searchable")) {
					id.setSearchable("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("required")) {
					id.setRequired("true".equals(attributes.getValue(i)));

				} else if (attributes.getQName(i).equals("unique")) {
					id.setUnique("true".equals(attributes.getValue(i)));

				}
			}
			elementMap.get(baseProperty).addStereotype(id);

			break;

		case "_:Zoom":
			elementMap.get(baseProperty).addStereotype(
					new Zoom(label, visible, uIElementType));

			break;

		case "_:Next":
			elementMap.get(baseProperty).addStereotype(
					new Next(label, visible, uIElementType));

			break;

		case "_:Tab":
			Tab t = new Tab(label, visible, uIElementType);

			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("dependant")) {
					t.setDependant("true".equals(attributes.getValue(i)));
				}
			}
			// System.out.println("tab na " +
			// elementMap.get(baseProperty).getName());
			elementMap.get(baseProperty).addStereotype(t);

			break;

		case "_:UIGroup":
			elementMap.get(baseProperty).addStereotype(new UIGroup());

			break;

		case "_:BusinessOperation":
			elementMap.get(baseOperation)
					.addStereotype(new BusinessOperation());

			break;

		case "_:Report":
			elementMap.get(baseOperation)
					.addStereotype(new BusinessOperation());

			break;

		case "_:Transaction":
			elementMap.get(baseOperation)
					.addStereotype(new BusinessOperation());

			break;

		default:
			break;

		}

	}

	public static FMClass getClass(String typeId) {
		if (types.containsKey(typeId))
			if (types.get(typeId) instanceof FMClass)
				return (FMClass) types.get(typeId);
		return null;
	}
}
