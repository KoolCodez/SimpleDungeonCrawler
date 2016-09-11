package things;

import java.awt.Point;
import java.util.ArrayList;

import combatSystem.ControlRouter;
import misc.SDC;
import things.entities.BattleEntity;
import things.entities.Entity;
import things.items.weapons.Weapon;

public class BattleAI {
	private BattleEntity host;
	private ControlRouter control;
	private BattleEntity target;
	
	public BattleAI(BattleEntity h) {
		host = h;
	}
	
	public int nextMove(int turnPoints, ArrayList<BattleEntity> entList, 
			BattleEntity[][] entTable, ControlRouter c) {
		control = c;
		target = decideTarget(entList);
		Weapon hostWeapon = host.equipped.weapon;
		if (host.battleLoc.distance(target.battleLoc) > hostWeapon.reach) {
			if (turnPoints >= 3) {
				moveToward(entTable);
				turnPoints -= 3;
			} else {
				turnPoints = 0;
			}
		} else if (hostWeapon.speed <= turnPoints) {
			control.attack(host, target);
			turnPoints -= hostWeapon.speed;
		}
		return turnPoints;
	}
	
	private BattleEntity decideTarget(ArrayList<BattleEntity> entList) {
		for (int i = 0; i < entList.size(); i++) {
			BattleEntity ent  = entList.get(i);
			if (ent.getType().equals("Friendly") || ent.getType().equals("Character")) {
				return ent;
			}
		}
		return null;
	}
	
	public Point getTargetLoc() {
		return target.battleLoc;
	}
	
	private void moveToward(Entity[][] entTable) {
		int xDist = target.battleLoc.x - host.battleLoc.x;
		int yDist = target.battleLoc.y - host.battleLoc.y;
		Point destination = new Point(host.battleLoc.x, host.battleLoc.y);
		
		if (Math.abs(xDist) >= Math.abs(yDist) ) {
			int xAdj = 0;
			if (xDist != 0) {
			xAdj = 1* xDist / Math.abs(xDist);
			}
			
			if (entTable[destination.x + xAdj][destination.y] == null) {
				control.battleMove(xAdj, 0, host, 3);
				return;
				
			} else {
				int yAdj = 0;
				if (yDist != 0) {
					yAdj = 1* yDist / Math.abs(yDist);
				}
				
				if (entTable[destination.x][destination.y + yAdj] == null) {
					control.battleMove(0, yAdj, host, 3);
					return;
				} else if (entTable[destination.x][destination.y + yAdj] == null) {
					control.battleMove(0, yAdj*-1, host, 3);
					return;
				} else {
					yAdj = 1;
					if (entTable[destination.x][destination.y + yAdj] == null) {
						control.battleMove(0, yAdj, host, 3);
						return;
					} else if (entTable[destination.x][destination.y + yAdj] == null) {
						control.battleMove(0, yAdj*-1, host, 3);
						return;
					}
				}
				if (entTable[destination.x - xAdj][destination.y] == null) {
					control.battleMove(xAdj*-1, 0, host, 3);
					return;
				}
			}
		} else {
			int yAdj = 0;
			if (yDist != 0) {
			yAdj = 1* yDist / Math.abs(yDist);
			}
			
			if (entTable[destination.x][destination.y + yAdj] == null) {
				control.battleMove(0, yAdj, host, 3);
				return;
				
			} else {
				int xAdj = 0;
				if (xDist != 0) {
					xAdj = 1* xDist / Math.abs(xDist);
				}
				
				if (entTable[destination.x + xAdj][destination.y] == null) {
					control.battleMove(xAdj, 0, host, 3);
					return;
				} else if (entTable[destination.x + xAdj][destination.y] == null) {
					control.battleMove(xAdj*-1, 0, host, 3);
					return;
				} else {
					xAdj = 1;
					if (entTable[destination.x + xAdj][destination.y] == null) {
						control.battleMove(xAdj, 0, host, 3);
						return;
					} else if (entTable[destination.x + xAdj][destination.y] == null) {
						control.battleMove(xAdj*-1, 0, host, 3);
						return;
					}
				}
				if (entTable[destination.x][destination.y - yAdj] == null) {
					control.battleMove(0, yAdj*-1, host, 3);
					return;
				}
			}
		}
	}
}