package combatSystem;

import misc.SDC;
import things.entities.Entity;

public class Move extends Thread {
	private Entity ent;
	private double deltaX;
	private double deltaY;

	public Move(Entity ent, double deltaX, double deltaY) {
		this.ent = ent;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public void run() {
		double theta = Math.atan(deltaY / deltaX);
		double totalDist = Math.sqrt((deltaY * deltaY) * (deltaX * deltaX));
		double xSpeed = deltaX / 30;
		double ySpeed = deltaY / 30;
		double prevX = ent.location.getX();
		double prevY = ent.location.getY();
		for (int i = 0; i < 30; i++) {
			try {
				//ent.battleMove(xSpeed, ySpeed);
				sleep(SDC.refreshRate);
				if (prevX == ent.location.getX() && prevY == ent.location.getY()) {
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
