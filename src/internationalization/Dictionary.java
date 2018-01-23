package internationalization;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public abstract class Dictionary {
	protected static final Logger LOG = Logger.getLogger(Dictionary.class);

	protected void translate(Internationalizer internationalizer) throws Exception {
		File xmlFile = stepOne();
		stepTwo(xmlFile, internationalizer);

	}

	protected abstract File stepOne() throws Exception;

	protected void stepTwo(File xmlFile, Internationalizer internationalizer) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document dictionary = dBuilder.parse(xmlFile);
		internationalizer.setDictionary(dictionary);
	}

}
