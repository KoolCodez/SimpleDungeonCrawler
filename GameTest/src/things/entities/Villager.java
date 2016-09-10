package things.entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Images;
import misc.SDC;
import things.items.Item;

public class Villager extends Entity {
	double SCALE_FACTOR = SDC.SCALE_FACTOR;
	public Villager() {
		stats.setStats(Math.random() * 10, Math.random() * 10, Math.random() * 10, Math.random() * 10);
		super.rarity = 100;
		super.setType("Villager");
		super.setName("Nameless Pawn");
		location = new Point2D.Double(randomLoc(), randomLoc());
		setImage(Images.loadImage("Characters\\Villager.png", 100.0, 100.0));
		this.setSize((int) (100 * SCALE_FACTOR), (int) (100 * SCALE_FACTOR));
		this.setLocation(location.getX(), location.getY());
	}
	
	@Override
	public void addOptions(JPanel panel) {
		JButton pokeButton = new JButton("POKE");
		pokeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Ouch! Stop That!");
			}
		});
		pokeButton.setBounds((int) (1000 * SDC.SCALE_FACTOR), (int) (500 * SDC.SCALE_FACTOR), SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		panel.add(pokeButton);
	}
	
	private double randomLoc() {
		return 500*SCALE_FACTOR + ((Math.random() * 400*SCALE_FACTOR)- 200*SCALE_FACTOR);
	}
}