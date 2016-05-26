package combatSystem;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import items.GenericWeapon;
import misc.Images;
import misc.SimpleDungeonCrawler;
import panels.BattleTurnPanel;

public class PlayerTurnPhase {
	public TurnWait waitForTurn = new TurnWait();
	
	public PlayerTurnPhase(BattleTurnPanel battleTurnPanel) {
		waitForTurn.reset();
		setDefaultWeapon();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.addButtonsToTurnPanel();
			}
		});
	}
	
	private void letPlayerTakeTurn() {
		synchronized (waitForTurn) {
			try {
				System.out.println("wait");
				waitForTurn.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setDefaultWeapon() { //TODO this is temporary, should go away when inventory is fixed
		GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.array[Images.stickItemIndex]), "weapon");
		weapon.damage = 1.0;
		weapon.ranged = false;
		weapon.speed = 1.0;
		SimpleDungeonCrawler.character.setWeapon(weapon);
	}
	
}
