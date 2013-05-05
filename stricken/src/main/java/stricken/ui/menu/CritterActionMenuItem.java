package stricken.ui.menu;

import stricken.Stricken;
import stricken.action.CritterAction;
import stricken.event.IEventContext;

public class CritterActionMenuItem extends AbstractMenuItem {
	private static final long serialVersionUID = -2796309755131998313L;

	private CritterAction action;

	public CritterActionMenuItem(Menu parent, String label, CritterAction action) {
		super(parent, label);
		this.action = action;
	}

	@Override
	public void execute() {
		IEventContext eventContext = parent.getEventContext();

		eventContext.fire(Stricken.Event.CRITTER_ACTION, action);
	}

}
