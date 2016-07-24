package combatSystem;

public class TurnWait {
	private int turnPoints = 5;
	public TurnWait() {
		
	}
	
	public void reset() {
		turnPoints = 5;
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
