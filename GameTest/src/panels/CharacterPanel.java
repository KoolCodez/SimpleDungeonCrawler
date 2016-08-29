package panels;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.SDC;
import things.entities.Entity;

public class CharacterPanel extends JPanel {
	private static double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SDC.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SDC.BUTTON_HEIGHT;
	Entity character = SDC.character;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("STRENGTH: " + character.stats.getStr(), 200, 100);
		g.drawString("DEXTERITY: " + character.stats.getDex(), 200, 150);
		g.drawString("WILLPOWER: " + character.stats.getWlp(), 200, 200);
	}

	public CharacterPanel() {
		createStatButtons();
		createExitButton();
		setLayout(null);
		
	}
	
	private void createExitButton() {
		JButton exitButton = new JButton("EXIT");
		// exit button
				exitButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						SDC.frame.getContentPane().add(new PauseMenuPanel());
					}
				});
				exitButton.setBounds(350, 100, 150, 50);
		add(exitButton);
	}

	private void createStatButtons() {
		JButton strUp = new JButton("+1");
		JButton strDown = new JButton("-1");
		JButton dexUp = new JButton("+1");
		JButton dexDown = new JButton("-1");
		JButton wlpUp = new JButton("+1");
		JButton wlpDown = new JButton("-1");

		add(strUp);
		add(strDown);
		add(dexUp);
		add(dexDown);
		add(wlpUp);
		add(wlpDown);
		
		strUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setStr(1);
			}
		});
		strUp.setBounds(200, 100, 50, 25);
		strDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setStr(-1);
			}
		});
		strDown.setBounds(250, 100, 50, 25);

		dexUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setDex(1);
			}
		});
		dexUp.setBounds(200, 150, 50, 25);
		dexDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setDex(-1);
			}
		});
		dexDown.setBounds(250, 150, 50, 25);

		wlpUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setWlp(1);
			}
		});
		wlpUp.setBounds(200, 200, 50, 25);
		wlpDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.stats.setWlp(-1);
			}
		});
		wlpDown.setBounds(250, 200, 50, 25);
	}
}
