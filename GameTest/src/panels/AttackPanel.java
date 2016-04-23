package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import combatSystem.Battle;
import misc.Images;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;

public class AttackPanel extends JPanel{
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	private static int MENU_SIZE = SimpleDungeonCrawler.MENU_SIZE;
	private static int SCALED_100 = SimpleDungeonCrawler.SCALED_100;
	private Battle battle;

	public AttackPanel() {
		createBattle();
		setLayout(null);
	}
	
	public AttackPanel(Battle battle) {
		this.battle = battle;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.battleMenu, 0, 0, MENU_SIZE, MENU_SIZE, null);
		g.setColor(Color.red);
		g.fillRect((int) (214 * SCALE_FACTOR), (int) (932 * SCALE_FACTOR), (int) (440 * SCALE_FACTOR
				* SimpleDungeonCrawler.character.stats.getHealth() / SimpleDungeonCrawler.character.stats.getMaxHealth()),
				(int) (36 * SCALE_FACTOR));
		g.setColor(Color.black);
		g.drawImage(Images.battleChar, (int) (300 * SCALE_FACTOR), (int) (600 * SCALE_FACTOR),
				(int) (200 * SCALE_FACTOR), (int) (100 * SCALE_FACTOR), null);
		StandardRoom current = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		for (int i = 0; i < current.entities.size(); i++) {
			Point2D point = current.entities.get(i).getBattleLoc();
			g.drawImage(Images.battleGoblin, (int) point.getX(), (int) point.getY(), (int) (200 * SCALE_FACTOR),
					(int) (100 * SCALE_FACTOR), null);
		}
		g.drawString("Turn Points" + battle.waitForTurn.getTurnPoints(), 0, 0);
		// g.drawString(console1.get(console1.size() - 1), 10, 100);
	}
	
	private void createBattle() {
		SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
			@Override
			protected Integer doInBackground() throws Exception {
				System.out.println("new battle");
				battle = new Battle();
				battle.battleSequence();
				battle.flee = false;
				return 0;
			}
		};
		worker.execute();
	}

	public void removeButtonsFromAttackPanel() {
		this.removeAll();
	}

}
