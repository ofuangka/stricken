package stricken.board;

import java.awt.Dimension;

/**
 * This object has an xyz position
 * 
 * @author ofuangka
 * 
 */
public abstract class AbstractPositionedSprite extends AbstractSprite {

	public static final int DEFAULT_X = Board.INVALID_X;
	public static final int DEFAULT_Y = Board.INVALID_Y;
	public static final int DEFAULT_Z = 0;

	protected int x = DEFAULT_X;
	protected int y = DEFAULT_Y;
	protected int z = DEFAULT_Z;

	public AbstractPositionedSprite(Dimension spriteSize) {
		super(spriteSize);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
