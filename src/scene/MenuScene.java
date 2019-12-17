package scene;

import java.util.Timer;
import java.util.TimerTask;

import application.Game;
import application.Settings;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MenuScene {
	
	public static Scene scene;
	 
	private static Pane appRoot = new Pane();
	private static Button btn = new Button("Game Start");
	private static VBox buttons = new VBox();
	
	private static ImageView background = new ImageView(new Image(ClassLoader.getSystemResourceAsStream("bg.jpg"), Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT, false, true));
	private static ImageView logo = new ImageView(new Image(ClassLoader.getSystemResourceAsStream("JojoTitle.png")));
	
	public static void init() {
		scene = new Scene(appRoot, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		
		btn.setOnAction(event -> {
			transitionToGameScene();
		});
		
		logo.setFitWidth(Settings.SCENE_WIDTH);
		logo.setPreserveRatio(true);
		
		buttons.setPrefSize(200, 300);
		buttons.getChildren().addAll(btn);
		buttons.setAlignment(Pos.TOP_CENTER);
		buttons.setTranslateX(Settings.SCENE_WIDTH / 2 - buttons.getPrefWidth() / 2);
		buttons.setTranslateY(Settings.SCENE_HEIGHT * 0.8);
		
		appRoot.getChildren().addAll(background, logo);
		appRoot.getChildren().add(buttons);
		
		Game.window.sceneProperty().addListener((obs, old, newValue) -> {
			playBackgroundMusic();
			playAnimation();
		});
	}
	
	private static void playBackgroundMusic() {
		try {
			AudioClip bgm = new AudioClip(ClassLoader.getSystemResource("sounds/bgm-1.mp3").toURI().toString());
			bgm.setVolume(1.0);
			bgm.setCycleCount(AudioClip.INDEFINITE);
			bgm.play();
			System.out.println("setting OK");
		} catch (Exception e) {
			System.out.println("error");
		}
	}
	
	private static void playAnimation() {
		RotateTransition rt = new RotateTransition(Duration.seconds(2), logo);
		rt.setAutoReverse(true);
		rt.setToAngle(720);
		rt.setCycleCount(3);
		rt.setInterpolator(Interpolator.EASE_BOTH);
		rt.play();
	}
	
	private static void transitionToGameScene() {
		Rectangle fadeScreen = new Rectangle(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		// Pre-load
		new Thread(new Runnable() {
			@Override
			public void run() {
				GameScene.init();
				Thread.interrupted();
			}
		}).run();
		
		FillTransition transition = new FillTransition(Duration.seconds(1), fadeScreen);
		fadeScreen.setFill(Color.TRANSPARENT);
		transition.setFromValue(Color.TRANSPARENT);
		transition.setToValue(Color.WHITE);
		
		appRoot.getChildren().add(fadeScreen);
		
		transition.play();
		transition.setOnFinished(event -> {
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					javafx.application.Platform.runLater(() -> {
						Game.window.setScene(GameScene.scene);
						GameScene.startGameLoop();					
					});
				}
			};
			timer.schedule(task, 1000); // Delay 1 seconds
		});
	}
	
}
