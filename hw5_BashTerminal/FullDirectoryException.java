//package hw5;
/**
 * This Exception indicates that there are no available spot for
 * a additional child in this folder.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class FullDirectoryException extends Exception {
	public FullDirectoryException(String msg) {
		super(msg);
	}
}
