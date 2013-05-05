package stricken.ui.menu;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import stricken.event.IEventContext;
import stricken.ui.IKeySink;

public class Menu extends JPanel implements IKeySink {

	private static final long serialVersionUID = 272208021126912914L;

	public static final Color BG_COLOR = Color.black;

	private List<AbstractMenuItem> items = new ArrayList<AbstractMenuItem>();
	private int currentIndex;
	private IEventContext eventContext;

	public Menu(IEventContext eventContext) {
		super(new GridBagLayout());
		this.eventContext = eventContext;
		setOpaque(true);
	}

	public Menu(IEventContext eventContext, List<AbstractMenuItem> items) {
		this(eventContext);
		setItems(items);
	}

	public void addItem(AbstractMenuItem item) {
		items.add(item);
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = items.size() - 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(item, gc);
	}

	@Override
	public void backspace() {

	}

	public void clearItems() {
		if (items != null) {
			while (!items.isEmpty()) {
				remove(items.remove(0));
			}
		}
	}

	public void down() {
		if (items != null && !items.isEmpty()) {
			items.get(currentIndex).uiDeselect();
			if (currentIndex == items.size() - 1) {
				currentIndex = 0;
			} else {
				currentIndex++;
			}
			items.get(currentIndex).uiSelect();
		}
	}

	@Override
	public void enter() {
		executeCurrentItem();
	}

	@Override
	public void esc() {

	}

	protected void executeCurrentItem() {
		if (items != null && !items.isEmpty()) {
			items.get(currentIndex).execute();
		} else {
			throw new IllegalStateException(
					"Calling execute with null items list");
		}
	}

	@Override
	public void left() {

	}

	public void reset() {
		if (items != null) {
			for (int i = 0; i < items.size(); i++) {
				items.get(i).uiDeselect();
			}
		}
		currentIndex = 0;
	}

	@Override
	public void right() {

	}

	public void setItems(List<AbstractMenuItem> items) {
		if (items != null && !items.isEmpty()) {
			clearItems();
			for (AbstractMenuItem item : items) {
				addItem(item);
			}
			reset();
			items.get(0).uiSelect();
		} else {
			throw new IllegalArgumentException(
					"Must have at least one MenuItem");
		}
	}

	@Override
	public void space() {

	}

	public void up() {
		if (items != null & !items.isEmpty()) {
			items.get(currentIndex).uiDeselect();
			if (currentIndex == 0) {
				currentIndex = items.size() - 1;
			} else {
				currentIndex--;
			}
			items.get(currentIndex).uiSelect();
		}
	}

	@Override
	public void x() {

	}

	public IEventContext getEventContext() {
		return eventContext;
	}

}
