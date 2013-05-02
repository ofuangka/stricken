package stricken.board.mode;

import stricken.board.Board;
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

	protected Board board;
	protected IEventContext eventContext;

	public AbstractBoardControlMode(Board board, IEventContext eventContext) {
		this.board = board;
		this.eventContext = eventContext;
	}

	/**
	 * This method should store the mode's state
	 */
	public abstract void readAndStoreState();

	/**
	 * This method does all of the enabling and targeting of tiles necessary for
	 * this mode's state. It should clear all previously enabled and targeted
	 * tiles before doing the new ones
	 */
	public abstract void enableAndTargetTiles();

	/**
	 * This method resets the mode without recreating its state
	 */
	public abstract void resetToOriginalState();

	@Override
	public void backspace() {

	}

	@Override
	public void esc() {

	}

	@Override
	public void space() {

	}

	@Override
	public void x() {

	}

}
