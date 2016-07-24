package combatSystem;

import java.awt.Point;

import misc.MouseClick;
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
			adjustedPoint.x = (x - (x % 140)) / 140;
			adjustedPoint.y = (y - (y % 140)) / 140;
			control.select(adjustedPoint);
		}
	}
}
