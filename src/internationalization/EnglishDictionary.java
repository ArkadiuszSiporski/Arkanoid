package internationalization;

import java.io.File;
import java.net.URISyntaxException;


import panels.Menu;

public class EnglishDictionary extends Dictionary {
	protected File stepOne() throws URISyntaxException {
		return new File(Menu.class.getResource("/resources/internationalization/english.xml").toURI());
	}
}
