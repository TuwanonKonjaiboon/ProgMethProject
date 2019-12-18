package application.gameover;

import application.Settings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import scene.GameScene;

public class GameOverMenu extends Pane{
	Label gameoverLabel;
	GameOverBox gameOverBox;
	Rectangle fadeRectangle;
	public GameOverMenu() {
		gameoverLabel = new Label("Game Over");
		gameoverLabel.setFont(new Font(30));
		gameoverLabel.setPadding(new Insets(5));
		gameoverLabel.setPrefWidth(300);
		gameoverLabel.setTranslateX(Settings.SCENE_WIDTH/2 - gameoverLabel.getPrefWidth()/4);
		gameoverLabel.setTranslateY(Settings.SCENE_HEIGHT/4);
		gameoverLabel.setTextFill(Paint.valueOf("#ffffff"));
		
		gameOverBox = new GameOverBox();
		gameOverBox.setTranslateX(Settings.SCENE_WIDTH / 2 - gameOverBox.getPrefWidth() / 2);
		gameOverBox.setTranslateY(Settings.SCENE_HEIGHT /2 -gameOverBox.getPrefHeight()/2);
		fadeRectangle = new Rectangle(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		fadeRectangle.setOpacity(0.4);
		fadeRectangle.setFill(Paint.valueOf("#000000"));
		getChildren().addAll(fadeRectangle ,gameoverLabel, gameOverBox);
	}
	
	public GameOverBox getGameOverBox() {
		return gameOverBox;
	}

}
