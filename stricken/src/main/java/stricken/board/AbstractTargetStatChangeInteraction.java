package stricken.board;

import java.util.Random;

import org.apache.log4j.Logger;

import stricken.Stricken;
import stricken.board.critter.Critter;
import stricken.board.critter.Critter.Stat;
import stricken.event.IEventContext;

/**
 * This class represents an HP reducing attack whose damage is driven by a
 * Critter.Stat
 * 
 * @author ofuangka
 * 
 */
public abstract class AbstractTargetStatChangeInteraction extends
		AbstractCritterTileInteraction {

	private static final Logger log = Logger
			.getLogger(AbstractTargetStatChangeInteraction.class);

	private final Stat affectedStat;
	private final int effectRange;
	private final int modifier;
	private IEventContext eventContext;

	/**
	 * The attack damage is calculated as follows: driverValue + random(0,
	 * damageRange) + modifier
	 * 
	 * @param driver
	 * @param affects
	 *            - The target stat to affect
	 * @param effectRange
	 *            - An int representing the range of possible damage values
	 * @param modifier
	 *            - An int that is always added to the resulting calculation
	 * @param eventContext
	 */
	public AbstractTargetStatChangeInteraction(Stat affects, int effectRange,
			int modifier, IEventContext eventContext) {
		this.affectedStat = affects;
		this.effectRange = effectRange;
		this.modifier = modifier;
		this.eventContext = eventContext;
	}

	/**
	 * This method checks that a Critter is occupying the target tile. If so, it
	 * calculates the damage done by the attack and subtracts from the target's
	 * HP. If the target has died, it fires a CRITTER_DEATH event
	 */
	@Override
	public void interact(Tile targetTile) {

		if (targetTile.isOccupied()) {

			AbstractBoardPiece targetPiece = targetTile.getOccupant();

			if (Critter.class.isAssignableFrom(targetPiece.getClass())) {

				Critter target = (Critter) targetPiece;

				Random random = eventContext.getRandom();

				int netEffect = getStartingValue(targetTile) + modifier
						+ random.nextInt(effectRange);

				int newValue = target.getStat(affectedStat) + netEffect;

				int maxValue = -1;

				if (Critter.Stat.HP.equals(affectedStat)) {
					maxValue = target.getStat(Critter.Stat.MAXHP);
				}

				if (maxValue != -1 && maxValue < newValue) {
					newValue = maxValue;
				}

				log.debug("Target '" + target + "'." + affectedStat + " = "
						+ newValue);

				target.setStat(affectedStat, newValue);

				if (target.getStat(Critter.Stat.HP) <= 0) {
					eventContext.fire(Stricken.Event.CRITTER_DEATH, target);
				}
			} else {
				log.debug("Occupant is not a Critter");
			}
		} else {
			log.debug("No occupant in Tile " + targetTile);
		}
	}

	public abstract int getStartingValue(Tile targetTile);

}
