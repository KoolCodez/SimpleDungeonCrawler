
public class MoveUp extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingUp) {
				SimpleDungeonCrawler.movePlayer("up");
				SimpleDungeonCrawler.frame.getContentPane().validate();
				SimpleDungeonCrawler.frame.getContentPane().repaint();
				Thread.sleep(10);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
