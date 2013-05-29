package stricken.board;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class SpriteSheetSprite extends AbstractSprite {

	private final BufferedImage spriteSheet;
	private final int spriteSheetX;
	private final int spritesheetY;

	public SpriteSheetSprite(Dimension spriteSize, BufferedImage spriteSheet,
			int spriteSheetX, int spriteSheetY) {
		super(spriteSize);
		this.spriteSheet = spriteSheet;
		this.spriteSheetX = spriteSheetX;
		this.spritesheetY = spriteSheetY;
	}

	@Override
	public BufferedImage getImage() {
		Dimension spriteSize = getSpriteSize();
		return spriteSheet.getSubimage(spriteSheetX, spritesheetY,
				spriteSize.width, spriteSize.height);
	}

}
