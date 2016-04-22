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

public class AttackPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = Panels.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = Panels.BUTTON_HEIGHT;
	private static int MENU_SIZE = Panels.MENU_SIZE;
	private static int SCALED_100 = Panels.SCALED_100;
	private JPanel attackPanel;
	private Battle battle;

	public AttackPanel() {
		createAttackPanel();
		createBattle();
	}
	
	public AttackPanel(Battle battle) {
		
	}
	
	public JPanel getPanel() {
		return attackPanel;
	}
	
	private void createBagButton() {
		JButton bagButton = new JButton();
		bagButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.remove(attackPanel);
				Panels.frame.add(new BagPanel(battle).getPanel());
			}
		});
		bagButton.setBounds((int) (698 * SCALE_FACTOR), (int) (552 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		bagButton.setIcon(new ImageIcon(Images.bagButton));
		attackPanel.add(bagButton);
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
		fightButton.setIcon(new ImageIcon(Images.fightButton));
		attackPanel.add(fightButton);
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
		moveButton.setIcon(new ImageIcon(Images.moveButton));
		attackPanel.add(moveButton);
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
		fleeButton.setIcon(new ImageIcon(Images.fleeButton));
		attackPanel.add(fleeButton);
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
		attackPanel.add(endTurnButton);
	}

	private void drawBattlePanel(Graphics g) {
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

	private void createAttackPanel() {
		attackPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawBattlePanel(g);
			}
		};
		attackPanel.setLayout(null);
	}
	
	private void createBattle() {
		battle = new Battle();
		SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
			@Override
			protected Integer doInBackground() throws Exception {
				battle.battleSequence();
				return 0;
			}
		};
		battle.flee = false;
		worker.execute();
	}

	public void addButtonsToAttackPanel() {
		createFightButton();
		createEndTurnButton();
		createMoveButton();
		createBagButton();
		createFleeButton();
		attackPanel.validate();
		attackPanel.repaint();
	}

	public void removeButtonsFromAttackPanel() {
		attackPanel.removeAll();
	}

}
