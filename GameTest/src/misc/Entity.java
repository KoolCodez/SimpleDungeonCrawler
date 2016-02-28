package misc;
import java.awt.Point;
import java.util.Random;

public class Entity { //extend this class with specific entity-classes.
	public String entityType;
	public Point entityLocation;
	public String[] entityInventory = new String[5];
	Random rand = new Random();
	public Entity() {
		entityType = "Generic Entity";
		entityLocation = new Point(250 - rand.nextInt(), 250 + rand.nextInt());
	}
	
}
