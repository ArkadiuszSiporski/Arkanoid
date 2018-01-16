package internationalization;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import panels.Menu;

public final class Internationalizer {
	private static volatile Internationalizer instance;
	private Language currentLanguage = Language.ENGLISH;
	private Document dictionary;

	public enum Language {
		POLISH, ENGLISH;
	}

	private Internationalizer() {
		changeLanguage(Language.POLISH);
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

	public boolean changeLanguage(Language language) {
		if (!currentLanguage.equals(language)) {
			try {
				if(Language.POLISH.equals(language)){
					new PolishDictionary().translate(this);
				} else if(Language.ENGLISH.equals(language)){
					new EnglishDictionary().translate(this);
				}
				
				
				currentLanguage = language;
				return true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		
		return false;

	}
	
	public String getString(String tag){
		return dictionary.getElementsByTagName(tag).item(0).getTextContent();
	}

	void setDictionary(Document dictionary) {
		this.dictionary = dictionary;
	}

}
