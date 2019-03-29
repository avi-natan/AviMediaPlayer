package mediaFiles;

public interface MediaFile {
	
	public void playPause();
	public void stop();
	
	public MediaFileState getState();
	public void setState(MediaFileState state);
	
	public String getFileName();
	public void setFileName(String fileName);
	
	public String getTitle();
	public void setTitle(String title);
	
	public String getArtist();
	public void setArtist(String artist);
	
	public String getAlbum();
	public void setAlbum(String album);
	
	public int getYear();
	public void setYear(int year);

}
