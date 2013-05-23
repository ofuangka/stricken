package stricken.board.critter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.core.io.Resource;

import stricken.board.AbstractEffect;
import stricken.board.ConstantStatEffect;
import stricken.board.StatDrivenStatEffect;
import stricken.board.Tile;
import stricken.board.critter.Critter.Stat;
import stricken.collector.AbstractDecayingTileCollector;
import stricken.collector.AbstractFilteredTileCollector;
import stricken.collector.ITileCollector;
import stricken.collector.ITileFilter;
import stricken.event.IEventContext;
import stricken.util.AbstractXmlConsumer;

/**
 * This class produces a CritterAction for a String
 * 
 * @author ofuangka
 * 
 */
public class CritterActionFactory extends AbstractXmlConsumer {

	public enum TileFilterType {
		OCCUPIED_BY_CRITTER, NO_FILTER
	}

	public enum TileEffectType {
		SOURCE_STAT_DRIVEN_TARGET_STAT_CHANGE, CONSTANT_DRIVEN_TARGET_STAT_CHANGE
	}

	private static final Logger log = Logger
			.getLogger(CritterActionFactory.class);

	private IEventContext eventContext;

	public CritterActionFactory(IEventContext eventContext, Resource resource)
			throws DocumentException, IOException {
		super(resource);
		this.eventContext = eventContext;
	}

	public CritterAction get(String id, Critter critter) {

		// get the main element representing the ID provided
		String elXpath = "/actions/action[@id='" + id + "']";
		log.debug("Requesting critter using XPath: '" + elXpath + "'...");
		return parseAction((Element) getDocument().selectSingleNode(elXpath),
				critter);
	}

	protected CritterAction parseAction(Element el, Critter critter) {

		ITileCollector targetingRange = parseRange((Element) el
				.selectSingleNode("range[@type='TARGETING']"));

		ITileCollector actualRange = parseRange((Element) el
				.selectSingleNode("range[@type='ACTUAL']"));

		ITileCollector aoe = parseRange((Element) el
				.selectSingleNode("range[@type='AOE']"));

		AbstractEffect effect = parseEffect(
				(Element) el.selectSingleNode("effect"), critter);

		return new CritterAction(el.valueOf("@name"), targetingRange,
				actualRange, aoe, effect);
	}

	protected ITileCollector parseRange(Element el) {
		return getAbstractDecayingTileCollector(
				parseFilter((Element) el.selectSingleNode("filter")),
				Integer.valueOf(el.valueOf("@costThreshold")),
				Integer.valueOf(el.valueOf("@tileCost")),
				StringUtils.isNotBlank(el.valueOf("@isInclusive")));
	}

	protected AbstractEffect parseEffect(Element el,
			Critter critter) {
		AbstractEffect ret = null;

		TileEffectType effectType = TileEffectType.valueOf(el.valueOf("@type"));

		Stat affectedStat = Critter.Stat.valueOf(el.valueOf("@affectedStat"));
		int effectRange = Integer.valueOf(el.valueOf("@effectRange"));
		int modifier = Integer.valueOf(el.valueOf("@modifier"));
		boolean positive = StringUtils.isNotBlank(el.valueOf("@isPositive"));

		switch (effectType) {
		case CONSTANT_DRIVEN_TARGET_STAT_CHANGE: {
			int drivingValue = Integer.valueOf("@drivingValue");
			ret = new ConstantStatEffect(drivingValue,
					affectedStat, effectRange, modifier, positive, eventContext);
		}
		default: {
			Stat drivingStat = Critter.Stat.valueOf(el.valueOf("@drivingStat"));
			ret = new StatDrivenStatEffect(drivingStat,
					affectedStat, effectRange, modifier, positive, eventContext);
			break;
		}
		}
		ret.setSource(critter);
		return ret;
	}

	protected ITileFilter parseFilter(Element el) {
		ITileFilter ret = null;

		TileFilterType filterType = TileFilterType.valueOf(el.valueOf("@type"));

		switch (filterType) {
		case OCCUPIED_BY_CRITTER: {
			ret = new ITileFilter() {

				@Override
				public boolean apply(Tile t) {
					return t != null
							&& t.isOccupied()
							&& Critter.class.isAssignableFrom(t.getOccupant()
									.getClass());
				}
			};
			break;
		}
		default: {
			ret = AbstractFilteredTileCollector.NO_FILTER;
			break;
		}
		}

		return ret;
	}

	protected AbstractDecayingTileCollector getAbstractDecayingTileCollector(
			final ITileFilter filter, final int costThreshold,
			final int tileCost, final boolean isInclusive) {
		return new AbstractDecayingTileCollector(filter) {

			@Override
			protected int getCostThreshold() {
				return costThreshold;
			}

			@Override
			protected int getTileCost(Tile tile) {
				return tileCost;
			}

			@Override
			protected boolean isInclusive() {
				return isInclusive;
			}

			@Override
			protected boolean isTileValid(Tile tile) {
				return tile != null;
			}

		};
	}
}
