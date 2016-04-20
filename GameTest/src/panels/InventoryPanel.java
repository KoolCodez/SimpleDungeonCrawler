package panels;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import items.GenericItem;
import items.Stick;
import misc.SimpleDungeonCrawler;

public class InventoryPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = Panels.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = Panels.BUTTON_HEIGHT;
	private static int SCALED_100 = Panels.SCALED_100;
	
	public static void createInventory() {
		JButton exitButton = new JButton("EXIT");
		JButton addStick = new JButton("STICK ME");

		// attack panel
		Panels.invPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};
		Panels.invPanel.add(addStick);
		Panels.invPanel.add(exitButton);
		Panels.invPanel.setLayout(null);

		// exit button
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.invPanel.removeAll();
				Panels.invPanel.add(addStick);
				Panels.invPanel.add(exitButton);
				Panels.frame.getContentPane().add(Panels.menuPanel);
				Panels.frame.getContentPane().remove(Panels.invPanel);
			}
		});
		exitButton.setBounds((int) (700*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.setFont(SimpleDungeonCrawler.font);

		// stick button
		addStick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SimpleDungeonCrawler.character.getInventory().size() < 20) {
					Stick stick = new Stick();
					stick.getImage();
					SimpleDungeonCrawler.character.addItem(stick);
					Panels.invPanel.removeAll();
					Panels.invPanel.add(addStick);
					Panels.invPanel.add(exitButton);
					refreshInv();
				}
			}
		});
		addStick.setBounds((int) (0*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		addStick.setFont(SimpleDungeonCrawler.font);
	}

	public static void refreshInv() {
		Rectangle rText = new Rectangle(0, SCALED_100, SCALED_100, (int) (40*SCALE_FACTOR));
		Rectangle rImage = new Rectangle(0, 0, SCALED_100, SCALED_100);
		for (int i = SimpleDungeonCrawler.character.getInventory().size() - 1; i >= 0; i--) {
			GenericItem item = SimpleDungeonCrawler.character.getInventory().get(i);
			JTextArea text = new JTextArea(item.itemName);
			text.setEditable(false);
			text.setBounds(rText);
			rText.x += SCALED_100;
			if (rText.x >= (int) (900*SCALE_FACTOR)) {
				rText.x -= (int) (900*SCALE_FACTOR);
				rText.y += (int) (140*SCALE_FACTOR);
			}
			Panels.invPanel.add(text);
			JLabel itemLabel = new JLabel(item.itemImage);
			itemLabel.setBounds(rImage);
			rImage.x += SCALED_100;
			if (rImage.x >= (int) (900*SCALE_FACTOR)) {
				rImage.x -= (int) (900*SCALE_FACTOR);
				rImage.y += (int) (140*SCALE_FACTOR);
			}
			Panels.invPanel.add(itemLabel);
		}
	}
}
