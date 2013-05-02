package stricken.event;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Event {

	private final String type;
	private final IEventContext source;
	private List<IEventHandler> handlers = new ArrayList<IEventHandler>();

	private Logger log = Logger.getLogger(Event.class);

	public Event(String type) {
		this(type, null);
	}

	public Event(String type, IEventContext source) {
		this.type = type;
		this.source = source;
	}

	public void fire() {
		fire(null);
	}

	public void fire(Object arg) {
		log.debug("Firing Event " + this
				+ ((arg != null) ? "(" + arg + ")" : "")); // show any non-null
															// arguments in
															// parentheses
		for (IEventHandler handler : handlers) {
			handler.handleEvent(type, arg);
		}
	}

	public IEventContext getSource() {
		return source;
	}

	public String getType() {
		return type;
	}

	public void subscribe(IEventHandler handler) {
		if (!handlers.contains(handler)) {
			handlers.add(handler);
		}
	}

	@Override
	public String toString() {
		return type;
	}

}
