package stricken.ui.menu;

import stricken.Stricken;
import stricken.event.IEventContext;

public abstract class AbstractSubMenuItem extends AbstractMenuItem {

	private static final long serialVersionUID = 3288949207624820781L;

	public AbstractSubMenuItem(Menu parent, String label) {
		super(parent, label);
	}

	@Override
	public void execute() {
		IEventContext eventContext = parent.getEventContext();
		eventContext.fire(Stricken.Event.PUSH_IN_GAME_SUBMENU, getSubMenu());
	}

	public abstract Menu getSubMenu();

}
