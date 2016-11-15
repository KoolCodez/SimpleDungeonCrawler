package things.entities;

import javax.swing.ImageIcon;

import misc.Images;
import misc.SDC;
import things.items.Stick;
import things.items.weapons.Fists;
import things.items.weapons.GodWeapon;

public class Character extends Entity {
	
	public Character() {
		super(5, 10, 10, 10, 1000);
		setType("Character");
		addItem(new Stick());
		setSize((int) (100 * SDC.SCALE_FACTOR), (int) (100 * SDC.SCALE_FACTOR));
		setRoom(SDC.roomArray[SDC.loc.x][SDC.loc.y]);
		setImage("Characters/MainChar.png");
		deadImage = "Enemies/Skull.png";
		//equipped.weapon = new GodWeapon();
		equipped.weapon = new Fists();
	}
}
