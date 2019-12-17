package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import scene.GameScene;
import scene.MenuScene;

public class GameOverBox extends VBox {
	Button restartButton , backtoMenuButton;
	public GameOverBox() {
		restartButton = new Button("Restart");
		backtoMenuButton = new Button("Back to menu");
		this.setPrefWidth(200);
		this.setPrefHeight(400);
		this.setSpacing(10);
		this.setPadding(new Insets(10));
		
		restartButton.setOnAction(e ->{
			GameScene.restart();
		});
		
		backtoMenuButton.setOnAction( e->{
			GameScene.stopGameLoop();
			MenuScene.init();
			Game.window.setScene(MenuScene.scene);
			
		});
		
		restartButton.setStyle("-fx-background-color: \r\n" + 
				"        linear-gradient(#f2f2f2, #d6d6d6),\r\n" + 
				"        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\r\n" + 
				"        linear-gradient(#dddddd 0%, #f6f6f6 50%);\r\n" + 
				"    -fx-background-radius: 8,7,6;\r\n" + 
				"    -fx-background-insets: 0,1,2;\r\n" + 
				"    -fx-text-fill: black;\r\n" + 
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		backtoMenuButton.setStyle("-fx-background-color: \r\n" + 
				"        linear-gradient(#f2f2f2, #d6d6d6),\r\n" + 
				"        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\r\n" + 
				"        linear-gradient(#dddddd 0%, #f6f6f6 50%);\r\n" + 
				"    -fx-background-radius: 8,7,6;\r\n" + 
				"    -fx-background-insets: 0,1,2;\r\n" + 
				"    -fx-text-fill: black;\r\n" + 
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		backtoMenuButton.setPrefWidth(100);
		restartButton.setPrefWidth(backtoMenuButton.getPrefWidth());
		setAlignment(Pos.CENTER);
		getChildren().addAll(restartButton, backtoMenuButton);
		
	}

}
