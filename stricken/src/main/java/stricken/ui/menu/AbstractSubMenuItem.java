package stricken.ui.menu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import stricken.Stricken;
import stricken.event.IEventContext;

public abstract class AbstractSubMenuItem extends AbstractMenuItem {

	private static final long serialVersionUID = 3288949207624820781L;

	public AbstractSubMenuItem(IEventContext eventContext, String label) {
		super(eventContext, label + ' ');
	}

	@Override
	public void execute() {
		eventContext.fire(Stricken.Event.PUSH_IN_GAME_SUBMENU, getSubMenu());
	}

	public abstract Menu getSubMenu();

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		Dimension actualSize = getSize();

		int[] xPoints = new int[] { actualSize.width - 8, actualSize.width - 5,
				actualSize.width - 8 };
		int[] yPoints = new int[] { 10, actualSize.height / 2,
				actualSize.height - 10 };

		g2d.setColor(FONT_COLOR);
		g2d.fillPolygon(xPoints, yPoints, 3);
	}

}
