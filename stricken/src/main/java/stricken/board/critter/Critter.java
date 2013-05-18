package stricken.board.critter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stricken.board.AbstractBoardPiece;

public class Critter extends AbstractBoardPiece {

	public enum Stat {
		HP, MAXHP, SPEED, STRENGTH, WILL, DAMAGE_RANGE, DAMAGE_MODIFIER
	}

	private static int DEFAULT_STAT_VALUE = 0;

	private static final String HTH_ATTACK = "HTH_ATTACK";

	private static final Color DEFAULT_COLOR = Color.magenta;
	private static final Color SELECTED_OUTLINE_COLOR = Color.red;

	private String attack = HTH_ATTACK;
	private List<String> talents = new ArrayList<String>();
	private List<String> items = new ArrayList<String>();

	private Color color;
	private boolean selected;
	private boolean hostile;
	private boolean human;

	private Map<Stat, Integer> stats = new HashMap<Stat, Integer>();

	public Critter(Dimension spriteSize) {
		this(spriteSize, DEFAULT_COLOR);
	}

	public Critter(Dimension spriteSize, Color color) {
		super(spriteSize);
		this.color = color;
		setTakingUpSpace(true);
		setStat(Stat.DAMAGE_RANGE, 1);
	}

	public String getAttack() {
		return attack;
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

	public List<String> getItems() {
		return items;
	}

	public Integer getStat(Stat key) {
		return (stats.containsKey(key)) ? stats.get(key) : DEFAULT_STAT_VALUE;
	}

	public List<String> getTalents() {
		return talents;
	}

	public boolean isHostile() {
		return hostile;
	}

	public boolean isHuman() {
		return human;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setAttack(String attack) {
		this.attack = attack;
	}

	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}

	public void setHuman(boolean human) {
		this.human = human;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setStat(Stat key, Integer value) {
		stats.put(key, value);
	}

	public void setTalents(List<String> talents) {
		this.talents = talents;
	}

}
