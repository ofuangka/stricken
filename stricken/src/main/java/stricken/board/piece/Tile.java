package stricken.board.piece;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
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
public class Tile extends PositionedSpriteSheetSprite {

	private static final Logger LOG = Logger.getLogger(Tile.class);

	public static final Comparator<Tile> TOP_BOTTOM_LEFT_RIGHT = new Comparator<Tile>() {

		@Override
		public int compare(Tile o1, Tile o2) {
			int y1 = o1.getY();
			int y2 = o2.getY();
			int x1 = o1.getX();
			int x2 = o2.getX();

			int ret = -1;
			if (y1 > y2) {
				ret = 1;
			} else if (y1 == y2) {
				if (x1 > x2) {
					ret = 1;
				} else if (x1 == x2) {
					ret = 0;
				}
			}
			return ret;
		}
	};

	public static final int NUM_TILE_EDGES = 4;
	public static final int TOP_EDGE = 0;
	public static final int RIGHT_EDGE = 1;
	public static final int BOTTOM_EDGE = 2;
	public static final int LEFT_EDGE = 3;

	private static final int DEFAULT_MOVEMENT_COST = 1;

	private static final Color DISABLED_COLOR = new Color(0, 0, 0, 50);
	private static final Color IN_TARGETING_RANGE_COLOR = new Color(255, 255,
			255, 50);
	private static final Color TARGETED_COLOR = new Color(255, 0, 0, 255);

	private Tile[] adjacents = new Tile[NUM_TILE_EDGES];

	private List<AbstractBoardPiece> pieces = new ArrayList<AbstractBoardPiece>();
	private AbstractBoardPiece occupant;

	private int movementCost = DEFAULT_MOVEMENT_COST;

	private boolean enabled = true;
	private boolean targeted = false;
	private boolean inTargetingRange = false;
	private boolean walkable = true;

	public Tile(Dimension spriteSize, BufferedImage spriteSheet,
			int spriteSheetX, int spriteSheetY, int x, int y) {
		super(spriteSize, spriteSheet, spriteSheetX, spriteSheetY);
		setXY(x, y);
	}

	/**
	 * Adds a piece to the list of pieces in increasing z-order
	 * 
	 * @param piece
	 */
	public void add(AbstractBoardPiece piece) {
		if (piece != null) {
			if (!piece.isTakingUpSpace()
					|| (piece.isTakingUpSpace() && !isOccupied())) {
				if (pieces.isEmpty()) {
					pieces.add(piece);
				} else {
					int i = 0;
					while (i < pieces.size()
							&& piece.getZ() < pieces.get(i).getZ()) {
						i++;
					}
					pieces.add(i, piece);
				}
				if (piece.isTakingUpSpace()) {
					occupant = piece;
				}
			} else {
				LOG.warn("Trying to add a space taking piece when Tile is already occupied.");
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
		Dimension spriteSize = getSpriteSize();

		BufferedImage ret = new BufferedImage(spriteSize.width,
				spriteSize.height, BufferedImage.TYPE_INT_ARGB);

		// TODO: implement
		Graphics2D g2d = (Graphics2D) ret.getGraphics();

		g2d.drawImage(super.getImage(), 0, 0, spriteSize.width,
				spriteSize.height, 0, 0, spriteSize.width, spriteSize.height,
				null);

		if (!isEnabled()) {
			g2d.setColor(DISABLED_COLOR);
			g2d.fillRect(0, 0, spriteSize.width, spriteSize.height);
		}

		for (PositionedSpriteSheetSprite piece : pieces) {
			g2d.drawImage(piece.getImage(), 0, 0, spriteSize.width,
					spriteSize.height, 0, 0, spriteSize.width,
					spriteSize.height, null);
		}

		if (isInTargetingRange()) {
			g2d.setColor(IN_TARGETING_RANGE_COLOR);
			g2d.fillRect(0, 0, spriteSize.width, spriteSize.height);
		}

		if (isTargeted()) {
			g2d.setColor(TARGETED_COLOR);

			// draw a border if the adjacent tile is not targetted
			if (adjacents[TOP_EDGE] == null
					|| !adjacents[TOP_EDGE].isTargeted()) {
				g2d.drawLine(1, 0, spriteSize.width - 2, 0);
			}
			if (adjacents[RIGHT_EDGE] == null
					|| !adjacents[RIGHT_EDGE].isTargeted()) {
				g2d.drawLine(spriteSize.width - 1, 1, spriteSize.width - 1,
						spriteSize.height - 2);
			}
			if (adjacents[BOTTOM_EDGE] == null
					|| !adjacents[BOTTOM_EDGE].isTargeted()) {
				g2d.drawLine(1, spriteSize.height - 1, spriteSize.width - 2,
						spriteSize.height - 1);
			}
			if (adjacents[LEFT_EDGE] == null
					|| !adjacents[LEFT_EDGE].isTargeted()) {
				g2d.drawLine(0, 1, 0, spriteSize.height - 2);
			}
		}

		return ret;
	}

	public int getMovementCost() {
		return movementCost;
	}

	public AbstractBoardPiece getOccupant() {
		return occupant;
	}

	public List<AbstractBoardPiece> getPieces() {
		return pieces;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isInTargetingRange() {
		return inTargetingRange;
	}

	public boolean isOccupied() {
		return occupant != null;
	}

	public boolean isTargeted() {
		return targeted;
	}

	public boolean isWalkable() {
		return walkable;
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
		adjacents[BOTTOM_EDGE] = bottom;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setInTargetingRange(boolean inTargetingRange) {
		this.inTargetingRange = inTargetingRange;
	}

	public void setLeft(Tile left) {
		adjacents[LEFT_EDGE] = left;
	}

	public void setMovementCost(int movementCost) {
		this.movementCost = movementCost;
	}

	public void setRight(Tile right) {
		adjacents[RIGHT_EDGE] = right;
	}

	public void setTargeted(boolean targeted) {
		this.targeted = targeted;
	}

	public void setTop(Tile top) {
		adjacents[TOP_EDGE] = top;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
}
