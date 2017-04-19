package example;

public class Main {

	public static void main(String[] args) {
		String filePath = null;

		System.out.println("[INFO] SAX Parser");

		//if (args.length != 1) {

		filePath = "shema-skracena.xml";

		// System.out.println("[INFO] No input file, using default \""
		// + filePath + "\"");
		//
		// } else {
		// filePath = args[0];
		// }

		SAXDtdHandler handler = new SAXDtdHandler();
		handler.parse(filePath);
		
		GeneratorEngine.generate();

	}
}
