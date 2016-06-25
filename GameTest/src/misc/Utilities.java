package misc;

import java.io.Serializable;
import java.util.Random;

public class Utilities implements Serializable {
	public static int r20() {
		return new Random().nextInt(20) + 1;
	}

	public static int r6() {
		return new Random().nextInt(6) + 1;
	}
}
