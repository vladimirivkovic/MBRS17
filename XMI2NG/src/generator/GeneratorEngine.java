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
import generator.model.FMInterface;
import generator.model.FMNamedElement;
import generator.model.FMProperty;

public class GeneratorEngine {
	private static String[] templates = { "frontend/ng-controller.ftl", "frontend/ng-resource.ftl", "frontend/ng-view.ftl", "frontend/app.ftl",
			"frontend/modal/ng-modalctrl.ftl", "frontend/modal/ng-modalview.ftl" };
	private static String[] tplnames = { "Ctrl.js", "Rsrc.js", "View.html", ".js", "ModalCtrl.js", "ModalView.html" };

	/**
	 * Generate folder structure and apply freemarker templates
	 * @param outputFolder 
	 */

	@SuppressWarnings("unchecked")
	static void generate(Map<String, FMNamedElement> elementMap, String outputFolder) {
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

		GeneratorUtil.createDirectory(outputFolder);

		GeneratorUtil.createDirectory(outputFolder + "/app");

		GeneratorUtil.createDirectory(outputFolder + "/backendConfig");
		
		GeneratorUtil.createDirectory(outputFolder + "/model");

		GeneratorUtil.createDirectory(outputFolder + "/controller");

		// name, package, visibility - dummy values
		FMClass cl = new FMClass("City", "ordering", "public");
		FMEnumeration en = new FMEnumeration("dummyName", "dummyNamespace");
		FMInterface in = new FMInterface("dummyName", "dummyNamespace");

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
				model.put("fieldGroups", GeneratorUtil.getFieldGroupsMap(cl));

				try {
					GeneratorUtil.generateFile("backend/model/model.ftl", outputFolder + "/model/" + cl.getName() + ".cs", cfg, model);
					GeneratorUtil.generateFile("backend/model/modelConstraints.ftl", outputFolder + "/model/" + cl.getName() + "_constraints.cs", cfg, model);
					GeneratorUtil.generateFile("backend/controllers/controller.ftl",
							outputFolder + "/controller/" + cl.getName() + "Controller.cs", cfg, model);
					
					/**
					 * New folder for each class in AngularJS app structure
					 */
					
					GeneratorUtil.createDirectory(outputFolder + "/app/" + cl.getLowerName());
					GeneratorUtil.createDirectory(outputFolder + "/app/" + cl.getLowerName() + "/modal");

					for (int i = 0; i < templates.length; i++) {
						GeneratorUtil.generateFile(templates[i], 
								outputFolder + "/app/" + cl.getLowerName() + "/" + cl.getLowerName() + tplnames[i], cfg, model);
					}
					
					for (FMProperty p : cl.getProperties()) {
						if (!p.getPrimitive()) {
							model.clear();
							model.put("prop", p);
							model.put("class", cl);
							model.put("propClass", (FMClass) elementMap.get(p.getTypeId()));

							GeneratorUtil.generateFile("frontend/modal/chooseModalView.ftl", 
									outputFolder + "/app/" + cl.getLowerName() + "/modal/" + p.getName() + "ModalView.html", cfg, model);
	
							GeneratorUtil.generateFile("frontend/modal/chooseModalCtrl.ftl", 
									outputFolder + "/app/" + cl.getLowerName() + "/modal/" + p.getName() + "ModalCtrl.js", cfg, model);
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
					GeneratorUtil.generateFile("backend/model/enumeration.ftl", 
							outputFolder + "/model/" + en.getName() + ".cs", cfg, model);

				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					e.printStackTrace();
				}

			} else if (el instanceof FMInterface) {
				in = (FMInterface) el;

				System.out.println("********GENERATING for " + in.getName());

				model.clear();
				model.put("interface", in);
				model.put("methods", in.getMethods());

				try {
					GeneratorUtil.generateFile("backend/model/interface.ftl", 
							outputFolder + "/model/" + in.getName() + ".cs", cfg, model);

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
		try {
			GeneratorUtil.generateFile("backend/controllers/loginController.ftl", 
					outputFolder + "/controller/" + "LoginController.cs", cfg, model);
			System.out.println("GENERATING LOGIN CONTROLLER BACKEND");
			GeneratorUtil.generateFile("backend/model/korisnik.ftl", 
					outputFolder + "/model/" + "Korisnik.cs", cfg, model);
			System.out.println("GENERATING KORISNIK BACKEND");
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
			GeneratorUtil.createDirectory(outputFolder + "/app/login/");
			
			GeneratorUtil.generateFile("frontend/login/loginViewFront.ftl",outputFolder + "/app/login/"
					+"loginView.html",cfg,model2);
	 
			GeneratorUtil.generateFile("frontend/login/loginCtrlFront.ftl",outputFolder + "/app/login/"
					+"loginCtrl.js",cfg,model2);
			
			GeneratorUtil.generateFile("frontend/login/authenticationServiceFront.ftl",outputFolder + "/app/login/"
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
			
			GeneratorUtil.generateFile("frontend/ng-app.ftl", 
					outputFolder + "/app/app.js", cfg, model2);
			
			GeneratorUtil.generateFile("backend/model/appDBContext.ftl", 
					outputFolder + "/model/AppDBContext.cs", cfg, model2);
		
			GeneratorUtil.generateFile("backend/webApiConfig.ftl", 
					outputFolder + "/backendConfig/WebApiConfig.cs", cfg, model2);
			
			GeneratorUtil.generateFile("backend/controllers/operations.ftl", 
					outputFolder + "/controller/OperationController.cs", cfg, model2);	
					
			GeneratorUtil.generateFile("frontend/index.ftl", 
					outputFolder + "/app/index.html", cfg, model2);
			
			GeneratorUtil.generateFile("frontend/main.ftl", 
					outputFolder + "/app/main.html", cfg, model2);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
