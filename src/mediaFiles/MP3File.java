package mediaFiles;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MP3File implements MediaFile{
	
	private MediaFileState state;
	
	private String fileName;
	private Player player;
	private Thread playThread;
	
	private String title;
	private String artist;
	private String album;
	private int year;	
	
	public MP3File(String fileName) {
		this.state = MediaFileState.STOPPED;
		
		this.fileName = fileName;
		
		this.title = "Undefined"; // TODO fix when we have a parser
		this.artist = "Undefined";
		this.album = "Undefined";
		this.year = 1991;
		
		
	}
	
	
	@Override
	public void playPause() {
		
		if(state == MediaFileState.STOPPED) {
			System.out.println("Playing MP3 file " + fileName);
			try {
				FileInputStream fis = new FileInputStream(fileName);
				BufferedInputStream bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} catch (Exception e) {
				System.out.println("Problem playing file " + fileName);
				System.out.println(e);
			}
			
			// run in new thread to play in background
			playThread = new Thread() {
				public void run() {
					try { player.play(); }
					catch (Exception e) { System.out.println(e); }
				}
			};
			playThread.start();
			state = MediaFileState.PLAYING;
		} else if(state == MediaFileState.PAUSED) {
			System.out.println("Resuming MP3 file " + fileName);
			playThread.resume();
			state = MediaFileState.PLAYING;
		} else if(state == MediaFileState.PLAYING) {
			System.out.println("Pausing MP3 file " + fileName);
			state = MediaFileState.PAUSED;
			playThread.suspend();
		}
		
	}
	
	@Override
	public void stop() {
		System.out.println("Stopping MP3 file " + fileName);
		
		playThread.stop();
		if(player != null) player.close();
		
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
	}

	
}
