package stricken.board;

import stricken.board.critter.Critter;
import stricken.board.critter.Critter.Stat;
import stricken.event.IEventContext;

public class StatDrivenStatEffect extends
		AbstractStatEffect {

	private final Critter.Stat drivingStat;

	public StatDrivenStatEffect(Stat drivingStat,
			Stat affectedStat, int effectRange, int modifier, boolean positive,
			IEventContext eventContext) {
		super(affectedStat, effectRange, modifier, positive, eventContext);
		this.drivingStat = drivingStat;

	}

	@Override
	public int getStartingValue(Tile targetTile) {
		return getSource().getStat(drivingStat);
	}

}
