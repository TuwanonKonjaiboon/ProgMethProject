package application.pause;

import application.Settings;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class PauseMenu extends Pane {
	Rectangle fadeRectangle;
	Rectangle menuRectangle;
	MenuBox menuBox;
	Label gamepauseLabel;
	
	public PauseMenu() {
		gamepauseLabel = new Label("Game Pause");
		gamepauseLabel.setFont(new Font(30));
		gamepauseLabel.setPrefWidth(300);
		gamepauseLabel.setTranslateX(Settings.SCENE_WIDTH/2 - gamepauseLabel.getPrefWidth()/4);
		gamepauseLabel.setTranslateY(Settings.SCENE_HEIGHT/4);
		gamepauseLabel.setTextFill(Color.WHITE);
		gamepauseLabel.setStyle("");
		
		menuBox = new MenuBox();
		menuBox.setTranslateX(Settings.SCENE_WIDTH / 2 - menuBox.getPrefWidth() / 2);
		menuBox.setTranslateY(Settings.SCENE_HEIGHT /2 -menuBox.getPrefHeight()/2);
		
		fadeRectangle = new Rectangle(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		fadeRectangle.setOpacity(0.4);
		fadeRectangle.setFill(Color.BLACK);
		
		getChildren().addAll(fadeRectangle, menuBox , gamepauseLabel);
	}
	
	public MenuBox getMenuBox() {
		return menuBox;
	}
}
 