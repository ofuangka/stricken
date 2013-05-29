package stricken.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import stricken.board.critter.Critter;
import stricken.board.mode.AbstractGameBoardControlMode;
import stricken.board.mode.AdventureMode;
import stricken.board.mode.CombatMovementMode;
import stricken.common.Direction;
import stricken.event.Event;
import stricken.event.IEventContext;
import stricken.ui.IKeySink;

/**
 * The GameBoard keeps a registry of Critters, enabled and targeted Tiles. It
 * also manages control modes
 * 
 * @author ofuangka
 * 
 */
public class GameBoard extends AbstractViewportBoard {

	private static final long serialVersionUID = -1963940535869410879L;

	public GameBoard(IEventContext eventContext) {
		super(eventContext);
	}

	private static final Logger log = Logger.getLogger(GameBoard.class);

	public static final int INVERSE_CHANCE_TO_MOVE = 8;

	private Critter mainCharacter;
	private Critter controllingCritter;

	private List<Critter> critters = new ArrayList<Critter>();
	private List<Critter> sequence = new ArrayList<Critter>();

	private List<Tile> disabledTiles = new ArrayList<Tile>();
	private List<Tile> tilesInTargetingRange = new ArrayList<Tile>();
	private List<Tile> targetedTiles = new ArrayList<Tile>();

	private List<AbstractGameBoardControlMode> modeHistory = new ArrayList<AbstractGameBoardControlMode>();

	/**
	 * Sets the controlling Critter and calls repaint()
	 * 
	 * @param newCritter
	 */
	public void assignControl(Critter newCritter) {
		log.debug("Assigning control to Critter " + newCritter + "...");
		if (controllingCritter != null) {
			controllingCritter.setSelected(false);
		}
		controllingCritter = newCritter;
		controllingCritter.setSelected(true);
		repaint();
	}

	public void clear() {
		sequence.clear();
		critters.clear();
		disabledTiles.clear();
		tilesInTargetingRange.clear();
		targetedTiles.clear();
	}

	/**
	 * Clears all targeted Tiles
	 */
	public void clearCrosshair() {
		log.debug("Clearing targeted Tile objects...");
		while (!targetedTiles.isEmpty()) {
			targetedTiles.remove(0).setTargeted(false);
		}
		repaint();
	}

	/**
	 * Enables all tiles
	 */
	public void clearDisabledTiles() {
		log.debug("Removing all out of range tiles...");
		while (!disabledTiles.isEmpty()) {
			disabledTiles.remove(0).setEnabled(true);
		}
		repaint();
	}

	/**
	 * Puts all tiles out of targeting range
	 */
	public void clearTargetingRange() {
		log.debug("Clearing targetable Tile objects...");
		while (!tilesInTargetingRange.isEmpty()) {
			tilesInTargetingRange.remove(0).setInTargetingRange(false);
		}
	}

	private void createCritterSequence() {
		log.debug("Creating Critter sequence...");
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

	public void load(String id) {
		log.info("Loading board '" + id + "'...");

		// TODO: implement
		tiles = new Tile[11][11];
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = new Tile(getSpriteSize(), x, y);
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

		Random random = getEventContext().getRandom();
		int numCritters = random.nextInt(11) + 1;
		for (int i = 0; i < numCritters; i++) {
			Critter critter = new Critter(getSpriteSize(), new Color(
					random.nextInt(255), random.nextInt(255),
					random.nextInt(255)));
			critter.setStat(Critter.Stat.MAXHP, random.nextInt(3) + 1);
			critter.setStat(Critter.Stat.HP,
					critter.getStat(Critter.Stat.MAXHP));
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
			critter.setTalents(talents);

			int x = random.nextInt(11);
			int y = random.nextInt(11);
			if (!getTile(x, y).isOccupied()) {
				placePiece(critter, x, y);
				critters.add(critter);
			}
		}
		mainCharacter = critters.get(0);
		mainCharacter.setHuman(true);
		mainCharacter.setHostile(false);

	}

	public void nextTurn() {
		log.debug("Starting new turn...");

		clearDisabledTiles();
		clearTargetingRange();
		clearCrosshair();

		modeHistory.clear();

		// check end conditions
		if (!isMainCharacterOnBoard()) {
			getEventContext().fire(Event.LOSE_CONDITION);
		} else {
			AbstractGameBoardControlMode firstMode;
			if (isInCombat()) {

				if (sequence.isEmpty()) {
					log.debug("Critter sequence empty, starting new round...");
					createCritterSequence();
				}

				// figure out who the next controlling piece is
				assignControl(sequence.remove(0));

				// detect and set up the first control mode
				firstMode = new CombatMovementMode(this, getEventContext());
			} else {

				// give the main character control
				assignControl(mainCharacter);

				firstMode = new AdventureMode(this, getEventContext());
			}

			pushMode(firstMode);

			// wait for user input
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
			log.warn("targetTiles called with null Tile List");
		}
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
			log.warn("setTargetable called with null Tile List");
		}
	}

	public boolean isMainCharacterOnBoard() {
		return critters.contains(mainCharacter);
	}

	public boolean tryMove(Direction dir) {
		return tryMove(controllingCritter, dir);
	}

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

	public void alignViewport() {
		alignViewport(controllingCritter.getX(), controllingCritter.getY());
	}

}
