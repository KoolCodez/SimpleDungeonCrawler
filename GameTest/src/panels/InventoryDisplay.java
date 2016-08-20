package panels;

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
	private List<Item> inventory;
	private int size;
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
		startMouseListener();
		selectedLoc = new Point(0,0);
		parentPanel = parent;
	}
	
	public void drawInv(Graphics g) {
		Point image = new Point(location.x, location.y);
		for (int i = inventory.size() - 1; i >= 0; i--) {
			Item item = inventory.get(i);
			g.drawImage(item.inventoryImage.getImage(), image.x, image.y, null);
			g.setFont(new Font("Harrington", Font.BOLD, 18));
			g.drawString(item.itemName, image.x, image.y + (int) (100*SCALE));
			image.x += (int) (100*SCALE);
			if (image.x >= bounds.width) {
				image.x = location.x;
				image.y += (int) (100*SCALE);
			}
		}
		g.drawRect(selectedLoc.x, selectedLoc.y, (int) (100 * SCALE), (int) (100 * SCALE));
	}
	
	public void setInventory(List<Item> items) {
		inventory = items;
	}
	
	public void setRect(Rectangle rect) {
		bounds = rect;
		perLine = (int) (bounds.width / 100*SCALE);
		lines = size / perLine + 1;
		lastLineRemainder = size % lines - 1;
	}
	
	public void setLocation(Point point) {
		location = point;
	}

	private void startMouseListener() {
		SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
			@Override
			protected Integer doInBackground() throws Exception {
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
					System.out.println("clicked at " + point.toString());
					selectItem(point);
				}
			}
		};
		worker.execute();
	}

	private void selectItem(Point point) {
		if (point.x < location.x || point.y < location.y || point.x > location.x + bounds.width
				|| point.y > location.y + bounds.height) {
			System.out.println("Out of bounds");
		} else {
			point.x -= location.x;
			point.y -= location.y;
			
			
			if (point.y > lines * 100*SCALE) {
				point.y = (int) (lines * 100*SCALE);
			}
			if (point.x > lastLineRemainder * 100*SCALE) {
				point.x = (int) (lastLineRemainder * 100*SCALE);
			}
			
			int trunkatedX = (int) (point.x - (point.x % 100*SCALE));
			int trunkatedY = (int) (point.y - (point.y % 100*SCALE));
			int itemX = (int) (trunkatedX / SCALE);
			int itemY = perLine * (int) (trunkatedY / SCALE);
			int itemNum = itemX + itemY;
			selectedItem = inventory.get(itemNum);
			selectedLoc = new Point(trunkatedX + location.x, trunkatedY + location.y);
			System.out.println(selectedLoc.toString());
		}
	}
}
