package stricken.event;

import java.util.Random;

public interface IEventContext {

	void fire(IEvent event);

	void fire(IEvent event, Object arg);

	Random getRandom();

	void subscribe(IEvent event, IEventHandler handler);
}
