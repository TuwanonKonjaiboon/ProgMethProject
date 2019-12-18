package asset;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class GameImage {
	
	public static int progress = 0;
	
	public static Image backgroundImage;
	public static Image topbarImage;
	
	public static Image platform1Image;
	public static Image platform2Image;
	public static ArrayList<Image> platform3Images;
	public static Image platform9Image;
	
	public static ArrayList<Image> dioImages;
	
	public static ArrayList<Image> monsterImages;
	
	public static void init() {
		
		backgroundImage = new Image(ClassLoader.getSystemResourceAsStream("bg.jpg"));
		
		// GameScene
		topbarImage = new Image(ClassLoader.getSystemResourceAsStream("images/topbar.png"));
		platform1Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-green.png"));
		platform2Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-blue.png"));
		platform3Images = new ArrayList<Image>();
		for (int i = 1; i <= 6; i++) {
			platform3Images.add(new Image(ClassLoader.getSystemResourceAsStream("images/brown/p-brown-" + i + ".png")));
		}
		platform9Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-white.png"));
	
		dioImages = new ArrayList<Image>();
		dioImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/Dio-1.png")));
		dioImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/Dio-4.png")));
		dioImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/Dio-5.png")));
		dioImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/Neko.png")));
		
		monsterImages = new ArrayList<Image>();
		monsterImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/star-1.png")));
		monsterImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/star-2.png")));
	}
	
}
