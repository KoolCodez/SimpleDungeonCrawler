package panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Images;
import misc.SDC;

public class PauseMenuPanel extends JPanel {
	private static double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SDC.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SDC.BUTTON_HEIGHT;
	private static int SCALED_100 = SDC.SCALED_100;
	private static Font font = SDC.font;
	Point menuCoord = new Point((int) (350*SCALE_FACTOR), (int) (250*SCALE_FACTOR));
	
	public PauseMenuPanel() {
		createResumeButton();
		createInventoryButton();
		createCharacterButton();
		createSaveButton();
		createExitButton();
		setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.array[Images.pauseMenuIndex], 0, 0, null);
	}
	
	private void createExitButton() {
		JButton exitButton = new JButton("QUIT");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SDC.frame.add(new MainMenu());
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.setFont(font);
		add(exitButton);
	}
	
	private void createSaveButton() {
		JButton saveButton = new JButton("SAVE");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SDC.saveAllImportantStuff();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		saveButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		saveButton.setFont(font);
		add(saveButton);
	}
	
	private void createInventoryButton() {
		JButton invButton = new JButton("INVENTORY");
		invButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				InventoryPanel inventoryPanel = new InventoryPanel();
				inventoryPanel.createDisplay();
				//SDC.frame.add(inventoryPanel);
				/*InventoryDisplay display = new InventoryDisplay();
				display.setInventory(SDC.character.getInventory());
				display.setRect(new Rectangle(0, 0, (int) (900*SCALE_FACTOR), (int) (900*SCALE_FACTOR)));
				display.setLocation(new Point(0, 0));*/
				//SDC.frame.add(display);
			}
		});
		invButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		invButton.setFont(font);
		add(invButton);
	}
	
	private void createCharacterButton() {
		JButton charButton = new JButton("CHARACTER");
		charButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SDC.frame.add(new CharacterPanel());
			}
		});
		charButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		charButton.setFont(font);
		add(charButton);
	}
	
	private void createResumeButton() {
		JButton resume = new JButton();
		resume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SDC.frame.add(new CoreGameplayPanel());
			}
		});
		resume.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		resume.setIcon(new ImageIcon(Images.array[Images.resumeButtonIndex]));
		menuCoord.y += BUTTON_HEIGHT;
		resume.setFont(font);
		add(resume);
	}
}
