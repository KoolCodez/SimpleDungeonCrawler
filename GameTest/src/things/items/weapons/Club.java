package things.items.weapons;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;
import misc.SDC;
import things.items.Weapon;

public class Club extends Weapon{
	static Image i = Images.loadImage("\\Items\\Club.png", (int) (100 * SDC.SCALE_FACTOR), (int) (100 * SDC.SCALE_FACTOR));
	public Club() {
		super(new ImageIcon(i), i, "Club", 40);
		damage = 1.0;
		ranged = false;
		speed = 3;
		reach = 1;
	}
}