package mediaFiles;

public class MP3File implements MediaFile{
	
	private int id;
	private MediaFileState state;
	
	private String name;
	private String artist;
	private String album;
	private int year;	
	
	public MP3File(String filename, int id) {
		this.id = id;
		this.state = MediaFileState.STOPPED;
		
		this.name = filename; // TODO fix when we have a parser
		this.artist = "Undefined";
		this.album = "Undefined";
		this.year = 1991;
	}
	
	@Override
	public void play() {
		System.out.println("Playing MP3 file " + id);
	}
	
	@Override
	public void pause() {
		System.out.println("MP3 file " + id + " paused");
	}
	
	@Override
	public void stop() {
		System.out.println("MP3 file " + id + " stopped");
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
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
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
		System.out.println("finilizing MP3 file " + this.name);
	}
	
}
