package hw3;
import java.util.Stack;

/**
 * An <code>BlockStack</code> object that uses stack to store a
 * stack of <code>CodeBlock</code>.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class BlockStack {
	private Stack<CodeBlock> blockStack;
	private int size = 0;
	
	/**
	 * Initiates a <code>BlockStack</code> object.
	 */
	public BlockStack() {
		blockStack = new Stack<CodeBlock>();
	}
	
	/**
	 * Pushes a <code>CodeBlock</code> object into this
	 * <code>BlockStack</code>.
	 * 
	 * @param c
	 * 	The <code>CodeBlock</code> object that is to be pushed in
	 * 	this <code>BlockStack</code>.
	 */
	public void push(CodeBlock c) {
		blockStack.push(c);
		size++;
	}
	
	/**
	 * Pops the <code>CodeBlock</code> object that is on the top of
	 * this <code>BlockStack</code>.
	 * 
	 * @return
	 * 	returns the top <code>CodeBlock</code> object on this
	 * 	<code>BlockStack</code> object.
	 */
	public CodeBlock pop() {
		size--;
		return blockStack.pop();
	}
	
	/**
	 * Peeks the <code>CodeBlock</code> object that is on this
	 * <code>BlockStack</code> object.
	 * @return
	 */
	public CodeBlock peek() {
		return blockStack.peek();
	}
	
	/**
	 * Getter for the size of this <code>BlockStack</code>.
	 * 
	 * @return
	 * 	the size of this <code>BlockStack</code>.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks if this <code>BlockStack</code> is empty.
	 * 
	 * @return
	 * 	true if this <code>BlockStack</code> is empty
	 * 	otherwise false.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
}
