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

public class PauseMenuPanel extends JPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	private static int SCALED_100 = SimpleDungeonCrawler.SCALED_100;
	private static Font font = SimpleDungeonCrawler.font;
	Point menuCoord = new Point((int) (350*SCALE_FACTOR), (int) (250*SCALE_FACTOR));
	
	public PauseMenuPanel() {
		createResumeButton();
		createInventoryButton();
		createCharacterButton();
		createSaveButton();
		createExitButton();
		setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.pauseMenu, 0, 0, null);
	}
	
	private void createExitButton() {
		JButton exitButton = new JButton("QUIT");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SimpleDungeonCrawler.frame.add(new MainMenu());
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.setFont(font);
		add(exitButton);
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
		add(saveButton);
	}
	
	private void createInventoryButton() {
		JButton invButton = new JButton("INVENTORY");
		invButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				InventoryPanel inventoryPanel = new InventoryPanel();
				SimpleDungeonCrawler.frame.add(inventoryPanel);
				inventoryPanel.refreshInv();
			}
		});
		invButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		invButton.setFont(font);
		add(invButton);
	}
	
	private void createCharacterButton() {
		JButton charButton = new JButton("CHARACTER");
		charButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SimpleDungeonCrawler.frame.add(new CharacterPanel());
			}
		});
		charButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		charButton.setFont(font);
		add(charButton);
	}
	
	private void createResumeButton() {
		JButton resume = new JButton();
		resume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SimpleDungeonCrawler.frame.add(new CoreGameplayPanel());
			}
		});
		resume.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		resume.setIcon(new ImageIcon(Images.resumeButton));
		menuCoord.y += BUTTON_HEIGHT;
		resume.setFont(font);
		add(resume);
	}
}
