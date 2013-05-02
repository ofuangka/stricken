package stricken.ui;

import stricken.event.GameEventContext;
import stricken.event.IEventContext;

public class AttackMenuItem extends AbstractMenuItem {

	private static final long serialVersionUID = -2047166862973311422L;

	public AttackMenuItem(Menu parent) {
		super(parent, "Attack");
	}

	@Override
	public void execute() {
		IEventContext eventContext = parent.getEventContext();
		eventContext.fire(GameEventContext.CLEAR_IN_GAME_MENUS);
		eventContext.fire(GameEventContext.ATTACK_REQUEST);
	}

}
