package stricken.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * A Tile is a positioned container of BoardPiece objects. It can have up to one
 * occupant BoardPiece that takes up space. It has a movement cost and can be
 * enabled or targeted. It also tracks its adjacent siblings.
 * 
 * @author ofuangka
 * 
 */
public class Tile extends AbstractPositionedSprite {

	private static final Logger log = Logger.getLogger(Tile.class);

	private static final int DEFAULT_MOVEMENT_COST = 1;

	private static final Color DISABLED_COLOR = new Color(0, 0, 0, 50);
	private static final Color TARGETABLE_COLOR = new Color(255, 255, 255, 50);
	private static final Color TARGETED_COLOR = new Color(255, 0, 0, 255);

	private Tile[] adjacents = new Tile[4];

	private List<AbstractBoardPiece> pieces = new ArrayList<AbstractBoardPiece>();
	private AbstractBoardPiece occupant;

	private int movementCost = DEFAULT_MOVEMENT_COST;

	private boolean disabled;
	private boolean targeted;
	private boolean targetable;

	public Tile(Dimension spriteSize, int x, int y) {
		super(spriteSize);
		setXY(x, y);
	}

	/**
	 * Adds a piece to the list of pieces as long as it can fit
	 * 
	 * @param piece
	 */
	public void add(AbstractBoardPiece piece) {
		if (piece != null) {
			if (!piece.isTakingUpSpace()
					|| (piece.isTakingUpSpace() && !isOccupied())) {
				pieces.add(piece);
				if (piece.isTakingUpSpace()) {
					occupant = piece;
				}
			} else {
				log.warn("Trying to add a space taking piece when Tile is already occupied.");
			}
		}

	}

	/**
	 * Produces an array of 4 tiles in clockwise order (top, right, bottom,
	 * left). If a Tile does not exist at the given position, it is null in the
	 * array
	 * 
	 * @return
	 */
	public Tile[] getAdjacentTiles() {
		return adjacents;
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage ret = new BufferedImage(spriteSize.width,
				spriteSize.height, BufferedImage.TYPE_INT_ARGB);

		// TODO: implement
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		/*
		 * g2d.setColor(Color.red); g2d.drawRect(0, 0, spriteSize.width - 1,
		 * spriteSize.height - 1);
		 */

		if (isDisabled()) {
			g2d.setColor(DISABLED_COLOR);
			g2d.fillRect(0, 0, spriteSize.width, spriteSize.height);
		}

		for (AbstractPositionedSprite piece : pieces) {
			g2d.drawImage(piece.getImage(), 0, 0, spriteSize.width,
					spriteSize.height, 0, 0, spriteSize.width,
					spriteSize.height, null);
		}
		
		if (isTargetable()) {
			g2d.setColor(TARGETABLE_COLOR);
			g2d.fillRect(0, 0, spriteSize.width, spriteSize.height);
		}

		if (isTargeted()) {
			g2d.setColor(TARGETED_COLOR);

			// draw a border if the adjacent tile is not targetted
			if (adjacents[0] == null || !adjacents[0].isTargeted()) {
				g2d.drawLine(1, 0, spriteSize.width - 2, 0);
			}
			if (adjacents[1] == null || !adjacents[1].isTargeted()) {
				g2d.drawLine(spriteSize.width - 1, 1, spriteSize.width - 1,
						spriteSize.height - 2);
			}
			if (adjacents[2] == null || !adjacents[2].isTargeted()) {
				g2d.drawLine(1, spriteSize.height - 1, spriteSize.width - 2,
						spriteSize.height - 1);
			}
			if (adjacents[3] == null || !adjacents[3].isTargeted()) {
				g2d.drawLine(0, 1, 0, spriteSize.height - 2);
			}
		}

		return ret;
	}

	public int getMovementCost() {
		return movementCost;
	}

	public AbstractPositionedSprite getOccupant() {
		return occupant;
	}

	public List<AbstractBoardPiece> getPieces() {
		return pieces;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public boolean isOccupied() {
		return occupant != null;
	}
	
	public boolean isTargetable() {
		return targetable;
	}

	public boolean isTargeted() {
		return targeted;
	}

	public void remove(AbstractBoardPiece piece) {
		if (piece != null) {
			pieces.remove(piece);

			if (piece.isTakingUpSpace()) {
				occupant = null;
			}
		}
	}

	public void setBottom(Tile bottom) {
		adjacents[2] = bottom;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setLeft(Tile left) {
		adjacents[3] = left;
	}

	public void setMovementCost(int movementCost) {
		this.movementCost = movementCost;
	}

	public void setRight(Tile right) {
		adjacents[1] = right;
	}
	
	public void setTargetable(boolean targetable) {
		this.targetable = targetable;
	}

	public void setTargeted(boolean targeted) {
		this.targeted = targeted;
	}

	public void setTop(Tile top) {
		adjacents[0] = top;
	}
}
