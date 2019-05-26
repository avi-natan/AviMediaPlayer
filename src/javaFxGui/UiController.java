package javaFxGui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXSlider;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import playerCore.Manager;
import playerCore.UiCallbackInterface;

public class UiController implements Initializable{

	private double xOffset = 0;
    private double yOffset = 0;
    private static final int DEFAULT_STARTING_X_POSITION = 0;
    private static final int DEFAULT_ENDING_X_POSITION = -120;
    private AnimationGenerator animationGenerator = null;
    
    private FileChooser fileChooser;
    private Manager manager;
    
    @FXML
    private Pane sidebar;
    
    @FXML
    private ListView<Label> listview;
    
    @FXML
    private FontAwesomeIcon btn_remove;
    
    
    @FXML
	private HBox parent;
    
    @FXML
    private JFXSlider slider;
    
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
    private FontAwesomeIcon volume_icon;
    
    @FXML
    private JFXSlider volume_slider;
    
    @FXML
    private FontAwesomeIcon btn_repeat;
    
    @FXML
    private FontAwesomeIcon btn_random;
    
    @FXML
    private Label curr_song_title;
    
    
    
    private boolean repeat;
    
    private boolean random;
    
    private double volumeBeforeMute;
    
	private Label selectedLabel;
	
	private Label playingLabel;
	
	private int eventCnt;
	
	Timer timer = new Timer("doubleClickTimer", false);

	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDrageable();
		sidebar.setVisible(false);
		animationGenerator = new AnimationGenerator();
		fileChooser = new FileChooser();
		repeat = false;
		random = false;
		change_button(btn_repeat, repeat);
		change_button(btn_random, random);
		manager = Manager.getInstance();
		manager.setUiInterface(new UiCallbackInterface() {
				
				@Override
				public JFXSlider getSlider() {
					return slider;
				}
				
				@Override
				public FontAwesomeIcon getPlayButton() {
					return btn_play;
				}
				
				@Override
				public FontAwesomeIcon getPauseButton() {
					return btn_pause;
				}

				@Override
				public void advance() {
					next_song(null);
				}

				@Override
				public FontAwesomeIcon getVolumeIcon() {
					return volume_icon;
				}

				@Override
				public JFXSlider getVolumeSlider() {
					return volume_slider;
				}

				@Override
				public void updateVolume(double volume) {
					if(volume == 0.0) {
						volume_icon.setIconName("VOLUME_OFF");
					}else if(volume < 0.5) {
						volume_icon.setIconName("VOLUME_DOWN");
					} else {
						volume_icon.setIconName("VOLUME_UP");
					}
				}

				@Override
				public void stop() {
					stop_current_song(null);
				}

				@Override
				public void updatePlayingSong(int songIndex) {
					playingLabel.setTextFill(Color.BLACK);
					playingLabel.setStyle(playingLabel == selectedLabel ? "-fx-font-weight: normal; -fx-background-color: #93abc3;" : "-fx-font-weight: normal;");
					playingLabel = listview.getItems().get(songIndex);
					playingLabel.setTextFill(Color.web("#4e6d8d"));
					playingLabel.setStyle(playingLabel == selectedLabel ? "-fx-font-weight: bold; -fx-background-color: #93abc3;" : "-fx-font-weight: bold;");
					btn_play.setVisible(false);
					btn_pause.setVisible(true);
					System.out.println("UPDATING " + playingLabel.getText());
					curr_song_title.setText(playingLabel.getText());
				}

				@Override
				public void setForEmptyPlaylist() {
					btn_play.setDisable(true);
					btn_play.setOpacity(0.5);
					btn_stop.setDisable(true);
					btn_stop.setOpacity(0.5);
					btn_step_forward.setDisable(true);
					btn_step_forward.setOpacity(0.5);
					btn_step_backward.setDisable(true);
					btn_step_backward.setOpacity(0.5);
					btn_pause.setDisable(true);
					btn_pause.setOpacity(0.5);
//					btn_pause.setVisible(false);
				}
				
				@Override
				public void setForNonEmptyPlaylist() {
					btn_play.setDisable(false);
					btn_play.setOpacity(1);
					btn_stop.setDisable(false);
					btn_stop.setOpacity(1);
					btn_step_forward.setDisable(false);
					btn_step_forward.setOpacity(1);
					btn_step_backward.setDisable(false);
					btn_step_backward.setOpacity(1);
					btn_pause.setDisable(false);
					btn_pause.setOpacity(1);
//					btn_pause.setVisible(false);
				}
			});
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
		        	
