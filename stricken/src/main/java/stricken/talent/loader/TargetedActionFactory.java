package stricken.talent.loader;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.core.io.Resource;

import stricken.board.collector.AbstractDecayingTileCollector;
import stricken.board.collector.ITileCollector;
import stricken.board.collector.TileCollectorType;
import stricken.board.collector.TileListFilter;
import stricken.board.collector.TileListFilter.TileListFilterType;
import stricken.board.effect.AbstractEffect;
import stricken.board.effect.StatDrivenStatEffect;
import stricken.board.piece.Critter;
import stricken.board.piece.Critter.Stat;
import stricken.board.piece.TargetedAction;
import stricken.board.piece.Tile;
import stricken.event.IEventContext;

public class TargetedActionFactory {

	private final IEventContext eventContext;
	private final Map<String, TargetedActionDefinition> targetedActionDefinitions;

	public TargetedActionFactory(Resource resource, ObjectMapper objectMapper,
			IEventContext eventContext) {
		try {
			targetedActionDefinitions = objectMapper.readValue(resource
					.getURI().toURL(),
					new TypeReference<Map<String, TargetedActionDefinition>>() {
					});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.eventContext = eventContext;
	}

	public TargetedAction get(String id, Critter critter) {
		TargetedActionDefinition targetedActionDefinition = targetedActionDefinitions
				.get(id);
		ITileCollector targetedRange = getTileCollector(targetedActionDefinition
				.getTargetingRange());
		TileListFilter actualRangeFilter = getTileListFilter(TileListFilterType
				.valueOf(targetedActionDefinition.getActualRangeFilterType()));
		ITileCollector areaOfEffect = getTileCollector(targetedActionDefinition
				.getAreaOfEffect());
		AbstractEffect effect = getEffect(targetedActionDefinition.getEffect());
		effect.setSource(critter);
		return new TargetedAction(targetedActionDefinition.getName(),
				targetedRange, actualRangeFilter, areaOfEffect, effect);

	}

	protected AbstractEffect getEffect(EffectDefinition def) {
		return new StatDrivenStatEffect(Stat.valueOf(def.getDrivingStat()),
				Stat.valueOf(def.getAffectedStat()), def.getMin(),
				def.getMax(), def.isPositive(), eventContext);
	}

	protected TileListFilter getTileListFilter(TileListFilterType type) {
		TileListFilter ret = TileListFilter.NULL_TILE_FILTER;
		switch (type) {
		case OCCUPIED_BY_CRITTER_FILTER: {
			ret = TileListFilter.OCCUPIED_BY_CRITTER_FILTER;
		}
		default: {

		}
		}
		return ret;
	}

	protected ITileCollector getTileCollector(final TileCollectorDefinition def) {
		ITileCollector ret = null;
		switch (TileCollectorType.valueOf(def.getType())) {
		case DECAYING: {
			ret = new AbstractDecayingTileCollector(
					getTileListFilter(TileListFilterType.valueOf(def
							.getFilterType()))) {

				@Override
				protected int getCostThreshold() {
					return def.getRange();
				}

				@Override
				protected int getTileCost(Tile tile) {
					return 1;
				}

				@Override
				protected boolean isInclusive() {
					return def.isInclusive();
				}

				@Override
				protected boolean isTileValid(Tile tile) {
					return tile != null;
				}

			};
		}
		}
		return ret;
	}
}
