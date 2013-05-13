package stricken.ui.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import stricken.Stricken;
import stricken.board.critter.Critter;
import stricken.board.critter.CritterAction;
import stricken.board.critter.CritterActionFactory;
import stricken.event.IEventContext;

public class CritterMenuFactory {

	public class NoopMenuItem extends AbstractMenuItem {
		private static final long serialVersionUID = -8766319162177106177L;

		public NoopMenuItem(IEventContext eventContext, String label) {
			super(eventContext, label);
		}

		@Override
		public void execute() {
			eventContext.fire(Stricken.Event.POP_IN_GAME_MENU);
		}
	}

	public static final String ATTACK_LABEL = "Attack";
	public static final String TALENT_LABEL = "Talents";
	public static final String ITEM_LABEL = "Items";
	public static final String WAIT_LABEL = "Wait";
	public static final String NO_ITEM_LABEL = "No items";
	public static final String NO_TALENT_LABEL = "No talents";

	private IEventContext eventContext;
	private CritterActionFactory critterActionFactory;

	public CritterMenuFactory(IEventContext eventContext) {
		this.eventContext = eventContext;
	}

	public Menu getCombatActionMenu(final Critter critter) {

		Menu ret = new Menu(eventContext);
		List<AbstractMenuItem> menuItems = new ArrayList<AbstractMenuItem>();
		CritterAction attack = critterActionFactory.get(critter.getAttack(),
				critter);
		AbstractMenuItem attackMenuItem = new EventMenuItem(eventContext,
				ATTACK_LABEL, Stricken.Event.CRITTER_ACTION, attack);

		AbstractSubMenuItem talentSubMenuItem = new AbstractSubMenuItem(
				eventContext, TALENT_LABEL) {

			private static final long serialVersionUID = 815195033986998056L;

			@Override
			public Menu getSubMenu() {
				return getTalentMenu(critter);
			}

		};
		AbstractSubMenuItem itemSubMenuItem = new AbstractSubMenuItem(
				eventContext, ITEM_LABEL) {

			private static final long serialVersionUID = 6369030028324018292L;

			@Override
			public Menu getSubMenu() {
				return getItemMenu(critter);
			}

		};
		menuItems.add(attackMenuItem);
		menuItems.add(talentSubMenuItem);
		menuItems.add(itemSubMenuItem);
		menuItems.add(new AbstractMenuItem(eventContext, WAIT_LABEL) {
			private static final long serialVersionUID = -4070052990440134098L;

			@Override
			public void execute() {
				eventContext.fire(Stricken.Event.END_OF_TURN);
			}

		});
		ret.setItems(menuItems);
		return ret;
	}

	public Menu getItemMenu(Critter critter) {
		Menu ret = new Menu(eventContext);
		List<AbstractMenuItem> menuItems = new ArrayList<AbstractMenuItem>();

		List<String> items = critter.getItems();

		if (items == null || items.isEmpty()) {
			menuItems.add(new NoopMenuItem(eventContext, NO_ITEM_LABEL));
		} else {
			for (String item : items) {
				menuItems.add(new EventMenuItem(eventContext,
						critterActionFactory.getLabel(item),
						Stricken.Event.CRITTER_ACTION, critterActionFactory
								.get(item, critter)));
			}
		}

		ret.setItems(menuItems);
		return ret;
	}

	public Menu getTalentMenu(Critter critter) {
		Menu ret = new Menu(eventContext);
		List<AbstractMenuItem> menuItems = new ArrayList<AbstractMenuItem>();

		List<String> talents = critter.getTalents();

		if (talents == null || talents.isEmpty()) {
			menuItems.add(new NoopMenuItem(eventContext, NO_TALENT_LABEL));
		} else {
			for (String talent : talents) {
				menuItems.add(new EventMenuItem(eventContext,
						critterActionFactory.getLabel(talent),
						Stricken.Event.CRITTER_ACTION, critterActionFactory
								.get(talent, critter)));
			}
		}

		ret.setItems(menuItems);
		return ret;
	}

	@Required
	public void setCritterActionFactory(
			CritterActionFactory critterActionFactory) {
		this.critterActionFactory = critterActionFactory;
	}
}
