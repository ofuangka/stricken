package stricken.ui.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import stricken.action.CritterAction;
import stricken.action.CritterActionFactory;
import stricken.board.Critter;
import stricken.event.IEventContext;

public class CritterMenuFactory {

	private IEventContext eventContext;
	private CritterActionFactory critterActionFactory;

	public CritterMenuFactory(IEventContext eventContext) {
		this.eventContext = eventContext;
	}

	public Menu getCombatActionMenu(final Critter critter) {

		Menu ret = new Menu(eventContext);
		List<AbstractMenuItem> items = new ArrayList<AbstractMenuItem>();
		CritterAction attack = critterActionFactory.get(critter.getWeapon(),
				critter);
		AbstractMenuItem attackMenuItem = new CritterActionMenuItem(ret,
				"Attack", attack);

		AbstractSubMenuItem talentSubMenuItem = new AbstractSubMenuItem(ret,
				"Talents") {

			private static final long serialVersionUID = 815195033986998056L;

			@Override
			public Menu getSubMenu() {
				return getTalentMenu(critter);
			}

		};
		AbstractSubMenuItem itemSubMenuItem = new AbstractSubMenuItem(ret,
				"Items") {

			private static final long serialVersionUID = 6369030028324018292L;

			@Override
			public Menu getSubMenu() {
				return getItemMenu(critter);
			}

		};
		items.add(attackMenuItem);
		items.add(talentSubMenuItem);
		items.add(itemSubMenuItem);
		items.add(new WaitMenuItem(ret));
		ret.setItems(items);
		return ret;
	}

	public Menu getTalentMenu(Critter critter) {
		Menu ret = new Menu(eventContext);
		List<AbstractMenuItem> items = new ArrayList<AbstractMenuItem>();
		items.add(new CritterActionMenuItem(ret, "Summon fire",
				critterActionFactory.get("smnf", critter)));
		items.add(new NoopMenuItem(ret, "Curse"));
		items.add(new NoopMenuItem(ret, "Hypnotize"));
		ret.setItems(items);
		return ret;
	}

	public Menu getItemMenu(Critter critter) {
		Menu ret = new Menu(eventContext);
		List<AbstractMenuItem> items = new ArrayList<AbstractMenuItem>();
		items.add(new NoopMenuItem(ret, "Health vial"));
		ret.setItems(items);
		return ret;
	}

	@Required
	public void setCritterActionFactory(
			CritterActionFactory critterActionFactory) {
		this.critterActionFactory = critterActionFactory;
	}
}
