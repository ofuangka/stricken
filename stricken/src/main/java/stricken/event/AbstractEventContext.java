package stricken.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

public class AbstractEventContext implements IEventContext {

	private class EventTracker {

		private final IEvent event;
		private List<IEventHandler> handlers = new ArrayList<IEventHandler>();

		private Logger log = Logger.getLogger(EventTracker.class);

		public EventTracker(IEvent event) {
			this.event = event;
		}

		public void fire(Object arg) {
			log.debug("Firing Event " + this
					+ ((arg != null) ? "(" + arg + ")" : "")); // show any
																// non-null
																// arguments in
																// parentheses
			for (IEventHandler handler : handlers) {
				handler.handleEvent(event, arg);
			}
		}

		public void subscribe(IEventHandler handler) {
			if (!handlers.contains(handler)) {
				handlers.add(handler);
			}
		}

		@Override
		public String toString() {
			return event.toString();
		}

	}

	private Logger log = Logger.getLogger(getClass());

	private Map<IEvent, EventTracker> events = new HashMap<IEvent, EventTracker>();
	
	private static Random random = new Random(System.currentTimeMillis());

	protected void createEvent(IEvent event) {
		events.put(event, new EventTracker(event));
	}

	public void fire(IEvent event) {
		fire(event, null);
	}

	public void fire(IEvent event, Object arg) {
		if (events.containsKey(event)) {
			events.get(event).fire(arg);
		} else {
			log.warn("Attempt to fire event " + event
					+ " that has not been created");
		}
	}

	public Random getRandom() {
		return random;
	}
	
	public void subscribe(IEvent event, IEventHandler handler) {
		if (events.containsKey(event)) {
			events.get(event).subscribe(handler);
		} else {
			log.warn("Attempt to subscribe event " + event
					+ " that has not been created");
		}
	}

}
