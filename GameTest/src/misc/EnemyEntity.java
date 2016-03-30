package misc;

import java.awt.Point;
import java.util.Random;

import items.GenericWeapon;

public class EnemyEntity extends Entity {
	Random rand = new Random();
	public EnemyEntity(double health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma, int AC) {
		super();
		super.setType("Enemy");
		super.setStats(health, strength, dexterity, constitution, intelligence, wisdom, charisma, AC);
	}
}
