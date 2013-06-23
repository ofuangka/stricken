package stricken.board.mode;

import java.util.List;

import org.apache.log4j.Logger;

import stricken.board.GameBoard;
import stricken.board.collector.ITileCollector;
import stricken.board.effect.AbstractEffect;
import stricken.board.piece.Critter;
import stricken.board.piece.TargetedAction;
import stricken.board.piece.Tile;
import stricken.event.Event;
import stricken.event.IEventContext;

public class TargetingMode extends AbstractGameBoardControlMode {

	public static final int NO_SELECTABLE_TILE_INDEX = -1;

	private static final Logger LOG = Logger.getLogger(TargetingMode.class);

	private List<Tile> actualRange;
	private int currentIndex;
	private final TargetedAction action;

	public TargetingMode(GameBoard board, IEventContext eventContext,
			TargetedAction action) {
		super(board, eventContext);
		this.action = action;
	}

	@Override
	public void configureTileState() {

		// clear the previously selected tile
		currentIndex = NO_SELECTABLE_TILE_INDEX;

		// get the different range collectors
		Critter controllingCritter = getGameBoard().getControllingCritter();
		Tile targetTile = getGameBoard().getTile(controllingCritter.getX(),
				controllingCritter.getY());
		List<Tile> targetingRange = action.getTargetingRange().collect(
				targetTile);
		if (!targetingRange.isEmpty()) {
			getGameBoard().setTargetingRange(targetingRange);
		}
		actualRange = action.getActualRangeFilter().filter(targetingRange);

		// if anything is in targeting range, render the crosshair on the first
		// potential target
		if (!actualRange.isEmpty()) {
			currentIndex = 0;
			getGameBoard().setEnabledTiles(actualRange);
			renderCrosshair();

		} else {
			// otherwise disable all tiles
			getGameBoard().disableAllTiles();

			// error handling
			LOG.warn("No selectable tiles");
		}

	}

	@Override
	public void down() {
		left();
	}

	@Override
	public void enter() {

		if (currentIndex != NO_SELECTABLE_TILE_INDEX) {

			// get the tile collector and tile interaction

			ITileCollector aoe = action.getAreaOfEffect();
			AbstractEffect tileEffect = action.getTileEffect();

			List<Tile> affectedTiles = aoe.collect(actualRange
					.get(currentIndex));
			for (Tile tile : affectedTiles) {
				tileEffect.interact(tile);
			}
			LOG.debug("Valid tile selection, ending turn");
			getEventContext().fire(Event.END_OF_TURN);
		} else {
			LOG.warn("Invalid tile selection, please try again");
		}
	}

	@Override
	public void esc() {
		getGameBoard().clearDisabledTiles();
		getGameBoard().clearTargetingRange();
		getGameBoard().clearCrosshair();
		getGameBoard().popMode();
		getEventContext().fire(Event.POP_IN_GAME_MENU);
	}

	@Override
	public void left() {
		if (currentIndex != NO_SELECTABLE_TILE_INDEX) {
			getGameBoard().clearCrosshair();
			actualRange.get(currentIndex).setTargeted(false);
			currentIndex = (currentIndex == 0) ? actualRange.size() - 1
					: currentIndex - 1;
			renderCrosshair();
		}
	}

	protected void renderCrosshair() {
		ITileCollector aoe = action.getAreaOfEffect();
		Tile targetTile = actualRange.get(currentIndex);
		List<Tile> crosshairTiles = aoe.collect(targetTile);
		GameBoard board = getGameBoard();
		board.renderCrosshair(crosshairTiles);
		board.alignViewport(targetTile.getX(), targetTile.getY());

	}

	@Override
	public void right() {
		if (currentIndex != NO_SELECTABLE_TILE_INDEX) {
			getGameBoard().clearCrosshair();
			actualRange.get(currentIndex).setTargeted(false);
			currentIndex = (currentIndex == actualRange.size() - 1) ? 0
					: currentIndex + 1;
			renderCrosshair();
		}
	}

	@Override
	public void up() {
		right();
	}

}
