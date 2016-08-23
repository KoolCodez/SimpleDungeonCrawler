package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import items.Item;
import misc.MouseClick;
import misc.SDC;

public class InventoryDisplay {
	private static final int SCALED_100 = SDC.SCALED_100;
	private List<Item> inventory;
	private Rectangle bounds;
	private Point location;
	public Item selectedItem;
	private Point selectedLoc;
	private JPanel parentPanel;
	private static final double SCALE = SDC.SCALE_FACTOR;
	
	int perLine;
	int lines;
	int lastLineRemainder;

	public InventoryDisplay(JPanel parent) {
		//startMouseListener();
		selectedLoc = new Point(0,0);
		parentPanel = parent;
	}
	
	public void drawInv(Graphics g) {
		Point image = new Point(location.x, location.y);
		for (int i = inventory.size() - 1; i >= 0; i--) {
			Item item = inventory.get(i);
			g.drawImage(item.inventoryImage.getImage(), image.x, image.y, null);
			g.setFont(new Font("Harrington", Font.BOLD, 18));
			g.drawString(item.itemName, image.x, image.y + SCALED_100);
			image.x += SCALED_100;
			if (image.x >= bounds.width) {
				image.x = location.x;
				image.y += SCALED_100;
			}
		}
		g.setColor(Color.black);
		g.drawRect(location.x, location.y, bounds.width, bounds.height);
		for (int i = 0; i < (int) (bounds.width / SCALED_100); i++) {
			g.drawLine(location.x + SCALED_100*i, location.y, location.x + SCALED_100*i, location.y + bounds.height);
		}
		for (int i = 0; i < (int) (bounds.height / SCALED_100); i++) {
			g.drawLine(location.x, location.y + SCALED_100*i, location.x + bounds.width, location.y + SCALED_100*i);
		}
		g.setColor(Color.green);
		g.drawRect(selectedLoc.x, selectedLoc.y, SCALED_100, SCALED_100);
	}
	
	public void setInventory(List<Item> items) {
		inventory = items;
	}
	
	public void setRect(Rectangle rect) {
		bounds = rect;
		refreshInvStats();
	}
	
	public void setLocation(Point point) {
		location = point;
	}

	public void startMouseListener() {
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					MouseClick mouse = new MouseClick();
					parentPanel.addMouseListener(mouse);
					synchronized (mouse) {
						try {
							mouse.wait();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					Point point = mouse.getLocation();
					refreshInvStats();
					selectItem(point);
				}
			}
		};
		t.start();
	}
	
	private void refreshInvStats() {
		perLine = (int) (bounds.width / SCALED_100);
		lines = inventory.size() / perLine;
		lastLineRemainder = inventory.size() - (lines*perLine);
	}

	private void selectItem(Point point) {
		if (point.x < location.x || point.y < location.y || point.x > location.x + bounds.width
				|| point.y > location.y + bounds.height) {
			System.out.println("Out of bounds");
		} else {
			point.x -= location.x;
			point.y -= location.y;
			
			if (point.x > lastLineRemainder * SCALED_100 && point.y > lines * SCALED_100) {
				point.x = (int) ((lastLineRemainder-1) * SCALED_100);
			}
			
			if(point.x > perLine * SCALED_100) {
				point.x = (int) ((point.x-1) * SCALED_100);
			}
			
			if (point.y > lines * SCALED_100) {
				point.y = (int) (lines * SCALED_100);
			}
			int trunkatedX = (int) (point.x - (point.x % SCALED_100));
			int trunkatedY = (int) (point.y - (point.y % SCALED_100));
			int itemX = (int) (trunkatedX / SCALED_100);
			int itemY = perLine * (int) (trunkatedY / SCALED_100);
			int itemNum = itemX + itemY;
			selectedItem = inventory.get(itemNum);
			selectedLoc = new Point(trunkatedX + location.x, trunkatedY + location.y);
		}
	}
}
