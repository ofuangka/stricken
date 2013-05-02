package stricken.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class DialogLayer extends JPanel implements ILayer {

	private static final long serialVersionUID = 4610667649870282626L;

	public DialogLayer() {
		super(new BorderLayout());
		setOpaque(false);
	}

	@Override
	public void backspace() {

	}

	@Override
	public void down() {

	}

	@Override
	public void enter() {

	}

	@Override
	public void esc() {

	}

	@Override
	public boolean isEmpty() {

		return true;
	}

	@Override
	public void left() {

	}

	@Override
	public void right() {

	}

	@Override
	public void space() {

	}

	@Override
	public void up() {

	}

	@Override
	public void x() {

	}

}
