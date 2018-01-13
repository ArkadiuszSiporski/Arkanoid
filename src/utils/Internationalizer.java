package utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import panels.Menu;

public final class Internationalizer {
	private static volatile Internationalizer instance;
	private Language currentLanguage = Language.POLISH;
	private Document doc;

	public enum Language {
		POLISH, ENGLISH;
	}

	private Internationalizer() {
		initLanguage(Language.ENGLISH);
	};

	public static Internationalizer getInstance() {
		if (instance == null) {
			synchronized (Internationalizer.class) {
				if (instance == null) {
					instance = new Internationalizer();
				}
			}
		}
		return instance;
	}

	public boolean initLanguage(Language language) {
		if (!currentLanguage.equals(language)) {
			try {
				currentLanguage = language;
				System.out.println(language.toString().toLowerCase());
				File fXmlFile = new File(Menu.class
						.getResource("/resources/internationalization/" + language.toString().toLowerCase() + ".xml")
						.toURI());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(fXmlFile);
				return true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		return false;

	}
	
	public String getString(String tag){
		return doc.getElementsByTagName(tag).item(0).getTextContent();
	}

}
