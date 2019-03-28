package playerCore;

import java.util.ArrayList;
import java.util.List;

import mediaFiles.MediaFile;
import mediaFiles.MP3File;

public class Manager {
	
	private int nextFreeId;
	
	private List<MediaFile> playlist;
	private MediaFile currentMediaFile;
	
	
	private static class Loader {
		static final Manager INSTANCE = new Manager();
	}
	
	private Manager() {
		this.nextFreeId = 1; 
		this.playlist = new ArrayList<MediaFile>();
		this.currentMediaFile = null;
	}

	public static Manager getInstance() {
		return Loader.INSTANCE;
	}
	
	
	public void addMediaFile(String filename) {
		MediaFile file = new MP3File(filename, nextFreeId);
		System.out.println("Adding file " + file.getName() + ", file number is " + file.getId() + "\n");
		playlist.add(file);
		nextFreeId++;
		
		if(playlist.size() == 1) {
			currentMediaFile = playlist.get(0);
		}
	}
	
	public void removeMediaFile(int index) {
		System.out.println("Removing file " + playlist.get(index).getName());
		playlist.remove(index);
	}
	
	
	public void play() {
		currentMediaFile.play();
	}
	
	public void pause() {
		currentMediaFile.pause();
	}
	
	public void stop() {
		currentMediaFile.stop();
	}
	
	
	public void displayCurrentMediaFile() {
		System.out.println("Current media file: " + currentMediaFile.getName());
	}
	
	public void listMediaFiles() {
		for(int i = 0; i < playlist.size(); i++) {
			System.out.println("[" + i + "] " + playlist.get(i).getName());
		}
	}
	
	
	public void next() {
		System.out.println("Changing to next file...");
		int i = playlist.indexOf(currentMediaFile);
		currentMediaFile = i < playlist.size() - 1 ? playlist.get(i + 1) : playlist.get(0);
		System.out.println("New file is " + currentMediaFile.getName());
	}
	
	public void previous() {
		System.out.println("Changing to previous file...");
		int i = playlist.indexOf(currentMediaFile);
		currentMediaFile = i > 0 ? playlist.get(i - 1) : playlist.get(playlist.size() - 1);
		System.out.println("New file is " + currentMediaFile.getName());
	}
	
	
	
	

}
