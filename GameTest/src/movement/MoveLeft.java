package movement;
import misc.Images;
import misc.SDC;

public class MoveLeft extends Thread{
	public void run() {
		int count = 0;
		try {
			while (MovementController.movingLeft) {
				if (!MovementController.movingDown && !MovementController.movingUp) {
					double speed = SDC.playerSpeed;
					SDC.character.move(-speed, 0);
					count++;
					if (count % 10 < 5) {
						SDC.character.setImage(Images.charLeftOpArmIndex);
					} else {
						SDC.character.setImage(Images.charLeftIndex);
					}
				}
				Thread.sleep(SDC.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
