package stricken.ui;

import org.apache.log4j.Logger;

public class LoggingKeySink implements IKeySink {

	private Logger log = Logger.getLogger(LoggingKeySink.class);

	@Override
	public void backspace() {
		log.info("Backspace");
	}

	@Override
	public void down() {
		log.info("Down");
	}

	@Override
	public void enter() {
		log.info("Enter");
	}

	@Override
	public void esc() {
		log.info("Escape");
	}

	@Override
	public void left() {
		log.info("Left");
	}

	@Override
	public void right() {
		log.info("Right");
	}

	@Override
	public void space() {
		log.info("Space");
	}

	@Override
	public void up() {
		log.info("Up");
	}

	@Override
	public void x() {
		log.info("X");
	}

}