		        	eventCnt = event.getClickCount();
		            if ( event.getClickCount() == 1 ) {
		                timer.schedule(new TimerTask() {
		                	
		                	@Override
		                    public void run() {
		                    	if(selectedLabel != null) {
					            	selectedLabel.setStyle(listview.getItems().indexOf(selectedLabel) % 2 == 1 ? (selectedLabel == playingLabel ? "-fx-font-weight: bold; -fx-background-color: #F6CE8E;" : "-fx-background-color: #F6CE8E;") : (selectedLabel == playingLabel ? "-fx-font-weight: bold; -fx-background-color: #F0AF46;" : "-fx-background-color: #F0AF46;"));
					            }
					            selectedLabel = lb;
					            selectedLabel.setStyle(selectedLabel == playingLabel ? "-fx-font-weight: bold; -fx-background-color: #93abc3;" : "-fx-background-color: #93abc3;");
					            btn_remove.setDisable(false);
					            btn_remove.setOpacity(1.0);
		                        if ( eventCnt == 1 ) {
		                            System.out.println( "You did a single click.");
		                            
		                        } else if ( eventCnt == 2 ) {
		                            System.out.println("you clicked doule click.");
		                            int selectedIndex = listview.getItems().indexOf(selectedLabel);
		                            System.out.println("Song number in playlis it: " + selectedIndex);
		                            Platform.runLater(new Runnable() {
		                                @Override
		                                public void run() {
		                                	manager.ithSong(selectedIndex);
		                                	manager.play();
		                                }
		                            });
		                        }
		                        eventCnt = 0;
		                    }
		                }, 200);
		            }
		        	
		        	
		        	// =============
//		        	if(event.getButton().equals(MouseButton.PRIMARY)){
//		        		if(event.getClickCount() == 2 && !event.isConsumed()){
//		        			event.consume();
//		                    System.out.println("Double clicked");
//		                } else if(event.getClickCount() == 1){
//		                    System.out.println("Single clicked");
//		                    if(selectedLabel != null) {
//				            	selectedLabel.setStyle(listview.getItems().indexOf(selectedLabel) % 2 == 1 ? (selectedLabel == playingLabel ? "-fx-font-weight: bold; -fx-background-color: #F6CE8E;" : "-fx-background-color: #F6CE8E;") : (selectedLabel == playingLabel ? "-fx-font-weight: bold; -fx-background-color: #F0AF46;" : "-fx-background-color: #F0AF46;"));
//				            }
//				            selectedLabel = lb;
//				            selectedLabel.setStyle(selectedLabel == playingLabel ? "-fx-font-weight: bold; -fx-background-color: #93abc3;" : "-fx-background-color: #93abc3;");
//				            btn_remove.setDisable(false);
//				            btn_remove.setOpacity(1.0);
//		                }
//		            }
		        	// ============
		            
		        }
		    });
			listview.getItems().add(lb);
			if(listview.getItems().size() == 1) {
				playingLabel = lb;
				playingLabel.setTextFill(Color.web("#4e6d8d"));
				playingLabel.setStyle("-fx-font-weight: bold;");
				btn_play.setVisible(false);
				btn_pause.setVisible(true);
			}
			manager.addMediaFile(selectedFile.getAbsolutePath());
		}
	}
	
	@FXML
	private void remove_selected_file(MouseEvent event) {
		int toBeDeletedIndex = listview.getItems().indexOf(selectedLabel);
		
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
		manager.next();
		manager.play();
	}
	
	@FXML
	private void previous_song(MouseEvent event) {
		manager.previous();
		manager.play();
	}
	
	@FXML
	private void play_current_song(MouseEvent event) {
		btn_play.setVisible(false);
		btn_pause.setVisible(true);
		manager.play();
	}
	
	@FXML
	private void pause_current_song(MouseEvent event) {
		btn_pause.setVisible(false);
		btn_play.setVisible(true);
		manager.pause();
	}
	
	@FXML
	private void stop_current_song(MouseEvent event) {
		btn_pause.setVisible(false);
		btn_play.setVisible(true);
		manager.stop();
	}
	
	@FXML
	private void mute(MouseEvent event) {
		double v = volume_slider.getValue();
		if(v != 0.0) {
			volumeBeforeMute = v; 
			volume_slider.setValue(0.0);
		} else {
			volume_slider.setValue(volumeBeforeMute);
		}
	}
	
	@FXML
    private void repeat(MouseEvent event) {
    	repeat = !repeat;
    	change_button(btn_repeat, repeat);
    	manager.setRepeat(repeat);
    }
	
	@FXML
    private void random(MouseEvent event) {
		random = !random;
		change_button(btn_random, random);
    	manager.setRandom(random);
    }
	
	private void change_button(FontAwesomeIcon btn, boolean val) {
		if(val) {
			btn.setFill(Color.web("#E89511"));
			btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
    		      @Override public void handle(MouseEvent mouseEvent) {
    		    	  btn.setFill(Color.web("#FFF"));
    		    	  btn.setCursor(Cursor.HAND);
    		      }
    		    });
			btn.setOnMouseExited(new EventHandler<MouseEvent>() {
    		      @Override public void handle(MouseEvent mouseEvent) {
    		    	  btn.setFill(Color.web("#E89511"));
    		    	  btn.setCursor(Cursor.HAND);
    		      }
    		    });
    	} else {
    		btn.setFill(Color.web("#fff"));
    		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
    		      @Override public void handle(MouseEvent mouseEvent) {
    		    	  btn.setFill(Color.web("#E89511"));
    		    	  btn.setCursor(Cursor.HAND);
    		      }
    		    });
    		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
    		      @Override public void handle(MouseEvent mouseEvent) {
    		    	  btn.setFill(Color.web("#FFF"));
    		    	  btn.setCursor(Cursor.HAND);
    		      }
    		    });
    	}
	}
    
    @FXML
    private void close_app(MouseEvent event) {
    	System.exit(0);
    }
    
    @FXML
    private void minimize_app(MouseEvent event) {
    	((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

}
