package example;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * Primer demonstrira upotrebu SAX API-ja za potrebe parsiranje/čitanja XML
 * fajla.
 * 
 * Rukovanje greškama i upozorenjima prilikom parsiranja kroz implementaciju
 * odgovarajućih callback-a (testirati sa nevalidnim, loše formiranim XML-om,
 * npr <test> bez matching end-taga).
 * 
 * Parsiranje XML fajla sa DTD specifikacijom omogućuje handlovanje ignorable
 * whitespace-ova.
 * 
 * Validacija XML fajla u odnosu na njegovu DTD specifikaciju. Testirati
 * dodavanjem npr. test="abc" atributa u otvarajući tag korenskog elementa.
 * 
 * @author Igor Cverdelj-Fogaraši
 * 
 */
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
			Engine.handleStart(Engine.STATE.CLASS, attributes);
			break;

		case "UML:Attribute":
			Engine.handleStart(Engine.STATE.ATTRIBUTE, attributes);
			break;
		
		case "UML:MultiplicityRange.lower":
			Engine.handleStart(Engine.STATE.LOWER, attributes);
			break;
		
		case "UML:MultiplicityRange.upper":
			Engine.handleStart(Engine.STATE.UPPER, attributes);
			break;
			
		case "UML:StructuralFeature.type":
			Engine.handleStart(Engine.STATE.TYPE, attributes);
			break;
			
		case "UML:Classifier":
			Engine.handleStart(Engine.STATE.CLASSIFIER, attributes);
			break;
		
		case "UML:DataType":
			Engine.handleStart(Engine.STATE.DATATYPE, attributes);
			break;
		
		case "UML:Association":
			Engine.handleStart(Engine.STATE.ASSOCIATION, attributes);
			break;
		
		case "UML:AssociationEnd":
			Engine.handleStart(Engine.STATE.AEND, attributes);
			break;
		
		case "UML:AssociationEnd.type":
			Engine.handleStart(Engine.STATE.TYPE, attributes);
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
			Engine.handleEnd(Engine.STATE.ATTRIBUTE);
			break;

		case "UML:Attribute":
			Engine.handleEnd(Engine.STATE.LOWER);
			break;
		
		case "UML:Association":
			Engine.handleEnd(Engine.STATE.ASSOCIATION);
			break;
		
		default:
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String characters = new String(ch, start, length).trim();

		Engine.handleCharacters(characters);
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
