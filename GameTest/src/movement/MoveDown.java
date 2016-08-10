package movement;
import misc.Images;
import misc.SDC;

public class MoveDown extends Thread {
	public void run() {
		try {
			while (MovementController.movingDown) {
				if (!MovementController.movingLeft && ! MovementController.movingRight) {
					double speed = SDC.playerSpeed;
					SDC.character.move(0, speed);
					SDC.character.setImage(Images.array[Images.battleCharIndex]);
				}
				Thread.sleep(SDC.refreshRate);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
