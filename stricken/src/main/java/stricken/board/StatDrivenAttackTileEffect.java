package stricken.board;

import java.util.Random;

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
public class StatDrivenAttackTileEffect implements ITileEffect {

	private final Stat driver;
	private final int damageRange;
	private final int modifier;
	private IEventContext eventContext;

	/**
	 * The attack damage is calculated as follows: driverValue + random(0,
	 * damageRange) + modifier
	 * 
	 * @param driver
	 * @param damageRange
	 *            - An int representing the range of possible damage values
	 * @param modifier
	 *            - An int that is always added to the resulting calculation
	 * @param eventContext
	 */
	public StatDrivenAttackTileEffect(Stat driver, int damageRange,
			int modifier, IEventContext eventContext) {
		this.driver = driver;
		this.damageRange = damageRange;
		this.modifier = modifier;
		this.eventContext = eventContext;
	}

	/**
	 * This method checks that a Critter is occupying the target tile. If so, it
	 * calculates the damage done by the attack and subtracts from the target's
	 * HP. If the target has died, it fires a CRITTER_DEATH event
	 */
	@Override
	public void execute(Critter source, Tile targetTile) {

		if (targetTile.isOccupied()) {

			AbstractBoardPiece targetPiece = targetTile.getOccupant();

			if (Critter.class.isAssignableFrom(targetPiece.getClass())) {

				Critter target = (Critter) targetPiece;

				Random random = eventContext.getRandom();

				int netDamage = source.getStat(driver) + modifier
						+ random.nextInt(damageRange);

				target.setStat(Stat.HP, target.getStat(Stat.HP) - netDamage);

				if (target.getStat(Stat.HP) <= 0) {
					eventContext.fire(Stricken.Event.CRITTER_DEATH, target);
				}

			}
		}
	}

}
