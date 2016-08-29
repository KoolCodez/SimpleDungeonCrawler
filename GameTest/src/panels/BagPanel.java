package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import combatSystem.ControlRouter;
import misc.Images;
import misc.SDC;

public class BagPanel extends JPanel {
	private static double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SDC.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SDC.BUTTON_HEIGHT;
	private static int SCALED_100 = SDC.SCALED_100;
	private int turnPoints;
	ControlRouter control;;
	
	public BagPanel(ControlRouter c) {
		setLayout(null);
		SDC.frame.add(this);
		JLabel weaponLabel = new JLabel(SDC.character.equipped.weapon.getInventoryImage());
		weaponLabel.setBounds(0, 0, SCALED_100, SCALED_100);
		add(weaponLabel);
		control = c;
		turnPoints = control.waitForTurn.getTurnPoints();
		createReturnButton();
		createSelectWeaponButton();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("SELECTED WEAPON", 0, (int) (118*SCALE_FACTOR));
	}
	
	public void createSelectWeaponButton() {
		JButton selectWeapon = new JButton("SELECT WEAPON");
		selectWeapon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (turnPoints > 2) {
					//TODO choose weapon method
				}
			}
		});
		selectWeapon.setBounds((int) (0*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		add(selectWeapon);
	}
	
	public void createReturnButton() {
		JButton returnButton = new JButton("RETURN");
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				control.switchToTurnPhase();
			}
		});
		returnButton.setBounds((int) (700*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		add(returnButton);
	}
}
