package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import generator.model.FMAnnotation;
import generator.model.FMClass;
import generator.model.FMMethod;
import generator.model.FMParameter;
import generator.model.FMProperty;
import generator.model.FMType;

public class TestModel {

	public static void main(String[] args){
		
		// Prvo je potrebno konfigurisati FM
		Configuration cfg = new Configuration();
		
		// Šablone ćemo učitavati sa classpath-a iz foldera templates
		cfg.setTemplateLoader(new ClassTemplateLoader(TestModel.class, "template"));
		
		// Potrebno je postaviti tzv. ObjectWrapper. U većini slučajeva me dovoljno da 
		// to bude DefaultObjectWrapper
		cfg.setObjectWrapper(new DefaultObjectWrapper());  
		
		
		// Sada je potrebno kreirati model podataka. To su podaci koji će biti 
		// renderovani na mestima u šablonu koji nisu statičke prirode.
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		//name, package, visibility
		FMClass cl = new FMClass ("City", "ordering", "public");
		
		//name, type, visibility, lower, upper
		FMProperty p1 = new FMProperty("name", "String", "private", 1, 1);
		FMAnnotation a1 = new FMAnnotation("Display");
		FMAnnotation a2 = new FMAnnotation("Required");
		a1.addParameter(new FMParameter("\"Ime\"", "Name"));
		p1.addAnnotation(a1);
		p1.addAnnotation(a2);
		cl.addProperty(p1); // multiplicity 1
		cl.addProperty(new FMProperty("zipCode", "String", "private", 0, 1)); // multiplicity 0..1
		cl.addProperty(new FMProperty("state", "state", "private", 1, 1)); // multiplicity 1
		cl.addProperty(new FMProperty("enterprise", "Enterprise", "private", 0, -1));  // multiplicity *
		cl.addProperty(new FMProperty("department", "Department", "private", 1, 3));  // multiplicity 1..3
		
		FMMethod m1 = new FMMethod("open", "public", new FMType("int", ""));
		m1.addParameter(new FMParameter("in1", "double", false, false));
		m1.addParameter(new FMParameter("in2", "int", true, false));
		m1.addParameter(new FMParameter("in3", "String", false, true));
		cl.addMethod(m1);
		cl.addMethod(new FMMethod("close", "public", new FMType("int", "")));
		cl.addMethod(new FMMethod("next", "private", new FMType("void", "")));
		
		model.put("class", cl);
		model.put("properties", cl.getProperties());
		model.put("methods", cl.getMethods());
	
		try {
			// Uzimamo šablon
			Template temp = cfg.getTemplate("model.ftl");
			
			// Renderujemo ga
			//Writer out = new OutputStreamWriter(System.out);
			FileWriter fw = new FileWriter(new File("test.cs"));
			temp.process(model, fw);
			fw.flush();  			
			
		} catch (IOException e) {
			// TODO cfg.getTemplate može izazvati ovaj izuzetak ukoliko šablon nije pronađen.
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO template.process može da izazove ovaj izuzetak. Npr. zbog sintaksne greške u šablonu.
			e.printStackTrace();
		}  		
	}
	
}
