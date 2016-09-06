package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import misc.MouseClick;
import misc.SDC;
import things.items.Item;

public class InventoryDisplay {
	private static final int SCALED_100 = SDC.SCALED_100;
	private List<Item> inventory;
	private Rectangle bounds;
	public Item selectedItem;
	private Point selectedLoc;
	public List<Point> secondarySelected;
	JPanel parent;
	private static final double SCALE = SDC.SCALE_FACTOR;
	
	private int perLine;
	private int lines;
	private int lastLineRemainder;

	public InventoryDisplay() {
		//startMouseListener();
		selectedLoc = new Point(0,0);
		secondarySelected = new ArrayList<Point>();
	}
	
	public InventoryDisplay(List<Item> inv, Rectangle rect) {
		inventory = inv;
		bounds = rect;
		secondarySelected = new ArrayList<Point>();
	}
	
	public void addSecondarySelected(int invIndex) {
		int itemX = invIndex % perLine;
		int itemY;
		if (lines > 0) {
			itemY = invIndex / lines;
		} else {
			itemY = 0;
		}
		Point p = new Point ((int) (itemX * SCALED_100) + bounds.x, (int) (itemY * SCALED_100) + bounds.y);
		secondarySelected.add(p);
	}
	
	public void removeSecondarySelected(int invIndex) {
		int itemX = invIndex % this.perLine;
		int itemY;
		if (this.lines > 0) {
			itemY = invIndex / this.lines;
		} else {
			itemY = 0;
		}
		Point point = new Point ((int) (itemX * SCALED_100) + bounds.x, (int) (itemY * SCALED_100) + bounds.y);
		for (Point p : secondarySelected) {
			if (point.x == p.x && point.y == p.y) {
				secondarySelected.remove(p);
			} else {
				System.out.println("Item not secondary selected");
			}
		}
	}
	
	public void addCurrentToSecondarySelected() {
		secondarySelected.add(this.selectedLoc);
	}
	
	public void removeCurrentFromSecondarySelected() {
		for (Point p : secondarySelected) {
			if (p.x == selectedLoc.x && p.y == selectedLoc.y) {
				secondarySelected.remove(p);
			} else {
				System.out.println("Item not secondary selected");
			}
		}
	}
	
	public void drawInv(Graphics g) {
		Point image = new Point(bounds.x, bounds.y);
		for (int i = inventory.size() - 1; i >= 0; i--) {
			Item item = inventory.get(i);
			g.drawImage(item.inventoryImage.getImage(), image.x, image.y, null);
			g.setFont(new Font("Harrington", Font.BOLD, 18));
			g.drawString(item.itemName, image.x, image.y + SCALED_100);
			image.x += SCALED_100;
			if (image.x >= bounds.x + bounds.width) {
				image.x = bounds.x;
				image.y += SCALED_100;
			}
		}
		g.setColor(Color.black);
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		for (int i = 0; i < (int) (bounds.width / SCALED_100); i++) {
			g.drawLine(bounds.x + SCALED_100*i, bounds.y, bounds.x + SCALED_100*i, bounds.y + bounds.height);
		}
		for (int i = 0; i < (int) (bounds.height / SCALED_100); i++) {
			g.drawLine(bounds.x, bounds.y + SCALED_100*i, bounds.x + bounds.width, bounds.y + SCALED_100*i);
		}
		g.setColor(Color.CYAN);
		for (int i = 0; i < secondarySelected.size(); i++) {
			Point p = secondarySelected.get(i);
			g.fillRect(p.x, p.y, (int) (10 * SDC.SCALE_FACTOR), (int) (10 * SDC.SCALE_FACTOR));
		}
		g.setColor(Color.green);
		g.drawRect(selectedLoc.x, selectedLoc.y, SCALED_100, SCALED_100);
		refreshSelected();
	}
	
	private void refreshSelected() {
		Point trunkated = new Point(selectedLoc.x - bounds.x, selectedLoc.y - bounds.y);
		int itemX = (int) (trunkated.x / SCALED_100);
		int itemY = perLine * (int) (trunkated.y / SCALED_100);
		int itemNum = itemX + itemY;
		if (inventory.size() == 0) {
			selectedLoc = new Point(bounds.x, bounds.y);
			selectedItem = null;
			return;
		}
		if (itemNum >= inventory.size()) {
			itemNum -= 1;
			Point newPoint = new Point((int) ((itemNum % perLine) * SCALED_100),
					(int) (((itemNum - (itemNum % perLine)) / perLine)) * SCALED_100);
			newPoint.x += bounds.x;
			newPoint.y += bounds.y;
			selectedLoc = newPoint;
			//selectItem(newPoint);
		}
		selectItem(selectedLoc);
	}
	
	public void setInventory(List<Item> items) {
		inventory = items;
	}
	
	public void setRect(Rectangle rect) {
		bounds = rect;
		selectedLoc = new Point(bounds.x, bounds.y);
		refreshInvStats();
	}
	
	public void setRect(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
		selectedLoc = new Point(bounds.x, bounds.y);
		refreshInvStats();
	}

	public void startMouseListener() {
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					MouseClick mouse = new MouseClick();
					SDC.frame.getContentPane().addMouseListener(mouse);
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
		if (point.x < bounds.x || point.y < bounds.y || point.x > bounds.x + bounds.width
				|| point.y > bounds.y + bounds.height || inventory.size() <= 0) {
			//System.out.println("Out of bounds");
		} else {
			point.x -= bounds.x;
			point.y -= bounds.y;
			
			if (point.x >= lastLineRemainder * SCALED_100 && point.y >= (lines) * SCALED_100) {
				point.x = (int) ((lastLineRemainder-1) * SCALED_100);
			}
			
			if(point.x >= perLine * SCALED_100) {
				point.x = (int) ((point.x-1) * SCALED_100);
			}
			
			if (point.y >= lines * SCALED_100) {
				point.y = (int) (lines * SCALED_100);
			}
			int trunkatedX = (int) (point.x - (point.x % SCALED_100));
			int trunkatedY = (int) (point.y - (point.y % SCALED_100));
			int itemX = (int) (trunkatedX / SCALED_100);
			int itemY = perLine * (int) (trunkatedY / SCALED_100);
			int itemNum = itemX + itemY;
			if (itemNum < 0) {
				itemNum = 0;
			}
			selectedItem = inventory.get(inventory.size() - (itemNum+1));
			selectedLoc = new Point(trunkatedX + bounds.x, trunkatedY + bounds.y);
		}
	}
}
