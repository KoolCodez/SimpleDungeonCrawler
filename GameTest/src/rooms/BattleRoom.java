package rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import misc.Images;
import misc.SDC;
import things.entities.Entity;
import things.entities.Goblin;
import things.items.Item;
import things.items.Weapon;
import things.items.armors.Armor;
import things.items.armors.Linens;
import things.items.armors.NullArmor;
import things.items.weapons.Club;
import things.items.weapons.Fists;

public class BattleRoom extends StandardRoom {
	private final double SCALE_FACTOR = SDC.SCALE_FACTOR;
	public BattleRoom(int enemies) {
		super();
		typeOfRoom = BATTLE_TAG;
		for (int i = 0; i <= enemies; i++) {
			Entity temp = new Goblin();
			temp.setLocation(700*SCALE_FACTOR*Math.random() + 50,  700*SCALE_FACTOR*Math.random() + 50);
			temp.setRoom(SDC.roomArray[SDC.loc.x][SDC.loc.y]);
			entities.add(temp);
			things.add(temp);
		}
	}
	
	private void generateRoom(int xDist, int yDist) {
		int rarityPoints = (int) Math.sqrt(xDist*xDist + yDist*yDist);
		int enemies = (int) (Math.random() * 3.999) + 1;
		
		int portions = enemies * 10;
		int[] enemyPortions = new int[enemies];
		for (int i = 0; i < portions; i++) {
			int random = (int) (Math.random() * enemies);
			enemyPortions[random]++;
		}
		
		for (int i = 0; i < enemies; i++) {
			Entity enemy = generateEnemy((rarityPoints * enemyPortions[i] / portions) / 2);
			generateEquipment((rarityPoints * enemyPortions[i] / portions) / 2, enemy);
			enemy.setLocation(700*SCALE_FACTOR*Math.random() + 50,  700*SCALE_FACTOR*Math.random() + 50);
			enemy.setRoom(SDC.roomArray[SDC.loc.x][SDC.loc.y]);
			entities.add(enemy);
			things.add(enemy);
		}
	}
	
	private Entity[] enemyList = {new Goblin()};
	private Entity generateEnemy(int rarity) {
		Entity enemy = null;
		while (enemy == null && rarity >= 60) {
			Entity temp = enemyList[(int) (Math.random() * enemyList.length)];
			if (temp.rarity < rarity) {
				enemy = temp;
			}
		}
		enemy.generateStats(rarity);
		return enemy;
	}
	
	private Weapon[] weaponList = {new Club(), new Fists()};
	private Armor[] armorList = {new Linens(), new NullArmor()};
	private void generateEquipment(int rarity, Entity enemy) {
		double ratio = Math.random();
		enemy.equipped.weapon = generateWeapon((int) (rarity * ratio));
		
	}
	
	private Weapon generateWeapon(int rarity) {
		Weapon w = null;
		while (w == null && rarity >= 0) {
			Weapon temp = weaponList[(int) (Math.random() * weaponList.length)];
			if (temp.rarity <= rarity) {
				w = temp;
			}
		}
		return w;
	}
}
