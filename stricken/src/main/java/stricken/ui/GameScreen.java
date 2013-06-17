package stricken.ui;

import java.awt.Dimension;

import javax.swing.JSplitPane;

public class GameScreen extends JSplitPane implements IKeySink {

	private static final long serialVersionUID = 5664713333844630418L;

	private GameLayeredPane gameLayeredPane;
	private GameInterface gameInterface;
	private Dimension spriteSize;
	private double distribution;

	public GameScreen(GameLayeredPane gameLayeredPane,
			GameInterface gameInterface, Dimension spriteSize,
			double distribution) {
		super(JSplitPane.HORIZONTAL_SPLIT, gameLayeredPane, gameInterface);
		this.gameLayeredPane = gameLayeredPane;
		this.gameInterface = gameInterface;
		this.spriteSize = spriteSize;
		this.distribution = distribution;
		setOneTouchExpandable(false);
		setDividerSize(0);
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		gameLayeredPane.setPreferredSize(new Dimension(
				(int) (preferredSize.width * distribution / spriteSize.width)
						* spriteSize.width, preferredSize.height));
		gameInterface.setPreferredSize(new Dimension(preferredSize.width
				- gameLayeredPane.getPreferredSize().width,
				preferredSize.height));

	}

	@Override
	public void backspace() {
		gameLayeredPane.backspace();
	}

	@Override
	public void down() {
		gameLayeredPane.down();

	}

	@Override
	public void enter() {
		gameLayeredPane.enter();

	}

	@Override
	public void esc() {
		gameLayeredPane.esc();

	}

	@Override
	public void left() {
		gameLayeredPane.left();

	}

	@Override
	public void right() {
		gameLayeredPane.right();

	}

	@Override
	public void space() {
		gameLayeredPane.space();

	}

	@Override
	public void up() {
		gameLayeredPane.up();

	}

	@Override
	public void x() {
		gameLayeredPane.x();

	}

}
