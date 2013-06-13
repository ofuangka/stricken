package stricken.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import stricken.board.piece.Critter;

public class CritterListItem extends JLabel {

	private static final long serialVersionUID = -8559163820702667647L;

	private Critter critter;

	public CritterListItem(Critter critter) {
		this.critter = critter;
		setIcon(new ImageIcon(critter.getImage()));
		setPreferredSize(critter.getSpriteSize());
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);

		Dimension spriteSize = critter.getSpriteSize();

		int currHp = critter.getStat(Critter.Stat.HP);
		int maxHp = critter.getStat(Critter.Stat.MAXHP);
		int hpBarSize = ((spriteSize.height - 2) * currHp) / maxHp;
		int currMp = critter.getStat(Critter.Stat.MP);
		int maxMp = critter.getStat(Critter.Stat.MAXMP);
		int mpBarSize = ((spriteSize.height - 2) * currMp) / maxMp;

		g2d.setColor(Color.red);
		g2d.fillRect(spriteSize.width - 6, spriteSize.height - 1 - hpBarSize, 2, hpBarSize);
		
		g2d.setColor(Color.blue);
		g2d.fillRect(spriteSize.width - 3, spriteSize.height - 1 - mpBarSize, 2, mpBarSize);

	}
}
