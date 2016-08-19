package entities;

import items.Club;
import misc.SDC;

public class Goblin extends Entity {
	public BattleAI battleAI;
	public Goblin() {
		super(5, 10, 10, 10, 10, 10, 10, 1, 3);
		battleAI = new BattleAI(this);
		setType("Enemy");
		setWeapon(new Club());
		//setSelectedEntity(SDC.character);
		setSize((int) (72 * SDC.SCALE_FACTOR), (int) (92 * SDC.SCALE_FACTOR));
	}
}
