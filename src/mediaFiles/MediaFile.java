package mediaFiles;

public interface MediaFile {
	
	public void play();
	public void pause();
	public void stop();
	
	public MediaFileState getState();
	public void setState(MediaFileState state);
	
	public int getId();
	public void setId(int id);
	
	public String getName();
	public void setName(String name);
	
	public String getArtist();
	public void setArtist(String artist);
	
	public String getAlbum();
	public void setAlbum(String album);
	
	public int getYear();
	public void setYear(int year);

}
