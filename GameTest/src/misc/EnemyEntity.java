package misc;

import java.awt.Point;
import java.util.Random;

public class EnemyEntity extends Entity {
	public int enemyHealth;
	Random rand = new Random();
	public EnemyEntity(int health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma) {
		super();
		super.entityType = "Enemy";
		enemyHealth = health;
		super.setStats(strength, dexterity, constitution, intelligence, wisdom, charisma);
	}
}
