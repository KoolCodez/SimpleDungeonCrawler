package misc;

import java.util.ArrayList;
import java.util.List;

public class BattleRoom extends StandardRoom {
	
	public BattleRoom(int enemies) {
		for (int i = 0; i < enemies; i++) {
			enemyEntities.add(new EnemyEntity(5, 1, 1, 1, 1, 1, 1));
		}
	}

}
