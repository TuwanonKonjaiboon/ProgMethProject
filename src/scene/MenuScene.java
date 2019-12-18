package scene;

import java.util.Timer;
import java.util.TimerTask;

import application.Game;
import application.Settings;
import asset.ButtonStyles;
import asset.GameSounds;
import javafx.animation.Animation;
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
	private static ImageView eff_1 = new ImageView(new Image(ClassLoader.getSystemResourceAsStream("images/effect2.png")));
	
	private static MediaPlayer playerBGM = new MediaPlayer(GameSounds.mainBGM);
	private static boolean isBGMPlaying = false;
	private static ChangeListener<Scene> menuSceneIn;
	
	
	public static void init() {
		appRoot.getChildren().clear();
		buttons.getChildren().clear();
		
		btn.setStyle(ButtonStyles.defaultStyle);
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
		
		playerBGM.setCycleCount(MediaPlayer.INDEFINITE);
		playerBGM.setVolume(0.8);
		
		scene.getProperties().put("name", "menu");
		
		menuSceneIn = new ChangeListener<Scene>() {
			@Override
			public void changed(ObservableValue<? extends Scene> obs, Scene old, Scene newValue) {
				if (old != null && old.getProperties().getOrDefault("name", null) != null && old.getProperties().get("name") == "menu") {
					playerBGM.stop();
					isBGMPlaying = false;
					Game.window.sceneProperty().removeListener(menuSceneIn);
				} else {
					playerBGM.play();
					isBGMPlaying = true;
					playAnimation();
				}
			}
		};
		
		appRoot.getChildren().addAll(background, logo);
		
		scene.setOnMouseClicked(event -> {
			if (!appRoot.getChildren().contains(buttons)) {				
				appRoot.getChildren().add(buttons);
			}
		});
		
		Game.window.sceneProperty().addListener(menuSceneIn);
	}
	
	private static void playAnimation() {
		RotateTransition rt = new RotateTransition(Duration.seconds(1), logo);
		
		comp_1.setFitWidth(300);
		comp_1.setFitHeight(300);
		
		eff_1.setFitWidth(200);
		eff_1.setFitHeight(200);
		eff_1.setTranslateX(0);
		eff_1.setTranslateY(-eff_1.getFitHeight());
		
		rt.setAutoReverse(true);
		rt.setFromAngle(0);
		rt.setToAngle(360);
		rt.setCycleCount(3);
		rt.setInterpolator(Interpolator.EASE_BOTH);
		
		TranslateTransition tt = new TranslateTransition(Duration.seconds(1), comp_1);
		tt.setFromX(-300);
		tt.setFromY(Settings.SCENE_HEIGHT * 0.6);
		tt.setToX(0);
		tt.setInterpolator(Interpolator.EASE_OUT);
		tt.setOnFinished(event -> {
			appRoot.getChildren().add(eff_1);
			tt.setNode(eff_1);
			tt.setFromY(-eff_1.getFitHeight());
			tt.setToX(comp_1.getTranslateX());
			tt.setToY(comp_1.getTranslateY() - 100);
			tt.setDuration(Duration.millis(500));
			tt.setOnFinished(e -> {
				Timeline tl = new Timeline(
						new KeyFrame(Duration.millis(0), new KeyValue(eff_1.translateXProperty(), -10)),
		                new KeyFrame(Duration.millis(100), new KeyValue(eff_1.translateXProperty(), 10)),
		                new KeyFrame(Duration.millis(200), new KeyValue(eff_1.translateXProperty(), -10)),
		                new KeyFrame(Duration.millis(300), new KeyValue(eff_1.translateXProperty(), 10)),
		                new KeyFrame(Duration.millis(400), new KeyValue(eff_1.translateXProperty(), -10)),
		                new KeyFrame(Duration.millis(500), new KeyValue(eff_1.translateXProperty(), 10)),
		                new KeyFrame(Duration.millis(600), new KeyValue(eff_1.translateXProperty(), -10)),
		                new KeyFrame(Duration.millis(700), new KeyValue(eff_1.translateXProperty(), 10)),
		                new KeyFrame(Duration.millis(800), new KeyValue(eff_1.translateXProperty(), -10)),
		                new KeyFrame(Duration.millis(900), new KeyValue(eff_1.translateXProperty(), 10)),
		                new KeyFrame(Duration.millis(1000), new KeyValue(eff_1.translateXProperty(), -10))
				);
				tl.setCycleCount(Animation.INDEFINITE);
				tl.play();
			});
			tt.play();
		});
		
		appRoot.getChildren().add(comp_1);
		
		rt.setOnFinished(event -> {
			if (!appRoot.getChildren().contains(buttons)) {				
				appRoot.getChildren().add(buttons);
			}
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
		for (float i = 1.0f; i > 0.2f; i -= 0.05) {
			playerBGM.setVolume(i);
		}
		
		FillTransition transition = new FillTransition(Duration.seconds(1), fadeScreen);
		fadeScreen.setFill(Color.TRANSPARENT);
		transition.setFromValue(Color.TRANSPARENT);
		transition.setToValue(Color.WHITE);
		
		TranslateTransition tt1 = new TranslateTransition(Duration.seconds(1), comp_1);
		tt1.setToX(Settings.SCENE_WIDTH);
		tt1.setInterpolator(Interpolator.EASE_IN);
		tt1.play();
		
		TranslateTransition tt2 = new TranslateTransition(Duration.seconds(1), eff_1);
		tt2.setToX(Settings.SCENE_WIDTH);
		tt2.setInterpolator(Interpolator.EASE_IN);
		tt2.play();
		tt2.setOnFinished(event -> {
			appRoot.getChildren().remove(eff_1);
		});
		
		appRoot.getChildren().add(fadeScreen);
		
		MediaPlayer ef = new MediaPlayer(GameSounds.yes);
		ef.setCycleCount(1);
		ef.setVolume(10.0);
		ef.setOnEndOfMedia(() -> {
			transition.play();
			transition.setOnFinished(event -> {
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						javafx.application.Platform.runLater(() -> {
							Game.window.setScene(GameScene.scene);
							Game.window.sceneProperty().removeListener(menuSceneIn);
							GameScene.startGameLoop();	
						});
					}
				};
				timer.schedule(task, 1000);
			});					
		});
		ef.play();
	}
	
}
