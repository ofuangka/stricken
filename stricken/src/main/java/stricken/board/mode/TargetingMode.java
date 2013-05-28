package stricken.board.mode;

import java.util.List;

import org.apache.log4j.Logger;

import stricken.board.AbstractEffect;
import stricken.board.Board;
import stricken.board.Tile;
import stricken.board.critter.Critter;
import stricken.board.critter.CritterAction;
import stricken.collector.ITileCollector;
import stricken.event.Event;
import stricken.event.IEventContext;

public class TargetingMode extends AbstractBoardControlMode {

	public static final int NO_SELECTABLE_TILE_INDEX = -1;

	private static final Logger log = Logger.getLogger(TargetingMode.class);

	private List<Tile> actualRange;
	private int currentIndex;
	private final CritterAction action;

	public TargetingMode(Board board, IEventContext eventContext,
			CritterAction action) {
		super(board, eventContext);
		this.action = action;
	}

	@Override
	public void configureTileState() {

		// clear the previously selected tile
		currentIndex = NO_SELECTABLE_TILE_INDEX;

		// get the different range collectors
		Critter controllingCritter = getBoard().getControllingCritter();
		Tile targetTile = getBoard().getTile(controllingCritter.getX(),
				controllingCritter.getY());
		List<Tile> targetingRange = action.getTargetingRange().collect(
				targetTile);
		if (!targetingRange.isEmpty()) {
			getBoard().setTargetingRange(targetingRange);
		}
		actualRange = action.getActualRange().collect(targetTile);

		// if anything is in targeting range, render the crosshair on the first
		// potential target
		if (!actualRange.isEmpty()) {
			currentIndex = 0;
			getBoard().setEnabledTiles(actualRange);
			renderCrosshair();

		} else {
			// otherwise disable all tiles
			getBoard().disableAllTiles();

			// error handling
			log.warn("No selectable tiles");
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
			log.debug("Valid tile selection, ending turn");
			getEventContext().fire(Event.END_OF_TURN);
		} else {
			log.warn("Invalid tile selection, please try again");
		}
	}

	@Override
	public void esc() {
		getBoard().clearDisabledTiles();
		getBoard().clearTargetingRange();
		getBoard().clearCrosshair();
		getBoard().popMode();
		getEventContext().fire(Event.POP_IN_GAME_MENU);
	}

	@Override
	public void left() {
		if (currentIndex != NO_SELECTABLE_TILE_INDEX) {
			getBoard().clearCrosshair();
			actualRange.get(currentIndex).setTargeted(false);
			currentIndex = (currentIndex == 0) ? actualRange.size() - 1
					: currentIndex - 1;
			renderCrosshair();
		}
	}

	protected void renderCrosshair() {
		ITileCollector aoe = action.getAreaOfEffect();
		List<Tile> crosshairTiles = aoe.collect(actualRange.get(currentIndex));
		getBoard().renderCrosshair(crosshairTiles);

	}

	@Override
	public void right() {
		if (currentIndex != NO_SELECTABLE_TILE_INDEX) {
			getBoard().clearCrosshair();
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
