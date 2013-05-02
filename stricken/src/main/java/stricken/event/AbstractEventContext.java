package stricken.event;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class AbstractEventContext implements IEventContext {

	private Logger log = Logger.getLogger(getClass());

	private Map<String, Event> events = new HashMap<String, Event>();

	protected void createEvent(String type) {
		events.put(type, new Event(type));
	}

	public void fire(String type) {
		fire(type, null);
	}

	public void fire(String type, Object arg) {
		if (events.containsKey(type)) {
			events.get(type).fire(arg);
		} else {
			log.warn("Attempt to fire event " + type
					+ " that has not been created");
		}
	}

	public void subscribe(String type, IEventHandler handler) {
		if (events.containsKey(type)) {
			events.get(type).subscribe(handler);
		} else {
			log.warn("Attempt to subscribe event " + type
					+ " that has not been created");
		}
	}

}
