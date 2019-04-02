package playerCore;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Slider;
import mediaFiles.MediaFile;

public interface UiCallbackInterface {
	
	public Slider getSlider();
	public FontAwesomeIcon getPlayButton();
	public FontAwesomeIcon getPauseButton();
	public void advance();
	public boolean isCurrentFile(MediaFile mf);

}
