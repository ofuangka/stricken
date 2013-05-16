package stricken.util;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.core.io.Resource;

public abstract class AbstractXmlFactory<T> extends AbstractXmlConsumer {

	public AbstractXmlFactory(Resource resource) throws DocumentException,
			IOException {
		super(resource);
	}

	public T get(String id) {
		return get((Element) getDocument().selectSingleNode(
				getBaseElementXpath() + "[@id='" + id + "']"));
	}

	public abstract String getBaseElementXpath();

	public abstract T get(Element node);

}
