package items;

import javax.swing.ImageIcon;

import misc.Images;

public class Club extends GenericWeapon{

	public Club() {
		super(new ImageIcon(Images.array[Images.stickItemIndex]), "Club");
		damage = 1.0;
		ranged = false;
		speed = 1.0;
		reach = 100;
	}
	
}
