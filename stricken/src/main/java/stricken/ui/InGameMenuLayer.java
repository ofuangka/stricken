package stricken.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import stricken.event.GameEventContext;
import stricken.event.IEventContext;
import stricken.event.IEventHandler;

public class InGameMenuLayer extends JPanel implements ILayer,
		IDelegatingKeySink, IEventHandler {

	private static final long serialVersionUID = -194016976519008438L;

	private static final Logger log = Logger.getLogger(InGameMenuLayer.class);

	public static final int MARGIN_VERTICAL = 1;
	public static final int MARGIN_HORIZONTAL = 1;

	private List<Menu> previousMenus = new ArrayList<Menu>();
	private List<Menu> menus = new ArrayList<Menu>();

	private JLabel spacer = new JLabel();

	public InGameMenuLayer(IEventContext eventContext) {
		super(new GridBagLayout());
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(MARGIN_VERTICAL,
				MARGIN_HORIZONTAL, MARGIN_VERTICAL, MARGIN_HORIZONTAL));
		eventContext.subscribe(GameEventContext.IN_GAME_MENU, this);
		eventContext.subscribe(GameEventContext.CLEAR_IN_GAME_MENUS, this);
		eventContext.subscribe(GameEventContext.SHOW_PREVIOUS_IN_GAME_MENU,
				this);
	}

	public void addMenu(Menu menu) {

		log.info("Adding Menu " + menu);

		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = menus.size();
		gc.gridy = 0;
		gc.weighty = 1;
		gc.anchor = GridBagConstraints.NORTHWEST;
		add(menu, gc);
		menus.add(menu);

		resetSpacer();

		revalidate();

		repaint();
	}

	public void showPrevious() {
		for (Menu menu : previousMenus) {
			addMenu(menu);
		}
	}

	@Override
	public void backspace() {
		popMenu();
	}

	public void clearMenus() {
		previousMenus.clear();
		previousMenus.addAll(menus);
		while (!menus.isEmpty()) {
			remove(menus.remove(0));
		}
		resetSpacer();
		revalidate();
		repaint();
	}

	@Override
	public void down() {
		getCurrentKeySink().down();
	}

	@Override
	public void enter() {
		getCurrentKeySink().enter();
	}

	@Override
	public void esc() {
		popMenu();
	}

	@Override
	public IKeySink getCurrentKeySink() {
		if (!menus.isEmpty()) {
			return menus.get(menus.size() - 1);
		}
		throw new IllegalStateException(
				"Cannot send key presses to an empty InGameMenuLayer");
	}

	@Override
	public void handleEvent(String type, Object arg) {
		if (GameEventContext.IN_GAME_MENU.equals(type)) {
			addMenu((Menu) arg);
		} else if (GameEventContext.CLEAR_IN_GAME_MENUS.equals(type)) {
			clearMenus();
		} else if (GameEventContext.SHOW_PREVIOUS_IN_GAME_MENU.equals(type)) {
			showPrevious();
		}
	}

	@Override
	public boolean isEmpty() {
		return menus.isEmpty();
	}

	@Override
	public void left() {
		popMenu();
	}

	public void popMenu() {
		if (menus.size() > 0) {
			remove(menus.remove(menus.size() - 1));
			resetSpacer();
			repaint();
		}
	}

	private void resetSpacer() {
		remove(spacer);
		GridBagConstraints spacerGc = new GridBagConstraints();
		spacerGc.gridx = menus.size();
		spacerGc.gridy = 0;
		spacerGc.weightx = 1;
		spacerGc.weighty = 1;
		add(spacer, spacerGc);
	}

	@Override
	public void right() {
		getCurrentKeySink().right();
	}

	@Override
	public void space() {
		right();
	}

	@Override
	public void up() {
		getCurrentKeySink().up();
	}

	@Override
	public void x() {
		getCurrentKeySink().x();
	}
}
