package panels;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Entity;
import misc.Images;
import misc.SimpleDungeonCrawler;

public class MainMenu extends JPanel{

	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	Point menuCoord = new Point((int) (350 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR));

	public MainMenu() {
		createStartButton();
		createLoadButton();
		createExitButton();
		setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.mainMenu, (int) (0 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), SimpleDungeonCrawler.MENU_SIZE,
				SimpleDungeonCrawler.MENU_SIZE, null);

	}
	
	private void createExitButton() {
		JButton exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		exitButton.setFont(SimpleDungeonCrawler.font);
		add(exitButton);
	}
	
	private void createStartButton() {
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDungeonCrawler.frame.getContentPane().add(new CoreGameplayPanel());
				setVisible(false);
			}
		});
		startButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		startButton.setFont(SimpleDungeonCrawler.font);
		add(startButton);
	}
	
	private void createLoadButton() {
		JButton loadButton = new JButton("LOAD");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SimpleDungeonCrawler.loadAllImportantStuff();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		loadButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		loadButton.setFont(SimpleDungeonCrawler.font);
		add(loadButton);
	}
}
