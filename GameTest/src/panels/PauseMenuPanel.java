package panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Images;
import misc.SimpleDungeonCrawler;

public class PauseMenuPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = Panels.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = Panels.BUTTON_HEIGHT;
	private static int SCALED_100 = Panels.SCALED_100;
	private static Font font = SimpleDungeonCrawler.font;
	public static void createPauseMenu() {
		JButton resume = new JButton();
		JButton charButton = new JButton("CHARACTER");
		JButton invButton = new JButton("INVENTORY");
		JButton saveButton = new JButton("SAVE");
		JButton exitButton = new JButton("QUIT");
		Point menuCoord = new Point((int) (350*SCALE_FACTOR), (int) (250*SCALE_FACTOR));

		// menu panel
		Panels.pauseMenuPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.pauseMenu, 0, 0, null);
			}
		};
		Panels.pauseMenuPanel.setLayout(null);
		Panels.pauseMenuPanel.add(resume);
		Panels.pauseMenuPanel.add(charButton);
		Panels.pauseMenuPanel.add(invButton);
		Panels.pauseMenuPanel.add(saveButton);
		Panels.pauseMenuPanel.add(exitButton);

		// start button
		resume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().add(new CoreGameplayPanel().getPanel());
				Panels.frame.getContentPane().removeAll();
			}
		});
		resume.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		resume.setIcon(new ImageIcon(Images.resumeButton));
		menuCoord.y += BUTTON_HEIGHT;
		resume.setFont(font);

		// char button
		charButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().add(Panels.characterPanel);
				Panels.frame.getContentPane().remove(Panels.pauseMenuPanel);
			}
		});
		charButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		charButton.setFont(font);

		// inventory button
		invButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().remove(Panels.pauseMenuPanel);
				Panels.frame.getContentPane().add(Panels.inventoryPanel);
				InventoryPanel.refreshInv();
			}
		});
		invButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		invButton.setFont(font);

		// save button
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		saveButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		saveButton.setFont(font);

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().remove(Panels.pauseMenuPanel);
				Panels.frame.getContentPane().add(Panels.mainMenu);
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.setFont(font);
	}
}
