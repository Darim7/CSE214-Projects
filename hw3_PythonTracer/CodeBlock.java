package hw3;
/**
 * An object class <code>CodeBlock</code> which describes a 
 * nested block of code.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class CodeBlock {

	public static enum Keyword {
		DEF, FOR, WHILE, IF, ELIF, ELSE;
	}
	
	private Complexity blockComplexity;

	private Complexity highestSubComplexity;
	
	private String name;
	
	private String loopVariable;
	
	/**
	 * Instantiate an <code>CodeBlock</code> object.
	 */
	public CodeBlock() {
		blockComplexity = new Complexity();
		highestSubComplexity = new Complexity();
	}
	
	/**
	 * Instantiate a <code>CodeBlock</code> object with its
	 * initial complexity.
	 * 
	 * @param c
	 * 	complexity object that represents the complexity of the
	 * 	CodeBlock when it just get initiated.
	 */
	public CodeBlock(Complexity initialBlockComplx) {
		blockComplexity = initialBlockComplx;
		highestSubComplexity = new Complexity();
	}

	/**
	 * Getter for the block complexity of this <code>CodeBlock</code>.
	 * 
	 * @return
	 * 	the block complexity of this <code>CodeBlock</code>.
	 */
	public Complexity getBlockComplexity() {
		return blockComplexity;
	}

	/**
	 * Setter for the block complexity of this <code>CodeBlock</code>.
	 * 
	 * @param blockComplexity
	 * 	the intended block complexity for this <code>CodeBlock</code>.
	 */
	public void setBlockComplexity(Complexity blockComplexity) {
		this.blockComplexity = blockComplexity;
	}

	/**
	 * Getter for the highest block complexity of this
	 * <code>CodeBlock</code>.
	 * 
	 * @return
	 * 	the highest block complexity of this <code>CodeBlock</code>.
	 */
	public Complexity getHighestSubComplexity() {
		return highestSubComplexity;
	}

	/**
	 * Setter for the highest block complexity of
	 * <code>CodeBlock</code>.
	 * 
	 * @param highestSubComplexity
	 * 	the intended highest sub complexity for this
	 *  <code>CodeBlock</code>.
	 */
	public void setHighestSubComplexity(Complexity highestSubComplexity) {
		this.highestSubComplexity = highestSubComplexity;
	}

	/**
	 * Getter for the name of this <code>CodeBlock</code>.
	 * 
	 * @return
	 * 	the name of this <code>CodeBlock</code>.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the name of this <code>CodeBlock</code>.
	 * 
	 * @param name
	 * 	the intended name for this <code>CodeBlock</code>.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the loop variable of this <code>CodeBlock</code>.
	 * 
	 * @return
	 * 	the loop variable of this <code>CodeBlock</code>.
	 */
	public String getLoopVariable() {
		return loopVariable;
	}

	/**
	 * Setter for the loop variable of this <code>CodeBlock</code>.
	 * 
	 * @param loopVariable
	 * 	the intended loop variable for this <code>CodeBlock</code>.
	 */
	public void setLoopVariable(String loopVariable) {
		this.loopVariable = loopVariable;
	}
	
	/**
	 * Helper method that helps to update the block complexity.
	 */
	public void updateBlockComplexity() {
		blockComplexity.setNPower(blockComplexity.getNPower() + highestSubComplexity.getNPower());
		blockComplexity.setLogPower(blockComplexity.getLogPower() + highestSubComplexity.getLogPower());
	}
	
	/**
	 * ToString method that returns a string representation of
	 * this <code>CodeBlock</code>.
	 */
	public String toString() {
		return new String(String.format
				("        %-15sblock complexity = %-11shighest sub-complexity = %s", getName().toUpperCase() + ":",
						blockComplexity.toString(),
						highestSubComplexity.toString()));
	}
	
}
