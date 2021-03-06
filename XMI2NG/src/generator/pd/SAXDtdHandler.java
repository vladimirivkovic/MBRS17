package generator.pd;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXDtdHandler extends DefaultHandler {

	private static SAXParserFactory factory;

	/*
	 * Factory initialization static-block
	 */
	static {
		factory = SAXParserFactory.newInstance();
		// factory.setValidating(true);
	}

	void parse(String filePath) {

		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(filePath), this);

		} catch (SAXParseException e) {

			System.out.println("[ERROR] Parsing error, line: "
					+ e.getLineNumber() + ", uri: " + e.getSystemId());
			System.out.println("[ERROR] " + e.getMessage());
			System.out.print("[ERROR] Embedded exception: ");

			Exception embeddedException = e;
			if (e.getException() != null)
				embeddedException = e.getException();

			// Print stack trace...
			embeddedException.printStackTrace();

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private String getLocalName(String unqualifiedName, String qualifiedName) {
		return "".equals(unqualifiedName) ? qualifiedName : unqualifiedName;
	}

	

	@Override
	public void startDocument() throws SAXException {

		// System.out.println("START_DOCUMENT");
	}

	@Override
	public void endDocument() throws SAXException {

		// System.out.println("END_DOCUMENT");
	}

	@Override
	public void startElement(String uri, String uName, String qName,
			Attributes attributes) throws SAXException {
		
		switch (qName) {
		case "UML:Class":
			ParserEngine.handleStart(ParserEngine.STATE.CLASS, attributes);
			break;

		case "UML:Attribute":
			ParserEngine.handleStart(ParserEngine.STATE.ATTRIBUTE, attributes);
			break;
		
		case "UML:MultiplicityRange.lower":
			ParserEngine.handleStart(ParserEngine.STATE.LOWER, attributes);
			break;
		
		case "UML:MultiplicityRange.upper":
			ParserEngine.handleStart(ParserEngine.STATE.UPPER, attributes);
			break;
			
		case "UML:StructuralFeature.type":
			ParserEngine.handleStart(ParserEngine.STATE.TYPE, attributes);
			break;
			
		case "UML:Classifier":
			ParserEngine.handleStart(ParserEngine.STATE.CLASSIFIER, attributes);
			break;
		
		case "UML:DataType":
			ParserEngine.handleStart(ParserEngine.STATE.DATATYPE, attributes);
			break;
		
		case "UML:Association":
			ParserEngine.handleStart(ParserEngine.STATE.ASSOCIATION, attributes);
			break;
		
		case "UML:AssociationEnd":
			ParserEngine.handleStart(ParserEngine.STATE.AEND, attributes);
			break;
		
		case "UML:AssociationEnd.type":
			ParserEngine.handleStart(ParserEngine.STATE.TYPE, attributes);
			break;
		case "UML:AssociationEnd.isNavigable":
			ParserEngine.handleStart(ParserEngine.STATE.NAVIGABLE, attributes);
			break;
		default:
			break;
		}

	}

	@Override
	public void endElement(String uri, String uName, String qName)
			throws SAXException {

		switch (qName) {
		case "UML:Class":
			ParserEngine.handleEnd(ParserEngine.STATE.ATTRIBUTE);
			break;

		case "UML:Attribute":
			ParserEngine.handleEnd(ParserEngine.STATE.LOWER);
			break;
		
		case "UML:Association":
			ParserEngine.handleEnd(ParserEngine.STATE.ASSOCIATION);
			break;
		
		default:
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String characters = new String(ch, start, length).trim();

		ParserEngine.handleCharacters(characters);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {

		// System.out.println("IGNORABLE_WHITESPACE");
	}

	/*
	 * Exception handling
	 */

	@Override
	public void error(SAXParseException err) throws SAXParseException {

		// Propagate the exception
		throw err;
	}

	@Override
	public void warning(SAXParseException err) throws SAXParseException {

		System.out.println("[WARN] Warning, line: " + err.getLineNumber()
				+ ", uri: " + err.getSystemId());
		System.out.println("[WARN] " + err.getMessage());
	}

}
