package stricken.board.mode;

import stricken.board.Board;
import stricken.common.Direction;
import stricken.event.IEventContext;

public class AdventureMode extends AbstractBoardControlMode {

	public AdventureMode(Board board, IEventContext eventContext) {
		super(board, eventContext);
	}

	public void configureTileState() {
		getBoard().clearDisabledTiles();
		getBoard().clearCrosshair();
	}

	@Override
	public void down() {
		tryMove(Direction.DOWN);
	}

	@Override
	public void enter() {
		// show in game menu
	}

	@Override
	public void left() {
		tryMove(Direction.LEFT);
	}

	@Override
	public void right() {
		tryMove(Direction.RIGHT);
	}

	public void space() {
		getBoard().nextTurn();
	}

	/**
	 * Convenience method combines delegation call of board.tryMove() with
	 * board.nextTurn() if move was successful
	 * 
	 * @param dir
	 */
	private void tryMove(Direction dir) {
		if (getBoard().tryMove(dir)) {
			getBoard().nextTurn();
		}
	}

	@Override
	public void up() {
		tryMove(Direction.UP);
	}

}
