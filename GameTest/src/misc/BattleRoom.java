package misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import items.GenericWeapon;

public class BattleRoom extends StandardRoom {
	
	public BattleRoom(int enemies) {
		super();
		typeOfRoom = BATTLE_TAG;
		for (int i = 0; i <= enemies; i++) {
			Entity temp = new Entity(5, 10, 10, 10, 10, 10, 10, 1);
			GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.array[Images.stickItemIndex]), "weapon");
			weapon.damage = 1.0;
			weapon.ranged = false;
			weapon.speed = 1.0;
			temp.setWeapon(weapon);
			Random rand = new Random();
			temp.setLocation(250,  250);
			temp.setType("Enemy");
			temp.setImage(Images.battleGoblinIndex);
			entities.add(temp);
			
		}
	}

}
