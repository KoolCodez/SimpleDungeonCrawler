package panels;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Images;

public class MainMenu {
	
	public MainMenu() {
		createMainMenu();
	}
	
	public static void createMainMenu() {
		JButton startButton = new JButton("START");
		JButton exitButton = new JButton("EXIT");
		Point menuCoord = new Point((int) (350*SCALE_FACTOR), (int) (450*SCALE_FACTOR));
		PanelSubsystem.mainMenu = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.mainMenu, (int) (0*SCALE_FACTOR), (int) (0*SCALE_FACTOR), MENU_SIZE, MENU_SIZE, null);

			}
		};
		PanelSubsystem.mainMenu.setLayout(null);
		PanelSubsystem.mainMenu.add(startButton);
		PanelSubsystem.mainMenu.add(exitButton);

		// start button
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelSubsystem.frame.getContentPane().add(panel);
				PanelSubsystem.frame.getContentPane().remove(mainMenu);
			}
		});
		startButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		startButton.setFont(font);

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		exitButton.setFont(font);
	}
}
