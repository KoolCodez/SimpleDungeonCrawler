package items;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;

public class Club extends Weapon{
	static Image i = Images.array[Images.stickItemIndex];
	public Club() {
		super(new ImageIcon(i), i, "Club");
		damage = 1.0;
		ranged = false;
		speed = 3;
		reach = 1;
	}
	
}
