package stricken.board.loader;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.ClassPathResource;

public class BoardDefinitionFactory {
	private static final String DEFAULT_PREFIX = "boards/";
	private static final String DEFAULT_SUFFIX = ".json";

	private String prefix = DEFAULT_PREFIX;
	private String suffix = DEFAULT_SUFFIX;
	private ObjectMapper objectMapper;

	public BoardDefinition get(String id) throws IOException {
		return objectMapper.readValue((new ClassPathResource(prefix + id
				+ suffix)).getURI().toURL(), BoardDefinition.class);
	}

	@Required
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public void setPrefix(String prefix) {
		if (prefix.charAt(prefix.length() - 1) == '/') {
			this.prefix = prefix;
		} else {
			this.prefix = prefix + '/';
		}
	}

	public void setSuffix(String suffix) {
		if (suffix.charAt(0) == '.') {
			this.suffix = suffix;
		} else {
			this.suffix = '.' + suffix;
		}
	}
}
