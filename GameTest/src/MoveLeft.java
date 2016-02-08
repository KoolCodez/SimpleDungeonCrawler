

public class MoveLeft extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingLeft) {
				SimpleDungeonCrawler.movePlayer("left");
				SimpleDungeonCrawler.frame.getContentPane().validate();
				SimpleDungeonCrawler.frame.getContentPane().repaint();
				Thread.sleep(50);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
