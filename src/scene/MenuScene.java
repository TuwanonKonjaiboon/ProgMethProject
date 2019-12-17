package scene;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.ChangeEvent;

import application.Game;
import application.Settings;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	
	
	private static Pane appRoot = new Pane();
	private static VBox buttons = new VBox();
	private static Button btn = new Button("Game Start");
	
	public static Scene scene = new Scene(appRoot, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
	
	private static ImageView background = new ImageView(new Image(ClassLoader.getSystemResourceAsStream("bg.jpg"), Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT, false, true));
	private static ImageView logo = new ImageView(new Image(ClassLoader.getSystemResourceAsStream("JojoTitle.png")));
	private static ImageView comp_1 = new ImageView(new Image(ClassLoader.getSystemResourceAsStream("images/Dio-2.png")));
	
	private static AudioClip bgm;
	private static ChangeListener<Scene> menuSceneIn;
	
	public static void init() {
		appRoot.getChildren().clear();
		buttons.getChildren().clear();
		
		btn.setOnAction(event -> {
			transitionToGameScene();
		});
		
		logo.setFitWidth(Settings.SCENE_WIDTH);
		logo.setPreserveRatio(true);
		
		buttons.setPrefSize(200, 300);
		buttons.getChildren().addAll(btn);
		buttons.setAlignment(Pos.TOP_CENTER);
		buttons.setTranslateX(Settings.SCENE_WIDTH * 0.5 - 10);
		buttons.setTranslateY(Settings.SCENE_HEIGHT * 0.8);
		
		appRoot.getChildren().addAll(background, logo);
		
		menuSceneIn = new ChangeListener<Scene>() {
			@Override
			public void changed(ObservableValue<? extends Scene> obs, Scene old, Scene newValue) {
				if (newValue.getProperties().get("name") == "menu") {
					if (!bgm.isPlaying()) {
						bgm.play();
					}			
					playAnimation();
				} else {
					if (bgm.isPlaying()) {
						bgm.stop();
					}
				}
			}
		};
		
		Game.window.sceneProperty().addListener(menuSceneIn);
	}
	
	private static void playAnimation() {
		RotateTransition rt = new RotateTransition(Duration.seconds(1), logo);
		rt.setAutoReverse(true);
		rt.setToAngle(360);
		rt.setCycleCount(3);
		rt.setInterpolator(Interpolator.EASE_BOTH);
		
		TranslateTransition tt = new TranslateTransition(Duration.seconds(1), comp_1);
		tt.setFromX(-300);
		tt.setFromY(Settings.SCENE_HEIGHT * 0.6);
		tt.setToX(0);
		tt.setInterpolator(Interpolator.EASE_OUT);
		comp_1.setFitWidth(300);
		comp_1.setFitHeight(300);
		
		appRoot.getChildren().add(comp_1);
		
		rt.setOnFinished(event -> {
			appRoot.getChildren().add(buttons);
		});
		tt.play();
		rt.play();
		
	}
	
	private static void transitionToGameScene() {
		Rectangle fadeScreen = new Rectangle(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		new Thread(new Runnable() {
			@Override
			public void run() {
				GameScene.init();
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
						Game.window.sceneProperty().removeListener(menuSceneIn);
						Game.window.setScene(GameScene.scene);
						GameScene.startGameLoop();
					});
				}
			};
			timer.schedule(task, 1000);
		});
	}
	
}
