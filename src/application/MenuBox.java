package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import scene.GameScene;
import scene.MenuScene;

public class MenuBox extends VBox{
	Button resumeButton, backtomenuButton ,restartButton;
	public MenuBox() {
		this.setPrefWidth(200);
		this.setPrefHeight(400);
		this.setSpacing(10);
		resumeButton = new Button("Resume");
		backtomenuButton = new Button("Back to menu");
		restartButton = new Button("Restart");
		resumeButton.setStyle("-fx-background-color: \r\n" + 
				"        linear-gradient(#f2f2f2, #d6d6d6),\r\n" + 
				"        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\r\n" + 
				"        linear-gradient(#dddddd 0%, #f6f6f6 50%);\r\n" + 
				"    -fx-background-radius: 8,7,6;\r\n" + 
				"    -fx-background-insets: 0,1,2;\r\n" + 
				"    -fx-text-fill: black;\r\n" + 
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		backtomenuButton.setStyle("-fx-background-color: \r\n" + 
				"        linear-gradient(#f2f2f2, #d6d6d6),\r\n" + 
				"        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\r\n" + 
				"        linear-gradient(#dddddd 0%, #f6f6f6 50%);\r\n" + 
				"    -fx-background-radius: 8,7,6;\r\n" + 
				"    -fx-background-insets: 0,1,2;\r\n" + 
				"    -fx-text-fill: black;\r\n" + 
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		restartButton.setStyle("-fx-background-color: \r\n" + 
				"        linear-gradient(#f2f2f2, #d6d6d6),\r\n" + 
				"        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\r\n" + 
				"        linear-gradient(#dddddd 0%, #f6f6f6 50%);\r\n" + 
				"    -fx-background-radius: 8,7,6;\r\n" + 
				"    -fx-background-insets: 0,1,2;\r\n" + 
				"    -fx-text-fill: black;\r\n" + 
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		
		resumeButton.setPrefWidth(100);
		backtomenuButton.setPrefWidth(resumeButton.getPrefWidth());
		restartButton.setPrefWidth(resumeButton.getPrefWidth());
		resumeButton.setOnAction(e -> {
			GameScene.isPause = false;
		});
		restartButton.setOnAction( e -> {
			GameScene.restart();
		});
		backtomenuButton.setOnAction( e->{
			GameScene.stopGameLoop();
			Game.window.setScene(MenuScene.scene);
			MenuScene.init();
		});
		this.setPadding(new Insets(10));
		this.setAlignment(Pos.CENTER);
		getChildren().addAll(resumeButton,restartButton,backtomenuButton);
		//this.setBorder(new Border(new BorderStroke(Color.BLACK, 
	    //        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
	
}
