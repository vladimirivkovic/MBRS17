package generator;

public class Main {

	public static void main(String[] args) {
		String filePath = null;
		String outputFolder = null;

		System.out.println("[INFO] SAX Parser");

		if (args.length < 1) {
			filePath = "MBRS_MetaModel.xml";
			outputFolder = "generated";
		} else {
		    filePath = args[0];
		    
		    if (args.length == 1) {
		    	outputFolder = "generated";
		    } else {
		    	outputFolder = args[1];
		    }
		}

		SAXDtdHandler handler = new SAXDtdHandler();
		handler.parse(filePath);
		
		GeneratorEngine.generate(ParserEngine.getElementMap(), outputFolder);
		
		System.out.println("\n\n*********************************** FILES GENERATED ***********************************");

	}
}
