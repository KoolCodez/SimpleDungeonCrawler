package things.entities;

import java.io.Serializable;

import misc.SDC;

public class EntityStats implements Serializable {
	private static final double defaultSpeed = 8*SDC.SCALE_FACTOR;
	private double str;
	private double dex;
	private double wlp;
	private double health;
	private double maxHealth;
	private double speed;
	
	public EntityStats() {
		maxHealth = health;
		this.health = maxHealth;
		str = 10;
		dex = 10;
		speed = 3;
	}
	
	public void setStats(double health, double strength, double dexterity, double willPower) {
		maxHealth = health;
		this.health = maxHealth;
		str = strength;
		dex = dexterity;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setStr(double strength) {
		this.str += strength;
	}
	
	public double getStr() {
		return str;
	}
	
	public void setDex(double dexterity) {
		this.dex += dexterity;
	}
	
	public double getDex() {
		return dex;
	}
	
	public double getWlp() {
		return wlp;
	}

	public void setWlp(double wlp) {
		this.wlp = wlp;
	}

	public void setHealth(double deltaHealth) {
		health += deltaHealth;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setMaxHealth(double deltaMaxHealth) {
		maxHealth += deltaMaxHealth;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
}
