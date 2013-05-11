package stricken.event;

import java.util.Random;

public interface IEventContext {

	public void fire(IEvent event);

	public void fire(IEvent event, Object arg);

	public Random getRandom();

	public void subscribe(IEvent event, IEventHandler handler);
}
