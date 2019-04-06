package playerCore;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Slider;

public interface UiCallbackInterface {
	
	public Slider getSlider();
	public FontAwesomeIcon getPlayButton();
	public FontAwesomeIcon getPauseButton();
	public void advance();
	public void stop();
	public FontAwesomeIcon getVolumeIcon();
	public Slider getVolumeSlider();
	public void updateVolume(double volume);
	public void updatePlayingSong(int songIndex);
	

}
