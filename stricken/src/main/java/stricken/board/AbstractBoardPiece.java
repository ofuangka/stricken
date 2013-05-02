package stricken.board;

import java.awt.Dimension;

/**
 * Adds the capability to be able to take up space
 * 
 * @author ofuangka
 * 
 */
public abstract class AbstractBoardPiece extends AbstractPositionedSprite {

	public AbstractBoardPiece(Dimension spriteSize) {
		super(spriteSize);
	}

	protected boolean takingUpSpace;

	public boolean isTakingUpSpace() {
		return takingUpSpace;
	}

	public void setTakingUpSpace(boolean takingUpSpace) {
		this.takingUpSpace = takingUpSpace;
	}

}
