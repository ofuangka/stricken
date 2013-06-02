package stricken.board.piece;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Critter extends AbstractBoardPiece {

	public enum Stat {
		HP, MAXHP, SPEED, STRENGTH, WILL, DAMAGE_RANGE, DAMAGE_MODIFIER
	}

	private static final int DEFAULT_STAT_VALUE = 0;

	private static final String HTH_ATTACK = "HTH_ATTACK";

	private String attack = HTH_ATTACK;
	private List<String> talents = new ArrayList<String>();
	private List<String> items = new ArrayList<String>();

	private boolean selected;
	private boolean hostile;
	private boolean human;

	private Map<Stat, Integer> stats = new HashMap<Stat, Integer>();

	public Critter(Dimension spriteSize, BufferedImage spriteSheet,
			int spriteSheetX, int spriteSheetY) {
		super(spriteSize, spriteSheet, spriteSheetX, spriteSheetY);
		setTakingUpSpace(true);
		setStat(Stat.DAMAGE_RANGE, 1);
	}

	public String getAttack() {
		return attack;
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

	public final void setStat(Stat key, Integer value) {
		stats.put(key, value);
	}

	public void setTalents(List<String> talents) {
		this.talents = talents;
	}

}
