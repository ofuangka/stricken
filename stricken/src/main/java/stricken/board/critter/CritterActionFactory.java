package stricken.board.critter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.core.io.Resource;

import stricken.board.ITileEffect;
import stricken.board.StatDrivenAttackTileEffect;
import stricken.board.Tile;
import stricken.collector.AbstractDecayingTileCollector;
import stricken.collector.ITileCollector;
import stricken.event.IEventContext;
import stricken.util.AbstractXmlConsumer;

public class CritterActionFactory extends AbstractXmlConsumer {

	private static final Logger log = Logger
			.getLogger(CritterActionFactory.class);

	private IEventContext eventContext;

	public CritterActionFactory(IEventContext eventContext, Resource resource)
			throws DocumentException, IOException {
		super(resource);
		this.eventContext = eventContext;
	}

	public CritterAction get(String id, Critter critter) {
		
		String elXpath = "/critterActions/critterAction[@id='" + id + "']";
		
		log.info(elXpath);

		Element el = (Element) getDocument().selectSingleNode(
				elXpath);

		Element targetableMetadata = (Element) el
				.selectSingleNode("collector[@type='targetable']");
		Element aoeMetadata = (Element) el
				.selectSingleNode("collector[@type='aoe']");
		Element attackMetadata = (Element) el.selectSingleNode("attack");

		final int targetableCostThreshold = Integer.valueOf(targetableMetadata
				.valueOf("@costThreshold"));

		final int targetableTileCost = Integer.valueOf(targetableMetadata
				.valueOf("@tileCost"));

		final boolean targetableIsInclusive = StringUtils
				.isNotBlank(targetableMetadata.valueOf("@isInclusive"));

		final int aoeCostThreshold = Integer.valueOf(aoeMetadata
				.valueOf("@costThreshold"));
		final int aoeTileCost = Integer.valueOf(aoeMetadata
				.valueOf("@tileCost"));
		final boolean aoeIsInclusive = StringUtils.isNotBlank(aoeMetadata
				.valueOf("@isInclusive"));

		String statName = attackMetadata.valueOf("@stat");

		ITileCollector targetableRange = new AbstractDecayingTileCollector() {

			@Override
			protected int getCostThreshold() {
				return targetableCostThreshold;
			}

			@Override
			protected int getTileCost(Tile tile) {
				return targetableTileCost;
			}

			@Override
			protected boolean isInclusive() {
				return targetableIsInclusive;
			}

			@Override
			protected boolean isTileValid(Tile tile) {
				return tile != null;
			}

		};
		ITileCollector actualRange = new AbstractDecayingTileCollector() {

			@Override
			protected int getCostThreshold() {
				return targetableCostThreshold;
			}

			@Override
			protected int getTileCost(Tile tile) {
				return targetableTileCost;
			}

			@Override
			protected boolean isInclusive() {
				return targetableIsInclusive;
			}

			@Override
			protected boolean isTileValid(Tile tile) {
				return tile != null && tile.isOccupied();
			}

		};
		ITileCollector aoe = new AbstractDecayingTileCollector() {

			@Override
			protected int getCostThreshold() {
				return aoeCostThreshold;
			}

			@Override
			protected int getTileCost(Tile tile) {
				return aoeTileCost;
			}

			@Override
			protected boolean isInclusive() {
				return aoeIsInclusive;
			}

			@Override
			protected boolean isTileValid(Tile tile) {
				return tile != null && tile.isOccupied();
			}

		};

		int lookupDamageRange = 1;
		int lookupModifier = 0;

		ITileEffect effect = new StatDrivenAttackTileEffect(
				Critter.Stat.valueOf(statName.toUpperCase()),
				lookupDamageRange, lookupModifier, eventContext);

		return new CritterAction(el.valueOf("@name"), targetableRange,
				actualRange, aoe, effect);
	}
}
