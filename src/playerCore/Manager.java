package playerCore;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import mediaFiles.MediaFile;
import mediaFiles.MP3File;

public class Manager {
	
	private UiCallbackInterface ui;
	private Slider slider;
	private Runnable endOfMediaListener;
	private InvalidationListener currentTimeListener;
	private InvalidationListener sliderValueListener;
	private Slider volumeSlider;
	private InvalidationListener volumeSliderliderValueListener;
	
	private List<MediaFile> playlist;
	private MediaFile currentMediaFile;
	
	private Media media;
	private MediaPlayer player;
	
	private boolean repeat;
	private boolean random;
	
	
	private static class Loader {
		static final Manager INSTANCE = new Manager();
	}
	
	private Manager() {
		this.playlist = new ArrayList<MediaFile>();
		this.currentMediaFile = null;
	}

	public static Manager getInstance() {
		return Loader.INSTANCE;
	}
	
	
	public void setUiInterface(UiCallbackInterface ui) {
		this.ui = ui;
		this.slider = ui.getSlider();
		this.volumeSlider = ui.getVolumeSlider();
		this.endOfMediaListener = new Runnable() {
	       public void run() {
	    	   if(repeat) {
	    		   ui.advance();
	    	   } else {
	    		   ui.stop();
	    	   }
	       }
		};
		this.currentTimeListener = (o) -> {
			slider.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
		};
		this.sliderValueListener = (o) -> {
			if(slider.isPressed()) {
				player.seek(player.getMedia().getDuration().multiply(slider.getValue()/100));
			}
		};
		// Adjust the current time of the video after the time slider is clicked. 
		this.slider.valueProperty().addListener(sliderValueListener);
		
		this.volumeSliderliderValueListener = (o) -> {
			double v = volumeSlider.getValue()/100;
			if(player != null) player.setVolume(v);
			ui.updateVolume(v);
		};
		
		this.volumeSlider.valueProperty().addListener(volumeSliderliderValueListener);
		
		ui.setForEmptyPlaylist();
	}
	
	
	public void addMediaFile(String filename) {
		System.out.println("Adding file " + filename + "\n");
		MediaFile mf = new MP3File(filename);
		playlist.add(mf);
		
		if(playlist.size() == 1) {
			ui.setForNonEmptyPlaylist();
			currentMediaFile = playlist.get(0);
			preparePlayer();
			play();
		}
	}
	
	public void removeMediaFile(int index) {
		System.out.println("Removing file " + playlist.get(index).getFileName());
		if(currentMediaFile == playlist.get(index)) {
			cleanUpPlayer();
			if(playlist.size() == 1) {
				ui.setForEmptyPlaylist();
				currentMediaFile = null;
			} else {
				currentMediaFile = index == playlist.size() - 1 ? playlist.get(index - 1) : playlist.get(index + 1);
				preparePlayer();
			}
			ui.getPlayButton().setVisible(true);
			ui.getPauseButton().setVisible(false);
		}
		playlist.remove(index);
	}
	
	public boolean isPlaylistEmpty() {
		return playlist.size() == 0;
	}
	
	
	public void preparePlayer() {
		try {
			File f = new File(currentMediaFile.getFileName());
			media = new Media(f.toURI().toURL().toExternalForm());
			player = new MediaPlayer(media);
			player.setVolume(volumeSlider.getValue()/100);
			
			// Set end of media listener
			player.setOnEndOfMedia(endOfMediaListener);
			
			// Synchronize the current time of the video and the time slider.
			player.currentTimeProperty().addListener(currentTimeListener);
		} catch (MalformedURLException e) {
			System.out.println("Problem playing file " + currentMediaFile.getFileName());
			System.out.println(e);
		}
	}
	
	public void cleanUpPlayer() {
		stop();
		slider.setValue(Duration.ZERO.toMillis());
		player.dispose();
		player.setOnEndOfMedia(null);
		player.currentTimeProperty().removeListener(currentTimeListener);
	}
	
	public void play() {
		System.out.println("media play");
		player.play();
	}
	
	public void pause() {
		System.out.println("media pause");
		player.pause();
	}
	
	public void stop() {
		System.out.println("media stop");
		player.stop();
		player.seek(Duration.ZERO);
	}
	
	
	public void next() {
		cleanUpPlayer();
		int i = playlist.indexOf(currentMediaFile);
		int nextI = 0;
		if(!random) {
			nextI = i < playlist.size() - 1 ? i + 1 : 0;
		} else {
			nextI = (int) (Math.random() * playlist.size() - 1);
		}
		currentMediaFile = playlist.get(nextI);
		ui.updatePlayingSong(nextI);
		preparePlayer();
		System.out.println("New file is " + currentMediaFile.getTitle());
	}
	
	public void previous() {
		cleanUpPlayer();
		int i = playlist.indexOf(currentMediaFile);
		int prevI = 0;
		if(!random) {
			prevI = i > 0 ? i - 1 : playlist.size() - 1;
		} else {
			prevI = (int) (Math.random() * playlist.size() - 1);
		}
		currentMediaFile = playlist.get(prevI);
		ui.updatePlayingSong(prevI);
		preparePlayer();
		System.out.println("New file is " + currentMediaFile.getTitle());
	}

	
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	
	public void setRandom(boolean random) {
		this.random = random;
	}
	
	
	
	

}
