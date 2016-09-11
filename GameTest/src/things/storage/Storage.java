package things.storage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import misc.MouseClick;
import misc.SDC;
import panels.InventoryDisplay;
import panels.PauseMenuPanel;
import panels.TransferPanel;
import things.Thing;
import things.entities.Entity;
import things.items.Item;

public class Storage extends Thing {
	private List<Item> inventory;
	private int maxCapacity;
	private Entity interactor;
	private InventoryDisplay display;
	private boolean displaying;
	
	public Storage(Image i, double x, double y, double w, double l, int rarity) {
		this.rarity = rarity;
		super.setSize(w, l);
		super.setLocation(x, y);
		super.setImage(i);
		inventory = new ArrayList<Item>();
		display = new InventoryDisplay();
		display.setInventory(inventory);
		display.setRect((int) (1000*SDC.SCALE_FACTOR), (int) (600 * SDC.SCALE_FACTOR), (int) (300*SDC.SCALE_FACTOR), (int) (300*SDC.SCALE_FACTOR));
		displaying = false;
	}
	
	@Override
	public void addOptions(JPanel panel) {
		panel.add(createTakeButton());
		panel.add(createTransferButton());
	}
	
	private JButton createTakeButton() {
		JButton takeButton = new JButton("TAKE");
		takeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Item item = display.selectedItem;
				if (item != null) {
					if (interactor.getInventory().size() < interactor.maxItems) {
						inventory.remove(item);
						interactor.getInventory().add(item);
					}
				}
			}
		});
		takeButton.setBounds((int) (1000 * SDC.SCALE_FACTOR), (int) (900 * SDC.SCALE_FACTOR), SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		return takeButton;
	}
	
	private JButton createTransferButton() {
		JButton transferButton = new JButton("TRANSFER");
		transferButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SDC.stopGameplayPanel();
				SDC.frame.add(new TransferPanel(interactor.getInventory(), interactor.maxItems, inventory, maxCapacity));
			}
		});
		transferButton.setBounds((int) (1000 * SDC.SCALE_FACTOR), (int) (500 * SDC.SCALE_FACTOR), SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		return transferButton;
	}
	
	public void interact(Entity interactor) {
		this.interactor = interactor;
	}
	
	public void setCapacity(int capacity) {
		maxCapacity = capacity;
	}
	
	public boolean addItem(Item item) {
		if (inventory.size() == maxCapacity) {
			System.out.println("Storage at max capacity");
			return false;
		}
		inventory.add(item);
		return true;
	}
	
	public void addAll(Collection<Item> items) {
		inventory.addAll(items);
	}
	
	public Item take(int index) {
		return inventory.remove(index);
	}
	
	public void displayOnSide(Graphics g) {
		super.displayOnSide(g);
		if (!displaying) {
			display.startMouseListener();
			displaying = true;
		}
		display.drawInv(g);
		g.setColor(Color.black);
		g.drawString("Storage Capacity: " + maxCapacity, (int) (1000 * SDC.SCALE_FACTOR), (int) (450 * SDC.SCALE_FACTOR));
	}
}
