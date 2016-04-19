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
import misc.StandardRoom;

public class AttackPanel {
	public static void createAtkPanel() {
		JButton exitButton = new JButton("EXIT");
		JButton bagButton = new JButton();
		JButton fightButton = new JButton();
		JButton fleeButton = new JButton();
		JButton moveButton = new JButton();
		JButton endTurn = new JButton("END TURN");
		ArrayList<String> console1 = new ArrayList<String>();
		// console1.add("Console is funtioning.");
		// attack panel
		atkPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.battleMenu, 0, 0, MENU_SIZE, MENU_SIZE, null);
				g.setColor(Color.red);
				g.fillRect((int) (214*SCALE_FACTOR), (int) (932*SCALE_FACTOR), (int) (440*SCALE_FACTOR * character.getHealth() / character.getMaxHealth()), (int) (36*SCALE_FACTOR));
				g.setColor(Color.black);
				StandardRoom current = roomArray[loc.x][loc.y];
				for (int i = 0; i < current.enemyEntities.size(); i++) {
					Point2D point = current.enemyEntities.get(i).getBattleLoc();
					g.drawImage(Images.battleGoblin, (int) point.getX(), (int) point.getY(), (int) (200*SCALE_FACTOR), (int) (100*SCALE_FACTOR), null);
				}
				g.drawImage(Images.battleChar, (int) (300*SCALE_FACTOR), (int) (600*SCALE_FACTOR), (int) (200*SCALE_FACTOR), (int) (100*SCALE_FACTOR), null);
				// g.drawString(console1.get(console1.size() - 1), 10, 100);
			}
		};

		turnPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.battleMenu, 0, 0, MENU_SIZE, MENU_SIZE, null);
				g.setColor(Color.red);
				g.fillRect((int) (214*SCALE_FACTOR), (int) (932*SCALE_FACTOR), (int) (440*SCALE_FACTOR * character.getHealth() / character.getMaxHealth()), (int) (36*SCALE_FACTOR));
				g.setColor(Color.black);
				g.drawImage(Images.battleChar, (int) (300*SCALE_FACTOR), (int) (600*SCALE_FACTOR), (int) (200*SCALE_FACTOR), (int) (100*SCALE_FACTOR), null);
				StandardRoom current = roomArray[loc.x][loc.y];
				for (int i = 0; i < current.enemyEntities.size(); i++) {
					Point2D point = current.enemyEntities.get(i).getBattleLoc();
					g.drawImage(Images.battleGoblin, (int) point.getX(), (int) point.getY(), (int) (200*SCALE_FACTOR), (int) (100*SCALE_FACTOR), null);
				}
				g.drawString("Turn Points" + waitForTurn.getTurnPoints(), 0, 0);
				// g.drawString(console1.get(console1.size() - 1), 10, 100);
			}
		};

		turnPanel.add(bagButton);
		turnPanel.add(fightButton);
		turnPanel.add(fleeButton);
		turnPanel.add(moveButton);
		turnPanel.add(endTurn);
		turnPanel.setLayout(null);
		createBagPanel();

		fightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (waitForTurn.getTurnPoints() >= 3) {
					waitForTurn.setTurnPoints(-3);
					characterAttack(roomArray[loc.x][loc.y].enemyEntities.get(character.getSelectedEntity()));
				} else {
					System.out.println("Not enough turn points");
				}
			}
		});
		fightButton.setBounds((int) (698*SCALE_FACTOR), (int) (148*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		fightButton.setIcon(new ImageIcon(Images.fightButton));
		
		endTurn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				waitForTurn.endTurn();
			}
		});
		endTurn.setBounds((int) (698*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);

		moveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						move();
						return 0;
					}
				};
				worker.execute();
			}
		});
		moveButton.setBounds((int) (698*SCALE_FACTOR), (int) (348*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		moveButton.setIcon(new ImageIcon(Images.moveButton));
		
		bagButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(turnPanel);
				frame.add(bagPanel);
				JLabel weaponLabel = new JLabel(character.getWeapon().getImage());
				weaponLabel.setBounds(0, 0, SCALED_100, SCALED_100);
				bagPanel.add(weaponLabel);
			}
		});
		bagButton.setBounds((int) (698*SCALE_FACTOR), (int) (552*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		bagButton.setIcon(new ImageIcon(Images.bagButton));

		fleeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flee = true;
				waitForTurn.endTurn();
			}
		});
		fleeButton.setBounds((int) (698*SCALE_FACTOR), (int) (752*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		fleeButton.setIcon(new ImageIcon(Images.fleeButton));
	}
}
