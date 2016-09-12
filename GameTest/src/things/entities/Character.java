package things.entities;

import javax.swing.ImageIcon;

import misc.Images;
import misc.SDC;
import things.items.Stick;
import things.items.weapons.GodWeapon;

public class Character extends Entity {
	
	public Character() {
		super(5, 10, 10, 10, 1000);
		setType("Character");
		addItem(new Stick());
		setSize((int) (90 * SDC.SCALE_FACTOR), (int) (90 * SDC.SCALE_FACTOR));
		setRoom(SDC.roomArray[SDC.loc.x][SDC.loc.y]);
		setImage(Images.array[Images.battleCharIndex]);
		deadImage = Images.loadImage("Enemies\\Skull.png", 100.0, 100.0);
		equipped.weapon = new GodWeapon();
	}
}
