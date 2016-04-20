package panels;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.SimpleDungeonCrawler;

public class BagPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = Panels.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = Panels.BUTTON_HEIGHT;
	
	public static void createBagPanel() {
		JButton returnButton = new JButton("RETURN");
		JButton selectWeapon = new JButton("SELECT WEAPON");
		Panels.bagPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawString("SELECTED WEAPON", 0, (int) (118*SCALE_FACTOR));
			}
		};
		Panels.bagPanel.add(selectWeapon);
		Panels.bagPanel.add(returnButton);
		Panels.bagPanel.setLayout(null);
		
		selectWeapon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SimpleDungeonCrawler.waitForTurn.getTurnPoints() > 2) {
					//TODO choose weapon method
				}
			}
		});
		selectWeapon.setBounds((int) (0*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.add(Panels.turnPanel);
				Panels.frame.remove(Panels.bagPanel);
			}
		});
		returnButton.setBounds((int) (700*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
	}
}
