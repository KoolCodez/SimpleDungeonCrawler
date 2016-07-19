package combatSystem;

import misc.Entity;
import misc.SDC;

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
		for (int i = 0; i < 30; i++) {
			try {
				ent.move(xSpeed, ySpeed);
				sleep(SDC.refreshRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
