package javaFxGui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import playerCore.Manager;

public class UiController implements Initializable{

	private double xOffset = 0;
    private double yOffset = 0;
    private static final int DEFAULT_STARTING_X_POSITION = 0;
    private static final int DEFAULT_ENDING_X_POSITION = -120;
    AnimationGenerator animationGenerator = null;
    FileChooser fileChooser;
    Manager manager;
    
    @FXML
    private FontAwesomeIcon btn_play;

    @FXML
    private FontAwesomeIcon btn_pause;
    
    @FXML
    private FontAwesomeIcon btn_stop;
    
    @FXML
    private FontAwesomeIcon btn_step_backward;
    
    @FXML
    private FontAwesomeIcon btn_step_forward;
    
	@FXML
	private HBox parent;
	
	@FXML
    private Pane sidebar;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDrageable();
		sidebar.setVisible(false);
		animationGenerator = new AnimationGenerator();
		fileChooser = new FileChooser();
		manager = Manager.getInstance();
	}
	
	public void makeStageDrageable() {
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Launch.stage.setX(event.getScreenX() - xOffset);
                Launch.stage.setY(event.getScreenY() - yOffset);
                Launch.stage.setOpacity(0.7f);
            }
        });
        parent.setOnDragDone((e) -> {
            Launch.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((e) -> {
            Launch.stage.setOpacity(1.0f);
        });
    }
	
	@FXML
    private void open_or_close_sidebar(MouseEvent event) {
    	if(!sidebar.isVisible()) {
    		sidebar.setVisible(true);
            animationGenerator.applyTranslateAnimationOn(sidebar, 500, DEFAULT_ENDING_X_POSITION, DEFAULT_STARTING_X_POSITION);
            animationGenerator.applyFadeAnimationOn(sidebar, 1000, 0f, 1.0f, null);
    	} else {
    		animationGenerator.applyFadeAnimationOn(sidebar, 1000, 1.0f, 0.0f, (e) -> {
                sidebar.setVisible(false);
            });
    	}
    }
	
	@FXML
	private void open_file(MouseEvent event) {
		File selectedFile = fileChooser.showOpenDialog(null);
		manager.addMediaFile(selectedFile.getAbsolutePath());
	}
	
	@FXML
	private void next_song(MouseEvent event) {
		manager.next();
	}
	
	@FXML
	private void previous_song(MouseEvent event) {
		manager.previous();
	}
	
	@FXML
	private void play_current_song(MouseEvent event) {
		btn_play.setVisible(false);
		btn_pause.setVisible(true);
		manager.playPause();
	}
	
	@FXML
	private void pause_current_song(MouseEvent event) {
		btn_pause.setVisible(false);
		btn_play.setVisible(true);
		manager.playPause();
	}
	
	@FXML
	private void stop_current_song(MouseEvent event) {
		btn_pause.setVisible(false);
		btn_play.setVisible(true);
		manager.stop();
	}
    
    @FXML
    private void close_app(MouseEvent event) {
    	System.exit(0);
    }

}
