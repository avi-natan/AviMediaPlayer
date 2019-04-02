package mediaFiles;

public class MP3File implements MediaFile{
	
	private String fileName;
	
	private String title;
	private String artist;
	private String album;
	private String year;	
	
	public MP3File(String fileName) {
		
		this.fileName = fileName;
		
		this.title = "Undefined"; // TODO fix when we have a parser
		this.artist = "Undefined";
		this.album = "Undefined";
		this.year = "1991";
		
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
	public String getYear() {
		return year;
	}

	@Override
	public void setYear(String year) {
		this.year = year;
	}
	
	
	public void finalize() {
		System.out.println("finilizing MP3 file " + this.title);
	}

	
}
