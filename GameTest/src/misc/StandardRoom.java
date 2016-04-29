package misc;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StandardRoom {
	public static final String BATTLE_TAG = "battle";
	public static final String PUZZLE_TAG = "puzzle";
	public static final String TREASURE_TAG = "treasure";
	
	public String typeOfRoom;
	public List<Entity> entities;
	public StandardRoom() {
		entities = new ArrayList<Entity>();
	}
}
