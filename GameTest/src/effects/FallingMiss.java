package effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import misc.SDC;

public class FallingMiss extends Effect {
	private double velocityX;
	private double velocityY;
	private final double acceleration = 1;
	
	public FallingMiss(Point startLoc) {
		location = startLoc;
		velocityX = (Math.random() - .5) * 12;
		velocityY = -8;
	}
	
	@Override
	protected void drawEffect(Graphics2D g) {
		g.setFont(SDC.font);
		g.setColor(Color.orange);
		g.drawString("Miss!", location.x, location.y);
		g.setColor(Color.black);
	}
	
	@Override
	public void run() {
		super.run();
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
