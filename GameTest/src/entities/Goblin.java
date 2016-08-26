package entities;

import items.Club;
import misc.Images;
import misc.SDC;

public class Goblin extends Entity {
	public BattleAI battleAI;
	public Goblin() {
		super(5, 10, 10, 10, 10, 10, 10, 1, 3);
		battleAI = new BattleAI(this);
		setType("Enemy");
		setWeapon(new Club());
		//setSelectedEntity(SDC.character);
		setSize((int) (100 * SDC.SCALE_FACTOR), (int) (100 * SDC.SCALE_FACTOR));
		setImage(Images.loadImage("\\Enemies\\Goblin.png", outline.width, outline.height));
	}
}
