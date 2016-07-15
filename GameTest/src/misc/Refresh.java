package misc;

public class Refresh extends Thread {
	public void run() {
		try {
			while (true) {
				SDC.frame.getContentPane().validate();
				SDC.frame.getContentPane().repaint(); 
				Thread.sleep(SDC.fps);
			}
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
