package stricken.event;

public interface IEventContext {
	public void fire(IEvent event);

	public void fire(IEvent event, Object arg);

	public void subscribe(IEvent event, IEventHandler handler);
}
