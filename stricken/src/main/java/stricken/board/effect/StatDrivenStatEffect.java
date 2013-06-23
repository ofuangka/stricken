package stricken.board.effect;

import stricken.board.piece.Critter.Stat;
import stricken.event.IEventContext;

public class StatDrivenStatEffect extends AbstractStatEffect {

	private final Stat drivingStat;

	public StatDrivenStatEffect(Stat drivingStat, Stat affectedStat, int min,
			int max, boolean positive, IEventContext eventContext) {
		super(affectedStat, min, max, positive, eventContext);
		this.drivingStat = drivingStat;
	}

	@Override
	public int getModifier() {
		return getSource().getStat(drivingStat);
	}

}
