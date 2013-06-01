package stricken.board.piece;

import java.awt.Dimension;
import java.awt.image.BufferedImage;


/**
 * Adds the capability to be able to take up space
 * 
 * @author ofuangka
 * 
 */
public abstract class AbstractBoardPiece extends PositionedSpriteSheetSprite {
	
	public enum PieceType {
		CRITTER, TERRAIN, CONTAINER, FURNISHING
	}

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
