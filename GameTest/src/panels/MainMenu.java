package panels;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Images;
import misc.SimpleDungeonCrawler;

public class MainMenu {

	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = Panels.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = Panels.BUTTON_HEIGHT;

	public MainMenu() {
		createMainMenu();
	}

	public static void createMainMenu() {
		JButton startButton = new JButton("START");
		JButton exitButton = new JButton("EXIT");
		Point menuCoord = new Point((int) (350 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR));
		Panels.mainMenu = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.mainMenu, (int) (0 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), Panels.MENU_SIZE,
						Panels.MENU_SIZE, null);

			}
		};
		Panels.mainMenu.setLayout(null);
		Panels.mainMenu.add(startButton);
		Panels.mainMenu.add(exitButton);

		// start button
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().add(new CoreGameplayPanel().getPanel());
				Panels.frame.getContentPane().remove(Panels.mainMenu);
			}
		});
		startButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		startButton.setFont(SimpleDungeonCrawler.font);

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		exitButton.setFont(SimpleDungeonCrawler.font);
	}
}
