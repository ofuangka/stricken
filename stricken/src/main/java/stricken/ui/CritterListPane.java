package stricken.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import stricken.board.piece.Critter;

public class CritterListPane extends JScrollPane {

	private static final long serialVersionUID = 4716145778679751281L;

	Map<Critter, CritterListItem> items = new HashMap<Critter, CritterListItem>();

	public CritterListPane() {
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setViewportView(new JPanel(new GridBagLayout()));
	}

	public void addCritter(Critter critter) {
		CritterListItem critterItem = new CritterListItem(critter);
		items.put(critter, critterItem);
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = items.size();
		((JPanel) getViewport().getView()).add(critterItem, gc);
	}

	public void removeCritter(Critter critter) {
		((JPanel) getViewport().getView()).remove(items.remove(critter));
	}

	public void clearCritters() {
		Set<Critter> critters = items.keySet();
		for (Critter critter : critters) {
			((JPanel) getViewport().getView()).remove(items.get(critter));
		}
		items.clear();
	}

}
