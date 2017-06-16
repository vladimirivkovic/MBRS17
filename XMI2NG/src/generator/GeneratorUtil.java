package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import generator.model.FMClass;
import generator.model.FMProperty;

public class GeneratorUtil {

	public static void generateFile(String templateName, String fileName, Configuration cfg, Map<String, Object> model)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
			TemplateException {
		Template temp = cfg.getTemplate(templateName);
		File f = new File(fileName);

		if (f.exists())
			model.put("userCode", getUserCode(fileName, templateName));
		else
			model.put("userCode", new HashMap<String, String>());

		// Rendering
		FileWriter fw = new FileWriter(f);
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

	private static Map<String, String> getUserCode(String fileName, String templateName) throws IOException {
		List<String> lines;
		Map<String, String> retVal = new HashMap<String, String>();

		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch (Exception e) {
			return retVal;
		}

		if (lines.size() < 3) {
			return retVal;
		}

		if (!"// DO NOT CHANGE THIS CODE".equals(lines.get(0))) {
			return retVal;
		}

		String[] line1 = lines.get(1).trim().split(" ");
		String template = null;

		if ("TEMPLATE".equals(line1[1])) {
			template = line1[2];
		} else {
			return retVal;
		}

		if (!templateName.equals(template)) {
			return retVal;
		}

		if ("model.ftl".equals(templateName)) {
			Map<String, String> methodCodeMap = new HashMap<String, String>();
			Boolean isUserCode = false;
			String userCode = "";
			int methodStarts = -1;

			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);

				if ("// USER CODE STARTS HERE".equals(line.trim())) {
					isUserCode = true;
					methodStarts = i;
				} else if ("// USER CODE ENDS HERE".equals(line.trim())) {
					isUserCode = false;

					if (!"".equals(userCode)) {
						methodCodeMap.put(getMethodName(lines.get(methodStarts - 2)),
								userCode.substring(0, userCode.length() - 1));
						userCode = "";
					}
				} else {
					if (isUserCode) {
						userCode += line + "\n";
					}
				}
			}

			return methodCodeMap;
		} else if("ng-controller.ftl".equals(templateName)) {
			Map<String, String> buttonCodeMap = new HashMap<String, String>();
			Boolean isUserCode = false;
			String userCode = "";
			int methodStarts = -1;

			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);

				if ("// USER CODE STARTS HERE".equals(line.trim())) {
					isUserCode = true;
					methodStarts = i;
				} else if ("// USER CODE ENDS HERE".equals(line.trim())) {
					isUserCode = false;

					if (!"".equals(userCode)) {
						buttonCodeMap.put(getButtonName(lines.get(methodStarts - 1)),
								userCode.substring(0, userCode.length() - 1));
						userCode = "";
					}
				} else {
					if (isUserCode) {
						userCode += line + "\n";
					}
				}
			}
			
			return buttonCodeMap;
		}
		
		return retVal;
	}
	
	private static String getMethodName(String methodLine) {
		String[] temp = methodLine.trim().split("\\(")[0].split(" ");
		return temp[temp.length - 1];
	}
	
	private static String getButtonName(String buttonLine) {
		String temp = buttonLine.trim().split("Click")[0];
		return temp.split("\\.")[1];
	}

	public static Map<String, List<FMProperty>> getFieldGroupsMap(FMClass cl) {
		Map<String, List<FMProperty>> map = new HashMap<String, List<FMProperty>>();
		
		for (FMProperty p : cl.getProperties()) {
			if (p.getuIProperty() != null) {
				if (!map.containsKey(p.getuIProperty().getFieldGroup())) {
					map.put(p.getuIProperty().getFieldGroup(), new ArrayList<FMProperty>());
				}
				map.get(p.getuIProperty().getFieldGroup()).add(p);
			}
		}
		
		return map;
	}

}
