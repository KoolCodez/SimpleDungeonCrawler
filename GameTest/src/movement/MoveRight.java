package movement;
import misc.Images;
import misc.SDC;

public class MoveRight extends Thread {
	public void run() {
		int count = 0;
		try {
			while (MovementController.movingRight) {
				if (!MovementController.movingDown && !MovementController.movingUp) {
					double speed = SDC.playerSpeed;
					SDC.character.move(speed, 0);
					SDC.character.setAngle(90);
					//count++;
					//if (count % 10 < 5) {
					//	SDC.character.setImage(Images.array[Images.charRightOpArmIndex]);
					//} else {
					//	SDC.character.setImage(Images.array[Images.charRightIndex]);
					//}
				}
				Thread.sleep(SDC.refreshRate);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
