
public class Refresh extends Thread {
	public void run() {
		try {
			while (true) {
				SimpleDungeonCrawler.frame.getContentPane().validate();
				SimpleDungeonCrawler.frame.getContentPane().repaint();
				Thread.sleep(20);
			}
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
