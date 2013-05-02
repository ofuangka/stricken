package stricken.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public abstract class AbstractMenuItem extends JLabel {

	private static final long serialVersionUID = -7617853828536450022L;

	public static final Color DESELECTED = Color.black;
	public static final Color SELECTED = Color.red;
	public static final Color FONT_COLOR = Color.white;
	public static final int PADDING_VERTICAL = 5;
	public static final int PADDING_HORIZONTAL = 10;

	protected final Menu parent;

	public AbstractMenuItem(Menu parent, String label) {
		super(label);
		this.parent = parent;
		setOpaque(true);
		setForeground(FONT_COLOR);
		setBackground(DESELECTED);
		setBorder(BorderFactory.createEmptyBorder(PADDING_VERTICAL,
				PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL));
	}

	public abstract void execute();

	public void uiDeselect() {
		setBackground(DESELECTED);
	}

	public void uiSelect() {
		setBackground(SELECTED);
	}

}
