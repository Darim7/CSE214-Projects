//package hw5;

import java.util.Scanner;

/**
 * Creates a <code>BashTerminal</code> which allows a userto
 * interact with a file system implemented by an instance of
 * DirectoryTree
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class BashTerminal {

	public static void main(String[] args) {
		Boolean exit = false;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Starting bash terminal.");
		DirectoryTree tree = new DirectoryTree();
		do {
			System.out.print("[zling@pathToRoot]: $ ");
			String command = input.nextLine();
			// Print the "present working directory" of the
			// cursor node (e.g root/home/user/Documents).
			if (command.equals("pwd")) { 
				System.out.println(tree.presentWorkingDirectory());
			}
			// List the names of all the child directories or files of the cursor.
			else if (command.equals("ls")) {
				System.out.println(tree.listDirectory());
			}
			// Recursive traversal of the directory tree. Prints the entire tree
			// starting from the cursor in pre-order traversal.
			else if (command.equals("ls -R")) {
				System.out.println();
				tree.printDirectoryTree();
				System.out.println();
			}
			// Moves the cursor to the root of the tree.
			else if (command.equals("cd /")) {
				tree.resetCursor();
			}
			// Moves the cursor to the parent directory.
			else if (command.equals("cd ..")) {
				try {
					tree.moveToParent();
				} catch (NotADirectoryException e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			// Moves the cursor to the input path.
			else if (command.matches("cd [\\S]+[/][\\S]+")) {
				try {
					tree.moveToDirectory(extractName(command));
				} catch (NotADirectoryException e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			// Moves the cursor to the child directory with the indicated name
			// (Only consider the direct children of the cursor).
			else if (command.matches("cd [\\S]+")) {
				try {;
					tree.changeDirectory(extractName(command));
				} catch (NotADirectoryException | IllegalArgumentException e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			// Creates a new directory with the indicated name as a child of the
			// cursor, as long as there is room.
			else if (command.matches("mkdir [\\S]+")) {
				try {
					tree.makeDirectory(extractName(command));
				} catch (IllegalArgumentException | FullDirectoryException
						| NotADirectoryException e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			// Creates a new file with the indicated name as a child of the cursor,
			// as long as there is room.
			else if (command.matches("touch [\\S]+")) {
				try {
					tree.makeFile(extractName(command));
				} catch (IllegalArgumentException | FullDirectoryException
						| NotADirectoryException e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			else if (command.matches("find [\\S]+")) {
				System.out.println(tree.pathToNode(extractName(command)));
			}
			//TODO: Implement this.
			else if (command.matches("mv [\\S]+[/][\\S]+ [\\S]+[/]?[\\S]+")) {
				System.out.println("Feature not implemented yet.");
			}
			// Terminates the program.
			else if (command.equals("exit")) {
				exit = true;
			}			
			else {
				System.out.println("Please enter a valid command.");
				continue;
			}
		} while(!exit);
		System.out.print("Program terminating normally");
		input.close();
	}
	
	/**
	 * Helper method to get the name that the user input.
	 * 
	 * @param cmd
	 * @return
	 */
	private static String extractName(String cmd) {
		String[] cmdArr = cmd.split("[\\s]");
		if (cmdArr.length > 2) {
			throw new IllegalArgumentException("Name should not contain spaces.");
		}
		return cmdArr[1];
	}

}
