package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import combatSystem.BattleQueue;
import combatSystem.ControlRouter;
import combatSystem.FallingDamageNumber;
import entities.Entity;
import items.Weapon;
import misc.Images;
import misc.MouseClick;
import misc.SDC;
import misc.Utilities;
import rooms.StandardRoom;

public class BattleTurnPanel extends JPanel {
	private static double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SDC.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SDC.BUTTON_HEIGHT;
	private static int MENU_SIZE = SDC.MENU_SIZE;
	private static int SCALED_100 = SDC.SCALED_100;

	private BattleViewPanel battleView;
	private ControlRouter control;

	public BattleTurnPanel(ControlRouter c) {
		setLayout(null);
		SDC.frame.add(this);
		control = c;
		battleView = control.battleView;
		// addButtonsToTurnPanel();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.array[Images.battleMenuIndex], 0, 0, MENU_SIZE, MENU_SIZE, null);
		g.setColor(Color.red);
		g.fillRect((int) (214 * SCALE_FACTOR), (int) (932 * SCALE_FACTOR),
				(int) (440 * SCALE_FACTOR * SDC.character.stats.getHealth() / SDC.character.stats.getMaxHealth()),
				(int) (36 * SCALE_FACTOR));
		g.setColor(Color.green);
		
		 g.drawString("Turn Points: " + control.waitForTurn.getTurnPoints(), (int) (800 * SCALE_FACTOR), (int) (800 * SCALE_FACTOR));
		// g.drawString(console1.get(console1.size() - 1), 10, 100);
	}

	public void addButtonsToTurnPanel() {
		createFightButton();
		createEndTurnButton();
		createMoveButtons();
		createBagButton();
	}

	private void createBagButton() {
		JButton bagButton = new JButton();
		bagButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.switchToBagPanel();
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
				control.switchToAttackPhase();
			}
		});
		fightButton.setBounds((int) (698 * SCALE_FACTOR), (int) (148 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		fightButton.setIcon(new ImageIcon(Images.array[Images.fightButtonIndex]));
		add(fightButton);
	}

	private void createMoveButtons() {
		// leftMove.setIcon(new
		// ImageIcon(Images.array[Images.moveButtonIndex]));
		JButton leftMove = new JButton();
		leftMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						control.move("left");
						return 0;
					}
				};
				worker.execute();
			}
		});
		leftMove.setBounds((int) (775 * SCALE_FACTOR), (int) (400 * SCALE_FACTOR), (int) (50 * SCALE_FACTOR),
				(int) (50 * SCALE_FACTOR));
		add(leftMove);
		
		JButton rightMove = new JButton();
		rightMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						control.move("right");
						return 0;
					}
				};
				worker.execute();
			}
		});
		rightMove.setBounds((int) (875 * SCALE_FACTOR), (int) (400 * SCALE_FACTOR), (int) (50 * SCALE_FACTOR),
				(int) (50 * SCALE_FACTOR));
		add(rightMove);
		
		JButton upMove = new JButton();
		upMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						control.move("up");
						return 0;
					}
				};
				worker.execute();
			}
		});
		upMove.setBounds((int) (825 * SCALE_FACTOR), (int) (350 * SCALE_FACTOR), (int) (50 * SCALE_FACTOR),
				(int) (50 * SCALE_FACTOR));
		add(upMove);
		
		JButton downMove = new JButton();
		downMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						control.move("down");
						return 0;
					}
				};
				worker.execute();
			}
		});
		downMove.setBounds((int) (825 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), (int) (50 * SCALE_FACTOR),
				(int) (50 * SCALE_FACTOR));
		add(downMove);
	}

	private void createEndTurnButton() {
		JButton endTurnButton = new JButton("END TURN");
		endTurnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.waitForTurn.endTurn();
			}
		});
		endTurnButton.setBounds((int) (698 * SCALE_FACTOR), (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		add(endTurnButton);
	}

}
