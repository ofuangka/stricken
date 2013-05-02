package stricken.board.mode;

import java.util.List;

import org.apache.log4j.Logger;

import stricken.board.Board;
import stricken.board.Critter;
import stricken.board.Tile;
import stricken.collector.ITileCollector;
import stricken.effect.ITileEffect;
import stricken.event.GameEventContext;
import stricken.event.IEventContext;
import stricken.skill.Skill;

public class TargetingMode extends AbstractBoardControlMode {

	public static final int NO_SELECTABLE_TILE_INDEX = -1;

	private static final Logger log = Logger.getLogger(TargetingMode.class);

	private List<Tile> selectableTiles;
	private Tile targetingSeedTile;
	private int currentIndex;
	private final Skill skill;

	public TargetingMode(Board board, IEventContext eventContext, Skill skill) {
		super(board, eventContext);
		this.skill = skill;
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

			ITileCollector aoe = skill.getAreaOfEffect();
			ITileEffect tileEffect = skill.getTileEffect();

			List<Tile> affectedTiles = aoe.collect(selectableTiles
					.get(currentIndex));
			for (Tile tile : affectedTiles) {
				tileEffect.execute(controllingCritter, tile);
			}
			board.clearDisabledTiles();
			board.clearTargetedTiles();
			board.nextTurn();
		} else {
			board.clearDisabledTiles();
			board.clearTargetedTiles();
			board.nextTurn();
		}
	}

	@Override
	public void esc() {
		board.popMode();
		eventContext.fire(GameEventContext.SHOW_PREVIOUS_IN_GAME_MENU);
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
		ITileCollector aoe = skill.getAreaOfEffect();
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
		board.clearTargetedTiles();
		Critter controllingCritter = board.getControllingCritter();
		targetingSeedTile = board.getTile(controllingCritter.getX(),
				controllingCritter.getY());
		selectableTiles = skill.getRange().collect(targetingSeedTile);
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

	@Override
	public void readAndStoreState() {
		// do nothing

	}

	@Override
	public void resetToOriginalState() {
		// do nothing

	}

}
