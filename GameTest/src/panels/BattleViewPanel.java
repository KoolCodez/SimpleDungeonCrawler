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
import misc.Entity;
import misc.Images;
import misc.SimpleDungeonCrawler;
import rooms.StandardRoom;

public class BattleViewPanel extends JPanel {
	private final int CHAR_X_ADJUST = (int) (50 * SCALE_FACTOR);
	private final int CHAR_Y_ADJUST = (int) (25 * SCALE_FACTOR);
	private static final double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private ArrayList<FallingDamageNumber> damageNumbers;
	private Entity character = SimpleDungeonCrawler.character;
	public double moveRadius;
	public double attackRadius;
	private ControlRouter control;
	private Point2D rectLoc;
	
	public BattleViewPanel(ControlRouter c) {
		moveRadius = -1;
		attackRadius = -1;
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
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.array[Images.battleViewBackgroundIndex], 0, 0, (int) (697 * SCALE_FACTOR), (int) (710 * SCALE_FACTOR), null);
		g.drawImage(Images.array[Images.battleCharIndex], 
				(int) character.location.getX() - CHAR_X_ADJUST, (int) character.location.getY() - CHAR_Y_ADJUST,
				(int) (100 * SCALE_FACTOR), (int) (50 * SCALE_FACTOR), null);
		drawEntities(g);
		g.setColor(Color.red);
		for (int i = 0; i < damageNumbers.size(); i++) {
			FallingDamageNumber currentNum = damageNumbers.get(i);
			Point point = currentNum.getPoint();
			g.setFont(SimpleDungeonCrawler.font);
			g.drawString(currentNum.getDamage() + "", point.x, point.y);
		}
		Point2D p = character.location;
		g.drawOval((int) (p.getX() - moveRadius), (int) (p.getY() - moveRadius), (int) moveRadius*2, (int) moveRadius*2);
		g.drawRect((int) (rectLoc.getX()) - CHAR_X_ADJUST, (int) (rectLoc.getY()) - CHAR_Y_ADJUST,
				(int) (100 * SCALE_FACTOR),(int) (50 * SCALE_FACTOR));
		g.setColor(Color.GREEN);
		g.drawOval((int) (p.getX() - attackRadius), (int) (p.getY() - attackRadius), (int) attackRadius*2, (int) attackRadius*2);
		g.setColor(Color.white);
		g.drawString("Turn Points Remining: " + control.waitForTurn.getTurnPoints(), 10, 10);
		
	}
	
	private void drawEntities(Graphics g) {
		StandardRoom current = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		for (int i = 0; i < current.entities.size(); i++) {
			Point2D point = current.entities.get(i).location;
			g.drawImage(Images.array[Images.battleGoblinIndex], 
					(int) point.getX() - CHAR_X_ADJUST, (int) point.getY() - CHAR_Y_ADJUST, 
					(int) (100 * SCALE_FACTOR), (int) (50 * SCALE_FACTOR), null);
		}
	}
	
	public Point2D getBattleLoc(Entity ent) {
		double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
		double battleRatioX = (696 * SCALE_FACTOR) / (1000 * SCALE_FACTOR); //battle size / normal size
		double battleRatioY = (703 * SCALE_FACTOR) / (1000 * SCALE_FACTOR);
		Point2D battleLoc = new Point2D.Double(ent.location.getX() * (battleRatioX),
				ent.location.getY() * (battleRatioY) + 149); 
		//(0,149)(696, 149)(0,852)(696,852) 696, 703
		return battleLoc;
	}
}
