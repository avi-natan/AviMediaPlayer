package javaFxGui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import playerCore.Manager;

public class UiController implements Initializable{

	private double xOffset = 0;
    private double yOffset = 0;
    private static final int DEFAULT_STARTING_X_POSITION = 0;
    private static final int DEFAULT_ENDING_X_POSITION = -120;
    private AnimationGenerator animationGenerator = null;
    
    private FileChooser fileChooser;
    private Manager manager;
    
    @FXML
    private FontAwesomeIcon btn_remove;
    
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
	
	@FXML
    private ListView<Label> listview;
	
	private Label selectedLabel;
	
	private Label playingLabel;
	
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
		File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
		if(selectedFile != null) {
			Label lb = new Label();
			lb.setText(selectedFile.getName());
			lb.setMaxWidth(listview.getWidth()-2);
			lb.setTextFill(Color.BLACK);
			lb.setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		            System.out.println("clicked on " + lb.getText());  // TODO: cleanup
		            if(selectedLabel != null) {
		            	selectedLabel.setStyle(listview.getItems().indexOf(selectedLabel) % 2 == 1 ? (selectedLabel == playingLabel ? "-fx-font-weight: bold; -fx-background-color: #F6CE8E;" : "-fx-background-color: #F6CE8E;") : (selectedLabel == playingLabel ? "-fx-font-weight: bold; -fx-background-color: #F0AF46;" : "-fx-background-color: #F0AF46;"));
		            }
		            selectedLabel = lb;
		            selectedLabel.setStyle(selectedLabel == playingLabel ? "-fx-font-weight: bold; -fx-background-color: #93abc3;" : "-fx-background-color: #93abc3;");
		            btn_remove.setDisable(false);
		            btn_remove.setOpacity(1.0);
		        }
		    });
			listview.getItems().add(lb);
			manager.addMediaFile(selectedFile.getAbsolutePath());
			if(listview.getItems().size() == 1) {
				playingLabel = lb;
				playingLabel.setTextFill(Color.web("#4e6d8d"));
				playingLabel.setStyle("-fx-font-weight: bold;");
				btn_play.setVisible(false);
				btn_pause.setVisible(true);
				manager.playPause();
			}
		}
	}
	
	@FXML
	private void remove_selected_file(MouseEvent event) {
		int toBeDeletedIndex = listview.getItems().indexOf(selectedLabel);
		System.out.println(toBeDeletedIndex);
		
		if(playingLabel == selectedLabel) {
			if(listview.getItems().size() == 1) {
				playingLabel = null;
			} else {
				playingLabel = toBeDeletedIndex == listview.getItems().size() - 1 ? listview.getItems().get(toBeDeletedIndex - 1) : listview.getItems().get(toBeDeletedIndex + 1);
				playingLabel.setTextFill(Color.web("#4e6d8d"));
				playingLabel.setStyle("-fx-font-weight: bold;");
			}
			btn_pause.setVisible(false);
			btn_play.setVisible(true);
		}
		
		manager.removeMediaFile(toBeDeletedIndex);
		listview.getItems().remove(selectedLabel);
		selectedLabel = null;
		btn_remove.setDisable(true);
        btn_remove.setOpacity(0.5);
	}
	
	@FXML
	private void next_song(MouseEvent event) {
		int playingIndex = listview.getItems().indexOf(playingLabel);
		playingLabel.setTextFill(Color.BLACK);
		playingLabel.setStyle(playingLabel == selectedLabel ? "-fx-font-weight: normal; -fx-background-color: #93abc3;" : "-fx-font-weight: normal;");
		playingLabel = (playingIndex == listview.getItems().size() - 1) ? listview.getItems().get(0) : listview.getItems().get(playingIndex+1);
		playingLabel.setTextFill(Color.web("#4e6d8d"));
		playingLabel.setStyle(playingLabel == selectedLabel ? "-fx-font-weight: bold; -fx-background-color: #93abc3;" : "-fx-font-weight: bold;");
		btn_play.setVisible(false);
		btn_pause.setVisible(true);
		manager.stop();
		manager.next();
		manager.playPause();
	}
	
	@FXML
	private void previous_song(MouseEvent event) {
		int playingIndex = listview.getItems().indexOf(playingLabel);
		playingLabel.setTextFill(Color.BLACK);
		playingLabel.setStyle(playingLabel == selectedLabel ? "-fx-font-weight: normal; -fx-background-color: #93abc3;" : "-fx-font-weight: normal;");
		playingLabel = (playingIndex == 0) ? listview.getItems().get(listview.getItems().size() - 1) : listview.getItems().get(playingIndex-1);
		playingLabel.setTextFill(Color.web("#4e6d8d"));
		playingLabel.setStyle(playingLabel == selectedLabel ? "-fx-font-weight: bold; -fx-background-color: #93abc3;" : "-fx-font-weight: bold;");
		btn_play.setVisible(false);
		btn_pause.setVisible(true);
		manager.stop();
		manager.previous();
		manager.playPause();
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
