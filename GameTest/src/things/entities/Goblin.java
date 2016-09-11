package things.entities;

import java.awt.Image;

import misc.Images;
import misc.SDC;
import things.BattleAI;
import things.items.weapons.Club;

public class Goblin extends Entity {
	
	public Goblin() {
		super(5, 10, 10, 10, 60);
		setType("Enemy");
		equipped.weapon = new Club();
		//setSelectedEntity(SDC.character);
		setSize((int) (100 * SDC.SCALE_FACTOR), (int) (100 * SDC.SCALE_FACTOR));
		setImage(Images.loadImage("Enemies\\Goblin.png", 100.0, 100.0));
		Image deadIm = Images.loadImage("Enemies\\Skull.png", 100.0, 100.0);
		this.deadImage.setImage(deadIm);
	}
	
	@Override
	public void generateStats(int rarity) {
		double[] pointsPerStat = new double[4];
		int remaining = rarity;
		for (int i = 0; i < 3; i++) {
			double ratio = Math.random();
			pointsPerStat[i] = ratio * remaining;
			remaining -= ratio * remaining;
		}
		pointsPerStat[3] = remaining;
		double health = pointsPerStat[1];
		double strength = pointsPerStat[0];
		double dexterity = pointsPerStat[2];
		double willPower = pointsPerStat[3];
		stats.setStats(health, strength, dexterity, willPower);
	}
}
