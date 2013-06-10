package stricken.ui;

import java.awt.Dimension;

import javax.swing.JSplitPane;

public class GameInterface extends JSplitPane implements IKeySink {

	private static final long serialVersionUID = 5664713333844630418L;

	private GameScreen gameScreen;
	private CritterListPane unitListPane;
	private Dimension spriteSize;
	private double distribution;

	public GameInterface(GameScreen gameScreen, CritterListPane unitListPane,
			Dimension spriteSize, double distribution) {
		super(JSplitPane.HORIZONTAL_SPLIT, gameScreen, unitListPane);
		this.gameScreen = gameScreen;
		this.unitListPane = unitListPane;
		this.spriteSize = spriteSize;
		this.distribution = distribution;
		setOneTouchExpandable(false);
		setDividerSize(0);
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		gameScreen.setPreferredSize(new Dimension((int) (preferredSize.width
				* distribution / spriteSize.width)
				* spriteSize.width, preferredSize.height));
		unitListPane.setPreferredSize(new Dimension(preferredSize.width
				- gameScreen.getPreferredSize().width, preferredSize.height));

	}

	@Override
	public void backspace() {
		gameScreen.backspace();
	}

	@Override
	public void down() {
		gameScreen.down();

	}

	@Override
	public void enter() {
		gameScreen.enter();

	}

	@Override
	public void esc() {
		gameScreen.esc();

	}

	@Override
	public void left() {
		gameScreen.left();

	}

	@Override
	public void right() {
		gameScreen.right();

	}

	@Override
	public void space() {
		gameScreen.space();

	}

	@Override
	public void up() {
		gameScreen.up();

	}

	@Override
	public void x() {
		gameScreen.x();

	}

}
