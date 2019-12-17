package application;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.GameScene;
import scene.MenuScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;



public class Game extends Application {
	
	public static Stage window;
	
	Pane tempRoot = new Pane();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			window = primaryStage;
			MenuScene.init();
			
			window.setTitle("Jojo Jump Demo");
			window.setScene(MenuScene.scene);
			window.setResizable(false);
			window.show();
			
			
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
