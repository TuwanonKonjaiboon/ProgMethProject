package application;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.GameScene;
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
			
			Scene scene = new Scene(tempRoot, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
			
			Button btn = new Button("Game Start");
			btn.setOnAction(event -> {
				GameScene.init();
				window.setScene(GameScene.scene);
				GameScene.startGameLoop();
			});
			tempRoot.getChildren().add(btn);
			window.setTitle("Jojo Jump Demo");
			window.setResizable(false);
			window.setScene(scene);
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
