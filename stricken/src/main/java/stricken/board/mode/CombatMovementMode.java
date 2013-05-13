package stricken.board.mode;

import java.util.List;

import stricken.Stricken;
import stricken.board.Board;
import stricken.board.Tile;
import stricken.board.critter.Critter;
import stricken.collector.CombatMovementTileCollector;
import stricken.common.Direction;
import stricken.event.IEventContext;

public class CombatMovementMode extends AbstractBoardControlMode {

	private int origX;
	private int origY;

	public CombatMovementMode(Board board, IEventContext eventContext) {
		super(board, eventContext);
	}

	@Override
	public void down() {
		board.tryMove(Direction.DOWN);
	}

	@Override
	public void enableAndTargetTiles() {

		Critter me = board.getControllingCritter();

		board.disableAllTiles();
		board.clearTargetedTiles();

		// figure out which tiles to enable and enable them
		board.enableTiles(getValidTiles(me));
	}

	@Override
	public void enter() {
		eventContext.fire(Stricken.Event.SHOW_COMBAT_ACTION_MENU);
	}

	@Override
	public void esc() {
		resetToOriginalState();
	}

	private List<Tile> getValidTiles(Critter critter) {
		CombatMovementTileCollector tileCollector = new CombatMovementTileCollector();
		tileCollector.setCritter(critter);
		return tileCollector.collect(board.getTile(origX, origY));
	}

	@Override
	public void left() {
		board.tryMove(Direction.LEFT);
	}

	@Override
	public void readAndStoreState() {
		Critter me = board.getControllingCritter();

		// save the initial information
		origX = me.getX();
		origY = me.getY();

	}

	@Override
	public void resetToOriginalState() {

		Critter me = board.getControllingCritter();

		board.placePiece(me, origX, origY);
	}

	@Override
	public void right() {
		board.tryMove(Direction.RIGHT);
	}

	@Override
	public void up() {
		board.tryMove(Direction.UP);
	}

}
