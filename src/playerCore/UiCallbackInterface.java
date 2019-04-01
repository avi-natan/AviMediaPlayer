package playerCore;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Slider;

public interface UiCallbackInterface {
	
	public Slider getSlider();
	public FontAwesomeIcon getPlayButton();
	public FontAwesomeIcon getPauseButton();

}