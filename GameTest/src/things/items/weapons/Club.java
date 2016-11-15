package things.items.weapons;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;
import misc.SDC;

public class Club extends Weapon{
	public Club() {
		super("Items/Club.png", "Items/Club.png", "Club", 40);
		damage = 1.0;
		ranged = false;
		speed = 3;
		reach = 1;
	}
}