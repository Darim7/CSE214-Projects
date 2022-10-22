package hw3;
/**
 * An object class <code>Complexity</code> which represents the
 * Big-Oh complexity of some block of code.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class Complexity {

	private int nPower = 0;
	private int logPower = 0;
	
	/**
	 * Creates an instance of <code>Complexity</code>.
	 */
	public Complexity() {
		
	}
	
	/**
	 * Creates an instance of <code>Complexity</code> with inputs.
	 * Serves to test classes.
	 * 
	 * @param nPower
	 * @param logPower
	 */
	public Complexity(int nPower, int logPower) {
		setNPower(nPower);
		setLogPower(logPower);
	}
	
	/**
	 * Getter for the n power of this complexity.
	 * 
	 * @return
	 * 		integer that represents the n power of this complexity.
	 */
	public int getNPower() {
		return nPower;
	}
	
	/**
	 * Getter for the log power of this complexity.
	 * 
	 * @return
	 * 		integer that represents the log power of this complexity.
	 */
	public int getLogPower() {
		return logPower;
	}
	
	/**
	 * Setter for the n power of this complexity.
	 * 
	 * @param nPower
	 * 		the intended power for the n power for this complexity.
	 */
	public void setNPower(int nPower) {
		this.nPower = nPower;
	}
	
	/**
	 * Setter for the log power of this complexity.
	 * 
	 * @param nPower
	 * 		the intended power for the log power for this complexity.
	 */
	public void setLogPower(int logPower) {
		this.logPower = logPower;
	}
	
	/**
	 * toString method that prints a string that represents this complexity.
	 */
	public String toString() {
		if (logPower == 0 && nPower == 0) {
			return "O(1)";
		} else {
			String res = "O(";
			if (nPower > 0) {
					res += "n";
				if (nPower > 1) {
					res += "^" + String.valueOf(nPower);
				}
				if (logPower > 0)
					res += " * ";
			}
			if (logPower > 0) {
				res += "log(n)";
				if (logPower > 1) {
					res += "^" + String.valueOf(logPower);
				}
			}
			res += ")";
			
			return res;
		}
	}
	
	/**
	 * Helper method that helps to compare the complexity of
	 * this <code>Complexity</code> object and another.
	 * 
	 * @param c
	 * @return
	 * 	-1 if this Complexity is smaller.
	 *  0 if they are equal.
	 *  1 if this Complexity is bigger.
	 */
	public int compareTo(Complexity c) {
		if (this.nPower > c.nPower)
			return 1;
		else if (this.nPower < c.nPower)
			return -1;
		else {
			if (this.logPower > c.logPower)
				return 1;
			else if (this.logPower < c.logPower)
				return -1;
		}
		return 0;
	}
}
