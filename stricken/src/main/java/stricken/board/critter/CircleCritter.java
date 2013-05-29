package stricken.board.critter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class CircleCritter extends Critter {

	private static final Color SELECTED_HOSTILE_OUTLINE_COLOR = Color.red;
	private static final Color SELECTED_FRIENDLY_OUTLINE_COLOR = Color.blue;

	private Color color;

	public CircleCritter(Dimension spriteSize, Color color) {
		super(spriteSize, null, -1, -1);
		this.color = color;
		setTakingUpSpace(true);
		setStat(Stat.DAMAGE_RANGE, 1);
	}

	@Override
	public BufferedImage getImage() {
		Dimension spriteSize = getSpriteSize();
		BufferedImage ret = new BufferedImage(spriteSize.width,
				spriteSize.height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		g2d.setColor(color);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillArc(spriteSize.width / 4, spriteSize.height / 4,
				spriteSize.width / 2 - 1, spriteSize.height / 2 - 1, 0, 360);

		if (isSelected()) {
			if (isHostile()) {
				g2d.setColor(SELECTED_HOSTILE_OUTLINE_COLOR);
			} else {
				g2d.setColor(SELECTED_FRIENDLY_OUTLINE_COLOR);
			}
			g2d.drawArc(spriteSize.width / 4, spriteSize.height / 4,
					spriteSize.width / 2 - 1, spriteSize.height / 2 - 1, 0, 360);
		}

		return ret;
	}

}
