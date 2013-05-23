package stricken.board.mode;

import java.util.List;

import stricken.board.Board;
import stricken.board.Tile;
import stricken.board.critter.Critter;
import stricken.collector.AbstractFilteredTileCollector;
import stricken.collector.CombatMovementTileCollector;
import stricken.common.Direction;
import stricken.event.Event;
import stricken.event.IEventContext;

public class CombatMovementMode extends AbstractBoardControlMode {

	private int origX;
	private int origY;

	public CombatMovementMode(Board board, IEventContext eventContext) {
		super(board, eventContext);
	}

	@Override
	public void configureTileState() {

		Critter me = getBoard().getControllingCritter();

		// figure out which tiles to enable and enable them
		getBoard().setEnabledTiles(getMovementRange(me));
	}

	@Override
	public void down() {
		getBoard().tryMove(Direction.DOWN);
	}

	@Override
	public void enter() {
		getEventContext().fire(Event.SHOW_COMBAT_ACTION_MENU);
	}

	@Override
	public void esc() {
		resetToOriginalState();
	}

	private List<Tile> getMovementRange(Critter critter) {
		CombatMovementTileCollector tileCollector = new CombatMovementTileCollector(
				AbstractFilteredTileCollector.NO_FILTER, critter);
		return tileCollector.collect(getBoard().getTile(origX, origY));
	}

	@Override
	public void left() {
		getBoard().tryMove(Direction.LEFT);
	}

	@Override
	public void readAndStoreState() {
		Critter me = getBoard().getControllingCritter();

		// save the initial information
		origX = me.getX();
		origY = me.getY();

	}

	@Override
	public void resetToOriginalState() {

		Critter me = getBoard().getControllingCritter();

		getBoard().placePiece(me, origX, origY);
	}

	@Override
	public void right() {
		getBoard().tryMove(Direction.RIGHT);
	}

	@Override
	public void up() {
		getBoard().tryMove(Direction.UP);
	}

}
