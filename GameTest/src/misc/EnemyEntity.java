package misc;

import java.awt.Point;
import java.util.Random;

import items.GenericWeapon;

public class EnemyEntity extends Entity {
	public int enemyHealth;
	public GenericWeapon selectedWeapon;
	Random rand = new Random();
	public EnemyEntity(int health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma) {
		super();
		super.entityType = "Enemy";
		enemyHealth = health;
		super.setStats(strength, dexterity, constitution, intelligence, wisdom, charisma);
	}
}
