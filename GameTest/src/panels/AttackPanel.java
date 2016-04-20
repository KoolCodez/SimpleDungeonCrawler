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

import misc.Images;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;

public class AttackPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = Panels.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = Panels.BUTTON_HEIGHT;
	private static int MENU_SIZE = Panels.MENU_SIZE;
	private static int SCALED_100 = Panels.SCALED_100;

	public static void createAtkPanel() {
		
		Panels.attackPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawBattlePanel(g);
			}
		};

		Panels.turnPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawBattlePanel(g);
			}
		};
		
		JButton exitButton = new JButton("EXIT");
		JButton bagButton = new JButton();
		JButton fightButton = new JButton();
		JButton fleeButton = new JButton();
		JButton moveButton = new JButton();
		JButton endTurnButton = new JButton("END TURN");
		
		createFightButton(fightButton);
		createEndTurnButton(endTurnButton);
		createMoveButton(moveButton);
		createBagButton(bagButton);
		createFleeButton(fleeButton);

		Panels.turnPanel.add(bagButton);
		Panels.turnPanel.add(fightButton);
		Panels.turnPanel.add(fleeButton);
		Panels.turnPanel.add(moveButton);
		Panels.turnPanel.add(endTurnButton);
		Panels.turnPanel.setLayout(null);
		//Panels.createBagPanel(); //TODO
		
	}
	
	private static void createBagButton(JButton bagButton) {
		bagButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.remove(Panels.turnPanel);
				Panels.frame.add(Panels.bagPanel);
				JLabel weaponLabel = new JLabel(SimpleDungeonCrawler.character.getWeapon().getImage());
				weaponLabel.setBounds(0, 0, SCALED_100, SCALED_100);
				Panels.bagPanel.add(weaponLabel);
			}
		});
		bagButton.setBounds((int) (698 * SCALE_FACTOR), (int) (552 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		bagButton.setIcon(new ImageIcon(Images.bagButton));
	}
	
	private static void createFightButton(JButton fightButton) {
		fightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SimpleDungeonCrawler.waitForTurn.getTurnPoints() >= 3) {
					SimpleDungeonCrawler.waitForTurn.setTurnPoints(-3);
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
	}
	
	private static void createMoveButton(JButton moveButton) {
		moveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						SimpleDungeonCrawler.move();
						return 0;
					}
				};
				worker.execute();
			}
		});
		moveButton.setBounds((int) (698 * SCALE_FACTOR), (int) (348 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		moveButton.setIcon(new ImageIcon(Images.moveButton));
	}

	private static void createFleeButton(JButton fleeButton) {
		fleeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDungeonCrawler.flee = true;
				SimpleDungeonCrawler.waitForTurn.endTurn();
			}
		});
		fleeButton.setBounds((int) (698 * SCALE_FACTOR), (int) (752 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		fleeButton.setIcon(new ImageIcon(Images.fleeButton));
	}

	private static void createEndTurnButton(JButton endTurnButton) {
		endTurnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDungeonCrawler.waitForTurn.endTurn();
			}
		});
		endTurnButton.setBounds((int) (698 * SCALE_FACTOR), (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		
	}

	private static void drawBattlePanel(Graphics g) {
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
		g.drawString("Turn Points" + SimpleDungeonCrawler.waitForTurn.getTurnPoints(), 0, 0);
		// g.drawString(console1.get(console1.size() - 1), 10, 100);
	}
}
