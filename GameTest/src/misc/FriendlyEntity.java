package misc;

import java.util.Random;

import items.GenericWeapon;

public class FriendlyEntity extends Entity {
	public int health;
	public int maxHealth;
	public GenericWeapon selectedWeapon;
	Random rand = new Random();
	public FriendlyEntity(int health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma) {
		super();
		super.entityType = "Enemy";
		this.health = health;
		maxHealth = health;
		super.setStats(strength, dexterity, constitution, intelligence, wisdom, charisma);
	}
}
