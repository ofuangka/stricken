package stricken.util;

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;

public abstract class AbstractXmlConsumer {

	private final Document document;

	public AbstractXmlConsumer(Resource resource) throws DocumentException,
			IOException {
		SAXReader reader = new SAXReader();
		document = reader.read(resource.getURL());
	}

	public Document getDocument() {
		return document;
	}
}
