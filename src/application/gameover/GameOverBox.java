package application.gameover;

import application.Game;
import asset.ButtonStyles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import scene.GameScene;
import scene.MenuScene;

public class GameOverBox extends VBox {
	private Button restartButton , backtoMenuButton;
	public GameOverBox() {
		restartButton = new Button("Restart");
		backtoMenuButton = new Button("Back to menu");
		this.setPrefWidth(200);
		this.setPrefHeight(400);
		this.setSpacing(10);
		this.setPadding(new Insets(10));
		
		restartButton.setOnAction(event -> {
			GameScene.restart();
		});
		
		backtoMenuButton.setOnAction( event -> {
			GameScene.stopGameLoop();
			MenuScene.init();
			Game.window.setScene(MenuScene.scene);
		});
		
		restartButton.setStyle(ButtonStyles.defaultStyle);
		backtoMenuButton.setStyle(ButtonStyles.defaultStyle);
		backtoMenuButton.setPrefWidth(100);
		restartButton.setPrefWidth(backtoMenuButton.getPrefWidth());
		setAlignment(Pos.CENTER);
		getChildren().addAll(restartButton, backtoMenuButton);
	}
	
	public Button getRestartButton() {
		return restartButton;
	}
	
	public Button getBackToMenuButton() {
		return backtoMenuButton;
	}

}
