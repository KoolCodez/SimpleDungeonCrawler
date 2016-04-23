package panels;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import combatSystem.Battle;
import misc.SimpleDungeonCrawler;

public class BagPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	private static int SCALED_100 = SimpleDungeonCrawler.SCALED_100;
	private Battle battle;
	private JPanel bagPanel;
	
	public BagPanel(Battle battle) {
		this.battle = battle;
		createBagPanel();
		createReturnButton();
		createSelectWeaponButton();
	}
	
	public JPanel getPanel() {
		return bagPanel;
	}
	
	public void createBagPanel() {
		bagPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawString("SELECTED WEAPON", 0, (int) (118*SCALE_FACTOR));
			}
		};
		bagPanel.setLayout(null);
		JLabel weaponLabel = new JLabel(SimpleDungeonCrawler.character.getWeapon().getImage());
		weaponLabel.setBounds(0, 0, SCALED_100, SCALED_100);
		bagPanel.add(weaponLabel);
	}
	
	public void createSelectWeaponButton() {
		JButton selectWeapon = new JButton("SELECT WEAPON");
		selectWeapon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (battle.waitForTurn.getTurnPoints() > 2) {
					//TODO choose weapon method
				}
			}
		});
		selectWeapon.setBounds((int) (0*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		bagPanel.add(selectWeapon);
	}
	
	public void createReturnButton() {
		JButton returnButton = new JButton("RETURN");
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDungeonCrawler.frame.getContentPane().getComponent(1).setVisible(true); //TODO
				SimpleDungeonCrawler.frame.remove(bagPanel);
			}
		});
		returnButton.setBounds((int) (700*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		bagPanel.add(returnButton);
	}
}
