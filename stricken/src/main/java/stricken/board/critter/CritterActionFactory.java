package stricken.board.critter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.core.io.Resource;

import stricken.board.AbstractCritterTileInteraction;
import stricken.board.AbstractTargetStatChangeInteraction;
import stricken.board.Tile;
import stricken.collector.AbstractDecayingTileCollector;
import stricken.collector.IPredicate;
import stricken.collector.ITileCollector;
import stricken.event.IEventContext;
import stricken.util.AbstractXmlConsumer;

/**
 * This class produces a CritterAction for a String
 * 
 * @author ofuangka
 * 
 */
public class CritterActionFactory extends AbstractXmlConsumer {

	public enum PredicateType {
		OCCUPIED_BY_CRITTER, ALL_TILES
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
		String elXpath = "/critterActions/critterAction[@id='" + id + "']";
		log.debug("Requesting critter using XPath: '" + elXpath + "'...");
		return parseCritterAction(
				(Element) getDocument().selectSingleNode(elXpath), critter);
	}

	protected CritterAction parseCritterAction(Element el, Critter critter) {

		ITileCollector targetingRange = parseTileCollector((Element) el
				.selectSingleNode("tileCollector[@type='TARGETING_RANGE']"));

		ITileCollector aoe = parseTileCollector((Element) el
				.selectSingleNode("tileCollector[@type='AOE']"));

		AbstractCritterTileInteraction effect = parseInteraction(
				(Element) el.selectSingleNode("interaction"), critter);

		IPredicate<Tile> predicate = parsePredicate((Element) el
				.selectSingleNode("predicate"));

		return new CritterAction(el.valueOf("@name"), targetingRange,
				predicate, aoe, effect);
	}

	protected ITileCollector parseTileCollector(Element el) {
		return getAbstractDecayingTileCollector(
				Integer.valueOf(el.valueOf("@costThreshold")),
				Integer.valueOf(el.valueOf("@tileCost")),
				StringUtils.isNotBlank(el.valueOf("@isInclusive")));
	}

	protected AbstractCritterTileInteraction parseInteraction(Element el,
			Critter critter) {
		AbstractCritterTileInteraction ret = null;
		return ret;
	}

	protected AbstractDecayingTileCollector getAbstractDecayingTileCollector(
			final int costThreshold, final int tileCost,
			final boolean isInclusive) {
		return new AbstractDecayingTileCollector() {

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

	/**
	 * Produces a Predicate depending on which PredicateType was passed in
	 * 
	 * @param predicateType
	 * @return
	 */
	protected IPredicate<Tile> parsePredicate(Element el) {
		IPredicate<Tile> ret = new IPredicate<Tile>() {

			@Override
			public boolean apply(Tile t) {
				return t != null
						&& t.isOccupied()
						&& Critter.class.isAssignableFrom(t.getOccupant()
								.getClass());
			}

		};
		return ret;
	}
}
