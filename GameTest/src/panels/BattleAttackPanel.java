package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import combatSystem.ControlRouter;
import misc.Images;
import misc.SDC;
import rooms.StandardRoom;
import things.entities.Entity;

public class BattleAttackPanel extends JPanel {
	private static double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SDC.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SDC.BUTTON_HEIGHT;
	private static int MENU_SIZE = SDC.MENU_SIZE;
	private static int SCALED_100 = SDC.SCALED_100;

	private BattleViewPanel battleView;
	private ControlRouter control;
	
	public BattleAttackPanel(ControlRouter c) {
		setLayout(null);
		SDC.frame.add(this);
		control = c;
		battleView = control.battleView;
		createAttackButton();
		createShoveButton();
		createBackButton();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.loadImage("Menus/BattleMenu2.jpg", MENU_SIZE, MENU_SIZE), 0, 0, MENU_SIZE, MENU_SIZE, null);
		g.setColor(Color.red);
		g.fillRect((int) (214 * SCALE_FACTOR), (int) (932 * SCALE_FACTOR),
				(int) (440 * SCALE_FACTOR * SDC.character.stats.getHealth()
						/ SDC.character.stats.getMaxHealth()),
				(int) (36 * SCALE_FACTOR));
		g.setColor(Color.black);
		// g.drawString("Turn Points" + battle.waitForTurn.getTurnPoints(), 50,
		// 50);
		// g.drawString(console1.get(console1.size() - 1), 10, 100);
	}
	
	private void createAttackButton() {
		JButton attackButton = new JButton("ATTACK");
		attackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				attack();
			
			}
		});
		attackButton.setBounds((int) (698 * SCALE_FACTOR), (int) (148 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		add(attackButton);
	}
	
	private void attack() {
		if (control.waitForTurn.getTurnPoints() >= 3) {
			control.charAttack();
			control.waitForTurn.changeTurnPoints(-3);
			if (control.waitForTurn.getTurnPoints() == 0) {
				setVisible(false);
				control.switchToTurnPhase();
				control.switchToQueuePhase();
			}
			
		} else {
			System.out.println("Not enough turn points");
		}
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createShoveButton() {
		JButton shoveButton = new JButton("SHOVE");
		shoveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shove();
			}
		});
		shoveButton.setBounds((int) (698 * SCALE_FACTOR), (int) (248 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		add(shoveButton);
	}
	
	private void shove() {
		if (control.waitForTurn.getTurnPoints() >= 3) {
			back();
			control.charShove();
			control.waitForTurn.changeTurnPoints(-3);
		} else {
			System.out.println("Not enough turn points");
		}
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createBackButton() {
		JButton backButton = new JButton("BACK");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		backButton.setBounds((int) (698 * SCALE_FACTOR), (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		add(backButton);
	}
	
	private void back() {
		this.setVisible(false);
		control.switchToTurnPhase();
	}
}
