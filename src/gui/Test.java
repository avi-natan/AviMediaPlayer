package gui;

import java.util.Scanner;

import playerCore.Manager;

public class Test {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		Manager manager = Manager.getInstance();

		int menuChoice = -1;
		while(menuChoice != 0) {
			
			printMenu();
			
			menuChoice = s.nextInt();
			
			switch(menuChoice) {
			case 1:
				System.out.print("Enter song filename: ");
				s = new Scanner(System.in);
				String filename = s.nextLine();
				manager.addSong(filename);
				break;
			case 2:
				manager.listSongs();
				System.out.print("Select song to remove: ");
				s = new Scanner(System.in);
				int songIndex = s.nextInt(); // TODO: input guard
				manager.removeSong(songIndex);
				break;
			case 3:
				manager.displayCurrentSong();
				break;
			case 4:
				manager.previous();
				break;
			case 5:
				manager.next();
				break;
			}
		}
		
		s.close();

	}
	
	
	static void printMenu() {
		System.out.println("========== Menu ===========\n"
				+ "[0] Quit\n"
				+ "[1] Add song to playlist\n"
				+ "[2] Remove song from playlist\n"
				+ "[3] Display current song\n"
				+ "[4] Previous song\n"
				+ "[5] Next song\n");
	}

}
