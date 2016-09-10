package misc;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Images {
	//Arrows\\
	public static final int rightArrowOnIndex = 1;
	public static final int leftArrowOnIndex = 2;
	public static final int bottomArrowOnIndex = 3;
	public static final int topArrowOnIndex = 4;
	public static final int rightArrowOffIndex = 5;
	public static final int leftArrowOffIndex = 6;
	public static final int bottomArrowOffIndex = 7;
	public static final int topArrowOffIndex = 8;
	//Buttons\\
	public static final int bagButtonIndex = 9;
	public static final int fightButtonIndex = 10;
	public static final int fleeButtonIndex = 11;
	public static final int moveButtonIndex = 12;
	public static final int resumeButtonIndex = 13;
	//Characters\\
	
	public static final int charFrontIndex = 14;
	public static final int charLeftIndex = 15;
	public static final int charRightIndex = 16;
	public static final int charLeftOpArmIndex = 17;
	public static final int charRightOpArmIndex = 18;
	public static final int battleCharIndex = 19;
	//Enemies\\
	public static final int battleGoblinIndex = 20;
	//Grounds\\
	public static final int backgroundImgIndex = 21;
	public static final int battleViewBackgroundIndex = 30;
	//Items\\
	public static final int stickItemIndex = 22;
	public static final int stickItem2Index = 23;
	public static final int stickItem3Index = 24;
	public static final int stickItem4Index = 25;
	public static final int stickItem5Index = 26;
	public static final int headArmor1Index = 31;
	public static final int blankLayerIndex = 32;
	//Menus\\
	public static final int battleMenuIndex = 27;
	public static final int mainMenuIndex = 28;
	public static final int pauseMenuIndex = 29;
	
	public static Image[] array;
	public static int rightArrowIndex;
	public static int leftArrowIndex;
	public static int bottomArrowIndex;
	public static int topArrowIndex;
	private static String currentDir = System.getProperty("user.dir");
	private static double scale = SDC.SCALE_FACTOR;
	
	public static Image loadImage(String pathFromTextures, double widthUnscaled, double heightUnscaled) {
		Image i;
		try {
			String name = currentDir + "\\src\\misc\\Textures\\" + pathFromTextures;
//			System.out.println(name);
			System.out.println(System.getProperty("java.class.path"));
		i = ImageIO.read(new File(name));
		
//		InputStream is = Image.class.getResourceAsStream("BlankLayer.png");
//		i = ImageIO.read(is);
		
		i = ImageIO.read(Image.class.getClassLoader().getResource("/BlankLayer.png"));
		
		} catch (IOException e) {
			e.printStackTrace();
			return array[blankLayerIndex];
		}
		i = i.getScaledInstance((int) (widthUnscaled * SDC.SCALE_FACTOR), (int) (heightUnscaled * SDC.SCALE_FACTOR), Image.SCALE_SMOOTH);
		return i;
	}
	
	public static Image loadImage(String pathFromTextures, int widthScaled, int heightScaled) {
		Image i;
		try {
			String name = currentDir + "\\src\\Textures\\" + pathFromTextures;
//			System.out.println(name);
		i = ImageIO.read(new File(name));
		} catch (IOException e) {
			e.printStackTrace();
			return array[blankLayerIndex];
		}
		i = i.getScaledInstance(widthScaled, heightScaled, Image.SCALE_SMOOTH);
		return i;
	}
	
	public static void createImages() throws IOException {
		array = new Image[33];
		arrows();
		buttons();
		characters();
		enemies();
		grounds();
		items();
		menus();
	}
	
	public static void arrows() throws IOException {
		String localDir = "Arrows\\";
		
		Image rightArrowOn = loadImage(localDir + "RightArrowOn.jpg", 50.0, 100.0);
		array[rightArrowOnIndex] = rightArrowOn;
		Image leftArrowOn = loadImage(localDir + "LeftArrowOn.jpg", 50.0, 100.0);
		array[leftArrowOnIndex] = leftArrowOn;
		Image bottomArrowOn = loadImage(localDir + "BotArrowOn.jpg", 100.0, 50.0);
		array[bottomArrowOnIndex] = bottomArrowOn;
		Image topArrowOn = loadImage(localDir + "TopArrowOn.jpg", 100.0, 50.0);
		array[topArrowOnIndex] = topArrowOn;
		Image rightArrowOff = loadImage(localDir + "RightArrowOff.jpg", 50.0, 100.0);
		array[rightArrowOffIndex] = rightArrowOff;
		Image leftArrowOff = loadImage(localDir + "LeftArrowOff.jpg", 50.0, 100.0);
		array[leftArrowOffIndex] = leftArrowOff;
		Image bottomArrowOff = loadImage(localDir + "BotArrowOff.jpg", 100.0, 50.0);
		array[bottomArrowOffIndex] = bottomArrowOff;
		Image topArrowOff = loadImage(localDir + "TopArrowOff.jpg", 100.0, 50.0);
		array[topArrowOffIndex] = topArrowOff;
		
		rightArrowIndex = rightArrowOnIndex;
		
		leftArrowIndex = leftArrowOffIndex; // starts off
		
		bottomArrowIndex = bottomArrowOnIndex;
		
		topArrowIndex = topArrowOffIndex; // starts off
		
	}
	
	public static void buttons() throws IOException {
		String localDir = "Buttons\\";
		
		Image bagButton = loadImage(localDir + "BagButton2x.jpg", 300.0, 100.0);
		array[bagButtonIndex] = bagButton;
		Image fightButton = loadImage(localDir + "FightButton2x.jpg", 300.0, 100.0);
		array[fightButtonIndex] =fightButton;
		Image fleeButton = loadImage(localDir + "FleeButton2x.jpg", 300.0, 100.0);
		array[fleeButtonIndex] = fleeButton;
		Image moveButton = loadImage(localDir + "MoveButton2x.jpg", 300.0, 100.0);
		array[moveButtonIndex] = moveButton;
		Image resumeButton = loadImage(localDir + "ResumeButton2x.jpg", 300.0, 100.0);
		array[resumeButtonIndex] = resumeButton;
	}
	
	public static void characters() throws IOException {
		String localDir = "Characters\\";
		
//		Image charFront = ImageIO.read(new File("src\\Textures\\Characters\\MainCharFront.jpg"));
//		charFront = charFront.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
//		array[charFrontIndex] = charFront;
		
		Image mainChar = loadImage(localDir + "MainChar.png", 100.0, 100.0);
		array[battleCharIndex] = mainChar;
	}
	
	public static void enemies() throws IOException {
		String localDir = "Enemies\\";
		Image battleGoblin = loadImage(localDir + "BattleGoblin.jpg", 72.0, 100.0);
		array[battleGoblinIndex] = battleGoblin;
	}
	
	public static void grounds() throws IOException { //I AM THE GROUNDS KEEPER
		String localDir = "Grounds\\";
		
		Image backgroundImg = loadImage(localDir + "BasicGround.jpg", 1000.0, 1000.0);
		array[backgroundImgIndex] = backgroundImg;
		Image battleViewBackground = loadImage(localDir + "BasicGround.jpg", 697.0, 710.0);
		array[battleViewBackgroundIndex] = battleViewBackground;
	}
	
	public static void items() throws IOException {
		String localDir = "Items\\";
		
		Image stickItem = loadImage(localDir + "Stick.jpg", 100.0, 100.0);
		array[stickItemIndex] = stickItem;
		Image stickItem2 = loadImage(localDir + "Stick2.jpg", 100.0, 100.0);
		array[stickItem2Index] = stickItem2;
		Image stickItem3 = loadImage(localDir + "Stick3.jpg", 100.0, 100.0);
		array[stickItem3Index] = stickItem3;
		Image stickItem4 = loadImage(localDir + "Stick4.jpg", 100.0, 100.0);
		array[stickItem4Index] = stickItem4;
		Image stickItem5 = loadImage(localDir + "Stick5.jpg", 100.0, 100.0);
		array[stickItem5Index] = stickItem5;
		Image headArmor1 = loadImage(localDir + "headArmor.png", 100.0, 100.0);
		array[headArmor1Index] = headArmor1;
		Image blankLayer = loadImage(localDir + "BlankLayer.png", 100.0, 100.0);
		array[blankLayerIndex] = blankLayer;
	}
	
	public static void menus() throws IOException {
		String localDir = "Menus\\";
		Image battleMenu = loadImage(localDir + "BattleMenu2.jpg", 1000.0, 1000.0);
		array[battleMenuIndex] = battleMenu;
		Image mainMenu = loadImage(localDir + "BrickStone.jpg", 1000.0, 1000.0);
		array[mainMenuIndex] = mainMenu;
		Image pauseMenu = loadImage(localDir + "PauseMenu.jpg", 1000.0, 1000.0);
		array[pauseMenuIndex] = pauseMenu;
	}
}
