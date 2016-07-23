package entities;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import items.GenericItem;
import items.GenericWeapon;
import misc.Utilities;
import rooms.StandardRoom;

public class Entity extends Thing implements Comparable<Entity>, Serializable { // extend this class with specific entity-classes.
	private String entityType;
	public Point battleLoc;
	private List<GenericItem> inventory = new ArrayList<GenericItem>();
	public EntityStats stats = new EntityStats();
	private int initiative;
	protected GenericWeapon weapon;
	private Entity selectedEntity;
	private String name = "Entity";
	private Utilities utilities = new Utilities();
	private int imageIndex;
	private int deadImageIndex;
	
	public int getImage() {
		if (stats.getHealth() > 0) {
			return imageIndex;
		}
		return deadImageIndex;
	}

	public void setImage(int index) {
		this.imageIndex = index;
	}
	
	public void setDeadImage(int index) {
		deadImageIndex = index;
	}

	public Entity() {
		entityType = "Generic Entity";
		location = new Point2D.Double(250, 250);
		battleLoc = new Point(0,0);
	}

	public Entity(double health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma, int AC) {
		super();
		entityType = "Generic Entity";
		stats.setStats(health, strength, dexterity, constitution, intelligence, wisdom, charisma, AC);
		battleLoc = new Point(0,0);
	}
	
	public double meleeAttack(Entity enemy) {
		if (withinRange(enemy.location)) {
		System.out.println(name + entityType + " Attack!");
			if (enemy.stats.getDex() - stats.getDex() + 10 < utilities.r20()) {
				double damage = 0.0;
				damage = (stats.getStr() / enemy.stats.getStr() * weapon.damage) / enemy.stats.getAC();
				enemy.stats.setHealth(-damage);
				System.out.println("He Hit For " + damage + "Damage!");
				return damage;
			} else {
				System.out.println("He Missed!");
				return 0;
			}
		} else {
			System.out.println("Out of Reach!");
			return 0;
		}
	}
	
	private boolean withinRange(Point2D enemyLoc) {
		double dist = location.distance(enemyLoc) - 50;
		double reach = weapon.reach;
		return dist < reach;
	}
	
	public void setRoom(StandardRoom r) {
		super.setRoom(r);
		r.entities.add(this);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Entity getSelectedEntity() {
		return selectedEntity;
	}

	public void setSelectedEntity(Entity selectedEntity) {
		this.selectedEntity = selectedEntity;
	}
	
	public void setType(String newType) {
		entityType = newType;
	}
	
	public String getType() {
		return entityType;
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
		this.weapon = weapon;
	}
	
	public GenericWeapon getWeapon() {
		return weapon;
	}
	
	public int compareTo(Entity otherInit) {
		return this.initiative - otherInit.initiative;
	}
}
