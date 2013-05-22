package stricken.board;

import stricken.board.critter.Critter;
import stricken.board.critter.Critter.Stat;
import stricken.event.IEventContext;

public class SourceStatDrivenTargetStatChangeInteraction extends
		AbstractTargetStatChangeInteraction {

	private final Critter.Stat drivingStat;

	public SourceStatDrivenTargetStatChangeInteraction(Stat drivingStat,
			Stat affects, int effectRange, int modifier, boolean positive,
			IEventContext eventContext) {
		super(affects, effectRange, modifier, positive, eventContext);
		this.drivingStat = drivingStat;

	}

	@Override
	public int getStartingValue(Tile targetTile) {
		return getSource().getStat(drivingStat);
	}

}
