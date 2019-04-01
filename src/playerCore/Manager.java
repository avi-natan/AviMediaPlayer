package playerCore;

import java.util.ArrayList;
import java.util.List;

import mediaFiles.MediaFile;
import mediaFiles.MP3File;

public class Manager {
	
	private List<MediaFile> playlist;
	private MediaFile currentMediaFile;
	
	
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
	
	
	public void addMediaFile(String filename, UiCallbackInterface c) {
		MediaFile file = new MP3File(filename, c);
		System.out.println("Adding file " + file.getTitle() + ", file path is " + file.getFileName() + "\n");
		playlist.add(file);
		
		if(playlist.size() == 1) {
			currentMediaFile = playlist.get(0);
		}
	}
	
	public void removeMediaFile(int index) {
		System.out.println("Removing file " + playlist.get(index).getTitle());
		if(currentMediaFile == playlist.get(index)) {
			currentMediaFile.stop();
			if(playlist.size() == 1) {
				currentMediaFile = null;
			} else {
				currentMediaFile = index == playlist.size() - 1 ? playlist.get(index - 1) : playlist.get(index + 1);
			}
		}
		playlist.remove(index);
	}
	
	public boolean isPlaylistEmpty() {
		return playlist.size() == 0;
	}
	
	
	public void playPause() {
		if(currentMediaFile != null) {
			currentMediaFile.playPause();
		}
	}
	
	public void stop() {
		if(currentMediaFile != null) currentMediaFile.stop();
	}
	
	
	public void displayCurrentMediaFile() {
		System.out.println("Current media file: " + currentMediaFile.getTitle());
	}
	
	public void listMediaFiles() {
		for(int i = 0; i < playlist.size(); i++) {
			System.out.println("[" + i + "] " + playlist.get(i).getTitle());
		}
	}
	
	
	public void next() {
		System.out.println("Changing to next file...");
		int i = playlist.indexOf(currentMediaFile);
		currentMediaFile = i < playlist.size() - 1 ? playlist.get(i + 1) : playlist.get(0);
		System.out.println("New file is " + currentMediaFile.getTitle());
	}
	
	public void previous() {
		System.out.println("Changing to previous file...");
		int i = playlist.indexOf(currentMediaFile);
		currentMediaFile = i > 0 ? playlist.get(i - 1) : playlist.get(playlist.size() - 1);
		System.out.println("New file is " + currentMediaFile.getTitle());
	}
	
	
	
	

}
