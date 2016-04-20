package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;

import items.GenericItem;
import items.Stick;
import misc.Images;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;
import movement.MoveDown;
import movement.MoveDownLeft;
import movement.MoveDownRight;
import movement.MoveLeft;
import movement.MoveRight;
import movement.MoveUp;
import movement.MoveUpLeft;
import movement.MoveUpRight;

public class Panels {
	
	public static JFrame frame;
	public static JPanel coreGameplayPanel;
	public static JPanel menuPanel;
	public static JPanel atkPanel;
	public static JPanel turnPanel;
	public static JPanel invPanel;
	public static JPanel charPanel;
	public static JPanel mainMenu;
	public static JPanel bagPanel;
	static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	public static final int SCALED_100 = (int) (100*SCALE_FACTOR);
	public static final int MENU_SIZE = (int) (1000*SCALE_FACTOR);
	public static final int BUTTON_HEIGHT = (int) (100*SCALE_FACTOR);
	public static final int BUTTON_WIDTH = (int) (300*SCALE_FACTOR);
	
	public Panels() {
		// initializing frame stuff
				MainMenu mainMenuInitializer = new MainMenu();
				mainMenuInitializer.createMainMenu();
				AttackPanel attackPanel = new AttackPanel();
				attackPanel.createAtkPanel();
				PauseMenuPanel pauseMenu = new PauseMenuPanel();
				pauseMenu.createPauseMenu();
				InventoryPanel inventoryPanel = new InventoryPanel();
				inventoryPanel.createInventory();
				CharacterPanel characterPanel = new CharacterPanel();
				characterPanel.createCharScreen();
				frame.getContentPane().add(mainMenu);
	}
}
