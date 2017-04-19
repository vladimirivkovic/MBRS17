package example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;

import example.Engine.STATE;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import generator.TestModel;
import generator.model.FMAnnotation;
import generator.model.FMAssociation;
import generator.model.FMClass;
import generator.model.FMMethod;
import generator.model.FMNamedElement;
import generator.model.FMParameter;
import generator.model.FMProperty;
import generator.model.FMType;

public class Engine {

	private static Map<String, FMType> types = new HashMap<String, FMType>();
	private static Map<String, FMNamedElement> elementMap = new HashMap<>();
	private static Stack<FMNamedElement> elementStack = new Stack<FMNamedElement>();

	private static String[] templates = { "ng-controller.ftl",
			"ng-resource.ftl", "ng-view.ftl", "app.ftl" };
	private static String[] tplnames = { "Ctrl.js", "Rsrc.js", "View.html", ".js" };

	public enum STATE {
		IDLE, CLASS, ATTRIBUTE, LOWER, LOWERX, UPPER, UPPERX, TYPE, CLASSIFIER, DATATYPE, ASSOCIATION, AEND
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
			//System.out.println("lower");
			//System.out.println(current);
			if (current == STATE.ATTRIBUTE || current == STATE.AEND) {
				
				current = STATE.LOWERX;
			}
			break;

		case UPPER:
			if (current == STATE.UPPER)
				current = STATE.UPPERX;
			break;

		case TYPE:
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
					// System.out.println(value);
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

			FMAssociation a = new FMAssociation(name);
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
				//System.out.println(value);
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
			// System.out.println(value);

			break;

		case LOWERX:
			el = elementStack.peek();
			value = Integer.parseInt(characters);
			if (el instanceof FMProperty) {
				((FMProperty) el).setLower(value);
				//System.out.println(value);
			} else if (el instanceof FMAssociation) {
				asoc = (FMAssociation) el;
				if (asoc.getSecondEnd() == null) {
					asoc.getFirstEnd().setLower(value);
				} else {
					asoc.getSecondEnd().setLower(value);
				}
			}
			// System.out.println(value);
			current = STATE.UPPER;
			break;

		default:
			// if (!"".equals(characters))System.out.println(characters);
			break;
		}
	}

	static void generate() {
		// Prvo je potrebno konfigurisati FM
		Configuration cfg = new Configuration();

		// Šablone ćemo učitavati sa classpath-a iz foldera templates
		cfg.setTemplateLoader(new ClassTemplateLoader(TestModel.class,
				"template"));

		// Potrebno je postaviti tzv. ObjectWrapper. U većini slučajeva me
		// dovoljno da
		// to bude DefaultObjectWrapper
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		// Sada je potrebno kreirati model podataka. To su podaci koji će biti
		// renderovani na mestima u šablonu koji nisu statičke prirode.
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> model2 = new HashMap<String, Object>();
		model2.put("classes", new ArrayList<FMClass>());

		File f = new File("generated");

		if (!f.exists()) {
			System.out.println("creating directory: " + f.getName());
			boolean result = false;

			try {
				f.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		File fa = new File("generated/app");

		if (!fa.exists()) {
			System.out.println("creating directory: " + fa.getName());
			boolean result = false;

			try {
				fa.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		// name, package, visibility
		FMClass cl = new FMClass("City", "ordering", "public");
		for (FMNamedElement el : elementMap.values()) {
			if (el instanceof FMClass) {
				cl = (FMClass) el;
				
				((ArrayList<FMClass>) model2.get("classes")).add(cl);

				model.clear();

				model.put("class", cl);
				model.put("properties", cl.getProperties());
				model.put("methods", cl.getMethods());

				try {
					// Uzimamo šablon
					Template temp = cfg.getTemplate("model.ftl");

					// Renderujemo ga
					// Writer out = new OutputStreamWriter(System.out);
					FileWriter fw = new FileWriter(new File("generated/"
							+ cl.getName() + ".cs"));
					temp.process(model, fw);
					fw.flush();
					fw.close();

					File f1 = new File("generated/app/" + cl.getLowerName());

					if (!f1.exists()) {
						System.out.println("creating directory: "
								+ f1.getName());
						boolean result = false;

						try {
							f1.mkdir();
							result = true;
						} catch (SecurityException se) {
							// handle it
						}
						if (result) {
							System.out.println("DIR created");
						}
					}

					for (int i = 0; i < templates.length; i++) {
						Template tempx = cfg.getTemplate(templates[i]);
						FileWriter fwx = new FileWriter(new File(
										"generated/app/" + cl.getLowerName() + "/"
										+ cl.getLowerName() + tplnames[i]));
						tempx.process(model, fwx);
						fwx.flush();
						fwx.close();
					}

				} catch (IOException e) {
					// TODO cfg.getTemplate može izazvati ovaj izuzetak ukoliko
					// šablon
					// nije pronađen.
					e.printStackTrace();
				} catch (TemplateException e) {
					// TODO template.process može da izazove ovaj izuzetak. Npr.
					// zbog
					// sintaksne greške u šablonu.
					e.printStackTrace();
				}
			}
		}
		
		try {
			Template temp = cfg.getTemplate("ng-app.ftl");
			FileWriter fw = new FileWriter(new File("generated/app/app.js"));
			temp.process(model2, fw);
			fw.flush();
			fw.close();
			
			temp = cfg.getTemplate("index.ftl");
			fw = new FileWriter(new File("generated/app/index.html"));
			temp.process(model2, fw);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO cfg.getTemplate može izazvati ovaj izuzetak ukoliko
			// šablon
			// nije pronađen.
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO template.process može da izazove ovaj izuzetak. Npr.
			// zbog
			// sintaksne greške u šablonu.
			e.printStackTrace();
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

			c2.addProperty(new FMProperty(a.getName(), end1.getTypeId(), end1
					.getVisibility(), end1.getLower(), end1.getUpper()));
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
}
