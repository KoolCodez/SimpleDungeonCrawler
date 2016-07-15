package misc;

public class Goblin extends Entity {
	public BattleAI battleAI;
	public Goblin() {
		super(5, 10, 10, 10, 10, 10, 10, 1);
		battleAI = new BattleAI(this);
		setType("Enemy");
	}

}
