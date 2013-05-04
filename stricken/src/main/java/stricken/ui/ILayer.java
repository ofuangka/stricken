package stricken.ui;

import java.awt.Dimension;

public interface ILayer extends IKeySink {
	public boolean isEmpty();

	public void setBounds(int x1, int y1, int x2, int y2);

	public void setPreferredSize(Dimension preferredSize);
	
	public boolean isVisible();
}
