package stricken.board;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Adds the capability to be able to take up space
 * 
 * @author ofuangka
 * 
 */
public abstract class AbstractBoardPiece extends PositionedSpriteSheetSprite {

	private boolean takingUpSpace;

	public AbstractBoardPiece(Dimension spriteSize, BufferedImage spriteSheet,
			int spriteSheetX, int spriteSheetY) {
		super(spriteSize, spriteSheet, spriteSheetX, spriteSheetY);
	}

	public boolean isTakingUpSpace() {
		return takingUpSpace;
	}

	public void setTakingUpSpace(boolean takingUpSpace) {
		this.takingUpSpace = takingUpSpace;
	}

}
