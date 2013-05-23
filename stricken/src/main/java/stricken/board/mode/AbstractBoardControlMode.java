package stricken.board.mode;

import stricken.board.Board;
import stricken.event.Event;
import stricken.event.IEventContext;
import stricken.ui.IKeySink;

/**
 * This is a special KeySink that has visibility of the Board and the overall
 * EventContext. The Board keeps a stack of these as its key delegates
 * 
 * @author ofuangka
 * 
 */
public abstract class AbstractBoardControlMode implements IKeySink {

	private Board board;
	private IEventContext eventContext;

	public AbstractBoardControlMode(Board board, IEventContext eventContext) {
		this.board = board;
		this.eventContext = eventContext;
	}

	@Override
	public void backspace() {

	}

	/**
	 * This method does all of the enabling and targeting of tiles necessary for
	 * this mode's state. It should clear all previously enabled and targeted
	 * tiles before doing the new ones
	 */
	public abstract void configureTileState();

	@Override
	public void esc() {
		eventContext.fire(Event.SHOW_SYSTEM_MENU);
	}

	/**
	 * This method should store the mode's state
	 */
	public void readAndStoreState() {
		
	}

	/**
	 * This method resets the mode without recreating its state
	 */
	public void resetToOriginalState() {
		
	}

	@Override
	public void space() {

	}

	@Override
	public void x() {

	}
	
	public Board getBoard() {
		return board;
		
	}
	
	public IEventContext getEventContext() {
		return eventContext;
	}

}
