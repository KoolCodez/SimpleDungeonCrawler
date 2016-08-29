package panels;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.SDC;
import things.items.Item;

public class TransferPanel extends JPanel {
	
	private List<Item> inv1;
	private int inv1Max;
	private List<Item> inv2;
	private int inv2Max;
	private InventoryDisplay inv1Display;
	private InventoryDisplay inv2Display;
	
	public TransferPanel(List<Item> inventory1, int max1, List<Item> inventory2, int max2) {
		setLayout(null);
		inv1 = inventory1;
		inv1Max = max1;
		inv2 = inventory2;
		inv2Max = max2;
		createDisplay();
		createPutButton();
		createTakeButton();
		createExitButton();
	}
	
	private void createDisplay() {
		inv1Display = new InventoryDisplay();
		inv1Display.setInventory(inv1);
		inv1Display.setRect((int) (0 * SDC.SCALE_FACTOR), (int) (0 * SDC.SCALE_FACTOR), (int) (400*SDC.SCALE_FACTOR), (int) (1000*SDC.SCALE_FACTOR));
		inv1Display.startMouseListener();
		
		inv2Display = new InventoryDisplay();
		inv2Display.setInventory(inv2);
		inv2Display.setRect((int) (600 * SDC.SCALE_FACTOR), (int) (0 * SDC.SCALE_FACTOR), (int) (400*SDC.SCALE_FACTOR), (int) (1000*SDC.SCALE_FACTOR));
		inv2Display.startMouseListener();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		inv1Display.drawInv(g);
		inv2Display.drawInv(g);
	}
	
	private void createPutButton() {
		JButton putButton = new JButton("PUT ITEM");
		putButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inv1Display.selectedItem != null && inv2.size() + 1 < inv2Max) {
					inv1.remove(inv1Display.selectedItem);
					inv2.add(inv1Display.selectedItem);
				}
			}
		});
		putButton.setBounds((int) (0 * SDC.SCALE_FACTOR), (int) (900 * SDC.SCALE_FACTOR), SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		putButton.setFont(SDC.font);
		add(putButton);
	}
	
	private void createTakeButton() {
		JButton takeButton = new JButton("TAKE ITEM");
		takeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inv2Display.selectedItem != null && inv1.size() + 1 < inv1Max) {
					inv2.remove(inv2Display.selectedItem);
					inv1.add(inv2Display.selectedItem);
				}
			}
		});
		takeButton.setBounds((int) (700 * SDC.SCALE_FACTOR), (int) (900 * SDC.SCALE_FACTOR), SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		takeButton.setFont(SDC.font);
		add(takeButton);
	}
	
	private void createExitButton() {
		JButton exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SDC.frame.add(new CoreGameplayPanel());
			}
		});
		exitButton.setBounds((int) (350 * SDC.SCALE_FACTOR), (int) (900 * SDC.SCALE_FACTOR), SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		exitButton.setFont(SDC.font);
		add(exitButton);
	}

}
