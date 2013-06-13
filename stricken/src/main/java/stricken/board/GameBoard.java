package stricken.board;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;

import stricken.board.loader.BoardDefinition;
import stricken.board.loader.BoardDefinitionFactory;
import stricken.board.loader.EntranceDefinition;
import stricken.board.loader.TileDefinition;
import stricken.board.mode.AbstractGameBoardControlMode;
import stricken.board.mode.CombatMovementMode;
import stricken.board.piece.AbstractBoardPiece;
import stricken.board.piece.CircleCritter;
import stricken.board.piece.Critter;
import stricken.board.piece.DecorationFactory;
import stricken.board.piece.Tile;
import stricken.board.piece.TileFactory;
import stricken.common.Direction;
import stricken.event.Event;
import stricken.event.IEventContext;
import stricken.ui.IKeySink;

/**
 * The GameBoard keeps a registry of Critters, enabled and targeted Tiles. It
 * also manages control modes and tracks the main character
 * 
 * @author ofuangka
 * 
 */
public class GameBoard extends AbstractViewportBoard {

	private static final long serialVersionUID = -1963940535869410879L;

	private static final Logger LOG = Logger.getLogger(GameBoard.class);

	public static final int INVERSE_CHANCE_TO_MOVE = 8;

	private BoardDefinitionFactory boardDefinitionFactory;
	private TileFactory tileFactory;
	private DecorationFactory decorationFactory;

	// game state
	private Critter mainCharacter;

	private Critter controllingCritter;
	private List<Critter> critters = new ArrayList<Critter>();

	private List<Critter> sequence = new ArrayList<Critter>();
	private List<Tile> disabledTiles = new ArrayList<Tile>();

	private List<Tile> tilesInTargetingRange = new ArrayList<Tile>();
	private List<Tile> targetedTiles = new ArrayList<Tile>();
	private List<AbstractGameBoardControlMode> modeHistory = new ArrayList<AbstractGameBoardControlMode>();

	private Map<Tile, EntranceDefinition> entrances = new HashMap<Tile, EntranceDefinition>();

	public GameBoard(IEventContext eventContext) {
		super(eventContext);
	}

	public GameBoard(IEventContext eventContext,
			Resource terrainSpriteSheetResource) {
		super(eventContext);
	}

	/**
	 * Convenience method that just aligns the viewport to whichever critter is
	 * in control
	 */
	public void alignViewport() {
		alignViewport(controllingCritter.getX(), controllingCritter.getY());
	}

	/**
	 * Sets the controlling Critter and calls repaint()
	 * 
	 * @param newCritter
	 */
	public void assignControl(Critter newCritter) {
		LOG.debug("Assigning control to Critter " + newCritter + "...");
		if (controllingCritter != null) {
			controllingCritter.setSelected(false);
		}
		controllingCritter = newCritter;
		controllingCritter.setSelected(true);

		alignViewport();
		repaint();
	}

	/**
	 * This clears all registries and sets the tiles to null
	 */
	public void clearBoardState() {
		modeHistory.clear();
		sequence.clear();
		critters.clear();
		disabledTiles.clear();
		tilesInTargetingRange.clear();
		targetedTiles.clear();
		tiles = null;
		mainCharacter = null;
		controllingCritter = null;
		entrances.clear();
	}

	/**
	 * Clears all targeted Tiles
	 */
	public void clearCrosshair() {
		LOG.debug("Clearing targeted Tile objects...");
		while (!targetedTiles.isEmpty()) {
			targetedTiles.remove(0).setTargeted(false);
		}
		repaint();
	}

	/**
	 * Enables all tiles
	 */
	public void clearDisabledTiles() {
		LOG.debug("Removing all out of range tiles...");
		while (!disabledTiles.isEmpty()) {
			disabledTiles.remove(0).setEnabled(true);
		}
		repaint();
	}

	/**
	 * Puts all tiles out of targeting range
	 */
	public void clearTargetingRange() {
		LOG.debug("Clearing targetable Tile objects...");
		while (!tilesInTargetingRange.isEmpty()) {
			tilesInTargetingRange.remove(0).setInTargetingRange(false);
		}
	}

	private void createCritterSequence() {
		LOG.debug("Creating Critter sequence...");
		// TODO: implement
		sequence.addAll(critters);
	}

