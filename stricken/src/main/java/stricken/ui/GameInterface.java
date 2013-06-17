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

public class GameInterface extends JScrollPane {

	private static final long serialVersionUID = 4716145778679751281L;

	private Map<Critter, CritterSticker> items = new HashMap<Critter, CritterSticker>();
	
	private JLabel spacer = new JLabel();

	public GameInterface() {
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setViewportView(new JPanel(new GridBagLayout()));
	}

	public void addCritter(Critter critter) {
		CritterSticker critterItem = new CritterSticker(critter);
		items.put(critter, critterItem);
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = items.size();
		getView().add(critterItem, gc);
		refreshSpacer();
	}

	public void removeCritter(Critter critter) {
		getView().remove(items.remove(critter));
	}

	public void clearCritters() {
		Set<Critter> critters = items.keySet();
		for (Critter critter : critters) {
			getView().remove(items.get(critter));
		}
		items.clear();
	}
	
	protected JPanel getView() {
		return ((JPanel) getViewport().getView());
	}
	
	protected void refreshSpacer() {
		getView().remove(spacer);
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = items.size() + 1;
		gc.weighty = 1;
		getView().add(spacer, gc);
	}

}
