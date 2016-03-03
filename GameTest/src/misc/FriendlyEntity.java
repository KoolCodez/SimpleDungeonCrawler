package misc;

import java.util.Random;

public class FriendlyEntity extends Entity {
	public int friendlyHealth;
	Random rand = new Random();
	public FriendlyEntity(int health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma) {
		super();
		super.entityType = "Enemy";
		friendlyHealth = health;
		super.setStats(strength, dexterity, constitution, intelligence, wisdom, charisma);
	}
}
