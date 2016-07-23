package entities;

import java.io.Serializable;

public class EntityStats implements Serializable {
	private double str;
	private double dex;
	private double con;
	private double intl;
	private double wis;
	private double chr;
	private double AC;
	private double health;
	private double maxHealth;
	
	public EntityStats() {
		maxHealth = health;
		this.health = maxHealth;
		str = 10;
		dex = 10;
		con = 10;
		intl = 10;
		wis = 10;
		chr = 10;
		AC = 10;
	}
	
	public void setStats(double health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma, int AC) {
		maxHealth = health;
		this.health = maxHealth;
		str = strength;
		dex = dexterity;
		con = constitution;
		intl = intelligence;
		wis = wisdom;
		chr = charisma;
		this.AC = AC;
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
	
	public void setCon(double constitution) {
		this.con += constitution;
	}
	
	public double getCon() {
		return con;
	}
	
	public void setIntl(double intelligence) {
		this.intl += intelligence;
	}
	
	public double getIntl() {
		return intl;
	}
	
	public void setWis(double wisdom) {
		this.wis += wisdom;
	}
	
	public double getWis() {
		return wis;
	}
	
	public void setChr(double charisma) {
		chr += charisma;
	}
	
	public double getChr() {
		return chr;
	}
	
	public void setAC(double deltaAC) {
		AC += deltaAC;
	}
	
	public double getAC() {
		return AC;
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
