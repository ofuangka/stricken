package stricken.board.mode;

import java.util.List;

import org.apache.log4j.Logger;

import stricken.Stricken;
import stricken.action.CritterAction;
import stricken.action.ITileEffect;
import stricken.board.Board;
import stricken.board.Critter;
import stricken.board.Tile;
import stricken.collector.ITileCollector;
import stricken.event.IEventContext;

public class TargetingMode extends AbstractBoardControlMode {

	public static final int NO_SELECTABLE_TILE_INDEX = -1;

	private static final Logger log = Logger.getLogger(TargetingMode.class);

	private List<Tile> selectableTiles;
	private Tile targetingSeedTile;
	private int currentIndex;
	private final CritterAction action;

	public TargetingMode(Board board, IEventContext eventContext, CritterAction action) {
		super(board, eventContext);
		this.action = action;
	}

	@Override
	public void down() {
		left();
	}

	@Override
	public void enter() {

		if (currentIndex != NO_SELECTABLE_TILE_INDEX) {

			// get the tile collector and tile interaction for the current
			// controlling critter
			Critter controllingCritter = board.getControllingCritter();

			ITileCollector aoe = action.getAreaOfEffect();
			ITileEffect tileEffect = action.getTileEffect();

			List<Tile> affectedTiles = aoe.collect(selectableTiles
					.get(currentIndex));
			for (Tile tile : affectedTiles) {
				tileEffect.execute(controllingCritter, tile);
			}
			log.info("Valid tile selection, ending turn");
			eventContext.fire(Stricken.Event.END_OF_TURN);
		} else {
			log.warn("Invalid tile selection, ending turn");
			eventContext.fire(Stricken.Event.END_OF_TURN);
		}
	}

	@Override
	public void esc() {
		board.clearDisabledTiles();
		board.clearTargetableTiles();
		board.clearTargetedTiles();
		board.popMode();
		eventContext.fire(Stricken.Event.POP_IN_GAME_MENU);
	}

	@Override
	public void left() {
		if (currentIndex != NO_SELECTABLE_TILE_INDEX) {
			board.clearTargetedTiles();
			selectableTiles.get(currentIndex).setTargeted(false);
			currentIndex = (currentIndex == 0) ? selectableTiles.size() - 1
					: currentIndex - 1;
			renderCrosshair();
		}
	}

	protected void renderCrosshair() {
		ITileCollector aoe = action.getAreaOfEffect();
		List<Tile> crosshairTiles = aoe.collect(selectableTiles
				.get(currentIndex));
		board.targetTiles(crosshairTiles);

	}

	@Override
	public void right() {
		if (currentIndex != NO_SELECTABLE_TILE_INDEX) {
			board.clearTargetedTiles();
			selectableTiles.get(currentIndex).setTargeted(false);
			currentIndex = (currentIndex == selectableTiles.size() - 1) ? 0
					: currentIndex + 1;
			renderCrosshair();
		}
	}

	@Override
	public void enableAndTargetTiles() {
		currentIndex = NO_SELECTABLE_TILE_INDEX;
		board.disableAllTiles();
		board.clearTargetableTiles();
		board.clearTargetedTiles();
		Critter controllingCritter = board.getControllingCritter();
		targetingSeedTile = board.getTile(controllingCritter.getX(),
				controllingCritter.getY());
		List<Tile> targetableTiles = action.getTargetableRange().collect(targetingSeedTile);
		if (!targetableTiles.isEmpty()) {
			board.setTargetable(targetableTiles);
		}
		selectableTiles = action.getActualRange().collect(targetingSeedTile);
		if (!selectableTiles.isEmpty()) {
			currentIndex = 0;
			board.enableTiles(selectableTiles);
			renderCrosshair();

		} else {
			// error handling
			log.warn("No selectable tiles");
		}

	}

	@Override
	public void up() {
		right();
	}

}
