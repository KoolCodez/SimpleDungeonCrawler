package combatSystem;

import java.awt.Point;

import misc.MouseClick;
import misc.SDC;
import panels.BattleViewPanel;

public class Select extends Thread {
	BattleViewPanel battleView;
	ControlRouter control;

	public Select(BattleViewPanel battleV, ControlRouter c) {
		battleView = battleV;
		control = c;
	}

	public void run() {
		while (!control.flee) {
			MouseClick mouse = new MouseClick();
			// SDC.frame.getContentPane().addMouseListener(mouse);
			battleView.addMouseListener(mouse);
			synchronized (mouse) {
				try {
					mouse.wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			Point mousePoint = mouse.getLocation();
			Point adjustedPoint = new Point();
			int x = mousePoint.x;
			int y = mousePoint.y;
			int scaled140 = (int) (140 * SDC.SCALE_FACTOR);
			adjustedPoint.x = (x - (x % scaled140)) / scaled140;
			adjustedPoint.y = (y - (y % scaled140)) / scaled140;
			control.select(adjustedPoint);
		}
	}
}
