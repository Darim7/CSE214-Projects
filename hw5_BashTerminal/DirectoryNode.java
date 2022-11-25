//package hw5;
/**
 * Creates a <code>DirectoryNode</code> which represents
 * a node in the file tree.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class DirectoryNode {
	private String name;
	private boolean isFile = false;
	final public static int MAX_CHILDREN = 10;
	
	// TODO: Extra Credit
	private DirectoryNode[] children = new DirectoryNode[MAX_CHILDREN];
	
	/**
	 * Initializes a DirectoryNode
	 */
	public DirectoryNode() {}
	
	/**
	 * Initializes a DirectoryNode with a given name.
	 * @param name
	 * @throws IsDirectoryException 
	 */
	public DirectoryNode(String name, boolean isFile) {
		setName(name);
		this.isFile = isFile;
	}
	
	/**
	 * Adds newChild to any of the open child positions of this
	 * node (left, middle, or right).
	 * 
	 * Preconditions:
	 * 	This node is not a file. There is at least
	 * 	one empty position in the children of this node
	 * 	(left,middle, or right).
	 * 
	 * Postconditions:
	 * 	newChild has been added as a child of this
	 * 	node. If there is no room for a new node, throw a
	 * 	FullDirectoryException.
	 * 
	 * @param child
	 * @throws FullDirectoryException
	 * @throws NotADirectoryException
	 */
	public void addChild(DirectoryNode child)
			throws FullDirectoryException, NotADirectoryException {
		
		if (isFile) throw new NotADirectoryException("Can't add file/directory to a file.");
		
		for (int i = 0; i < MAX_CHILDREN; i++) {
			if (children[i] == null) {
				children[i] = child;
				return;
			}
		}
		throw new FullDirectoryException("ERROR: Present directory is full.");
	}

	/**
	 * Getter for the member variable name.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the member variable name.
	 * 
	 * @param name
	 * @throws IllegalArgumentException
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name.contains("\\w") || name.contains("\\t") || name.contains("/"))
			throw new IllegalArgumentException("The node name should "
					+ "be a full string with no spaces, tabs, forward slashes,"
					+ " or any other whitespace.");
		this.name = name;
	}

	/**
	 * Determines whether this is DirectoryNode is a file.
	 * 
	 * @return true of this DirectoryNode is a file, returns false
	 * 	otherwise.
	 */
	public boolean isFile() {
		return isFile;
	}	
	
	//TODO: Extra Credit	
	public DirectoryNode getNode(int index) {
		return children[index];
	}
}
