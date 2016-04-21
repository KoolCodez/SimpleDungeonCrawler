package misc;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import items.GenericItem;
import items.GenericWeapon;

public class Entity implements Comparable<Entity>{ // extend this class with specific entity-classes.
	private String entityType;
	private Point2D location;
	private List<GenericItem> inventory;
	public EntityStats stats = new EntityStats();
	private int initiative;
	private GenericWeapon selectedWeapon;
	private int selectedEntity;
	private Point2D battleLoc;
	private String name = "Entity";
	private Utilities utilities = new Utilities();
	
	public Entity() {
		inventory = new ArrayList<GenericItem>();
		entityType = "Generic Entity";
		location = new Point2D.Double(250, 250);
	}

	public Entity(double health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma, int AC) {
		inventory = new ArrayList<GenericItem>();
		entityType = "Generic Entity";
		location = new Point2D.Double(250, 250);
		stats.setStats(health, strength, dexterity, constitution, intelligence, wisdom, charisma, AC);
	}
	
	public void attack(Entity enemy) {
		System.out.println(name + " Attack!");
		// does it hit
		if (enemy.stats.getDex() - stats.getDex() + 10 < utilities.r20()) {
			// how much damage does it do
			double damage = 0.0;
			damage = (stats.getStr() / enemy.stats.getStr() * selectedWeapon.damage) / enemy.stats.getAC();
			// subtract damage
			enemy.stats.setHealth(-damage);
			System.out.println("He Hit For " + damage + "Damage!");
		} else {
			System.out.println("He Missed!");
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Point2D getBattleLoc() {
		double battleRatioX = 696*SimpleDungeonCrawler.SCALE_FACTOR / (1000*SimpleDungeonCrawler.SCALE_FACTOR);
		double battleRatioY = 703*SimpleDungeonCrawler.SCALE_FACTOR / (1000*SimpleDungeonCrawler.SCALE_FACTOR);
		battleLoc = new Point2D.Double(this.location.getX() * (battleRatioX), this.location.getY() * (battleRatioY) + 149); 
		//(0,149)(696, 149)(0,852)(696,852) 696, 703
		return battleLoc;
	}
	
	public int getSelectedEntity() {
		return selectedEntity;
	}

	public void setSelectedEntity(int selectedEntity) {
		this.selectedEntity = selectedEntity;
	}
	
	public void setType(String newType) {
		entityType = newType;
	}
	
	public String getType() {
		return entityType;
	}
	
	public void setLocation(double deltaX, double deltaY) {
		location.setLocation(location.getX() + deltaX, location.getY() + deltaY);
	}
	
	public Point2D getLocation() {
		return location;
	}
	
	public List<GenericItem> getInventory() {
		return inventory;
	}
	
	public void addItem(GenericItem item) {
		inventory.add(item);
	}
	
	public void setInitiative(int init) {
		initiative = init;
	}
	
	public int getInitiative() {
		return initiative;
	}
	
	public void setWeapon(GenericWeapon weapon) {
		selectedWeapon = weapon;
	}
	
	public GenericWeapon getWeapon() {
		return selectedWeapon;
	}
	
	public int compareTo(Entity otherInit) {
		return this.initiative - otherInit.initiative;
	}
}
