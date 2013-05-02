package stricken.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import stricken.collector.AbstractDecayingTileCollector;
import stricken.collector.ITileCollector;
import stricken.collector.SingleTileCollector;
import stricken.effect.AttackTileEffect;
import stricken.skill.Skill;

public class Critter extends AbstractBoardPiece {

	public static final String DEATH_EVENT = "death";

	public static final String HP = "hp";
	public static final String MAXHP = "maxhp";
	public static final String SPEED = "speed";
	public static final String STRENGTH = "strength";

	private static final Color DEFAULT_COLOR = Color.magenta;
	private static final Color SELECTED_OUTLINE_COLOR = Color.red;

	private Color color;
	private boolean selected;

	private Map<String, Integer> stats = new HashMap<String, Integer>();

	public Critter(Dimension spriteSize) {
		this(spriteSize, DEFAULT_COLOR);
	}

	public Critter(Dimension spriteSize, Color color) {
		super(spriteSize);
		this.color = color;
		setTakingUpSpace(true);
	}

	public Skill getAttack() {
		ITileCollector range = new AbstractDecayingTileCollector() {

			@Override
			protected int getCostThreshold() {
				return 1;
			}

			@Override
			protected int getTileCost(Tile tile) {
				return 1;
			}

			@Override
			protected boolean isTileValid(Tile tile) {
				return tile != null && tile.isOccupied();
			}

			@Override
			protected boolean isInclusive() {
				return false;
			}

		};
		ITileCollector aoe = new SingleTileCollector();
		return new Skill(range, aoe, new AttackTileEffect());
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage ret = new BufferedImage(spriteSize.width,
				spriteSize.height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		g2d.setColor(color);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillArc(spriteSize.width / 4, spriteSize.height / 4,
				spriteSize.width / 2 - 1, spriteSize.height / 2 - 1, 0, 360);

		if (isSelected()) {
			g2d.setColor(SELECTED_OUTLINE_COLOR);
			g2d.drawArc(spriteSize.width / 4, spriteSize.height / 4,
					spriteSize.width / 2 - 1, spriteSize.height / 2 - 1, 0, 360);
		}

		return ret;
	}

	public Integer getStat(String key) {
		return (stats.containsKey(key)) ? stats.get(key) : 3;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setStat(String key, Integer value) {
		stats.put(key, value);
	}

}
