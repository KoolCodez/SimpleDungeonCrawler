package rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import items.GenericWeapon;
import misc.Entity;
import misc.Images;
import misc.SDC;

public class BattleRoom extends StandardRoom {
	private final double SCALE_FACTOR = SDC.SCALE_FACTOR;
	public BattleRoom(int enemies) {
		super();
		typeOfRoom = BATTLE_TAG;
		for (int i = 0; i <= enemies; i++) {
			Entity temp = new Entity(5, 10, 10, 10, 10, 10, 10, 1);
			GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.array[Images.stickItemIndex]), "weapon");
			weapon.damage = 1.0;
			weapon.ranged = false;
			weapon.speed = 1.0;
			weapon.reach = 90;
			temp.setWeapon(weapon);
			temp.setLocation(700*SCALE_FACTOR*Math.random() + 50,  700*SCALE_FACTOR*Math.random() + 50);
			temp.setType("Enemy");
			temp.setImage(Images.battleGoblinIndex);
			entities.add(temp);
			
		}
	}

}
