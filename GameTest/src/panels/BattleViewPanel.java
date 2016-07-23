package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import combatSystem.ControlRouter;
import combatSystem.FallingDamageNumber;
import entities.Entity;
import misc.Images;
import misc.SDC;
import rooms.StandardRoom;

public class BattleViewPanel extends JPanel {
	private final int CHAR_X_ADJUST = (int) (50 * SCALE_FACTOR);
	private final int CHAR_Y_ADJUST = (int) (25 * SCALE_FACTOR);
	private static final double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private ArrayList<FallingDamageNumber> damageNumbers;
	private Entity character = SDC.character;
	private ControlRouter control;
	private Point2D rectLoc;
	
	public BattleViewPanel(ControlRouter c) {
		damageNumbers = new ArrayList<FallingDamageNumber>();
		this.setBounds(0, (int) (148 * SCALE_FACTOR), (int) (697 * SCALE_FACTOR), (int) (710 * SCALE_FACTOR));
		control = c;
		rectLoc = new Point2D.Double(-100, -100);
	}
	
	public void displayDamage(double damage, Point point) {
		FallingDamageNumber n = new FallingDamageNumber(damage, point);
		damageNumbers.add(n);
		n.start();
	}
	
	public void highlight(Entity ent) {
		rectLoc.setLocation(ent.location);
	}
	
	public Point2D getBattleLoc(Entity ent) {
		double SCALE_FACTOR = SDC.SCALE_FACTOR;
		double battleRatioX = (696 * SCALE_FACTOR) / (1000 * SCALE_FACTOR); //battle size / normal size
		double battleRatioY = (703 * SCALE_FACTOR) / (1000 * SCALE_FACTOR);
		Point2D battleLoc = new Point2D.Double(ent.location.getX() * (battleRatioX),
				ent.location.getY() * (battleRatioY) + 149); 
		//(0,149)(696, 149)(0,852)(696,852) 696, 703
		return battleLoc;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//g.drawImage(Images.array[Images.battleViewBackgroundIndex], 0, 0, (int) (697 * SCALE_FACTOR), (int) (710 * SCALE_FACTOR), null);
		drawGrid(g);
		StandardRoom current = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		for (int i = 0; i < current.entities.size(); i++) {
			drawEntities(g, current, i);
		}
		
		drawDamageNumbers(g);
		g.drawRect((int) (rectLoc.getX()) - CHAR_X_ADJUST, (int) (rectLoc.getY()) - CHAR_Y_ADJUST,
				(int) (100 * SCALE_FACTOR),(int) (50 * SCALE_FACTOR));
		g.setColor(Color.white);
		g.drawString("Turn Points Remining: " + control.waitForTurn.getTurnPoints(), 10, 10);
		
	}
	
	private void drawGrid(Graphics g) {
		g.drawLine(0, 0, 0, (int) (700*SCALE_FACTOR));
		g.drawLine((int) (140*SCALE_FACTOR), 0, (int) (140*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		g.drawLine((int) (280*SCALE_FACTOR), 0, (int) (280*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		g.drawLine((int) (420*SCALE_FACTOR), 0, (int) (420*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		g.drawLine((int) (560*SCALE_FACTOR), 0, (int) (560*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		g.drawLine((int) (700*SCALE_FACTOR), 0, (int) (700*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		
		g.drawLine(0, 0, (int) (700*SCALE_FACTOR), 0);
		g.drawLine(0, (int) (140*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (140*SCALE_FACTOR));
		g.drawLine(0, (int) (280*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (280*SCALE_FACTOR));
		g.drawLine(0, (int) (420*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (420*SCALE_FACTOR));
		g.drawLine(0, (int) (560*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (560*SCALE_FACTOR));
		g.drawLine(0, (int) (700*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		
	}
	
	private void drawEntities(Graphics g, StandardRoom current, int i) {
		//System.out.println(current.entities.size());
		Entity ent = current.entities.get(i);
		int scale = (int) (140 * SCALE_FACTOR);
		int scaled30 = (int) (30*SCALE_FACTOR);
		g.drawImage(Images.array[ent.getImage()], ent.battleLoc.x * scale + scaled30, ent.battleLoc.y * scale + scaled30, null);
	}
	
	private void drawDamageNumbers(Graphics g) {
		g.setColor(Color.red);
		for (int i = 0; i < damageNumbers.size(); i++) {
			FallingDamageNumber currentNum = damageNumbers.get(i);
			Point point = currentNum.getPoint();
			g.setFont(SDC.font);
			g.drawString(currentNum.getDamage() + "", point.x, point.y);
		}
	}
	
}
