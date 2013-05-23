package stricken.ui.menu;

import stricken.event.IEvent;
import stricken.event.IEventContext;

public class EventMenuItem extends AbstractMenuItem {

	private static final long serialVersionUID = 9148425736157375354L;

	private IEvent event;
	private Object arg;

	public EventMenuItem(IEventContext eventContext, String label, IEvent event) {
		super(eventContext, label);
		this.event = event;
	}

	public EventMenuItem(IEventContext eventContext, String label,
			IEvent event, Object arg) {
		this(eventContext, label, event);
		this.arg = arg;
	}

	@Override
	public void execute() {
		getEventContext().fire(event, arg);

	}

	public void setArg(Object arg) {
		this.arg = arg;
	}

}
