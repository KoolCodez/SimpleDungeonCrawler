package misc;

import java.util.Random;

import items.GenericWeapon;

public class FriendlyEntity extends Entity {
	public GenericWeapon selectedWeapon;
	Random rand = new Random();
	public FriendlyEntity(double health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma, int AC) {
		super();
		super.entityType = "Enemy";
		super.setStats(health, strength, dexterity, constitution, intelligence, wisdom, charisma, AC);
	}
}
