package stricken;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import stricken.board.GameBoard;
import stricken.board.mode.TargetingMode;
import stricken.board.piece.Critter;
import stricken.board.piece.CritterAction;
import stricken.common.StrickenConstants;
import stricken.event.AbstractEventContext;
import stricken.event.Event;
import stricken.event.IEvent;
import stricken.event.IEventHandler;
import stricken.ui.CritterListItem;
import stricken.ui.CritterListPane;
import stricken.ui.IKeySink;
import stricken.ui.InGameMenuLayer;
import stricken.ui.menu.CritterMenuFactory;
import stricken.ui.menu.Menu;

public class Stricken extends AbstractEventContext implements IEventHandler {

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
	private Menu youDiedScreen;
	private Menu youWinScreen;
	private JPanel contentPane;
	private JPanel glassPane;
	private IKeySink currentKeySink;

	private JComponent gameInterface;
	private CritterListPane critterListPane;
	private GameBoard board;
	private InGameMenuLayer inGameMenuLayer;

	private CritterMenuFactory critterMenuFactory;

	private String startGameBoardId;

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

	/* Event handlers */
	public void handleCritterAction(CritterAction action) {
		board.pushMode(new TargetingMode(board, this, action));
		inGameMenuLayer.setVisible(false);
	}

	public void handleCritterDeath(Critter deceased) {
		board.removeCritter(deceased);
		critterListPane.removeCritter(deceased);
	}
	
	public void handleCritterSpawn(Critter spawn) {
		critterListPane.addCritter(spawn);
	}

	/**
	 * Handles an end of turn request. Clears any in game menus, makes the in
	 * game menu layer visible (some modes set it invisible), and calls the
	 * board.nextTurn() method
	 */
	public void handleEndOfTurn() {
		inGameMenuLayer.clearMenus();
		inGameMenuLayer.setVisible(true);
		board.nextTurn();
		critterListPane.repaint();
	}

	/**
	 * Attach all Event handlers
	 */
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
		case CRITTER_SPAWN: {
			handleCritterSpawn((Critter) arg);
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
			showScreen(gameInterface);
			break;
		}
		case SHOW_MAIN_MENU: {
			showScreen(mainMenu);
			break;
		}
		case LOSE_CONDITION: {
			showScreen(youDiedScreen);
			break;
		}
		case WIN_CONDITION: {
			showScreen(youWinScreen);
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

	/**
	 * This method shows the previous inGameMenuLayer (if there was one)
	 */
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
		board.clearBoardState();
		critterListPane.clearCritters();
		try {
			board.load(startGameBoardId);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		showScreen(gameInterface);
		board.nextTurn();
	}

	@Required
	public void setCritterMenuFactory(CritterMenuFactory critterMenuFactory) {
		this.critterMenuFactory = critterMenuFactory;
	}

	@Required
	public void setGameBoard(GameBoard board) {
		this.board = board;
	}

	@Required
	public void setGameInterface(JComponent gameInterface) {
		this.gameInterface = gameInterface;
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
	public void setStartGameBoardId(
			@Qualifier("startGameBoardId") String startGameBoardId) {
		this.startGameBoardId = startGameBoardId;
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

	@Required
	public void setYouDiedScreen(Menu youDiedScreen) {
		this.youDiedScreen = youDiedScreen;
	}

	@Required
	public void setYouWinScreen(Menu youWinScreen) {
		this.youWinScreen = youWinScreen;
	}

	@Required
	public void setCritterListPane(CritterListPane critterListPane) {
		this.critterListPane = critterListPane;
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
