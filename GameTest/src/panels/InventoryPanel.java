package panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import items.Item;
import items.Stick;
import misc.MouseClick;
import misc.SDC;

public class InventoryPanel extends JPanel {
	private static final double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private static final int BUTTON_WIDTH = SDC.BUTTON_WIDTH;
	private static final int BUTTON_HEIGHT = SDC.BUTTON_HEIGHT;
	private static final int SCALED_140 = (int) (140 * SCALE_FACTOR);
	private static final int SCALED_100 = SDC.SCALED_100;
	private static final int SCALED_40 = (int) (40 * SCALE_FACTOR);
	private Point selectedLocation;
	private boolean endMouseListener;
	private int selectedItemNumber;
	private List<Item> inventory;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawInv(g);
		g.drawRect(selectedLocation.x, selectedLocation.y, SCALED_100, SCALED_140);
	}
	
	private void drawInv(Graphics g) {
		Rectangle rText = new Rectangle(0, SCALED_100, SCALED_100, SCALED_40);
		Rectangle rImage = new Rectangle(0, 0, SCALED_100, SCALED_100);
		for (int i = SDC.character.getInventory().size() - 1; i >= 0; i--) {
			Item item = SDC.character.getInventory().get(i);
			g.drawImage(item.inventoryImage.getImage(), rImage.x, rImage.y, null);
			g.setFont(new Font("Harrington", Font.BOLD, 18));
			g.drawString(item.itemName, rImage.x, rImage.y + (int) (120 * SCALE_FACTOR));
			rImage.x += SCALED_100;
			if (rImage.x >= (int) (900 * SCALE_FACTOR)) {
				rImage.x -= (int) (900 * SCALE_FACTOR);
				rImage.y += SCALED_140;
			}
		}
	}

	public InventoryPanel() {
		inventory = SDC.character.getInventory();
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
		point.x -= 10 * SCALE_FACTOR * SCALE_FACTOR;
		point.y -= 30 * SCALE_FACTOR * SCALE_FACTOR;
		int trunkatedX = point.x - (point.x % SCALED_100);
		int trunkatedY = point.y - (point.y % SCALED_140);
		int itemX = trunkatedX / SCALED_100;
		int itemY = 9 * (trunkatedY / SCALED_140);
		selectedItemNumber = itemX + itemY;
		refreshItem();
	}
	
	private void createDeleteItemButton() {
		JButton deleteItemButton = new JButton("DELETE ITEM");
		deleteItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Item> inventory = SDC.character.getInventory();
				int inventorySize = inventory.size();
				inventory.remove(inventorySize - selectedItemNumber - 1);
				refreshItem();
			}
		});
		deleteItemButton.setBounds(BUTTON_WIDTH, (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		deleteItemButton.setFont(SDC.font);
		add(deleteItemButton);
	}
	
	private void refreshItem() {
		if (selectedItemNumber > SDC.character.getInventory().size() - 1) {
			selectedItemNumber = SDC.character.getInventory().size() - 1;
		}
		Point newPoint = new Point((int) ((selectedItemNumber % 9) * SCALED_100),
				(int) (((selectedItemNumber - selectedItemNumber % 9) / 9)) * SCALED_140);

		selectedLocation = newPoint;
	}

	private void createAddStickButton() {
		JButton addStick = new JButton("STICK ME");
		addStick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SDC.character.getInventory().size() < 20) {
					Stick stick = new Stick();
					stick.getInventoryImage();
					SDC.character.addItem(stick);
					/*removeAll();
					add(addStick);
					createExitButton();
					refreshInv();*/
				}
			}
		});
		addStick.setBounds((int) (0 * SCALE_FACTOR), (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		addStick.setFont(SDC.font);
		add(addStick);
	}

	private void createExitButton() {
		JButton exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endMouseListener = true;
				setVisible(false);
				SDC.frame.getContentPane().add(new PauseMenuPanel());
			}
		});
		exitButton.setBounds((int) (700 * SCALE_FACTOR), (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.setFont(SDC.font);
		add(exitButton);
	}

}
