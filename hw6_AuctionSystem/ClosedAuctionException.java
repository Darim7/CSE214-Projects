package hw6;
/**
 * Indicates that an auction is closed.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class ClosedAuctionException extends Exception {
	public ClosedAuctionException(String msg) {
		super(msg);
	}
}
