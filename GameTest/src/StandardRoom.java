import java.util.Random;

public class StandardRoom {
	public String typeOfRoom;
	Random rand = new Random();
	public StandardRoom() {
		int typeNum = rand.nextInt(10);
		if (typeNum == 0 || typeNum == 1 || typeNum == 2) {
			typeOfRoom = "puzzle";
		} else if(typeNum == 3 || typeNum == 4 || typeNum == 5 || typeNum == 6 || typeNum == 7) {
			typeOfRoom = "battle";
		} else if(typeNum == 8 || typeNum == 9) {
			typeOfRoom = "treasure";
		}
	}
}
