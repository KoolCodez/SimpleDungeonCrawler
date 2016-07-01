package misc;

public class BattleAI {
	public static final String MOVE_TOWARD_TAG = "move toward";
	public static final String ATTACK_TAG = "attack";
	private Entity host;
	public BattleAI(Entity h) {
		host = h;
	}
	public String getNextMove() {
		if (host.getWeapon().reach > host.location.distance(host.getSelectedEntity().getLocation())) {
			return ATTACK_TAG;
		}
		return MOVE_TOWARD_TAG;
	}
}