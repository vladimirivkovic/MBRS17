package generator;

public class Main2 {

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

		SAXDtdHandler2 handler = new SAXDtdHandler2();
		handler.parse(filePath);
		
		GeneratorEngine.generate(ParserEngine2.getElementMap());

	}
}
