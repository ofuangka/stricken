package stricken.board;

import java.awt.Dimension;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import stricken.board.critter.Critter;
import stricken.event.IEventContext;

public class BoardTest {

	static final String DUMMY_ID = "boardId";

	Dimension spriteSize = new Dimension(32, 32);
	AbstractBoardPiece piece = new Critter(spriteSize);
	IEventContext mockEventContext = EasyMock.createMock(IEventContext.class);

	Board testObj = new Board(mockEventContext) {
		private static final long serialVersionUID = 6542740741021689448L;

		@Override
		public void load(String id) {
			tiles = new Tile[4][4];
			for (int x = 0; x < tiles.length; x++) {
				for (int y = 0; y < tiles[x].length; y++) {
					tiles[x][y] = new Tile(spriteSize, x, y);
					if (isInBounds(x - 1, y)) {
						tiles[x - 1][y].setRight(tiles[x][y]);
						tiles[x][y].setLeft(tiles[x - 1][y]);
					}
					if (isInBounds(x, y - 1)) {
						tiles[x][y - 1].setBottom(tiles[x][y]);
						tiles[x][y].setTop(tiles[x][y - 1]);
					}
				}
			}
		}
	};

	@Before
	public void setup() {
		testObj.load(DUMMY_ID);
		testObj.placePiece(piece, 3, 3);
	}

	@Test
	public void testGetAdjacentTilesIntInt() {
		Tile[] adjacentTiles = testObj.getAdjacentTiles(0, 0);

		// check that the tiles are null for the top and left
		Assert.assertNull(adjacentTiles[0]);
		Assert.assertNotNull(adjacentTiles[1]);
		Assert.assertNotNull(adjacentTiles[2]);
		Assert.assertNull(adjacentTiles[3]);
	}

	@Test
	public void testGetAdjacentTilesPiece() {
		Tile[] adjacentTiles = testObj.getAdjacentTiles(piece);

		// check that the tiles are null for right and bottom
		Assert.assertNotNull(adjacentTiles[0]);
		Assert.assertNull(adjacentTiles[1]);
		Assert.assertNull(adjacentTiles[2]);
		Assert.assertNotNull(adjacentTiles[3]);
	}

	@Test
	public void testIsInBounds() {
		Assert.assertTrue(testObj.isInBounds(0, 0));
		Assert.assertTrue(testObj.isInBounds(3, 3));
		Assert.assertFalse(testObj.isInBounds(-1, 0));
		Assert.assertFalse(testObj.isInBounds(4, 0));
	}

	@Test
	public void testPlacePiece() {
		Assert.assertEquals(piece.getX(), 3);
		Assert.assertEquals(piece.getY(), 3);
	}

	@Test
	public void testRemovePiece() {
		int origX = piece.getX();
		int origY = piece.getY();
		testObj.removePiece(piece);
		Assert.assertEquals(piece.getX(), Board.INVALID_X);
		Assert.assertEquals(piece.getY(), Board.INVALID_Y);
		Assert.assertTrue(testObj.getTile(origX, origY).getPieces().isEmpty());
	}

}
