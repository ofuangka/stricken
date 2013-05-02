package stricken.ui;

import stricken.event.GameEventContext;
import stricken.event.IEventContext;

public class WaitMenuItem extends AbstractMenuItem {

	private static final long serialVersionUID = -6573448083858809215L;

	public WaitMenuItem(Menu parent) {
		super(parent, "Wait");
	}

	@Override
	public void execute() {
		IEventContext eventContext = parent.getEventContext();
		eventContext.fire(GameEventContext.CLEAR_IN_GAME_MENUS);
		eventContext.fire(GameEventContext.NEXT_TURN);
	}

}
