package generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import generator.model.FMClass;
import generator.model.FMEnumeration;
import generator.model.FMNamedElement;
import generator.model.FMProperty;

public class GeneratorEngine {
	private static String[] templates = { "ng-controller.ftl", "ng-resource.ftl", "ng-view.ftl", "app.ftl",
			"ng-modalctrl.ftl", "ng-modalview.ftl" };
	private static String[] tplnames = { "Ctrl.js", "Rsrc.js", "View.html", ".js", "ModalCtrl.js", "ModalView.html" };

	/**
	 * Generate folder structure and apply freemarker templates
	 */

	@SuppressWarnings("unchecked")
	static void generate(Map<String, FMNamedElement> elementMap) {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

		cfg.setTemplateLoader(new ClassTemplateLoader(GeneratorEngine.class, "template"));

		// cfg.setObjectWrapper(new DefaultObjectWrapper());

		try {
			cfg.setSharedVariable("root", "");
			// cfg.setSharedVariable("root", "app/");
		} catch (TemplateModelException e1) {
			e1.printStackTrace();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> model2 = new HashMap<String, Object>();
		model2.put("classes", new ArrayList<FMClass>());

		ArrayList<FMEnumeration> enumerations = new ArrayList<FMEnumeration>();

		GeneratorUtil.createDirectory("generated");

		GeneratorUtil.createDirectory("generated/app");

		GeneratorUtil.createDirectory("generated/model");

		GeneratorUtil.createDirectory("generated/controller");

		// name, package, visibility - dummy values
		FMClass cl = new FMClass("City", "ordering", "public");
		FMEnumeration en = new FMEnumeration("dummyName", "dummyNamespace");

		for (FMNamedElement el : elementMap.values()) {
			if (el instanceof FMClass) {
				cl = (FMClass) el;

				((ArrayList<FMClass>) model2.get("classes")).add(cl);

				System.out.println("********GENERATING for " + cl.getName());

				model.clear();

				model.put("class", cl);
				model.put("properties", cl.getProperties());
				model.put("methods", cl.getMethods());
				model.put("constraints", cl.getConstraints());

				try {
					GeneratorUtil.generateFile("model.ftl", "generated/model/" + cl.getName() + ".cs", cfg, model);

					GeneratorUtil.generateFile("controller.ftl",
							"generated/controller/" + cl.getName() + "Controller.cs", cfg, model);

					/**
					 * New folder for each class in AngularJS app structure
					 */
					
					GeneratorUtil.createDirectory("generated/app/" + cl.getLowerName());
					GeneratorUtil.createDirectory("generated/app/" + cl.getLowerName() + "/modal");

					for (int i = 0; i < templates.length; i++) {
						GeneratorUtil.generateFile(templates[i], 
								"generated/app/" + cl.getLowerName() + "/" + cl.getLowerName() + tplnames[i], cfg, model);
					}
					
					for (FMProperty p : cl.getProperties()) {
						if (!p.getPrimitive()) {
							model.clear();
							model.put("prop", p);
							model.put("class", cl);
							model.put("propClass", (FMClass) elementMap.get(p.getTypeId()));

							GeneratorUtil.generateFile("chooseModalView.ftl", 
									"generated/app/" + cl.getLowerName() + "/modal/" + p.getName() + "ModalView.html", cfg, model);
	
							GeneratorUtil.generateFile("chooseModalCtrl.ftl", 
									"generated/app/" + cl.getLowerName() + "/modal/" + p.getName() + "ModalCtrl.html", cfg, model);
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					e.printStackTrace();
				}
			} else if (el instanceof FMEnumeration) {
				en = (FMEnumeration) el;

				enumerations.add(en);

				System.out.println("********GENERATING for " + en.getName());

				model.clear();
				model.put("enumeration", en);

				try {
					GeneratorUtil.generateFile("enumeration.ftl", 
							"generated/model/" + en.getName() + ".cs", cfg, model);

				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					e.printStackTrace();
				}

			}
		}

		/**
		 * generate login controller
		 */
		System.out.println("GENERATING LOGIN CONTROLLER BACKEND");
		try {
			GeneratorUtil.generateFile("loginController.ftl", 
					"generated/controller/" + "LoginController.cs", cfg, model);
		} catch (IOException ioExc) {
			ioExc.printStackTrace();
		} catch (TemplateException tmplExc) {
			tmplExc.printStackTrace();

		}

		/**
		 * Generating ng-app
		 */
		System.out.println("GENERATING login stuff on frontend");
		try{
			GeneratorUtil.createDirectory("generated/app/login/");
			
			GeneratorUtil.generateFile("loginViewFront.ftl","generated/app/login/"
					+"loginView.html",cfg,model2);
	 
			GeneratorUtil.generateFile("loginCtrlFront.ftl","generated/app/login/"
					+"loginCtrl.js",cfg,model2);
			
			GeneratorUtil.generateFile("authenticationServiceFront.ftl","generated/app/login/"
					+"AuthenticationService.js",cfg,model2);
		}
		catch(IOException ioExc)
		{
			ioExc.printStackTrace();
		}
		catch(TemplateException tmplExc)
		{
			tmplExc.printStackTrace();
		
		}
		
		try {
			model2.put("enumerations", enumerations);
			model2.put("groups", ParserEngine.groups);
			
			GeneratorUtil.generateFile("ng-app.ftl", 
					"generated/app/app.js", cfg, model2);
			
			GeneratorUtil.generateFile("appDBContext.ftl", 
					"generated/model/AppDBContext.cs", cfg, model2);
		
			GeneratorUtil.generateFile("index.ftl", 
					"generated/app/index.html", cfg, model2);
			
			GeneratorUtil.generateFile("main.ftl", 
					"generated/app/main.html", cfg, model2);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
