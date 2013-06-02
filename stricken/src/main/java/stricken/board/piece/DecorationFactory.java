package stricken.board.piece;

import java.awt.Dimension;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.Resource;

import stricken.board.loader.DecorationDefinition;

public class DecorationFactory extends AbstractBoardPieceFactory {

	public DecorationFactory(Resource spriteSheetResource,
			Resource typeResource, ObjectMapper objectMapper,
			Dimension spriteSize) {
		super(spriteSheetResource, typeResource, objectMapper, spriteSize);
	}

	public Decoration get(DecorationDefinition def, int x, int y) {
		Integer[] spriteSheetXY = getTypes().get(def.getId());
		Decoration ret = new Decoration(getSpriteSize(), getSpriteSheet(),
				spriteSheetXY[0], spriteSheetXY[1]);
		ret.setTakingUpSpace(def.isTakingUpSpace());
		return ret;
	}

}
