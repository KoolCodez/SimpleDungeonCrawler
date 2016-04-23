package panels;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Entity;
import misc.SimpleDungeonCrawler;

public class CharacterPanel extends JPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	Entity character = SimpleDungeonCrawler.character;
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawString("STRENGTH: " + character.stats.getStr(), 200, 100);
		g.drawString("DEXTERITY: " + character.stats.getDex(), 200, 150);
		g.drawString("CONSTITUTION: " + character.stats.getCon(), 200, 200);
		g.drawString("WISDOM: " + character.stats.getWis(), 200, 250);
		g.drawString("INTELLIGENCE: " + character.stats.getIntl(), 200, 300);
		g.drawString("CHARISMA: " + character.stats.getChr(), 200, 350);
	}
	
	public CharacterPanel() {
		
		JButton exitButton = new JButton("EXIT");
		JButton setStr = new JButton("+1");
		JButton strDown = new JButton("-1");
		JButton setDex = new JButton("+1");
		JButton dexDown = new JButton("-1");
		JButton setCon = new JButton("+1");
		JButton conDown = new JButton("-1");
		JButton setWis = new JButton("+1");
		JButton wisDown = new JButton("-1");
		JButton intUp = new JButton("+1");
		JButton intDown = new JButton("-1");
		JButton setChr = new JButton("+1");
		JButton chrDown = new JButton("-1");
		// attack panel
		add(exitButton);
		add(setStr);
		add(strDown);
		add(setDex);
		add(dexDown);
		add(setCon);
		add(conDown);
		add(setWis);
		add(wisDown);
		add(intUp);
		add(intDown);
		add(setChr);
		add(chrDown);
		setLayout(null);

		// level buttons
		setStr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setStr(1);
			}
		});
		setStr.setBounds(200, 100, 50, 25);
		strDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setStr(-1);
			}
		});
		strDown.setBounds(250, 100, 50, 25);

		setDex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setDex(1);
			}
		});
		setDex.setBounds(200, 150, 50, 25);
		dexDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setDex(-1);
			}
		});
		dexDown.setBounds(250, 150, 50, 25);

		setCon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setCon(1);
			}
		});
		setCon.setBounds(200, 200, 50, 25);
		conDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setCon(-1);
			}
		});
		conDown.setBounds(250, 200, 50, 25);

		setWis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setWis(1);
			}
		});
		setWis.setBounds(200, 250, 50, 25);
		wisDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setWis(-1);
			}
		});
		wisDown.setBounds(250, 250, 50, 25);

		intUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setIntl(1);
			}
		});
		intUp.setBounds(200, 300, 50, 25);
		intDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setIntl(-1);
			}
		});
		intDown.setBounds(250, 300, 50, 25);

		setChr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setChr(1);
			}
		});
		setChr.setBounds(200, 350, 50, 25);
		intDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setChr(-1);
			}
		});
		chrDown.setBounds(250, 350, 50, 25);

		// exit button
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDungeonCrawler.frame.getContentPane().removeAll();
				SimpleDungeonCrawler.frame.getContentPane().add(new PauseMenuPanel());
			}
		});
		exitButton.setBounds(350, 100, 150, 50);

	}

}
