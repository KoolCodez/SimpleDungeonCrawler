
public class MoveDown extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingDown) {
				SimpleDungeonCrawler.movePlayer("down");
				SimpleDungeonCrawler.frame.getContentPane().validate();
				SimpleDungeonCrawler.frame.getContentPane().repaint();
				Thread.sleep(10);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
