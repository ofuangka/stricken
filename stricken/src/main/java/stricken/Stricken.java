package stricken;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import stricken.board.Board;
import stricken.board.critter.Critter;
import stricken.board.critter.CritterAction;
import stricken.board.mode.TargetingMode;
import stricken.common.StrickenConstants;
import stricken.event.AbstractEventContext;
import stricken.event.IEvent;
import stricken.event.IEventHandler;
import stricken.ui.GameScreen;
import stricken.ui.IKeySink;
import stricken.ui.InGameMenuLayer;
import stricken.ui.menu.CritterMenuFactory;
import stricken.ui.menu.Menu;

public class Stricken extends AbstractEventContext implements IEventHandler {

	public enum Event implements IEvent {
		END_OF_TURN, SHOW_COMBAT_ACTION_MENU, POP_IN_GAME_MENU, CRITTER_ACTION, PUSH_IN_GAME_SUBMENU, SHOW_MAIN_MENU, RETURN_TO_GAME, SHOW_SYSTEM_MENU, CRITTER_DEATH, START_GAME, EXIT
	}

	/* constants */
	private static final String APP_CTX_FILE_LOCATION = "spring-context.xml";

	private static final String STRICKEN_BEAN_ID = "stricken";

	/* static variables */
	private static final Logger log = Logger.getLogger(Stricken.class);

	public static void main(String[] args) {

		// initialize the Spring application context
		AbstractApplicationContext appCtx = new ClassPathXmlApplicationContext(
				APP_CTX_FILE_LOCATION);

		// get the main bean
		Stricken stricken = appCtx.getBean(STRICKEN_BEAN_ID, Stricken.class);

		// close the file reference
		appCtx.close();

		// bootstrap the instance
		stricken.bootstrap();
	}

	/* instance variables */
	private List<JComponent> shownScreens = new ArrayList<JComponent>();
	private JFrame window;
	private Menu mainMenu;
	private Menu systemMenu;
	private JPanel contentPane;
	private JPanel glassPane;
	private IKeySink currentKeySink;

	private GameScreen gameScreen;
	private Board board;
	private InGameMenuLayer inGameMenuLayer;

	private CritterMenuFactory critterMenuFactory;

	/* configuration variables */
	private Dimension windowSize;
	private String windowTitle;

	public Stricken() {
		// iterate over the event types, creating them and subscribing to them
		for (Event event : Event.values()) {
			createEvent(event);
			subscribe(event, this);
		}
	}

	public void bootstrap() {
		log.info("Bootstrapping...");
		createWindow();
		createContentPane();
		createGlassPane();
		showScreen(mainMenu);
		showAndCenterWindow();
	}

