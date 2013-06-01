package stricken.board.mode;

import java.util.List;

import stricken.board.GameBoard;
import stricken.board.collector.AbstractFilteredTileCollector;
import stricken.board.collector.CombatMovementTileCollector;
import stricken.board.piece.Tile;
import stricken.board.piece.critter.Critter;
import stricken.common.Direction;
import stricken.event.Event;
import stricken.event.IEventContext;

public class CombatMovementMode extends AbstractGameBoardControlMode {

	private int origX;
	private int origY;

	public CombatMovementMode(GameBoard board, IEventContext eventContext) {
		super(board, eventContext);
	}

	@Override
	public void configureTileState() {

		GameBoard board = getGameBoard();
		
		Critter me = board.getControllingCritter();

		// figure out which tiles to enable and enable them
		board.setEnabledTiles(getMovementRange(me));
		
		board.alignViewport();
	}

	@Override
	public void down() {
		tryMove(Direction.DOWN);
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
		return tileCollector.collect(getGameBoard().getTile(origX, origY));
	}

	@Override
	public void left() {
		getGameBoard().tryMove(Direction.LEFT);
	}

	@Override
	public void readAndStoreState() {
		Critter me = getGameBoard().getControllingCritter();

		// save the initial information
		origX = me.getX();
		origY = me.getY();

	}

	@Override
	public void resetToOriginalState() {

		GameBoard board = getGameBoard();

		Critter me = board.getControllingCritter();

		board.placePiece(me, origX, origY);

		board.alignViewport();
	}

	@Override
	public void right() {
		tryMove(Direction.RIGHT);
	}

	protected void tryMove(Direction dir) {
		GameBoard board = getGameBoard();

		if (board.tryMove(dir)) {
			board.alignViewport();
		}
	}

	@Override
	public void up() {
		tryMove(Direction.UP);
	}

}
