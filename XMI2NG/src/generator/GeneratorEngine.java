package generator;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import generator.model.FMClass;
import generator.model.FMNamedElement;
import generator.model.FMProperty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneratorEngine {
	private static String[] templates = { "ng-controller.ftl",
		"ng-resource.ftl", "ng-view.ftl", "app.ftl", "ng-modalctrl.ftl", "ng-modalview.ftl" };
	private static String[] tplnames = { "Ctrl.js", "Rsrc.js", "View.html", ".js", "ModalCtrl.js", "ModalView.html" };

	/**
	 * Generate folder structure and apply freemarker templates
	 */
	
	@SuppressWarnings("unchecked")
	static void generate(Map<String, FMNamedElement> elementMap) {
		Configuration cfg = new Configuration();

		cfg.setTemplateLoader(new ClassTemplateLoader(GeneratorEngine.class,
				"template"));

		cfg.setObjectWrapper(new DefaultObjectWrapper());

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
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		// name, package, visibility - dummy values
		FMClass cl = new FMClass("City", "ordering", "public");
		for (FMNamedElement el : elementMap.values()) {
			if (el instanceof FMClass) {
				cl = (FMClass) el;
				
				((ArrayList<FMClass>) model2.get("classes")).add(cl);
				
				System.out.println("********GENERATING for " + cl.getName());

				model.clear();

				model.put("class", cl);
				model.put("properties", cl.getProperties());
				model.put("methods", cl.getMethods());

				try {
					Template temp = cfg.getTemplate("model.ftl");

					// Rendering
					FileWriter fw = new FileWriter(new File("generated/"
							+ cl.getName() + ".cs"));
					temp.process(model, fw);
					fw.flush();
					fw.close();

					/**
					 * New folder for each class in AngularJS app structure
					 */
					File f1 = new File("generated/app/" + cl.getLowerName());

					if (!f1.exists()) {
						System.out.println("creating directory: "
								+ f1.getName() + "/modal");
						boolean result = false;

						try {
							f1.mkdir();
							result = true;
							
							File fm = new File("generated/app/" + cl.getLowerName() + "/modal");
							fm.mkdir();
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
					
					for (FMProperty p : cl.getProperties()) {
						if (!p.getPrimitive()) {
							model.clear();
							model.put("prop", p);
							model.put("class", (FMClass) elementMap.get(p.getTypeId()));
							
							Template tempx = cfg.getTemplate("chooseModalView.ftl");
							FileWriter fwx = new FileWriter(new File(
									"generated/app/" + cl.getLowerName() + "/modal/"
									+ p.getName() + "ModalView.html"));
							tempx.process(model, fwx);
							fwx.flush();
							fwx.close();
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * Generating ng-app
		 */
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
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
