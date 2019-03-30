package textBasedUi;

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
				System.out.print("Enter filename: ");
				s = new Scanner(System.in);
				String filename = s.nextLine();
				manager.addMediaFile(filename);
				break;
			case 2:
				manager.listMediaFiles();
				System.out.print("Select file to remove: ");
				s = new Scanner(System.in);
				int index = s.nextInt(); // TODO: input guard
				manager.removeMediaFile(index);
				break;
			case 3:
				manager.displayCurrentMediaFile();
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
				+ "[1] Add file to playlist\n"
				+ "[2] Remove file from playlist\n"
				+ "[3] Display current file\n"
				+ "[4] Previous file\n"
				+ "[5] Next file\n");
	}

}
