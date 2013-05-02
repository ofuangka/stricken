package stricken.event;

public interface IEventContext {
	public void fire(String type);

	public void fire(String type, Object arg);

	public void subscribe(String type, IEventHandler handler);
}
