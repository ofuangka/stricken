package stricken.board.effect;

import stricken.board.piece.Critter;
import stricken.board.piece.Tile;
import stricken.board.piece.Critter.Stat;
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
