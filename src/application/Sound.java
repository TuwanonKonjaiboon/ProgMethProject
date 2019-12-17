package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
	
	MediaPlayer mediaPlayer;
	
	public Sound(String url) throws Exception {
		Media sound = new Media(new File(url).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setAutoPlay(true);
	}
	
	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
	
	public void play() {
		mediaPlayer.play();
	}
}
