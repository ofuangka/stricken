package stricken.board.piece;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.core.io.Resource;

public abstract class AbstractBoardPieceFactory {

	private static final Logger LOG = Logger
			.getLogger(AbstractBoardPieceFactory.class);

	private BufferedImage spriteSheet;
	private Map<String, Integer[]> types;
	private Dimension spriteSize;

	public AbstractBoardPieceFactory(Resource spriteSheetResource,
			Resource typeResource, ObjectMapper objectMapper,
			Dimension spriteSize) {
		try {
			spriteSheet = ImageIO.read(spriteSheetResource.getURI().toURL());
			types = objectMapper.readValue(typeResource.getURI().toURL(),
					new TypeReference<Map<String, Integer[]>>() {
					});
		} catch (IOException e) {
			LOG.error("Could not read spritesheet file", e);
		}
		this.spriteSize = spriteSize;
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public Dimension getSpriteSize() {
		return spriteSize;
	}

	public Map<String, Integer[]> getTypes() {
		return types;
	}

}
