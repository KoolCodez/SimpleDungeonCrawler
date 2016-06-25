package combatSystem;

import java.awt.Point;

import misc.SimpleDungeonCrawler;

public class FallingDamageNumber extends Thread {
	
	private double damage;
	private Point startingLocation;
	private double velocityX;
	private double velocityY;
	private final double acceleration = 1;
	
	public FallingDamageNumber(double damage, Point startLoc) {
		this.damage = damage;
		startingLocation = startLoc;
		velocityX = (Math.random() - .5) * 12;
		velocityY = -8;
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
			for (int i = 0; i < 50; i++) {
				startingLocation.x += velocityX;
				startingLocation.y += velocityY;
				velocityY += acceleration;
				Thread.sleep(SimpleDungeonCrawler.refreshRate * 2);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
