package panels;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Images;
import misc.SimpleDungeonCrawler;

public class MainMenu extends JPanel{

	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	Point menuCoord = new Point((int) (350 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR));

	public MainMenu() {
		createStartButton();
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
				SimpleDungeonCrawler.frame.getContentPane().remove(0);
			}
		});
		startButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		startButton.setFont(SimpleDungeonCrawler.font);
		add(startButton);
	}
}
