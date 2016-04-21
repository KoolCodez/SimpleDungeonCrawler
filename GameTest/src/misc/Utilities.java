package misc;

import java.util.Random;

public class Utilities {
	public static int r20() {
		return new Random().nextInt(20) + 1;
	}

	public static int r6() {
		return new Random().nextInt(6) + 1;
	}
}
