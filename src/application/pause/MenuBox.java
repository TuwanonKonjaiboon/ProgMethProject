package application.pause;

import application.Game;
import asset.ButtonStyles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import scene.GameScene;
import scene.MenuScene;

public class MenuBox extends VBox{
	
	private Button resumeButton, backtomenuButton ,restartButton;
	
	public MenuBox() {
		this.setPrefWidth(200);
		this.setPrefHeight(400);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10));
		this.setSpacing(10);
		
		resumeButton = new Button("Resume");
		resumeButton.setStyle(ButtonStyles.defaultStyle);
		resumeButton.setPrefWidth(100);
		resumeButton.setOnAction(e -> {
			GameScene.isPause = false;
		});

		backtomenuButton = new Button("Back to menu");
		backtomenuButton.setStyle(ButtonStyles.defaultStyle);
		backtomenuButton.setPrefWidth(resumeButton.getPrefWidth());
		backtomenuButton.setOnAction( e -> {
			GameScene.stopGameLoop();
			MenuScene.init();
			Game.window.setScene(MenuScene.scene);
		});
		
		restartButton = new Button("Restart");
		restartButton.setStyle(ButtonStyles.defaultStyle);
		restartButton.setPrefWidth(resumeButton.getPrefWidth());
		restartButton.setOnAction( e -> {
			GameScene.restart();
		});
		
		getChildren().addAll(resumeButton, restartButton, backtomenuButton);
	}
	
	public Button getResumeButton() {
		return resumeButton;
	}
	
	public Button getRestartButton() {
		return restartButton;
	}
	
	public Button getBackToMenuButton() {
		return backtomenuButton;
	}
	
}
