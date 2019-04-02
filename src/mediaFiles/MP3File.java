package mediaFiles;

import java.io.File;

import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import playerCore.UiCallbackInterface;

public class MP3File implements MediaFile{
	
	private MediaFileState state;
	
	private String fileName;
	private Media media;
	private MediaPlayer player;
	
	private String title;
	private String artist;
	private String album;
	private int year;	
	
	public MP3File(String fileName, UiCallbackInterface c) {
		this.state = MediaFileState.STOPPED;
		
		this.fileName = fileName;
		try {
			File f = new File(fileName);
			this.media = new Media(f.toURI().toURL().toExternalForm());
			this.player = new MediaPlayer(media);
			
			player.setVolume(0.1);
			
			player.setOnEndOfMedia(new Runnable() {
		       public void run() {
		    	   c.advance();
		       }
			});
			
			
			Slider slider = c.getSlider();
			// Synchronize the current time of the video and the time slider.
			player.currentTimeProperty().addListener((o) -> {
				System.out.println(player.getCurrentTime().toMillis());
				slider.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
			});
			
			// Adjust the current time of the video after the time slider is clicked. 
			slider.valueProperty().addListener((o) -> {
				if(slider.isPressed()) {
					if(c.isCurrentFile(this)) {
						System.out.println("pressed!" + fileName);
						player.seek(player.getMedia().getDuration().multiply(slider.getValue()/100));
					}
				}
			});
			
		} catch (Exception e) {
			System.out.println("Problem playing file " + fileName);
			System.out.println(e);
		}
		
		this.title = "Undefined"; // TODO fix when we have a parser
		this.artist = "Undefined";
		this.album = "Undefined";
		this.year = 1991;
		
	}
	
	
	@Override
	public void playPause() {
		
		if(state == MediaFileState.STOPPED) {
			System.out.println("Playing MP3 file " + fileName);
			player.play();
			state = MediaFileState.PLAYING;
		} else if(state == MediaFileState.PAUSED) {
			System.out.println("Resuming MP3 file " + fileName);
			player.play();
			state = MediaFileState.PLAYING;
		} else if(state == MediaFileState.PLAYING) {
			System.out.println("Pausing MP3 file " + fileName);
			player.pause();
			state = MediaFileState.PAUSED;
		}
		
	}
	
	@Override
	public void stop() {
		System.out.println("Stopping MP3 file " + fileName);
		
		if(player != null) {
			player.stop();
			player.seek(Duration.ZERO);
		}
		
		state = MediaFileState.STOPPED;
		
	}
	

	@Override
	public MediaFileState getState() {
		return state;
	}
	
	@Override
	public void setState(MediaFileState state) {
		this.state = state;
	}
	
	@Override
	public String getFileName() {
		return this.fileName;
	}
	
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	
	@Override
	public String getArtist() {
		return artist;
	}

	@Override
	public void setArtist(String artist) {
		this.artist = artist;
	}

	
	@Override
	public String getAlbum() {
		return album;
	}

	@Override
	public void setAlbum(String album) {
		this.album = album;
	}

	
	@Override
	public int getYear() {
		return year;
	}

	@Override
	public void setYear(int year) {
		this.year = year;
	}
	
	
	public void finalize() {
		System.out.println("finilizing MP3 file " + this.title);
		player.stop();
		player.dispose();
	}

	
}
