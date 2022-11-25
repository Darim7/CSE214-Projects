package hw6;
/**
 * Creates a <code>Auction</code> which represents an active
 * auction currently in the database.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class Auction implements java.io.Serializable  {
	private int timeRemaining;
	private double currentBid;
	private String auctionID, sellerName, buyerName, itemInfo;
	
	/**
	 * Initiates a new auction object.
	 */
	public Auction() {}
	
	/**
	 * Initiates a new auction object with given info.
	 * 
	 * @param totalAuctionTime
	 * @param auctionID
	 * @param sellerName
	 * @param itemInfo
	 */
	public Auction(String auctionID, String sellerName,
			String buyerName, double originalBid,
			int totalAuctionTime, String itemInfo) throws IllegalArgumentException {
		checkID(auctionID);
		this.auctionID = auctionID;
		this.sellerName = sellerName;
		this.buyerName = buyerName;
		currentBid = originalBid;
		timeRemaining = totalAuctionTime;
		this.itemInfo = itemInfo;
	}
	
	/**
	 * Decreases the time remaining for this auction by the
	 * specified amount. If time is greater than the current
	 * remaining time for the auction, then the time remaining
	 * is set to 0 (i.e. no negative times).
	 * 
	 * Postconditions:
	 * 	timeRemaining has been decremented by the indicated
	 * 	amount and is greater than or equal to 0.
	 * 
	 * @param time
	 */
	public void decrementTimeRemaining(int time) {
		if (time > timeRemaining)
			timeRemaining = 0;
		else 
			timeRemaining -= time;
	}
	
	/**
	 * Determines whether this auction is closed.
	 * 
	 * @return true if the timeRemaining is equals to 0, otherwise
	 * 	false.
	 */
	public boolean isClosed() {
		return timeRemaining == 0;
	}
	
	/**
	 * Makes a new bid on this auction. If bidAmt is larger than
	 * currentBid, then the value of currentBid is replaced by
	 * bidAmt and buyerName is is replaced by bidderName.
	 * 
	 * Preconditions:
	 * 	This auction is not closed.
	 * 
	 * Postconditions:
	 * 	currentBid Reflects the largest bid placed on this object.
	 * 	If the auction is closed, throw a ClosedAuctionException.
	 * 
	 * @param bidderName
	 * @param bidAmt
	 * @throws ClosedAuctionException
	 * 	Thrown if the auction is closed and no more bids can be
	 * 	placed (i.e. timeRemaining == 0).
	 */
	public void newBid(String bidderName, double bidAmt)
			throws ClosedAuctionException {
		if (isClosed())
			throw new ClosedAuctionException
			(String.format("Auction %s is CLOSED", auctionID));
		
		if (bidAmt > currentBid) {
			buyerName = bidderName;
			currentBid = bidAmt;
			System.out.println("Bid accepted.");
			return;
		}
		System.out.println("Bid was not accepted.");
	}	
	
	/**
	 * Getter for the timeRemaining.
	 * @return
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * Getter for the currentBid.
	 * @return
	 */
	public double getCurrentBid() {
		return currentBid;
	}

	/**
	 * Getter for the auction ID
	 * @return
	 */
	public String getAuctionID() {
		return auctionID;
	}

	/**
	 * Getter for the seller's name.
	 * @return
	 */
	public String getSellerName() {
		return sellerName;
	}

	/**
	 * Getter for the buyer's name.
	 * @return
	 */
	public String getBuyerName() {
		return buyerName;
	}

	/**
	 * Getter for the item's information.
	 * @return
	 */
	public String getItemInfo() {
		return itemInfo;
	}

	/**
	 * Returns a string representation of this auction object.
	 */
	public String toTableString() {
		return String.format
				(" %10s | %s%9s | %-22.22s|  %-22.22s | %3d hours | %-42.42s",
				auctionID, (noBid())?" ":"$", bidString(), sellerName, buyerName, timeRemaining, itemInfo);
	}
	
	/**
	 * Checks the input id is in number.
	 * @param ID
	 * @throws IllegalArgumentException
	 * 	if the input id is not in number.
	 */
	private static void checkID(String ID) throws IllegalArgumentException {
		try {
			Integer.parseInt(ID);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Auction ID can only be in numbers.");
		}
	}
	
	/**
	 * Helper method that converts double into currency format.
	 * and return the result as a string.
	 * 
	 * @return
	 */
	private String bidString() {
		if (noBid()) return "None";
		return String.format("%,.2f", currentBid);
	}
	
	private boolean noBid() {
		return currentBid <= 0;
	}
	
	/**
	 * @return a string representation of the bid of this auction.
	 */
	public String bidInfo() {
		return String.format("    Current Bid: %s %s", (noBid())?" ":"$", bidString());
	}
	
	/**
	 * Returns a string representation of this auction.
	 */
	public String toString() {
		return String.format("Auction %s:"
				+ "\n    Seller: %s"
				+ "\n    Buyer: %s"
				+ "\n    Time: %d hours"
				+ "\n    Info: %s", auctionID, sellerName, buyerName, timeRemaining, itemInfo);
	}
}
