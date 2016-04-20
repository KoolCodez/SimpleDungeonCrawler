package misc;

import panels.Panels;

public class Refresh extends Thread {
	public void run() {
		try {
			while (true) {
				Panels.frame.getContentPane().validate();
				Panels.frame.getContentPane().repaint(); 
				Thread.sleep(SimpleDungeonCrawler.fps);
			}
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
