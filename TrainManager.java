package hw2_TrainManager;

import java.util.Scanner;
/**
 * 
 * @author Zhenting Ling
 *      CSE214.RO1
 *      e-mail: zhenting.ling@stonybrook.edu
 *      Stony Brook ID: 114416612
 *
 */
public class TrainManager {
	
	/**
	 * The main method runs a menu driven application which first 
	 * creates an empty TrainLinkedList object. The program 
	 * prompts the user for a command to execute an operation.
	 * Once a command has been chosen, the program may ask the user
	 * for additional information if necessary, and performs
	 * the operation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TrainlinkedList train = new TrainlinkedList();
		String menu = "(F) Cursor Forward\n"
				+ "(B) Cursor Backward\n"
				+ "(I) Insert Car After Cursor\n"
				+ "(R) Remove Car At Cursor\n"
				+ "(L) Set Product Load\n"
				+ "(S) Search For Product\n"
				+ "(T) Display Train\n"
				+ "(M) Display Manifest\n"
				+ "(D) Remove Dangerous Cars\n"
				+ "(Q) Quit";
		
		System.out.println(menu);
		
		String choice = "";
		
		Scanner input = new Scanner(System.in);
		
		do {
			System.out.print("\nEnter a selection: ");
			choice = input.nextLine().toUpperCase();
			
			switch (choice) {
			// Cursor Forward
			case "F":
				train.cursorForward("");
				break;
				
			// Cursor Backward
			case "B":
				train.cursorBackward("");
				break;
				
			// Insert Car After Cursor
			case "I":
				System.out.print("\nEnter car length in meters: ");
				double carLength = Double.parseDouble(input.nextLine()); // Should we expect user to input correctly?
				System.out.print("Enter car weight in tons: ");
				double carWeight = Double.parseDouble(input.nextLine());
				try {
					train.insertAfterCursor(new TrainCar(carLength, carWeight));
				} catch (IllegalArgumentException e) {
					System.out.println("\nPlease enter a valid number.");
					continue;
				}
				System.out.println(new String(String.format
						("\nNew train car %.1f meters %.1f tons "
								+ "inserted into train.",
								carLength, carWeight)));
				break;
				
			// Remove Car At Cursor
			case "R":
				ProductLoad pTemp = train.removeCursor().getProductLoad();
				System.out.println("\nCar successfully unlinked. "
						+ "The following load has been removed "
						+ "from the train:");
				if (pTemp == null) {
					System.out.println(train.showEmptyCar());
				} else {
					System.out.println(train.showProduct(pTemp.getName(), pTemp.getWeight(), 
						pTemp.getValue(), pTemp.isDangerous()));
				}
				break;
				
			// Set Product Load
			case "L":
				System.out.print("\nEnter produce name: ");
				String name = input.nextLine();
				System.out.print("Enter product weight in tons: ");
				double weight = Double.parseDouble(input.nextLine());
				System.out.print("Enter product value in dollars: ");
				double value = Double.parseDouble(input.nextLine());
				System.out.print("Enter is product dangerous? (y/n): ");
				String dangerous = input.nextLine().toUpperCase();
				boolean isDangerous;
				if (dangerous.equals("Y"))
					isDangerous = true;
				else if (dangerous.equals("N"))
					isDangerous = false;
				else {
					System.out.println("\nPlease enter a valid "
							+ "character (y/n).");
					continue;
				}
				try {
					ProductLoad p = new ProductLoad(name, weight,
							value, isDangerous);
					train.setCursorCarLoad(p);
				} catch (IllegalArgumentException e) {
					System.out.println("\nPlease enter and valid "
							+ "input for weight/value.");
					continue;
				}
				String pMsg = String.format("\n%.1f tons of %s added"
						+ " to the current car.", weight, name);
				System.out.println(pMsg);
				break;
			
			// Search For Product
			case "S":
				System.out.print("\nEnter product name: ");
				String pName = input.nextLine();
				train.findProduct(pName);
				break;
				
			// Display Train
			case "T":
				System.out.println(train.toString());
				break;
				
			// Display Manifest
			case "M":
				train.printManifest();
				break;
				
			// Remove Dangerous Cars
			case "D":
				train.removeDangerousCars();
				break;
				
			// Quit
			case "Q":
				break;
				
			// Invalid input
			default:
				System.out.println("\nPlease enter an valid selection.");
				continue;
			}
			
			
		} while (!choice.equals("Q"));
		input.close();
		System.out.print("\nProgram terminating successfully...");
	}

}
