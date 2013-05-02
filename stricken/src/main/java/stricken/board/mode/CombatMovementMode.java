package stricken.board.mode;

import java.util.ArrayList;
import java.util.List;

import stricken.board.Board;
import stricken.board.Critter;
import stricken.board.Tile;
import stricken.collector.CombatMovementTileCollector;
import stricken.common.Direction;
import stricken.event.GameEventContext;
import stricken.event.IEventContext;
import stricken.ui.AbstractMenuItem;
import stricken.ui.AttackMenuItem;
import stricken.ui.Menu;
import stricken.ui.WaitMenuItem;

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
	public void enter() {
		Menu menu = new Menu(eventContext);
		List<AbstractMenuItem> items = new ArrayList<AbstractMenuItem>();
		items.add(new AttackMenuItem(menu));
		items.add(new WaitMenuItem(menu));
		menu.setItems(items);
		eventContext.fire(GameEventContext.IN_GAME_MENU, menu);
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
	public void enableAndTargetTiles() {

		Critter me = board.getControllingCritter();

		board.disableAllTiles();
		board.clearTargetedTiles();

		// figure out which tiles to enable and enable them
		board.enableTiles(getValidTiles(me));
	}

	public void resetToOriginalState() {

		Critter me = board.getControllingCritter();

		board.placePiece(me, origX, origY);
	}

	@Override
	public void right() {
		board.tryMove(Direction.RIGHT);
	}

	@Override
	public void readAndStoreState() {
		Critter me = board.getControllingCritter();

		// save the initial information
		origX = me.getX();
		origY = me.getY();

	}

	@Override
	public void up() {
		board.tryMove(Direction.UP);
	}

}
