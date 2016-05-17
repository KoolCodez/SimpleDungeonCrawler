package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import combatSystem.Battle;
import combatSystem.FallingDamageNumber;
import misc.Images;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;

public class BattleViewPanel extends JPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private Battle battle;
	private ArrayList<FallingDamageNumber> damageNumbers;
	
	public BattleViewPanel(Battle b) {
		battle = b;
		damageNumbers = new ArrayList<FallingDamageNumber>();
		this.setBounds(0, 0, 500, 500); //TODO fix pls
	}
	
	public void addDamageNumber(FallingDamageNumber n) {
		damageNumbers.add(n);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.array[Images.battleCharIndex], (int) (300 * SCALE_FACTOR), (int) (600 * SCALE_FACTOR),
				(int) (200 * SCALE_FACTOR), (int) (100 * SCALE_FACTOR), null);
		StandardRoom current = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		for (int i = 0; i < current.entities.size(); i++) {
			Point2D point = current.entities.get(i).getBattleLoc();
			g.drawImage(Images.array[Images.battleGoblinIndex], (int) point.getX(), (int) point.getY(), (int) (200 * SCALE_FACTOR),
					(int) (100 * SCALE_FACTOR), null);
		}
		g.setColor(Color.red);
		for (int i = 0; i < damageNumbers.size(); i++) {
			FallingDamageNumber currentNum = damageNumbers.get(i);
			Point point = currentNum.getPoint();
			g.setFont(SimpleDungeonCrawler.font);
			g.drawString(currentNum.getDamage() + "", point.x, point.y);
		}
		g.setColor(Color.black);
	}
}
