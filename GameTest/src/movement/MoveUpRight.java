package movement;
import misc.SDC;

public class MoveUpRight extends Thread{
	public void run() {
		try {
			while (MovementController.movingRight && MovementController.movingUp) {
				double speed = SDC.diagSpeed;
				SDC.character.move(speed, 0);
				SDC.character.move(0, -speed);
				//SDC.character.setAngle(45);
				Thread.sleep(SDC.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}