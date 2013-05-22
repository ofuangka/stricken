package stricken.board;

import stricken.board.critter.Critter.Stat;
import stricken.event.IEventContext;

public class ConstantDrivenTargetStatChangeInteraction extends
		AbstractTargetStatChangeInteraction {

	private final int drivingValue;

	public ConstantDrivenTargetStatChangeInteraction(int drivingValue,
			Stat affects, int effectRange, int modifier, boolean positive,
			IEventContext eventContext) {
		super(affects, effectRange, modifier, positive, eventContext);
		this.drivingValue = drivingValue;
	}

	@Override
	public int getStartingValue(Tile targetTile) {
		return drivingValue;
	}

}
