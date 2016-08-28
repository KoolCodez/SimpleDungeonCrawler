package effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import misc.SDC;

public class FallingDamageNumber extends Effect {
	
	private double damage;
	private double velocityX;
	private double velocityY;
	private final double acceleration = 1;
	
	public FallingDamageNumber(double damage, Point startLoc) {
		this.damage = damage;
		location = startLoc;
		velocityX = (Math.random() - .5) * 12;
		velocityY = -8;
	}
	
	public void draw(Graphics g) {
		g.setFont(SDC.font);
		g.setColor(Color.red);
		g.drawString(damage + "", location.x, location.y);
		g.setColor(Color.black);
	}
	
	public void run() {
		try {
			for (int i = 0; i < 50; i++) {
				location.x += velocityX;
				location.y += velocityY;
				velocityY += acceleration;
				Thread.sleep(SDC.refreshRate * 2);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}