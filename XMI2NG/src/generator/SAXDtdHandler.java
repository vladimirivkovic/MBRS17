package generator;

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
		case "uml:Model":
			ParserEngine.handleStart(ParserEngine.STATE.MODEL, attributes);
			break;
		
		case "packagedElement":
			ParserEngine.handleStart(ParserEngine.STATE.PACKED_ELEMENT, attributes);
			break;

		case "ownedAttribute":
			ParserEngine.handleStart(ParserEngine.STATE.OWNED_ATTRIBUTE, attributes);
			break;
		
		case "ownedOperation":
			ParserEngine.handleStart(ParserEngine.STATE.OWNED_OPERATION, attributes);
			break;
		
		case "ownedParameter":
			ParserEngine.handleStart(ParserEngine.STATE.OWNED_PARAMETER, attributes);
			break;
		
//		case "memberEnd":
//			ParserEngine2.handleStart(ParserEngine2.STATE.MEMBER_END, attributes);
//			break;
			
		case "ownedEnd":
			ParserEngine.handleStart(ParserEngine.STATE.OWNED_END, attributes);
			break;
		
		// case "type":
		case "referenceExtension":
			ParserEngine.handleStart(ParserEngine.STATE.TYPE, attributes);
			break;
			
		case "lowerValue":
			ParserEngine.handleStart(ParserEngine.STATE.LOWER, attributes);
			break;
		
		case "upperValue":
			ParserEngine.handleStart(ParserEngine.STATE.UPPER, attributes);
			break;
		
		case "ownedLiteral":
			ParserEngine.handleStart(ParserEngine.STATE.OWNED_LITERAL, attributes);
			break;
		
		case "_:UIElement":
		case "_:UIClass":
		case "_:UIProperty":
		case "_:UIAssociationEnd":
		case "_:Lookup":
		case "_:ReadOnly":
		case "_:NoInsert":
		case "_:Calculated":
		case "_:Id":
		case "_:Zoom":
		case "_:Next":
		case "_:Tab":
		case "_:UIGroup":
		case "_:BusinessOperation":
		case "_:Report":
		case "_:Transaction":
			ParserEngine.handleStereotype(qName, attributes);
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
			ParserEngine.handleEnd(ParserEngine.STATE.PACKED_ELEMENT);
			break;

		case "ownedAttribute":
			ParserEngine.handleEnd(ParserEngine.STATE.OWNED_ATTRIBUTE);
			break;
		
		case "uml:Model":
			ParserEngine.handleEnd(ParserEngine.STATE.MODEL);
			break;
		
		case "ownedOperation":
			ParserEngine.handleEnd(ParserEngine.STATE.OWNED_OPERATION);
			break;
		
		case "ownedParameter":
			ParserEngine.handleEnd(ParserEngine.STATE.OWNED_PARAMETER);
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
