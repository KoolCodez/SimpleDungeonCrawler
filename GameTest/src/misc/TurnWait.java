package misc;

public class TurnWait {
	public TurnWait() {
		
	}
	
	public void endTurn() {
		synchronized (this) {
			this.notify();
		}
	}
}
