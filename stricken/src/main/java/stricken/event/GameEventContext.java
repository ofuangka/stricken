package stricken.event;

public class GameEventContext extends AbstractEventContext {

	public static final String[] types = {};

	public GameEventContext() {

		for (String type : types) {
			createEvent(type);
		}
	}

}
