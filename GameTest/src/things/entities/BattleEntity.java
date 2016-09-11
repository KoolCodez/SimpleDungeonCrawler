package things.entities;

import java.awt.Graphics;
import java.awt.Point;

import misc.SDC;
import things.BattleAI;
import things.Thing;

public class BattleEntity extends Entity {
	
	private Entity host;
	public BattleAI battleAI;
	public Point battleLoc;
	
	public BattleEntity(Entity host) {
		super(host.stats.getMaxHealth(), host.stats.getStr(), host.stats.getDex(), host.stats.getWlp(), host.rarity);
		setImage(host.getImage());
		setLocation(host.outline.getX(), host.outline.getY());
		setSize(host.outline.getWidth(), host.outline.getHeight());
		this.setType(host.getType());
		this.setName(host.getName());
		battleLoc = new Point();
		this.host = host;
		battleAI = new BattleAI(this);
		equipped.head = host.equipped.head;
		equipped.body = host.equipped.body;
		equipped.legs = host.equipped.legs;
		equipped.feet = host.equipped.feet;
		equipped.hands = host.equipped.hands;
		equipped.weapon = host.equipped.weapon;
	}
	
	public void setBattleLoc(Point loc) {
		battleLoc = loc;
		double size = 140 * SDC.SCALE_FACTOR;
		double xOffset = (size - outline.getWidth()) / 2;
		double yOffset = (size - outline.getHeight()) / 2;
		setLocation(battleLoc.getX()*size + xOffset, battleLoc.getY()*size + yOffset);
	}
}