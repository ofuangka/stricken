package stricken.board.piece;

import java.awt.Dimension;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.Resource;

import stricken.board.loader.TileDefinition;

public class TileFactory extends AbstractBoardPieceFactory {

	public TileFactory(Resource spriteSheetResource, Resource typeResource,
			ObjectMapper objectMapper, Dimension spriteSize) {
		super(spriteSheetResource, typeResource, objectMapper, spriteSize);
	}

	public Tile get(TileDefinition def, int x, int y) {
		Integer[] spriteSheetXY = getTypes().get(def.getId());
		Tile ret = new Tile(getSpriteSize(), getSpriteSheet(),
				spriteSheetXY[0], spriteSheetXY[1], x, y);
		ret.setWalkable(def.is_());
		return ret;
	}
}
