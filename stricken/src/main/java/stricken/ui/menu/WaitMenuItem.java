package stricken.ui.menu;

import stricken.Stricken;
import stricken.event.IEventContext;

public class WaitMenuItem extends AbstractMenuItem {

	private static final long serialVersionUID = -6573448083858809215L;

	public WaitMenuItem(Menu parent) {
		super(parent, "Wait");
	}

	@Override
	public void execute() {
		IEventContext eventContext = parent.getEventContext();
		eventContext.fire(Stricken.Event.END_OF_TURN);
	}

}
