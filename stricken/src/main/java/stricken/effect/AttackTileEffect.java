package stricken.effect;

import java.util.Random;

import stricken.board.AbstractPositionedSprite;
import stricken.board.Critter;
import stricken.board.Tile;

public class AttackTileEffect implements ITileEffect {

	private Random random;

	@Override
	public void execute(Critter source, Tile targetTile) {
		if (targetTile.isOccupied()) {
			AbstractPositionedSprite targetPiece = targetTile.getOccupant();
			if (Critter.class.isAssignableFrom(targetPiece.getClass())) {
				Critter target = (Critter) targetPiece;

				// determine if the attempt is a hit
				if (isAttackAHit(target)) {

					// calculate the damage performed
					int damage = getDamage(source, target);

					// decrement the target
					target.setStat(Critter.HP, target.getStat(Critter.HP)
							- damage);
				}

			}
		}

	}

	private int getDamage(Critter source, Critter target) {
		int ret = source.getStat(Critter.STRENGTH);
		return ret;
	}

	private boolean isAttackAHit(Critter target) {
		// TODO: implement
		return true;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

}
