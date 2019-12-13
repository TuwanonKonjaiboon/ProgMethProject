package application;
	
import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class Game extends Application {
	
	public static Stage window;
	
	public static ArrayList<Platform> platforms = new ArrayList<Platform>();
	private boolean soundOff = false;
	private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
	
	Image backgroundImg = new Image(ClassLoader.getSystemResourceAsStream("images/bg-grid.png"));
	
	public static final int BLOCK_SIZE = 68;
	
	public static Pane appRoot = new Pane();
	public static Pane gameRoot = new Pane();
	public Character player;
	public static int score = 0;
	Text scoreText = new Text();
	Text gameoverText = new Text();
	public boolean checkPlayerPos;
	
	private void initialContent() {
		ImageView background = new ImageView(backgroundImg);
		background.setFitHeight(Settings.SCENE_HEIGHT);
		background.setFitWidth(Settings.SCENE_WIDTH);
		int shift = 650;
		int min = 130;
		int max = 160;
		for (int i = 0; i < 8; i++) {
			shift -= min + (int)(Math.random()*((max - min) + 1));
			platforms.add(new Platform("1", (int)(Math.random()*5*BLOCK_SIZE), shift));
		}
//		TODO: add Brown platform
//		for (int i = 0; i < 4; i++) {
//			shift -= min + (int)(Math.random()*((max - min) + 1));
//			platforms.add(new Platform("2", (int)(Math.random()*5*BLOCK_SIZE), shift);
//		}
		addCharacters(background);
	}
	
	public void addCharacters(ImageView background) {
		player = new Character(0);
		player.setTranslateX(185);
		player.setTranslateY(650);
		player.translateYProperty().addListener((obs, old, newValue) -> {
			checkPlayerPos = false;
			if (player.getTranslateY() < 300) {
				checkPlayerPos = true;
				for (Platform platform: Game.platforms) {
					platform.setTranslateY(platform.getTranslateY() + 2.5);
					if (platform.getTranslateY() == 701) {
						if (platform.getId().equals("2")) {
							platform.brownPlatfrom();
							platform.setDestroyOnce(false);
						}
						platform.setTranslateY(1);
						platform.setTranslateX(Math.random()*6*BLOCK_SIZE);
					}
				}
			}
		});
		
		gameRoot.getChildren().add(player);
		appRoot.getChildren().addAll(background, gameRoot);
	}
	
	private void update() {
		playerControl();
		playerScore();
		checkSide();
		System.out.println(player);
		if (player.ifFalls()) {
			gameOverText();
			gameoverText.setText("Game Over! press 'Space' to restart");
			scoreText.setTranslateX(300);
			scoreText.setTranslateY(200);
			if (soundOff == false) {
				// Add Sound
				soundOff = true;
			}
			if (isPress(KeyCode.SPACE)) {
				restart();
			}
		}
	}
	
	private void gameOverText() {
		gameRoot.getChildren().remove(gameoverText);
		gameoverText.setText(null);
		gameoverText.setTranslateX(150);
		gameoverText.setTranslateY(330);
		
		gameoverText.setScaleX(2);
		gameoverText.setScaleY(2);
		gameRoot.getChildren().add(gameoverText);
	}
	
	private void playerScore() {
		gameRoot.getChildren().remove(scoreText);
		
		if (checkPlayerPos == true) {
			score += 1;
		}
		scoreText.setText("Score: " + score);
		scoreText.setTranslateX(30);
		scoreText.setTranslateY(50);
		scoreText.setScaleX(2);
		scoreText.setScaleY(2);
		gameRoot.getChildren().add(scoreText);
	}
	
	private void checkSide() {
		if (player.getTranslateX() < -59) {
			player.setTranslateX(520);
		} else if (player.getTranslateX() > 450 && player.getTranslateX() > 519) {
			player.setTranslateX(-59);
		}
	}
	
	private void playerControl() {
		if (player.getTranslateY() >= 5) {
			player.jumpPlayer();
			player.setCanJump(false);
		}
		if (isPress(KeyCode.LEFT)) {
			player.setScaleX(-1);
			player.moveX(-7);
		}
		if (isPress(KeyCode.RIGHT)) {
			player.setScaleX(1);
			player.moveX(7);
		}
		if (player.playerVelocity.getY() < 10) {
			player.playerVelocity = player.playerVelocity.add(0, 1);
		} else player.setCanJump(false);
		player.moveY((int)player.playerVelocity.getY());
	}
	
	private void restart() {
		platforms.clear();
		soundOff = false;
		keys.clear();
		Game.score = 0;
		gameRoot.getChildren().remove(gameoverText);
		gameRoot.getChildren().clear();
		appRoot.getChildren().removeAll(gameRoot);
		gameRoot.getChildren().clear();
		
		initialContent();
	}
	
	private boolean isPress(KeyCode key) {
		return keys.getOrDefault(key, false);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			window = primaryStage;
			
			Scene scene = new Scene(appRoot, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
			
			scene.setOnKeyPressed(event -> {
				keys.put(event.getCode(), true);
			});
			
			scene.setOnKeyReleased(event -> {
				keys.put(event.getCode(), false);
			});
			
			window.setTitle("Jojo Jump Demo");
			window.setResizable(false);
			window.setScene(scene);
			window.show();
			
			restart();
			
			AnimationTimer gameloop = new AnimationTimer() {
				
				@Override
				public void handle(long now) {
//					System.out.println(now);
					update();
				}
			};
			gameloop.start();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		System.exit(1);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
