package scene;

import java.util.ArrayList;
import java.util.HashMap;

import application.Character;
import application.Platform;
import application.Settings;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class GameScene {
	
	public static final int BLOCK_SIZE = 68;
	
	public static Scene scene;
	public static AnimationTimer gameloop;
	public static ArrayList<Platform> platforms = new ArrayList<Platform>();
	public static ArrayList<Character> monsters = new ArrayList<Character>();
	public static HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
	
	public static Platform lastHitPlatform = null;
	public static boolean samePlatform = false;
	
	private static Pane appRoot = new Pane();
	private static Pane gameRoot = new Pane();
	private static ImageView background = new ImageView(new Image(ClassLoader.getSystemResourceAsStream("images/bg-grid.png")));
	
	private static Character player;
	public static int score = 0;
	public static int level = 0;
	
	
	public static void init() {
		// Insert Background Image
		background.setFitHeight(Settings.SCENE_HEIGHT);
		background.setFitWidth(Settings.SCENE_WIDTH);
		
		int shift = 550;
		int min = 0;
		int max = 100;
		for (int i = 0; i < 20; i++) {
			shift -= min + (int)(Math.random() * ((max - min) + 1));
			Platform plat1 = new Platform(1, (int) (Math.random() * 6 * 58), shift, 58, 15);
			platforms.add(plat1);
		}
				
		int xPosition = clamp((int) (Math.random() * 400), 0, Settings.SCENE_WIDTH - BLOCK_SIZE);
		int yPosition = 500;
				
		platforms.add(new Platform(1, xPosition, yPosition, 58, 15));
				
		player = new Character();
		player.setTranslateX(xPosition);
		player.setTranslateY(500);
		
		gameRoot.getChildren().addAll(platforms);
		gameRoot.getChildren().add(player);
		gameRoot.setMinHeight(Settings.SCENE_HEIGHT);
		gameRoot.setMinWidth(Settings.SCENE_WIDTH);
		appRoot.getChildren().addAll(background, gameRoot);
		
		scene = new Scene(appRoot, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		scene.setOnKeyPressed(event -> {
			keys.put(event.getCode(), true);
		});
		scene.setOnKeyReleased(event -> {
			keys.put(event.getCode(), false);
		});
		
		gameloop = new AnimationTimer() {
			
			// 30 FPS
			int count = 0;
			
			@Override
			public void handle(long arg0) {
				if (count > 1) {
					updatePlayer();
					updatePlatform();
					System.out.println(score);
					count = 0;
				}
				count ++;
			}
		};
	}
	
	public static int clamp(int value, int min, int max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	
	public static boolean isPress(KeyCode key) {
		return keys.getOrDefault(key, false);
	}
	
	private static void updatePlayer() {
		
		if (isPress(KeyCode.RIGHT)) {
			player.setScaleX(1);
			player.moveX(10);
		}
		if (isPress(KeyCode.LEFT)) {
			player.setScaleX(-1);
			player.moveX(-10);
		}
		player.jumpPlayer();
		
		if (player.playerVelocity.getY() < 15) {
			player.playerVelocity = player.playerVelocity.add(0, 2);
		}
		if (player.isFalls()) {
			player.jumpPlayer();
			player.setCanJump(true);
		}
		System.out.println(player);
		
		player.moveY((int) player.playerVelocity.getY());
	}
	
	private static void updatePlatform() {
		if (!player.isMovingDown() && player.getTranslateY() <= 249) {
			for (Platform platform : platforms) {
				platform.moveY((int) player.playerVelocity.getY());
			}
			for (int i = 0; i < Math.abs(player.playerVelocity.getY()); i++) {
				++score;
			}
		}
	}
	
	private static void generateLiveRandomPlatform() {
		
		int color = 1;
		
		// Process Level
		if (score > 40000) {
			level = 5;
		} else if (score > 30000) {
			level = 4;
		} else if (score > 20000) {
			level = 3;
		} else if (score > 10000) {
			level = 2;
		} else if (score > 5000) {
			level = 1;
		} else {
			level = 0;
		}
		
		// Random Type of Platform
		color = (int)(Math.random() * 110) + 1;
		
		if (level == 0) {
			color = 1;
		}
		
		int xPosition = clamp((int) (Math.random() * 400), 0, Settings.SCENE_WIDTH - BLOCK_SIZE);
		int yPosition = (int) (Math.random() * 10) * (-1);
		
		// green
		if ((color > 0) && (color < 50)) {
			if (level == 2) {
                color = (int) (Math.random() * 99) + 1;
            } else if (level == 3) {
                color = (int) (Math.random() * 70) + 30;
            } else if (level == 4) {
                color = (int) (Math.random() * 63) + 45;
            } else if (level == 5) {
                color = (int) (Math.random() * 63) + 45;
            }
			
			if ((color > 0) && (color <= 50)) {
                Platform plat1 = new Platform(1, xPosition, yPosition, 58, 15);
                GameScene.platforms.add(plat1);
            }
		}
		
		// light blue LR
		if ((color > 50) && (color <= 60) && (level > 1)) {
			Platform plat2 = new Platform(2, xPosition, yPosition, 56, 16);
			GameScene.platforms.add(plat2);
		}
		
		if ((color > 50) && (color <= 60) && (level < 2)) {
            color = 62;
        }
		
		// brown
        if ((color > 60) && (color <= 70)) {
            // makes sure there are not 2 brown in a row
            if (GameScene.platforms.get(GameScene.platforms.size() - 1).getType() == 3) {
            	Platform plat1 = new Platform(1, xPosition, yPosition, 58, 15);
            	GameScene.platforms.add(plat1);
            } else {
            	Platform plat3 = new Platform(3, xPosition, yPosition, 68, 20);
            	GameScene.platforms.add(plat3);
            }
        }
        
        // white
        if ((color > 70) && (color <= 80)) {
        	if (level == 5) {
        		color = (int) (Math.random() * 63) + 45;
        	}
        	
        	if ((color > 70) && (color <= 80)) {
                Platform plat9 = new Platform(9, xPosition, yPosition, 58, 15);
                GameScene.platforms.add(plat9);
            }
        }
		
	}
	
	public static void startGameLoop() {
		gameloop.start();
	}
	
	public static void stopGameLoop() {
		gameloop.stop();
	}
}
