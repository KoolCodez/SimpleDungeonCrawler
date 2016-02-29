package misc;

import java.awt.Point;
import java.util.Random;

public class Entity { // extend this class with specific entity-classes.
	public String entityType;
	public Point entityLocation;
	public String[] entityInventory = new String[5];
	public static double str;
	public static double dex;
	public static double con;
	public static double intl;
	public static double wis;
	public static double chr;

	Random rand = new Random();

	public Entity() {
		entityType = "Generic Entity";
		entityLocation = new Point(250 - rand.nextInt(), 250 + rand.nextInt());
	}

	public static void setStats(double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma) {
		str = strength;
		dex = dexterity;
		con = constitution;
		intl = intelligence;
		wis = wisdom;
		chr = charisma;
	}
}
