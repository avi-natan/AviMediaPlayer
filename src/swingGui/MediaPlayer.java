package swingGui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Slider;
import mediaFiles.MediaFile;
import playerCore.Manager;
import playerCore.UiCallbackInterface;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Insets;

public class MediaPlayer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JPanel contentPane;
	private Manager manager = Manager.getInstance();
	
	private JButton btnPlayPause;
	private JButton btnStop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MediaPlayer frame = new MediaPlayer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MediaPlayer() {
		setTitle("Avi Media Player");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 348);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File...");
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Open a file choosing dialog
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null); // TODO: fails if its closed. fix this
				File selectedFile = fileChooser.getSelectedFile();
				
				//System.out.println("Selected file: " + selectedFile.getAbsolutePath());
				
				// create an mp3 file based on the file that the user selects
				manager.addMediaFile(selectedFile.getAbsolutePath(), new UiCallbackInterface() {
					
					@Override
					public Slider getSlider() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public FontAwesomeIcon getPlayButton() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public FontAwesomeIcon getPauseButton() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public void advance() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public boolean isCurrentFile(MediaFile mf) {
						// TODO Auto-generated method stub
						return false;
					}
				});
				
				if(!manager.isPlaylistEmpty()) {
					btnPlayPause.setEnabled(true);
					btnStop.setEnabled(true);
				}
			}
			
		});
		mnFile.add(mntmOpenFile);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnPlayPause = new JButton("Play/Pause");
		btnPlayPause.setMargin(new Insets(2, 2, 2, 2));
		btnPlayPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.playPause();
			}
		});
		btnPlayPause.setBounds(235, 254, 89, 23);
		contentPane.add(btnPlayPause);
		btnPlayPause.setEnabled(false);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.stop();
			}
		});
		btnStop.setBounds(136, 254, 89, 23);
		contentPane.add(btnStop);
		btnStop.setEnabled(false);
	}

}
