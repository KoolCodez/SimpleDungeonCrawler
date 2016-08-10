package movement;
import misc.SDC;

public class MoveDownLeft extends Thread{
	public void run() {
		try {
			while (MovementController.movingLeft && MovementController.movingDown) {
				double speed = SDC.diagSpeed;
				SDC.character.move(-speed, 0);
				SDC.character.move(0, speed);
				//SDC.character.setAngle(-135);
				Thread.sleep(SDC.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
