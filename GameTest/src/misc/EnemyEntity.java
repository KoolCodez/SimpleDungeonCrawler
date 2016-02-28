package misc;

import java.awt.Point;
import java.util.Random;

public class EnemyEntity extends Entity {
	public int enemyHealth;
	Random rand = new Random();
	public EnemyEntity() {
		super();
		super.entityType = "Enemy";
		enemyHealth = 5;
	}
}
