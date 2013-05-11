package stricken.board;

import java.awt.Dimension;

/**
 * Adds the capability to be able to take up space
 * 
 * @author ofuangka
 * 
 */
public abstract class AbstractBoardPiece extends AbstractPositionedSprite {

	protected boolean takingUpSpace;

	public AbstractBoardPiece(Dimension spriteSize) {
		super(spriteSize);
	}

	public boolean isTakingUpSpace() {
		return takingUpSpace;
	}

	public void setTakingUpSpace(boolean takingUpSpace) {
		this.takingUpSpace = takingUpSpace;
	}

}
