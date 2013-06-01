package stricken.board.effect;

import stricken.board.piece.Tile;
import stricken.board.piece.critter.Critter.Stat;
import stricken.event.IEventContext;

public class ConstantStatEffect extends
		AbstractStatEffect {

	private final int drivingValue;

	public ConstantStatEffect(int drivingValue,
			Stat affectedStat, int effectRange, int modifier, boolean positive,
			IEventContext eventContext) {
		super(affectedStat, effectRange, modifier, positive, eventContext);
		this.drivingValue = drivingValue;
	}

	@Override
	public int getStartingValue(Tile targetTile) {
		return drivingValue;
	}

}
