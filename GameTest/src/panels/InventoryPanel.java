package panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import items.GenericItem;
import items.Stick;
import misc.MouseClick;
import misc.SimpleDungeonCrawler;

public class InventoryPanel extends JPanel {
	private static final double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static final int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static final int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	private static final int SCALED_140 = (int) (140 * SCALE_FACTOR);
	private static final int SCALED_100 = SimpleDungeonCrawler.SCALED_100;
	private static final int SCALED_40 = (int) (40 * SCALE_FACTOR);
	private Point selectedLocation;
	private boolean endMouseListener;
	private int selectedItemNumber;
	private List<GenericItem> inventory;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(selectedLocation.x, selectedLocation.y, SCALED_100, SCALED_140);
		drawInv(g);
	}
	
	private void drawInv(Graphics g) {
		Rectangle rText = new Rectangle(0, SCALED_100, SCALED_100, SCALED_40);
		Rectangle rImage = new Rectangle(0, 0, SCALED_100, SCALED_100);
		for (int i = SimpleDungeonCrawler.character.getInventory().size() - 1; i >= 0; i--) {
			GenericItem item = SimpleDungeonCrawler.character.getInventory().get(i);
			g.drawImage(item.itemImage.getImage(), rImage.x, rImage.y, null);
			g.setFont(new Font("Harrington", Font.BOLD, 18));
			g.drawString(item.itemName, rImage.x, rImage.y + (int) (120 * SCALE_FACTOR));
			rImage.x += SCALED_100;
			if (rImage.x >= (int) (900 * SCALE_FACTOR)) {
				rImage.x -= (int) (900 * SCALE_FACTOR);
				rImage.y += (int) (140 * SCALE_FACTOR);
			}
		}
	}

	public InventoryPanel() {
		inventory = SimpleDungeonCrawler.character.getInventory();
		selectedLocation = new Point(0, 0);
		selectedItemNumber = 0;
		endMouseListener = false;
		startMouseListener();
		createExitButton();
		createAddStickButton();
		createDeleteItemButton();
		setLayout(null);
	}

	private void startMouseListener() {
		SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
			@Override
			protected Integer doInBackground() throws Exception {
				while (!endMouseListener) {
					MouseClick mouse = new MouseClick();
					addMouseListener(mouse);
					synchronized (mouse) {
						try {
							mouse.wait();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					Point point = mouse.getLocation();
					selectItem(point);
				}
				return 0;
			}
		};
		worker.execute();
	}

	private void selectItem(Point point) {
		selectedItemNumber = (point.x - (point.x % SCALED_100)) / SCALED_100 + 8 * ((point.y - (point.y % SCALED_140)) / SCALED_140);
		refreshItem();
	}
	
	private void createDeleteItemButton() {
		JButton deleteItemButton = new JButton("DELETE ITEM");
		deleteItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(selectedItemNumber);
				List<GenericItem> inventory = SimpleDungeonCrawler.character.getInventory();
				int inventorySize = inventory.size();
				inventory.remove(inventorySize - selectedItemNumber - 1);
				refreshItem();
			}
		});
		deleteItemButton.setBounds(BUTTON_WIDTH, (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		deleteItemButton.setFont(SimpleDungeonCrawler.font);
		add(deleteItemButton);
	}
	
	private void refreshItem() {
		if (selectedItemNumber > SimpleDungeonCrawler.character.getInventory().size() - 1) {
			selectedItemNumber = SimpleDungeonCrawler.character.getInventory().size() - 1;
		}
		Point newPoint = new Point((int) ((selectedItemNumber % 8) * SCALED_100),
				(int) (((selectedItemNumber - selectedItemNumber % 8) / 8)) * SCALED_140);

		selectedLocation = newPoint;
	}

	private void createAddStickButton() {
		JButton addStick = new JButton("STICK ME");
		addStick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SimpleDungeonCrawler.character.getInventory().size() < 20) {
					Stick stick = new Stick();
					stick.getImage();
					SimpleDungeonCrawler.character.addItem(stick);
					/*removeAll();
					add(addStick);
					createExitButton();
					refreshInv();*/
				}
			}
		});
		addStick.setBounds((int) (0 * SCALE_FACTOR), (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		addStick.setFont(SimpleDungeonCrawler.font);
		add(addStick);
	}

	private void createExitButton() {
		JButton exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endMouseListener = true;
				setVisible(false);
				SimpleDungeonCrawler.frame.getContentPane().add(new PauseMenuPanel());
			}
		});
		exitButton.setBounds((int) (700 * SCALE_FACTOR), (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.setFont(SimpleDungeonCrawler.font);
		add(exitButton);
	}

}
