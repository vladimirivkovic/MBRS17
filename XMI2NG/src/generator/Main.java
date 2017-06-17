package generator;

public class Main {

	public static void main(String[] args) {
		String filePath = null;

		System.out.println("[INFO] SAX Parser");

		//if (args.length != 1) {

		filePath = "MBRS_MetaModel.xml";

		// System.out.println("[INFO] No input file, using default \""
		// + filePath + "\"");
		//
		// } else {
		// filePath = args[0];
		// }

		SAXDtdHandler handler = new SAXDtdHandler();
		handler.parse(filePath);
		
		GeneratorEngine.generate(ParserEngine.getElementMap());
		
		System.out.println("\n\n*********************************** FILES GENERATED ***********************************");

	}
}
