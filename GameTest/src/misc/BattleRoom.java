package misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import items.GenericWeapon;

public class BattleRoom extends StandardRoom {
	
	public BattleRoom(int enemies) {
		for (int i = 0; i <= enemies; i++) {
			Entity temp = new Entity(5, 10, 10, 10, 10, 10, 10, 1);
			GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.stickItem), "weapon");
			weapon.damage = 1.0;
			weapon.ranged = false;
			weapon.speed = 1.0;
			temp.setWeapon(weapon);
			Random rand = new Random();
			temp.setLocation(rand.nextInt(500) - 250, rand.nextInt(500) - 250);
			entities.add(temp);
			
		}
	}

}