	public void disableAllTiles() {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y].setEnabled(false);
				disabledTiles.add(tiles[x][y]);
			}
		}
		repaint();
	}

	public Critter getControllingCritter() {
		return controllingCritter;
	}

	/**
	 * The Board returns whichever current control mode
	 */
	@Override
	public IKeySink getCurrentKeySink() {
		return modeHistory.get(modeHistory.size() - 1);
	}

	public boolean isInCombat() {
		boolean ret = false;
		if (critters.size() > 1) {
			for (Critter critter : critters) {
				if (critter.isHostile()) {
					ret = true;
					break;
				}
			}
		}
		return ret;
	}

	public boolean isMainCharacterOnBoard() {
		return critters.contains(mainCharacter);
	}

	public void load(String id) throws IOException {
		load(id, 0);
	}

	public void load(String id, int index) throws IOException {
		LOG.info("Loading board '" + id + "'...");

		// load the cells into tiles
		BoardDefinition def = boardDefinitionFactory.get(id);
		TileDefinition[][] cells = def.getTiles();

		tiles = new Tile[cells.length][];
		for (int x = 0; x < tiles.length; x++) {
			tiles[x] = new Tile[cells[x].length];
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = tileFactory.get(cells[x][y], x, y);
				if (isInBounds(x - 1, y)) {
					tiles[x - 1][y].setRight(tiles[x][y]);
					tiles[x][y].setLeft(tiles[x - 1][y]);
				}
				if (isInBounds(x, y - 1)) {
					tiles[x][y - 1].setBottom(tiles[x][y]);
					tiles[x][y].setTop(tiles[x][y - 1]);
				}
			}
		}

		// load entrances
		EntranceDefinition[] entranceDefinitions = def.getEntrances();
		for (EntranceDefinition entranceDef : entranceDefinitions) {
			entrances.put(getTile(entranceDef.getX(), entranceDef.getY()),
					entranceDef);
		}

		// load any non-critter pieces

		// load any critters
		Random random = getEventContext().getRandom();
		int numCritters = random.nextInt(11) + 3;
		for (int i = 0; i < numCritters; i++) {
			Critter critter = new CircleCritter(getSpriteSize(), new Color(
					random.nextInt(255), random.nextInt(255),
					random.nextInt(255)));
			critter.setStat(Critter.Stat.MAXHP, random.nextInt(3) + 1);
			critter.setStat(Critter.Stat.HP,
					critter.getStat(Critter.Stat.MAXHP));
			critter.setStat(Critter.Stat.MAXMP, random.nextInt(3) + 1);
			critter.setStat(Critter.Stat.MP,
					critter.getStat(Critter.Stat.MAXMP));
			critter.setStat(Critter.Stat.STRENGTH, random.nextInt(3) + 1);
			critter.setStat(Critter.Stat.SPEED, random.nextInt(3) + 1);
			critter.setHostile(random.nextBoolean());
			critter.setHuman(random.nextBoolean());

			List<String> talents = new ArrayList<String>();

			if (random.nextBoolean()) {
				talents.add("FIRE_BOMB");
			}
			if (random.nextBoolean()) {
				talents.add("LIGHTNING_STRIKE");
			}
			if (random.nextBoolean()) {
				talents.add("THROW_ROCK");
			}
			if (random.nextBoolean()) {
				talents.add("METEOR_RAIN");
			}
			critter.setTalents(talents);

			int x = random.nextInt(tiles.length);
			int y = random.nextInt(tiles[0].length);
			if (!getTile(x, y).isOccupied() && getTile(x, y).isWalkable()) {
				placePiece(critter, x, y);
				critters.add(critter);
				getEventContext().fire(Event.CRITTER_SPAWN, critter);
			}
		}
		if (critters.isEmpty()) {
			throw new RuntimeException(
					"Randomness failed when placing Critters");
		}
		mainCharacter = critters.get(0);
		mainCharacter.setHuman(true);
		mainCharacter.setHostile(false);

	}

	/**
	 * This moves npcs randomly one tile
	 */
	public void moveNpcs() {

		// move all of the non main character critters
		Direction[] possibleDirs = new Direction[] { Direction.UP,
				Direction.RIGHT, Direction.DOWN, Direction.LEFT };
		for (int i = 0; i < critters.size(); i++) {
			Critter critter = critters.get(i);
			if (!critter.equals(mainCharacter)) {
				int nextDir = getEventContext().getRandom().nextInt(
						INVERSE_CHANCE_TO_MOVE);
				if (nextDir < possibleDirs.length) {
					tryMove(critter, possibleDirs[nextDir]);
				}
			}
		}
	}

	public void nextTurn() {
		LOG.debug("Starting new turn...");

		clearDisabledTiles();
		clearTargetingRange();
		clearCrosshair();

		modeHistory.clear();

		// check end conditions
		if (!isMainCharacterOnBoard()) {
			getEventContext().fire(Event.LOSE_CONDITION);
		} else {
			if (isInCombat()) {

				if (sequence.isEmpty()) {
					LOG.debug("Critter sequence empty, starting new round...");
					createCritterSequence();
				}

				// figure out who the next controlling piece is
				assignControl(sequence.remove(0));

				// detect and set up the first control mode

				AbstractGameBoardControlMode firstMode = new CombatMovementMode(
						this, getEventContext());

				pushMode(firstMode);

				// wait for user input
			} else {
				getEventContext().fire(Event.WIN_CONDITION);
				/*
				 * // give the main character control
				 * assignControl(mainCharacter);
				 * 
				 * firstMode = new AdventureMode(this, getEventContext());
				 */
			}
		}
	}

	public void popMode() {
		modeHistory.remove(modeHistory.size() - 1);
		refreshMode();
	}

	public void pushMode(AbstractGameBoardControlMode mode) {
		mode.readAndStoreState();
		mode.configureTileState();
		modeHistory.add(mode);
	}

	public void refreshMode() {
		modeHistory.get(modeHistory.size() - 1).configureTileState();
	}

	public void removeCritter(Critter critter) {
		getTile(critter.getX(), critter.getY()).remove(critter);
		sequence.remove(critter);
		critters.remove(critter);
		repaint();
	}

	public void renderCrosshair(List<Tile> tilesToTarget) {
		clearCrosshair();
		if (tilesToTarget != null) {
			for (Tile tile : tilesToTarget) {
				tile.setTargeted(true);
				targetedTiles.add(tile);
			}
			repaint();
		} else {
			LOG.warn("targetTiles called with null Tile List");
		}
	}

	@Required
	public void setBoardDefinitionFactory(
			BoardDefinitionFactory boardDefinitionFactory) {
		this.boardDefinitionFactory = boardDefinitionFactory;
	}

	/**
	 * This allows movement to the provided Tile List and only to that List
	 * 
	 * @param enabledTiles
	 */
	public void setEnabledTiles(List<Tile> enabledTiles) {
		disableAllTiles();
		if (enabledTiles != null) {
			for (Tile tile : enabledTiles) {
				tile.setEnabled(true);
				disabledTiles.remove(tile);
			}
		}
		repaint();
	}

	/**
	 * Clears any previous targeting range and sets a new one
	 * 
	 * @param targetingRange
	 */
	public void setTargetingRange(List<Tile> targetingRange) {
		clearTargetingRange();
		if (targetingRange != null) {
			for (Tile tile : targetingRange) {
				tile.setInTargetingRange(true);
				tilesInTargetingRange.add(tile);
			}
			repaint();
		} else {
			LOG.warn("setTargetable called with null Tile List");
		}
	}

	@Required
	public void setTileFactory(TileFactory tileFactory) {
		this.tileFactory = tileFactory;
	}

	public boolean tryMove(Direction dir) {
		return tryMove(controllingCritter, dir);
	}

	@Required
	public void setDecorationFactory(DecorationFactory decorationFactory) {
		this.decorationFactory = decorationFactory;
	}

	@Override
	protected boolean doBeforeMoveExecution(AbstractBoardPiece piece,
			Tile nextTile, Direction dir) {
		EntranceDefinition entranceDef = entrances.get(nextTile);
		if (entranceDef != null && piece.equals(mainCharacter)
				&& dir.equals(entranceDef.getDir())) {

			try {
				load(entranceDef.getId(), entranceDef.getIndex());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		return true;
	}

}
