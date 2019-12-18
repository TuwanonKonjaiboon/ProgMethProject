package asset;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class GameImage {
	
	public static int progress = 0;
	
	public static Image backgroundImage;
	public static Image topbarImage;
	public static Image logo;
	public static Image DioMenuSceneImage;
	public static Image GoGoEffectImage;
	
	public static Image platform1Image;
	public static Image platform2Image;
	public static ArrayList<Image> platform3Images;
	public static Image platform9Image;
	
	public static ArrayList<Image> dioImages;
	
	public static ArrayList<Image> monsterImages;
	public static Image knifeImage;
	
	public static void init() {
		
		backgroundImage = new Image(ClassLoader.getSystemResourceAsStream("bg.jpg"));
		
		// GameScene
		topbarImage = new Image(ClassLoader.getSystemResourceAsStream("images/topbar.png"));
		progress++;
		platform1Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-green.png"));
		progress++;
		platform2Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-blue.png"));
		progress++;
		platform3Images = new ArrayList<Image>();
		for (int i = 1; i <= 6; i++) {
			platform3Images.add(new Image(ClassLoader.getSystemResourceAsStream("images/brown/p-brown-" + i + ".png")));
			progress++;
		}
		platform9Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-white.png"));
		progress++;
		
		dioImages = new ArrayList<Image>();
		dioImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/Dio-1.png")));
		progress++;
		dioImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/Dio-4.png")));
		progress++;
		dioImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/Dio-5.png")));
		progress++;
		dioImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/Neko.png")));
		progress++;
		
		monsterImages = new ArrayList<Image>();
		progress++;
		monsterImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/star-1.png")));
		progress++;
		monsterImages.add(new Image(ClassLoader.getSystemResourceAsStream("images/star-2.png")));
		progress++;
		
		logo = new Image(ClassLoader.getSystemResourceAsStream("JojoTitle.png"));
		progress++;
		
		DioMenuSceneImage = new Image(ClassLoader.getSystemResourceAsStream("images/Dio-2.png"));
		progress++;
		
		GoGoEffectImage = new Image(ClassLoader.getSystemResourceAsStream("images/effect2.png"));
		progress++;
		
		knifeImage = new Image(ClassLoader.getSystemResourceAsStream("images/knife/knife.png"));
	}
	
}
