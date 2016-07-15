package movement;
import misc.SDC;

public class MoveUpLeft extends Thread{
	public void run() {
		try {
			while (MovementController.movingLeft && MovementController.movingUp) {
				double speed = SDC.diagSpeed;
				SDC.character.move(-speed, 0);
				SDC.character.move(0, -speed);
				Thread.sleep(SDC.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}