package internationalization;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import panels.Menu;

public class PolishDictionary extends Dictionary {

	protected File stepOne() throws URISyntaxException {
		return new File(Menu.class.getResource("/resources/internationalization/polish.xml").toURI());
	}

}
