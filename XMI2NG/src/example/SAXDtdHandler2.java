package example;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXDtdHandler2 extends DefaultHandler {

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
		case "uml:Model":
			ParserEngine2.handleStart(ParserEngine2.STATE.MODEL, attributes);
			break;
		
		case "packagedElement":
			ParserEngine2.handleStart(ParserEngine2.STATE.PACKED_ELEMENT, attributes);
			break;

		case "ownedAttribute":
			ParserEngine2.handleStart(ParserEngine2.STATE.OWNED_ATTRIBUTE, attributes);
			break;
		
		case "memberEnd":
			ParserEngine2.handleStart(ParserEngine2.STATE.MEMBER_END, attributes);
			break;
		
		case "type":
			ParserEngine2.handleStart(ParserEngine2.STATE.TYPE, attributes);
			break;
			
		case "lowerValue":
			ParserEngine2.handleStart(ParserEngine2.STATE.LOWER, attributes);
			break;
		
		case "upperValue":
			ParserEngine2.handleStart(ParserEngine2.STATE.UPPER, attributes);
			break;

		default:
			break;
		}

	}

	@Override
	public void endElement(String uri, String uName, String qName)
			throws SAXException {

		switch (qName) {
		case "packagedElement":
			ParserEngine2.handleEnd(ParserEngine2.STATE.PACKED_ELEMENT);
			break;

		case "ownedAttribute":
			ParserEngine2.handleEnd(ParserEngine2.STATE.OWNED_ATTRIBUTE);
			break;
		
		case "UML:Association":
			ParserEngine2.handleEnd(ParserEngine2.STATE.ASSOCIATION);
			break;
		
		default:
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String characters = new String(ch, start, length).trim();

		ParserEngine2.handleCharacters(characters);
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
