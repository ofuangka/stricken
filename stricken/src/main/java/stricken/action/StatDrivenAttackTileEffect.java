package stricken.action;

import java.util.Random;

import stricken.Stricken;
import stricken.board.AbstractBoardPiece;
import stricken.board.Critter;
import stricken.board.Critter.Stat;
import stricken.board.Tile;
import stricken.event.IEventContext;

public class StatDrivenAttackTileEffect implements ITileEffect {

	private final Stat driver;
	private final int damageRange;
	private final int modifier;
	private IEventContext eventContext;

	public StatDrivenAttackTileEffect(Stat driver, int damageRange, int modifier,
			IEventContext eventContext) {
		this.driver = driver;
		this.damageRange = damageRange;
		this.modifier = modifier;
		this.eventContext = eventContext;
	}

	@Override
	public void execute(Critter source, Tile targetTile) {

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
