package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Sound {
	
	MediaPlayer mediaPlayer;
	
	public Sound(String url) {
		Media sound = new Media(ClassLoader.getSystemResourceAsStream(url).toString());
		mediaPlayer = new MediaPlayer(sound);
		
		mediaPlayer.play();
		if (url == "menu.wav") {
			mediaPlayer.setOnEndOfMedia(new Runnable() {
				
				@Override
				public void run() {
					mediaPlayer.seek(Duration.ZERO);
				}
			});
		}
				
	}
	
	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
}
