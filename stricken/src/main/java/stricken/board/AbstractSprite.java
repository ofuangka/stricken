package stricken.board;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Sprites must have a spriteSize and be able to produce an image representing
 * itself
 * 
 * @author ofuangka
 * 
 */
public abstract class AbstractSprite {

	private final Dimension spriteSize;

	public AbstractSprite(Dimension spriteSize) {
		this.spriteSize = spriteSize;
	}

	/**
	 * Subclasses must be able to produce an image of itself
	 * 
	 * @return
	 */
	public abstract BufferedImage getImage();

	public Dimension getSpriteSize() {
		return spriteSize;
	}

}
