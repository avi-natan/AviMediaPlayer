package playerCore;

public class Song {
	
	private int songId;
	private SongState state;
	
	private String name;
	private String artist;
	private String album;
	private int year;	
	
	public Song(String filename, int songId) {
		this.songId = songId;
		this.state = SongState.STOPPED;
		
		this.name = filename; // TODO fix when we have a parser
		this.artist = "Undefined";
		this.album = "Undefined";
		this.year = 1991;
	}

	public void play() {
		System.out.println("Playing song " + songId);
	}
	
	public void pause() {
		System.out.println("Song " + songId + " paused");
	}
	
	public void stop() {
		System.out.println("Song " + songId + " stopped");
	}
	
	

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}
	
	public SongState getState() {
		return state;
	}
	
	

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void setState(SongState state) {
		this.state = state;
	}
	
	
	
	public void finalize() {
	}
	
}
