package hw6;

import java.io.*;

/**
 * Creates a <code>AuctionSystem</code> which allow the user
 * to interact with the database by listing open auctions,
 * make bids on open auctions, and create new auctions for
 * different items.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class AuctionSystem implements Serializable {
	private AuctionTable auctionTable;
	private String username;
	
	/**
	 * This class will allow the user to interact with the database by
	 * listing open auctions, make bids on open auctions, and create
	 * new auctions for different items. In addition, the class
	 * should provide the functionality to load a saved (serialized)
	 * AuctionTable or create a new one if a saved table does not exist.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AuctionSystem aucSys = new AuctionSystem();
		System.out.println("Starting...");
		try {
			FileInputStream inFile = new FileInputStream("auction.obj");
			System.out.println("Loading previous Auction Table...");
			try (ObjectInputStream inStream = new ObjectInputStream(inFile)) {
				AuctionTable aucTab = (AuctionTable)inStream.readObject();
				aucSys.setAuctionTable(aucTab);
			}
		} catch (FileNotFoundException e) {
			System.out.println("No previous auction table detected.\n"
					+ "Creating new table...\n");
			aucSys.setAuctionTable(new AuctionTable());
		} catch (IOException e) {
			System.out.println("Unexpected I/O Error.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class is not found.");
		}
		
		InputStreamReader read = new InputStreamReader(System.in);
		BufferedReader input = new BufferedReader(read);
		
		String userName = (String) promptInput("\nPlease select a username: ", "str", input);
		aucSys.setUsername(userName);
		
		String choice = "";
		
		do {
			printMenu();
			choice = (String) promptInput("\nPlease select an option: ", "str", input);
			Auction auc = null;
			String aucID = null;
			switch(choice.toUpperCase()) {
			case "D": // Import Data from URL
				String url = (String) promptInput("Please enter a URL: ", "str",input);
				System.out.println("\nLoading...");
					try {
						aucSys.getAuctionTable().putAll(AuctionTable.buildFromURL(url));
					} catch (IllegalArgumentException e) {
						System.out.println(e.getLocalizedMessage());
						continue;
					}
				System.out.println("Auction data loaded successfully!");
				break;
				
			case "A": // Create a New Auction
				System.out.println(String.format("\nCreating new Auction as %s.", aucSys.getUsername()));
				aucID = (String) promptInput("Please enter an Auction ID: ", "str", input);
				int aucTime = (int) promptInput("Please enter an Auction time (hours): ", "int", input);
				String itemInfo = (String) promptInput("Please enter some Item Info: ", "str", input);
				try {
					auc = new Auction(aucID, aucSys.getUsername(), "N/A", 0, aucTime, itemInfo);
				} catch (IllegalArgumentException e1) {
					System.out.println(e1.getLocalizedMessage());
					continue;
				}
				aucSys.getAuctionTable().putAuction(aucID, auc);
				System.out.println(String.format("\nAuction %s inserted into table.", aucID));
				break;
				
			case "B": // Bid on an Item
				aucID = (String) promptInput("Please enter an Auction ID: ", "str", input);
				auc = aucSys.getAuctionTable().get(aucID);
				if (auc == null) {
					System.out.println("Auction does not exist, please enter again");
					continue;
				}
				if (auc.isClosed()) {
					System.out.println(String.format("\nAuction %s is CLOSED", auc.getAuctionID()));
					System.out.println(auc.bidInfo());
					System.out.println("\nYou can no longer bid on this item.");
				} else {
					System.out.println(String.format("\nAuction %s is OPEN", auc.getAuctionID()));
					System.out.println(auc.bidInfo());
					double bidAmt = (double) promptInput("\nWhat would you like to bid?: ", "dbl", input);
					try {
						auc.newBid(userName, bidAmt);
					} catch (ClosedAuctionException e) {
						System.out.println(e.getLocalizedMessage());
					}
				}
				break;
				
			case "I": // Get Info on Auction
				aucID = (String) promptInput("Please enter an Auction ID: ", "str", input);
				auc = aucSys.getAuctionTable().get(aucID);
				if (auc == null) {
					System.out.println("Auction does not exist, please enter again");
					continue;
				}
				System.out.println();
				System.out.println(auc);
				break;
				
			case "P": // Print All Auctions
				aucSys.getAuctionTable().printTable();
				break;
				
			case "R": // Remove Expired Auctions
				System.out.println("\nRemoving expired auctions...");
				aucSys.getAuctionTable().removeExpiredAuctions();
				System.out.println("All expired auctions removed.");
				break;
				
			case "T": // Let Time Pass
				int timePassed =(int) promptInput("How many hours should pass: ", "int", input);
				System.out.println("\nTime passing...");
				aucSys.getAuctionTable().letTimePass(timePassed);
				System.out.println("Auction times updated.");
				break;
				
			case "Q": // Quit
				System.out.println("\nWriting Auction Table to file...");
				break;
				
			default:
				System.out.println("Please enter a valid input.");
				continue;
			}
		} while (!choice.equalsIgnoreCase("Q"));
		
		
		try {
			FileOutputStream file = new FileOutputStream("auction.obj");
			try (ObjectOutputStream outStream = new ObjectOutputStream(file)) {
				outStream.writeObject(aucSys.getAuctionTable());
			}
		} catch (IOException e) {
			System.out.println("Unexpected I/O Error.");
		}
		System.out.println("Done!");
		System.out.println("\nGoodbye.");
	}
	
	/**
	 * Getter for the auction table.
	 * @return
	 */
	public AuctionTable getAuctionTable() {
		return auctionTable;
	}
	
	/**
	 * Setter for the auction table.
	 * @param aucTab
	 */
	public void setAuctionTable(AuctionTable aucTab) {
		auctionTable = aucTab;
	}
	
	/**
	 * Getter for the user name.
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Setter for the user name.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Helper method to prompt the user to enter information.
	 * @param msg
	 * @param outputClass
	 * 	Expected outputClass
	 * @param input
	 * @return
	 * 	the acquired input from the user.
	 */
	private static Object promptInput(String msg, String outputClass, BufferedReader input) {
		boolean falseInput = false;
		Object res = null;
		do {
			falseInput = false;
			String in = "";
			System.out.print(msg);
			try {
				in = input.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error Occured.");
			}
			switch(outputClass) {
			case "int":
				try {
					res = Integer.parseInt(in);
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid input.");
					falseInput = true;
				}
				break;
			case "dbl":
				try {
					res = Double.parseDouble(in);
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid input.");
					falseInput = true;
				}
				break;
			case "str":
				res = in;
				break;
			}
			
		} while (falseInput);
		return res;
	}
	
	/**
	 * Prints the menu.
	 */
	private static void printMenu() {
		System.out.println("\nMenu:\n"
				+ "    (D) - Import Data from URL\n"
				+ "    (A) - Create a New Auction\n"
				+ "    (B) - Bid on an Item\n"
				+ "    (I) - Get Info on Auction\n"
				+ "    (P) - Print All Auctions\n"
				+ "    (R) - Remove Expired Auctions\n"
				+ "    (T) - Let Time Pass\n"
				+ "    (Q) - Quit");
	}
}
