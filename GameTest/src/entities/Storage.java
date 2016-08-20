package entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import items.Item;
import misc.MouseClick;
import misc.SDC;
import panels.PauseMenuPanel;

public class Storage extends Thing {
	private List<Item> inventory;
	private Entity interactor;
	private Item selectedItem;
	private Point selectedLoc;
	
	public Storage(Image i, double x, double y, int w, int l) {
		super(x, y, w, l);
		super.setImage(i);
		inventory = new ArrayList<Item>();
		selectedLoc = new Point(0,0);
	}
	
	@Override
	public void addOptions(JPanel panel) {
		panel.add(createTakeButton());
		panel.add(createPutButton());
		MouseClick mouse = new MouseClick();
		SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
			@Override
			protected Integer doInBackground() throws Exception {
				while (true) {
					MouseClick mouse = new MouseClick();
					panel.addMouseListener(mouse);
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
			}
		};
		worker.execute();
	}
	
	private void selectItem(Point point) {
		point.x -= 1000 * SDC.SCALE_FACTOR;
		point.y -= 500 * SDC.SCALE_FACTOR;
		int trunkatedX = point.x - (point.x % SDC.SCALED_100);
		int trunkatedY = point.y - (point.y % (int) (100 * SDC.SCALE_FACTOR));
		int itemX = trunkatedX / SDC.SCALED_100;
		int itemY = 9 * (trunkatedY / (int) (SDC.SCALE_FACTOR * 140));
		int selectedItemNumber = itemX + itemY;
		selectedItem = inventory.get(selectedItemNumber);
		selectedLoc = new Point((int) (trunkatedX + 1000*SDC.SCALE_FACTOR), (int) (trunkatedY + 500*SDC.SCALE_FACTOR));
	}
	
	private JButton createTakeButton() {
		JButton takeButton = new JButton("TAKE");
		takeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		takeButton.setBounds((int) (1000 * SDC.SCALE_FACTOR), (int) (900 * SDC.SCALE_FACTOR), SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		return takeButton;
	}
	
	private JButton createPutButton() {
		JButton putButton = new JButton("PUT");
		putButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		putButton.setBounds((int) (700 * SDC.SCALE_FACTOR), (int) (900 * SDC.SCALE_FACTOR), SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		return putButton;
	}
	
	public void interact(Entity interactor) {
		this.interactor = interactor;
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}
	
	public void addAll(Collection<Item> items) {
		inventory.addAll(items);
	}
	
	public Item take(int index) {
		return inventory.remove(index);
	}
	
	public void displayOnSide(Graphics g) {
		double scale = SDC.SCALE_FACTOR;
		super.displayOnSide(g);
		Point loc = new Point((int) (1000 * scale), (int) (500 * scale));
		for (Item i : inventory) {
			g.drawImage(i.getImage(), loc.x, loc.y, null);
			if (loc.x >= 1200 * scale) {
				loc.x = (int) (1000 * scale);
				loc.y += (int) (100 * scale);	
			} else {
				loc.x += (int) (100 * scale);
			}
		}
	}
}
