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
	private InventoryDisplay display;

	public InventoryPanel() {
		setLayout(null);
		SDC.frame.add(this);
		
		createExitButton();
		createAddStickButton();
		createDeleteItemButton();
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