	/**
	 * This method creates a content pane and configures it to funnel key events
	 * to the current keysink
	 */
	private void createContentPane() {
		log.debug("Initializing content pane...");
		contentPane = new JPanel(new GridBagLayout());
		InputMap inputMap = new InputMap();
		ActionMap actionMap = new ActionMap();

		inputMap.put(KeyStroke.getKeyStroke(StrickenConstants.UP),
				StrickenConstants.UP);
		inputMap.put(KeyStroke.getKeyStroke(StrickenConstants.RIGHT),
				StrickenConstants.RIGHT);
		inputMap.put(KeyStroke.getKeyStroke(StrickenConstants.DOWN),
				StrickenConstants.DOWN);
		inputMap.put(KeyStroke.getKeyStroke(StrickenConstants.LEFT),
				StrickenConstants.LEFT);
		inputMap.put(KeyStroke.getKeyStroke(StrickenConstants.ENTER),
				StrickenConstants.ENTER);
		inputMap.put(KeyStroke.getKeyStroke(StrickenConstants.ESCAPE),
				StrickenConstants.ESCAPE);
		inputMap.put(KeyStroke.getKeyStroke(StrickenConstants.BACKSPACE),
				StrickenConstants.BACKSPACE);
		inputMap.put(KeyStroke.getKeyStroke(StrickenConstants.SPACE),
				StrickenConstants.SPACE);
		inputMap.put(KeyStroke.getKeyStroke(StrickenConstants.X),
				StrickenConstants.X);

		actionMap.put(StrickenConstants.UP, new AbstractAction() {

			private static final long serialVersionUID = -4035258231309741970L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentKeySink.up();
			}

		});
		actionMap.put(StrickenConstants.RIGHT, new AbstractAction() {

			private static final long serialVersionUID = 407938813491787305L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentKeySink.right();
			}

		});
		actionMap.put(StrickenConstants.DOWN, new AbstractAction() {

			private static final long serialVersionUID = 4993801021049655318L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentKeySink.down();
			}

		});
		actionMap.put(StrickenConstants.LEFT, new AbstractAction() {

			private static final long serialVersionUID = 6666716265151808233L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentKeySink.left();
			}

		});
		actionMap.put(StrickenConstants.ENTER, new AbstractAction() {

			private static final long serialVersionUID = -2253852508738647202L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentKeySink.enter();
			}

		});
		actionMap.put(StrickenConstants.ESCAPE, new AbstractAction() {

			private static final long serialVersionUID = 5827916790133403430L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentKeySink.esc();
			}

		});
		actionMap.put(StrickenConstants.BACKSPACE, new AbstractAction() {

			private static final long serialVersionUID = 6253776701930914853L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentKeySink.backspace();
			}

		});
		actionMap.put(StrickenConstants.SPACE, new AbstractAction() {

			private static final long serialVersionUID = 4561198885927625005L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentKeySink.space();
			}

		});
		actionMap.put(StrickenConstants.X, new AbstractAction() {

			private static final long serialVersionUID = 3176448439753484377L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentKeySink.x();
			}

		});

		contentPane.setInputMap(JComponent.WHEN_FOCUSED, inputMap);
		contentPane.setActionMap(actionMap);
		window.setContentPane(contentPane);
	}

	private void createGlassPane() {
		log.debug("Initializing glass pane...");
		glassPane = new JPanel(new GridBagLayout());
		window.setGlassPane(glassPane);
	}

	/**
	 * Creates and configures the window
	 */
	private void createWindow() {
		log.debug("Initializing window...");
		window = new JFrame(windowTitle);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method gets called before a screen is shown for the first time
	 * 
	 * @param screen
	 */
	private void firstTimeConfigureScreen(JComponent screen) {
		screen.setPreferredSize(windowSize);
	}

	public void handleCritterAction(CritterAction action) {
		board.pushMode(new TargetingMode(board, this, action));
		inGameMenuLayer.setVisible(false);
	}

	public void handleCritterDeath(Critter deceased) {
		board.removeCritter(deceased);
	}

	public void handleEndOfTurn() {
		inGameMenuLayer.clearMenus();
		inGameMenuLayer.setVisible(true);
		board.nextTurn();
	}

	@Override
	public void handleEvent(IEvent event, Object arg) {
		switch ((Event) event) {
		case END_OF_TURN: {
			handleEndOfTurn();
			break;
		}
		case CRITTER_ACTION: {
			handleCritterAction((CritterAction) arg);
			break;
		}
		case SHOW_COMBAT_ACTION_MENU: {
			handleShowCombatActionMenu();
			break;
		}
		case PUSH_IN_GAME_SUBMENU: {
			handlePushInGameSubMenu((Menu) arg);
			break;
		}
		case POP_IN_GAME_MENU: {
			handlePopInGameMenu();
			break;
		}
		case SHOW_SYSTEM_MENU: {
			handleShowSystemMenu();
			break;
		}
		case CRITTER_DEATH: {
			handleCritterDeath((Critter) arg);
			break;
		}
		case EXIT: {
			handleExit();
			break;
		}
		case START_GAME: {
			handleStartGame();
			break;
		}
		case RETURN_TO_GAME: {
			showScreen(gameScreen);
			break;
		}
		case SHOW_MAIN_MENU: {
			showScreen(mainMenu);
			break;
		}
		default: {
			log.warn("No handler defined for event '" + event + "'");
			break;
		}
		}
	}

	public void handleExit() {
		System.exit(0);
	}

	public void handlePopInGameMenu() {
		if (inGameMenuLayer.isVisible()) {
			inGameMenuLayer.popMenu();
		} else {
			inGameMenuLayer.setVisible(true);
		}
	}

	private void handlePushInGameSubMenu(Menu menu) {
		inGameMenuLayer.addMenu(menu);

	}

	public void handleShowCombatActionMenu() {
		inGameMenuLayer.addMenu(critterMenuFactory.getCombatActionMenu(board
				.getControllingCritter()));
	}

	public void handleShowSystemMenu() {
		showScreen(systemMenu);
	}

	public void handleStartGame() {
		board.clear();
		board.load(null);
		showScreen(gameScreen);
		board.nextTurn();
	}

	@Required
	public void setBoard(Board board) {
		this.board = board;
	}

	@Required
	public void setCritterMenuFactory(CritterMenuFactory critterMenuFactory) {
		this.critterMenuFactory = critterMenuFactory;
	}

	@Required
	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	@Required
	public void setInGameMenuLayer(InGameMenuLayer inGameMenuLayer) {
		this.inGameMenuLayer = inGameMenuLayer;
	}

	@Required
	public void setMainMenu(Menu mainMenu) {
		this.mainMenu = mainMenu;
	}

	@Required
	public void setSystemMenu(Menu systemMenu) {
		this.systemMenu = systemMenu;
	}

	@Required
	public void setWindowSize(Dimension windowSize) {
		this.windowSize = windowSize;
	}

	@Required
	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	/**
	 * Places the window in the center of the screen and sets it visible
	 */
	private void showAndCenterWindow() {
		log.debug("Showing and centering window...");
		window.setVisible(true);
		window.pack();
		window.setLocationRelativeTo(null);
		contentPane.requestFocus();
	}

	/**
	 * Replaces all contents of the window with the argument JComponent, sizing
	 * it to the window size. If the screen is assignable from IKeySink, this
	 * method sets up the user input, setting currentKeySink. If the screen is
	 * not assignable from IKeySink, this method sets the currentKeySink equal
	 * to the preconfigured fallbackKeySink.
	 * 
	 * @param screen
	 */
	private void showScreen(JComponent screen) {
		log.info("Showing screen " + screen + "...");

		// check if the screen has already been added once
		if (!shownScreens.contains(screen)) {
			firstTimeConfigureScreen(screen);
			shownScreens.add(screen);
		}
		if (IKeySink.class.isAssignableFrom(screen.getClass())) {
			currentKeySink = (IKeySink) screen;
		} else {
			throw new IllegalArgumentException("Screen must implement IKeySink");
		}
		if (Menu.class.isAssignableFrom(screen.getClass())) {
			((Menu) screen).reset();
		}
		contentPane.removeAll();
		contentPane.add(screen, new GridBagConstraints());
		contentPane.revalidate();
		contentPane.repaint();
	}
}
