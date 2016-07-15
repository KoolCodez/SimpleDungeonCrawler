package movement;
import misc.SDC;

public class MoveUp extends Thread {
	public void run() {
		try {
			while (MovementController.movingUp) {
				if (!MovementController.movingRight && !MovementController.movingLeft) {
					double speed = SDC.playerSpeed;
					SDC.character.move(0, -speed);
				}					
				Thread.sleep(SDC.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
