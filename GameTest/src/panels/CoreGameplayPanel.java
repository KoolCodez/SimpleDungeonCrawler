package panels;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import combatSystem.Battle;
import misc.Images;
import misc.SimpleDungeonCrawler;
import movement.MovementController;

public class CoreGameplayPanel extends JPanel{

	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	private static int SCALED_100 = SimpleDungeonCrawler.SCALED_100;

	public CoreGameplayPanel() {
		MovementController movementController = new MovementController(this);
		createMenuButton();
		createAttackButton();
		setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.backgroundImg, 0, 0, null);
		g.drawImage(Images.charImg, (int) SimpleDungeonCrawler.character.getLocation().getX(),
				(int) SimpleDungeonCrawler.character.getLocation().getY(), null);
		g.drawImage(Images.rightArrow, (int) (948 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), null);
		g.drawImage(Images.leftArrow, (int) (0 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), null);
		g.drawImage(Images.bottomArrow, (int) (450 * SCALE_FACTOR), (int) (948 * SCALE_FACTOR), null);
		g.drawImage(Images.topArrow, (int) (450 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), null);
	}
	
	private void createMenuButton() {
		JButton menuButton = new JButton("PAUSE");
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDungeonCrawler.frame.getContentPane().remove(0);
				SimpleDungeonCrawler.frame.getContentPane().add(new PauseMenuPanel());
			}
		});
		menuButton.setBounds((int) (700 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuButton.setFont(SimpleDungeonCrawler.font);
		add(menuButton);
	}
	
	private void createAttackButton() {
		JButton atkButton = new JButton("ATTACK");
		atkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Component[] variables = SimpleDungeonCrawler.frame.getContentPane().getComponents();
				SimpleDungeonCrawler.frame.remove(0);
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
						SimpleDungeonCrawler.frame.invalidate();
						SimpleDungeonCrawler.frame.repaint();
				    }
				 });
				SimpleDungeonCrawler.frame.add(new AttackPanel());
				
			}
		});
		atkButton.setBounds((int) (700 * SCALE_FACTOR), (int) (100 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		atkButton.setFont(SimpleDungeonCrawler.font);
		add(atkButton);
	}

}
