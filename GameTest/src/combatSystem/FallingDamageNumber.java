package combatSystem;

import java.awt.Point;

import misc.SimpleDungeonCrawler;

public class FallingDamageNumber extends Thread {
	
	private double damage;
	private Point startingLocation;
	private Point velocity;
	private final double acceleration = -3;
	
	public FallingDamageNumber(double damage, Point startLoc) {
		this.damage = damage;
		startingLocation = startLoc;
		velocity = new Point(12, (int) (Math.random() * 2 - 1));
	}
	
	public Point getPoint() {
		return startingLocation;
	}
	
	public double getDamage() {
		int multipliedDamage = (int) (damage * 10);
		double roundedDamage = multipliedDamage / 10;
		return roundedDamage;
	}
	
	public void run() {
		try {
			for (int i = 0; i < 100; i++) {
				startingLocation.x += velocity.x;
				startingLocation.y += velocity.y;
				velocity.y += acceleration;
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
