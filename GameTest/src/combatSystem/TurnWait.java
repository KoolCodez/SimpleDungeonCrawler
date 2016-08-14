package combatSystem;

public class TurnWait {
	public static final int DEFAULT_TURNPOINTS = 6;
	private int turnPoints = DEFAULT_TURNPOINTS;
	public int turnPointModifier = 0;
	public TurnWait() {
		
	}
	
	public void reset() {
		turnPoints = DEFAULT_TURNPOINTS + turnPointModifier;
	}
	
	public void changeTurnPoints(int deltaPoints) {
		turnPoints += deltaPoints;
		if (turnPoints == 0) {
			this.endTurn();
		}
	}
	
	public void setTurnPoints(int newPoints) {
		turnPoints = newPoints;
		if (turnPoints == 0) {
			this.endTurn();
		}
	}
	
	public int getTurnPoints() {
		return turnPoints;
	}
	
	public void endTurn() {
		synchronized (this) {
			this.notify();
		}
	}
}
