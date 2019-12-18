package scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import application.Character;
import application.Game;
import application.Knife;
import application.Monster;
import application.Platform;
import application.Settings;
import application.gameover.GameOverMenu;
import application.pause.PauseMenu;
import asset.GameImage;
import asset.GameSounds;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class GameScene {
	
	public static final int BLOCK_SIZE = 68;
	
	public static AnimationTimer gameloop;
	
	public static Character player;
	public static Text scoreText = new Text("");
	public static ArrayList<Platform> platforms = new ArrayList<Platform>();
	public static ArrayList<Monster> monsters = new ArrayList<Monster>();
	public static ArrayList<Knife> knifes = new ArrayList<Knife>();
	
	// Player Inputs
	public static HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
	
	public static boolean isGameOver = false;
	public static boolean isPause = false;
	
	private static Pane appRoot = new Pane();
	private static Pane gameRoot = new Pane();
	private static PauseMenu pauseMenu = new PauseMenu();
	private static GameOverMenu gameOverMenu = new GameOverMenu();
	
	private static ImageView background = new ImageView(GameImage.backgroundImage);
	private static ImageView topbarImage = new ImageView(GameImage.topbarImage);
	
	private static MediaPlayer playerBGM = new MediaPlayer(GameSounds.mainBGM);
	private static boolean isBGMPlaying = false;
	
	private static MediaPlayer pauseSound = new MediaPlayer(GameSounds.timeStop);
	private static MediaPlayer resumeSound = new MediaPlayer(GameSounds.timeResume);
	private static MediaPlayer toBeContinueSound = new MediaPlayer(GameSounds.toBeContinue);
	private static MediaPlayer konoDioDaSound = new MediaPlayer(GameSounds.konoDioDa);
	private static MediaPlayer yareSound = new MediaPlayer(GameSounds.yare);
	
	public static Scene scene = new Scene(appRoot, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
	
	public static int score = 0;
	public static int level = 0;
	
	public static int lastGenerateType = 1;
	
	public static boolean isthrowKnife = false;
//	private static MediaPlayer throwKnifeSound = new MediaPlayer(GameSounds);
	
	private static ChangeListener<Scene> gameSceneIn;
	private static ChangeListener<Duration> gameOverEffect;
	
	public static void init() {
		GameImage.init();
		
		isGameOver = false;
		isPause = false;

		// Clean Up
		appRoot.getChildren().clear(); 
		gameRoot.getChildren().clear();
		platforms.clear();
		monsters.clear();
		knifes.clear();
		toBeContinueSound.stop();
		playerBGM.stop();
		gameOverMenu.getChildren().remove(scoreText);
		
		isBGMPlaying = false;
		
		score = 0;
		level = 0;
		
		// Insert Background Image
		background.setFitHeight(Settings.SCENE_HEIGHT);
		background.setFitWidth(Settings.SCENE_WIDTH);
		// Top Bar Image
		topbarImage.setFitWidth(Settings.SCENE_WIDTH);
		topbarImage.setPreserveRatio(true);
		
		playerBGM.setCycleCount(MediaPlayer.INDEFINITE);
		playerBGM.setVolume(0.5);
		playerBGM.setOnEndOfMedia(() -> {
			playerBGM.seek(Duration.ZERO);
		}); 
		
		konoDioDaSound.setOnEndOfMedia(() -> { konoDioDaSound.stop(); });
		yareSound.setOnEndOfMedia(() -> { yareSound.stop(); });
		
		// Generate First Platforms
		int shift = 550;
		int min = 50;
		int max = 80;
		for (int i = 0; i < 12; i++) {
			shift -= min + (int)(Math.random() * ((max - min) + 1));
			Platform plat1 = new Platform(1, (int) (Math.random() * 6 * 58), shift, 58, 15);
			platforms.add(plat1);
		}
				
		int xPosition = clamp((int) (Math.random() * 400), 0, Settings.SCENE_WIDTH - BLOCK_SIZE);
		int yPosition = 500;
				
		platforms.add(new Platform(1, xPosition, yPosition, 58, 15));
				
		// Create Player
		player = new Character();
		player.setTranslateX(xPosition);
		player.setTranslateY(500);
		
		// Setup Score
		scoreText = new Text("");
		scoreText.setTranslateX(20);
		scoreText.setTranslateY(20);
		scoreText.setFont(new Font(20));
		scoreText.setFill(Color.BLACK);
		scoreText.setStyle("-fx-border-color: red");
		
		gameOverMenu.getGameOverBox().getRestartButton().setOnAction(event -> {
			GameScene.restart();
		});
		gameOverMenu.getGameOverBox().getBackToMenuButton().setOnAction(event -> {
			GameScene.stopGameLoop();
			MenuScene.init();
			Game.window.setScene(MenuScene.scene);
		});
		pauseMenu.getMenuBox().getResumeButton().setOnAction(event -> {
			resumeSound.play();
			resumeSound.setOnEndOfMedia(() -> {
				startGameLoop();
				playerBGM.play();
				isBGMPlaying = true;
				isPause = false;
				resumeSound.stop();
			});
		});
		pauseMenu.getMenuBox().getRestartButton().setOnAction(event -> {
			GameScene.restart();
		});
		pauseMenu.getMenuBox().getBackToMenuButton().setOnAction(event -> {
			GameScene.stopGameLoop();
			MenuScene.init();
			Game.window.setScene(MenuScene.scene);
		});
		
		// Add Everything
		gameRoot.getChildren().addAll(platforms);
		gameRoot.getChildren().addAll(monsters);
		gameRoot.getChildren().add(player);
		gameRoot.getChildren().addAll(topbarImage);
		gameRoot.getChildren().add(scoreText);

		// Add Game Root to App Root
		appRoot.getChildren().addAll(background, gameRoot);

		// Setup Scene Input Listener
		scene.setOnKeyPressed(event -> {
			keys.put(event.getCode(), true);
		});
		scene.setOnKeyReleased(event -> {
			keys.put(event.getCode(), false);
		});
		
		scene.getProperties().put("name", "game");
		
		// Setup Game Loop 
		gameloop = new AnimationTimer() {
			
			// 30 FPS
			int count = 0;
			
			@Override
			public void handle(long arg0) {
				if ( count > 1 && !isPause ) {
					if (appRoot.getChildren().contains(pauseMenu)) {
						appRoot.getChildren().remove(pauseMenu);
					}
					
					if (isPress(KeyCode.ESCAPE)) {
						pauseSound.play();
						playerBGM.pause();
						isBGMPlaying = false;
						isPause = true;							
						pauseSound.setOnEndOfMedia(() -> {
							appRoot.getChildren().add(pauseMenu);
							pauseSound.stop();
							stopGameLoop();
						});
					}
					
					updatePlayer();
					updatePlatform();
					updateScore();
					updateKnife();
					updateMonster();
					
					System.out.println(score);
					System.out.println(isGameOver);
					
					count = 0;
				}
				
				if (isGameOver) {
					if (isBGMPlaying) {
						playerBGM.setVolume(playerBGM.getVolume() - 0.05);
						if (playerBGM.getVolume() < 0) {
							playerBGM.stop();
							isBGMPlaying = false;
							GameOver();
						}
						return;
					}
				}
				count ++;
			}
		};
		
		gameSceneIn = new ChangeListener<Scene>() {
			@Override
			public void changed(ObservableValue<? extends Scene> obs, Scene old, Scene newValue) {
				
				// Leave Scene
				if (old.getProperties().get("name") == "game") {
					toBeContinueSound.stop();
					playerBGM.stop();
					Game.window.sceneProperty().removeListener(gameSceneIn);
				} else {
					playerBGM.play();
					isBGMPlaying = true;
				}
			}
		};
		
		gameOverEffect = new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> obs, Duration old, Duration newValue) {
				System.out.println(toBeContinueSound.getCurrentTime().toSeconds());
				if (toBeContinueSound.getCurrentTime().toSeconds() >= Duration.seconds(45).toSeconds()) {
					appRoot.getChildren().add(gameOverMenu);
					stopGameLoop();
					
					scoreText.setTextAlignment(TextAlignment.CENTER);
					scoreText.setFont(new Font(30));
					scoreText.setFill(Color.WHITESMOKE);
					System.out.println(scoreText.getWrappingWidth());
					scoreText.setTranslateX(Settings.SCENE_WIDTH / 2 - scoreText.getBoundsInParent().getWidth() / 2);
					scoreText.setTranslateY(230);
					
					gameOverMenu.getChildren().add(scoreText);
					
					toBeContinueSound.currentTimeProperty().removeListener(gameOverEffect);
					
				
					yareSound.play();
				}
			}
		};
		
		Game.window.sceneProperty().addListener(gameSceneIn);
		
		scene.setRoot(appRoot);
	}
	
	private static void GameOver() {
		Timer timer = new Timer();
		TimerTask sound = new TimerTask() {
			
			@Override
			public void run() {
				toBeContinueSound.setStartTime(Duration.seconds(40));
				toBeContinueSound.currentTimeProperty().addListener(gameOverEffect);
				toBeContinueSound.play();
			}
		};
		timer.schedule(sound, 1000);
	}
	
	public static void restart() {
		stopGameLoop();
		init();
		
		playerBGM.play();
		isBGMPlaying = true;
		
		startGameLoop();
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
		
		if (!isGameOver) {
			// PlayerControl
			player.jumpPlayer();
			
			if (isPress(KeyCode.RIGHT)) {
				player.setScaleX(1);
				player.moveX(10);
			}
			if (isPress(KeyCode.LEFT)) {
				player.setScaleX(-1);
				player.moveX(-10);
			}
			if (isPress(KeyCode.SPACE) && !isthrowKnife) {
				generateKnife();
			} else if(!isPress(KeyCode.SPACE) ) {
				isthrowKnife = false; 
			}
		}
		
		// Check Side
		if (player.getTranslateX() + player.getWidth() <= -1) {
			player.setTranslateX(Settings.SCENE_WIDTH);
		}
		if (player.getTranslateX() >= Settings.SCENE_WIDTH + 1) {
			player.setTranslateX(0);
		}
		
		// Gravity
		if (player.playerVelocity.getY() < 15) {
			player.playerVelocity = player.playerVelocity.add(0, 2);
		}
		
		// Check Game Over
		if (player.isFalls()) {
			isGameOver = true;
		}
		System.out.println(player);
		player.moveY((int) player.playerVelocity.getY());
		player.update();
	}
	
	private static void updateScore() {
		scoreText.setText(String.format("%d", score));
	}
	
	private static void updatePlatform() {
		if (!player.isMovingDown() && player.getTranslateY() <= 249) {
			for (Platform platform : platforms) {
				platform.moveY((int) player.playerVelocity.getY());
				
				if (!platform.inScene()) {
					reGenerateLiveRandomPlatform(platform);
					continue;
				}
			}
			for (int i = 0; i < Math.abs(player.playerVelocity.getY()); i++) {
				score += 3;
			}
		}
		
		for (Platform platform : platforms) {
			platform.update();
		}
	}
	
	private static void updateKnife() {
		if (!player.isMovingDown() && player.getTranslateY() <= 249) {
			for(int i = 0 ; i < knifes.size() ; i++) {
				knifes.get(i).moveY((int) player.playerVelocity.getY());	
			}
		}

		for(Knife knife : knifes) {
			knife.update();
		}
		
		try {			
			for (int i = 0; i < gameRoot.getChildren().size(); i++) {
				if (gameRoot.getChildren().get(i) instanceof Knife) {
					Knife temp = (Knife) gameRoot.getChildren().get(i);
					if (temp.isAlreadyhit() || !temp.isInScene()) {
						gameRoot.getChildren().remove(temp);
						knifes.remove(temp);					
					}
				}
			}
		} catch (Exception e) { }
	}
	
	private static void updateMonster() {
		if (!player.isMovingDown() && player.getTranslateY() <= 249) {
			for (Monster monster : monsters) {
				monster.moveY((int) player.playerVelocity.getY());
			}
		}
		
		for (Monster monster : monsters) {
			monster.update();
		}
		
		try {
			for (int i = 0; i < monsters.size(); i++) {
				if (!monsters.get(i).inScene() || monsters.get(i).isDead()) {
					gameRoot.getChildren().remove(monsters.get(i));
					monsters.remove(i);
				}
			}
		} catch (Exception e) { }
	}

	public static void reGenerateLiveRandomPlatform(Platform platform) {
		
		int color = 1;
		int type = 1;
		
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
		int yPosition = ((int) Math.random() * 10) * (-1);
		
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
				type = 1;
            }
		}
		
		// light blue LR
		if ((color > 50) && (color <= 60) && (level > 1)) {
			type = 2;
		}
		
		if ((color > 50) && (color <= 60) && (level < 2)) {
            color = 62;
        }
		
		// brown
        if ((color > 60) && (color <= 70)) {
            // makes sure there are not 2 brown in a row
            if (lastGenerateType == 3) {
            	type = 1;
            	yPosition = ((int) Math.random() + 50) * (-1);
            } else {
            	type = 3;
            	yPosition = ((int) Math.random() + 50) * (-1);
            }
        }
        
        // white
        if ((color > 70) && (color <= 80)) {
        	if (level == 5) {
        		color = (int) (Math.random() * 63) + 45;
        	}
        	
        	if ((color > 70) && (color <= 80)) {
        		type = 9;
            }
        }
        
     // dark blue - vertical scroll
        if ((color > 90) && (color <= 100)) {
            type = 10;
        }

        if ((color > 100) && (color <= 105)) {
            if (level >= 2) {
            	if (player.getTranslateY() > 200) {
                    if (monsters.size() < 1) {
                        generateMonster();
                    }
                }
            }

            type = 1;
        }


        if ((color > 105) && (color <= 108)) {
            type = 1;
        }
        
        platform.changeType(type);
        platform.setTranslateX(xPosition);
        platform.setTranslateY(yPosition);
        lastGenerateType = type;
	}
	
	public static void generateMonster() {
		int xPosition = clamp((int) (Math.random() * 400), 0, Settings.SCENE_WIDTH - BLOCK_SIZE);
		int yPosition = ((int) Math.random() * 10) * (-1); 
		
		int type = (Math.random() >= 0.5 ? 0 : 1);
		
		Monster monster = new Monster(type, xPosition, yPosition);
		
		monsters.add(monster);
		gameRoot.getChildren().add(monster);
	}
	
	private static void generateKnife() {
		Knife knife = new Knife();
		knife.setdirection();
		
		if (konoDioDaSound.getStatus() == Status.PLAYING) konoDioDaSound.stop();
		konoDioDaSound.play();
		
		knifes.add(knife);
		gameRoot.getChildren().add(knife);
		
		isthrowKnife = true;
	}
	
	public static void startGameLoop() {
		gameloop.start();
	}
	
	public static void stopGameLoop() {
		gameloop.stop();
	}
}
