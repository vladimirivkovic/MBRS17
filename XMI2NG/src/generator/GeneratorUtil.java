package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class GeneratorUtil {
	
	public static void generateFile(String templateName, String fileName, Configuration cfg, Map<String, Object> model) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Template temp = cfg.getTemplate(templateName);

		// Rendering
		FileWriter fw = new FileWriter(new File(fileName));
		temp.process(model, fw);
		fw.flush();
		fw.close();
	}
	
	public static void createDirectory(String directoryName) {
		File fa = new File(directoryName);

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
	}

}
