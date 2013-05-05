package stricken.ui.menu;

public class NoopMenuItem extends AbstractMenuItem {

	private static final long serialVersionUID = 1153578584818266547L;

	public NoopMenuItem(Menu parent, String label) {
		super(parent, label);
	}

	@Override
	public void execute() {
		// do nothing
	}

}
