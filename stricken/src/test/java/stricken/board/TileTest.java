package stricken.board;

import java.awt.Dimension;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileTest {

	Dimension spriteSize = new Dimension(32, 32);
	Tile testObj = new Tile(spriteSize, 0, 0);

	AbstractBoardPiece spaceTakingPiece = new Critter(spriteSize);
	AbstractBoardPiece collidingPiece = new Critter(spriteSize);
	AbstractBoardPiece nonSpaceTakingPiece = new Critter(spriteSize);

	@Before
	public void setup() {
		spaceTakingPiece.setTakingUpSpace(true);
		collidingPiece.setTakingUpSpace(true);
		nonSpaceTakingPiece.setTakingUpSpace(false);
	}

	@Test
	public void testAdd() {
		testObj.add(nonSpaceTakingPiece);
		Assert.assertFalse(testObj.isOccupied());
		testObj.add(spaceTakingPiece);
		Assert.assertTrue(testObj.isOccupied());
		testObj.add(collidingPiece);
		Assert.assertEquals(testObj.getPieces().size(), 2);
	}

	@Test
	public void testRemove() {
		testObj.add(spaceTakingPiece);
		Assert.assertTrue(testObj.isOccupied());
		testObj.remove(spaceTakingPiece);
		Assert.assertFalse(testObj.isOccupied());
	}

}
