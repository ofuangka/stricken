package stricken.event;

import java.util.Random;

public interface IEventContext {

	public Random getRandom();

	public void fire(IEvent event);

	public void fire(IEvent event, Object arg);

	public void subscribe(IEvent event, IEventHandler handler);
}
