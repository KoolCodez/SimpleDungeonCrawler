package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import combatSystem.Battle;
import misc.Images;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;

public class BattleTurnPanel extends JPanel {
		private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
		private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
		private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
		private static int MENU_SIZE = SimpleDungeonCrawler.MENU_SIZE;
		private static int SCALED_100 = SimpleDungeonCrawler.SCALED_100;
		private Battle battle;
		
		public BattleTurnPanel(Battle battle) {
			this.battle = battle;
			setLayout(null);
			//addButtonsToTurnPanel();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(Images.array[Images.battleMenuIndex], 0, 0, MENU_SIZE, MENU_SIZE, null);
			g.setColor(Color.red);
			g.fillRect((int) (214 * SCALE_FACTOR), (int) (932 * SCALE_FACTOR), (int) (440 * SCALE_FACTOR
					* SimpleDungeonCrawler.character.stats.getHealth() / SimpleDungeonCrawler.character.stats.getMaxHealth()),
					(int) (36 * SCALE_FACTOR));
			g.setColor(Color.black);
			g.drawImage(Images.array[Images.battleCharIndex], (int) (300 * SCALE_FACTOR), (int) (600 * SCALE_FACTOR),
					(int) (200 * SCALE_FACTOR), (int) (100 * SCALE_FACTOR), null);
			StandardRoom current = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
			for (int i = 0; i < current.entities.size(); i++) {
				Point2D point = current.entities.get(i).getBattleLoc();
				g.drawImage(Images.array[Images.battleGoblinIndex], (int) point.getX(), (int) point.getY(), (int) (200 * SCALE_FACTOR),
						(int) (100 * SCALE_FACTOR), null);
			}
			g.drawString("Turn Points" + battle.waitForTurn.getTurnPoints(), 50, 50);
			// g.drawString(console1.get(console1.size() - 1), 10, 100);
		}
		
		public void addButtonsToTurnPanel() {
			createFightButton();
			createEndTurnButton();
			createMoveButton();
			createBagButton();
			createFleeButton();
		}
		
		private void createBagButton() {
			JButton bagButton = new JButton();
			JPanel current = this;
			
			bagButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					SimpleDungeonCrawler.frame.add(new BagPanel(battle, current).getPanel());
				}
			});
			bagButton.setBounds((int) (698 * SCALE_FACTOR), (int) (552 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
			bagButton.setIcon(new ImageIcon(Images.array[Images.bagButtonIndex]));
			add(bagButton);
		}
		
		private void createFightButton() {
			JButton fightButton = new JButton();
			fightButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (battle.waitForTurn.getTurnPoints() >= 3) {
						battle.waitForTurn.setTurnPoints(-3);
						SimpleDungeonCrawler.character.attack(
								SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y].entities
										.get(SimpleDungeonCrawler.character.getSelectedEntity()));
					} else {
						System.out.println("Not enough turn points");
					}
				}
			});
			fightButton.setBounds((int) (698 * SCALE_FACTOR), (int) (148 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
			fightButton.setIcon(new ImageIcon(Images.array[Images.fightButtonIndex]));
			add(fightButton);
		}
		
		private void createMoveButton() {
			JButton moveButton = new JButton();
			moveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
						@Override
						protected Integer doInBackground() throws Exception {
							battle.move();
							return 0;
						}
					};
					worker.execute();
				}
			});
			moveButton.setBounds((int) (698 * SCALE_FACTOR), (int) (348 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
			moveButton.setIcon(new ImageIcon(Images.array[Images.moveButtonIndex]));
			add(moveButton);
		}

		private void createFleeButton() {
			JButton fleeButton = new JButton();
			fleeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					battle.flee = true;
					battle.waitForTurn.endTurn();
				}
			});
			fleeButton.setBounds((int) (698 * SCALE_FACTOR), (int) (752 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
			fleeButton.setIcon(new ImageIcon(Images.array[Images.fleeButtonIndex]));
			add(fleeButton);
		}

		private void createEndTurnButton() {
			JButton endTurnButton = new JButton("END TURN");
			endTurnButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					battle.waitForTurn.endTurn();
				}
			});
			endTurnButton.setBounds((int) (698 * SCALE_FACTOR), (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
			add(endTurnButton);
		}
}
