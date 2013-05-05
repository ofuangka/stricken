package stricken.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Critter extends AbstractBoardPiece {

	public static final String DEATH_EVENT = "death";

	public static final String HP = "hp";
	public static final String MAXHP = "maxhp";
	public static final String SPEED = "speed";
	public static final String STRENGTH = "strength";

	private static final Color DEFAULT_COLOR = Color.magenta;
	private static final Color SELECTED_OUTLINE_COLOR = Color.red;

	private String weapon;
	private List<String> magic = new ArrayList<String>();

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

	public String getWeapon() {
		return weapon;
	}

	public List<String> getMagic() {
		return magic;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public void setMagic(List<String> magic) {
		this.magic = magic;
	}

}
