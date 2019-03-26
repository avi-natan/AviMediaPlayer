package playerCore;

import java.util.ArrayList;
import java.util.List;

public class Manager {
	
	private int nextFreeId;
	
	private List<Song> playlist;
	private Song currentSong;

	public Manager() {
		this.nextFreeId = 1; 
		this.playlist = new ArrayList<Song>();
		this.currentSong = null;
	}
	
	
	public void addSong(String filename) {
		Song song = new Song(filename, nextFreeId);
		System.out.println("Adding song " + song.getName() + ", song number is " + song.getSongId() + "\n");
		playlist.add(song);
		nextFreeId++;
		
		if(playlist.size() == 1) {
			currentSong = playlist.get(0);
		}
	}
	
	public void removeSong(int index) {
		System.out.println("Removing song " + playlist.get(index).getName());
		playlist.remove(index);
	}
	
	
	public void play() {
		currentSong.play();
	}
	
	public void pause() {
		currentSong.pause();
	}
	
	public void stop() {
		currentSong.stop();
	}
	
	
	public void displayCurrentSong() {
		System.out.println("Current song: " + currentSong.getName());
	}
	
	public void listSongs() {
		for(int i = 0; i < playlist.size(); i++) {
			System.out.println("[" + i + "] " + playlist.get(i).getName());
		}
	}
	
	
	public void next() {
		System.out.println("Changing to next song...");
		int i = playlist.indexOf(currentSong);
		currentSong = i < playlist.size() - 1 ? playlist.get(i + 1) : playlist.get(0);
		System.out.println("New song is " + currentSong.getName());
	}
	
	public void previous() {
		System.out.println("Changing to previous song...");
		int i = playlist.indexOf(currentSong);
		currentSong = i > 0 ? playlist.get(i - 1) : playlist.get(playlist.size() - 1);
		System.out.println("New song is " + currentSong.getName());
	}
	
	
	
	

}
