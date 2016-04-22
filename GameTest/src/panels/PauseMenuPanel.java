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
	Point menuCoord = new Point((int) (350*SCALE_FACTOR), (int) (250*SCALE_FACTOR));
	private JPanel pauseMenuPanel;
	
	public PauseMenuPanel() {
		createPauseMenuPanel();
		createResumeButton();
		createInventoryButton();
		createCharacterButton();
		createSaveButton();
		createExitButton();
	}
	
	public JPanel getPanel() {
		return pauseMenuPanel;
	}
	
	private void createPauseMenuPanel() {
		pauseMenuPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.pauseMenu, 0, 0, null);
			}
		};
		pauseMenuPanel.setLayout(null);
	}
	
	private void createExitButton() {
		JButton exitButton = new JButton("QUIT");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().remove(pauseMenuPanel);
				Panels.frame.getContentPane().add(Panels.mainMenu);
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.setFont(font);
		pauseMenuPanel.add(exitButton);
	}
	
	private void createSaveButton() {
		JButton saveButton = new JButton("SAVE");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		saveButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		saveButton.setFont(font);
		pauseMenuPanel.add(saveButton);
	}
	
	private void createInventoryButton() {
		JButton invButton = new JButton("INVENTORY");
		invButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().remove(pauseMenuPanel);
				Panels.frame.getContentPane().add(Panels.inventoryPanel);
				InventoryPanel.refreshInv();
			}
		});
		invButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		invButton.setFont(font);
		pauseMenuPanel.add(invButton);
	}
	
	private void createCharacterButton() {
		JButton charButton = new JButton("CHARACTER");
		charButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().add(Panels.characterPanel);
				Panels.frame.getContentPane().remove(pauseMenuPanel);
			}
		});
		charButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		charButton.setFont(font);
		pauseMenuPanel.add(charButton);
	}
	
	private void createResumeButton() {
		JButton resume = new JButton();
		resume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().removeAll();
				Panels.frame.getContentPane().add(new CoreGameplayPanel().getPanel());
			}
		});
		resume.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		resume.setIcon(new ImageIcon(Images.resumeButton));
		menuCoord.y += BUTTON_HEIGHT;
		resume.setFont(font);
		pauseMenuPanel.add(resume);
	}
}
