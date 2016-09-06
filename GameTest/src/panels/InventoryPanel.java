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

import misc.MouseClick;
import misc.SDC;
import things.items.Item;
import things.items.Stick;
import things.items.armors.NullArmor;

public class InventoryPanel extends JPanel {
	private static final double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private static final int BUTTON_WIDTH = SDC.BUTTON_WIDTH;
	private static final int BUTTON_HEIGHT = SDC.BUTTON_HEIGHT;
	private InventoryDisplay display;
	private List<Item> inventory;

	public InventoryPanel() {
		setLayout(null);
		SDC.frame.add(this);
		inventory = SDC.character.getInventory();
		createExitButton();
		createAddStickButton();
		createDeleteItemButton();
		createEquipItemButton();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		display.drawInv(g);
	}
	
	public void createDisplay() {
		display = new InventoryDisplay();
		display.setInventory(SDC.character.getInventory());
		display.setRect((int) (0 * SDC.SCALE_FACTOR), (int) (0 * SDC.SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		List<Item> items = SDC.character.equipped.allEquipped();
		for (Item i : items) {
			System.out.println();
			if (inventory.contains(i)) {
				display.addSecondarySelected(inventory.indexOf(i));
			}
		}
		display.startMouseListener();
	}
	
	private void createDeleteItemButton() {
		JButton deleteItemButton = new JButton("DELETE ITEM");
		deleteItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Item> inventory = SDC.character.getInventory();
				inventory.remove(display.selectedItem);
			}
		});
		deleteItemButton.setBounds(BUTTON_WIDTH, (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		deleteItemButton.setFont(SDC.font);
		add(deleteItemButton);
	}
	
	private void createEquipItemButton() {
		JButton equipItemButton = new JButton("EQUIP ITEM");
		equipItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Item i = display.selectedItem;
				if (SDC.character.equipped.dequipItem(i)) {
					display.removeCurrentFromSecondarySelected();
				} else {
					Item deEquipped = SDC.character.equipped.equipItem(i);
					display.addCurrentToSecondarySelected();
					boolean isNullArmor = deEquipped instanceof NullArmor || deEquipped == null;
					if (!isNullArmor) {
						display.removeSecondarySelected(inventory.indexOf(deEquipped));
					}
				}
				SDC.character.refreshImage();
			}
		});
		equipItemButton.setBounds((int) (0 * SDC.SCALE_FACTOR), (int) (800 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		equipItemButton.setFont(SDC.font);
		add(equipItemButton);
	}

	private void createAddStickButton() {
		JButton addStick = new JButton("STICK ME");
		addStick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SDC.character.getInventory().size() < SDC.character.maxItems) {
					Stick stick = new Stick();
					stick.getInventoryImage();
					SDC.character.addItem(stick);
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
				setVisible(false);
				SDC.frame.getContentPane().add(new PauseMenuPanel());
			}
		});
		exitButton.setBounds((int) (700 * SCALE_FACTOR), (int) (900 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.setFont(SDC.font);
		add(exitButton);
	}

}
