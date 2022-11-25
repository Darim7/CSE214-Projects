//package hw5;
/**
 * Creates a <code>DirectoryTree</code> which implements a
 * ternary (3-child) tree of DirectoryNodes.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class DirectoryTree {
	private DirectoryNode root;
	private DirectoryNode cursor;
	
	/**
	 * Initializes a DirectoryTree.
	 */
	public DirectoryTree() {
		root = new DirectoryNode();
		root.setName("root");
		cursor = root;
	}
	
	/**
	 * Initializes a DirectoryTree using a given node as root.
	 * @param root
	 */
	public DirectoryTree(DirectoryNode root) {
		this.root = root;
		root.setName("root");
		cursor = root;
	}
	
	/**
	 * Moves the cursor to the root node of the tree.
	 * 
	 * Postcondition:
	 * 	The cursor now references the root node of the tree.
	 */
	public void resetCursor() {
		cursor = root;
	}
	
	/**
	 * Moves the cursor to the directory with the name indicated by name.
	 * 
	 * Preconditions:
	 * 	'name' references a valid directory ('name' cannot reference a file).
	 * 
	 * Postconditions:
	 * 	The cursor now references the directory with the name indicated by name.
	 * 	If a child could not be found with that name, then the user is prompted to
	 * 	enter a different directory name. If the name was not a directory,
	 * 	a NotADirectoryException has been thrown.
	 * 
	 * @param name
	 * @throws NotADirectoryException
	 * 	Thrown if the node with the indicated name is a file, as files cannot be
	 * 	selected by the cursor, or cannot be found.
	 */
	public void changeDirectory(String name) throws NotADirectoryException {		
		for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++) {
			if (cursor.getNode(i) != null && cursor.getNode(i).getName().equals(name)) {
				if (cursor.getNode(i).isFile()) {
					throw new NotADirectoryException("ERROR: Cannot change directory into a file.");
				} else {
					cursor = cursor.getNode(i);
					return;
				}
			}
		}
		System.out.println(String.format("ERROR: No such directory named '%s'.", name));
	}
	
	/**
	 * Helper method, utilizes a node pointer while searching
	 * a node with given name and to avoid direct use of cursor in searching.
	 * 
	 * @param name
	 * @param nodePtr
	 * @return reference of the target node.
	 * @throws NotADirectoryException
	 */
	private DirectoryNode findNode(String name, DirectoryNode nodePtr) {
		if (nodePtr.getName().equals(name))
			return nodePtr;

		for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++) {
			if (nodePtr.getNode(i) != null &&
					findNode(name, nodePtr.getNode(i)) != null) {
				return findNode(name, nodePtr.getNode(i));
			}
		}
		
		return null;
	}
	
	/**
	 * Returns a String containing the path of directory names from
	 * the root node of the tree to the cursor, with each name separated
	 * by a forward slash "/".
	 * 
	 * Postconditions:
	 * 	The cursor remains at the same DirectoryNode.
	 * 
	 * @return
	 */
	public String presentWorkingDirectory() {
		String rev = reversedPathToNode("", root, cursor);
		String[] revArr = rev.split("/");
		String res = "";
		for (int i = revArr.length-1; i >= 0; i--) {
			if (i != 0)
				res += revArr[i] + "/";
			else
				res += revArr[i];
		}
		return res;
	}
	
	/**
	 * Helper method of presentWorkingDirectory.
	 * 
	 * @param output
	 * @param nodePtr
	 * @return
	 */
	private String reversedPathToNode(String output, DirectoryNode nodePtr, DirectoryNode target) {
		
		for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++) {
			if (nodePtr.getNode(i) != null &&
					foundDirectoryNode(nodePtr.getNode(i), target))
				output += reversedPathToNode(output, nodePtr.getNode(i), target);
		}
		
		output += nodePtr.getName() + "/";
		
		return output;
	}
	
	/**
	 * Helper method that indicates whether a DirectoryNode is the descendent
	 * of a given node.
	 * 
	 * @param currentNode
	 * @param target
	 * @return
	 */
	private boolean foundDirectoryNode
	(DirectoryNode currentNode, DirectoryNode target) {
		if (currentNode == target)
			return true;
		
		for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++) {
			if (currentNode.getNode(i) != null &&
					foundDirectoryNode(currentNode.getNode(i), target)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns a String containing a space-separated list of names of all the
	 * child directories or files of the cursor.
	 * 
	 * Postconditions:
	 * 	The cursor remains at the same DirectoryNode.
	 * 
	 * @return A formatted String of DirectoryNode names.
	 */
	public String listDirectory() {
		String res = "";
		for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++) {
			if (cursor.getNode(i) != null)
				res += cursor.getNode(i).getName() + " ";
		}
		return res;
	}
	
	/**
	 * Prints a formatted nested list of names of all the nodes in the
	 * directory tree, starting from the cursor.
	 * 
	 * Postconditions:
	 * 	The cursor remains at the same DirectoryNode.
	 */
	public void printDirectoryTree() {
		printDirectoryTree(cursor, 0);
	}
	
	/**
	 * Helper method for printDirectory.
	 * 
	 * @param nodePtr
	 * @param depth
	 */
	private void printDirectoryTree(DirectoryNode nodePtr, int depth) {
		String res = "";
		for (int i = 0; i < depth; i++)
			res += "    ";
		if (nodePtr.isFile())
			res += "- " + nodePtr.getName();
		else
			res += "|- " + nodePtr.getName();
		System.out.println(res);
		
		for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++) {
			if (nodePtr.getNode(i) != null)
				printDirectoryTree(nodePtr.getNode(i), depth+1);
		}
	}
	
	/**
	 * Creates a directory with the indicated name and adds it to the children of the cursor node.
	 * 
	 * Preconditions:
	 * 	'name' is a legal argument (does not contain spaces " " or forward slashes "/").
	 * 
	 * Postconditions:
	 * 	A new DirectoryNode has been added to the children of the cursor, or
	 * 	an exception has been thrown.
	 * 
	 * @param name
	 * 	The name of the directory to add.
	 * @throws IllegalArgumentException
	 * 	Thrown if the 'name' argument is invalid.
	 * @throws FullDirectoryException
	 * 	Thrown if all child references of this directory are occupied.
	 * @throws NotADirectoryException
	 */
	public void makeDirectory(String name)
			throws IllegalArgumentException, FullDirectoryException, NotADirectoryException{
		for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++) {
			if (cursor.getNode(i) != null && cursor.getNode(i).getName().equals(name))
				throw new IllegalArgumentException("This name exists in this directory,"
						+ " please try another name");
		}
		cursor.addChild(new DirectoryNode(name, false));
	}
	
	/**
	 * Creates a file with the indicated name and adds it to the children of the cursor node.
	 * 
	 * Preconditions:
	 * 	'name' is a legal argument (does not contain spaces " " or forward slashes "/").
	 * 
	 * Postconditions:
	 * 	A new DirectoryNode has been added to the children of the cursor,
	 * 	or an exception has been thrown.
	 * @param name
	 * 	The name of the file to add.
	 * @throws IllegalArgumentException
	 * 	Thrown if the 'name' argument is invalid.
	 * @throws FullDirectoryException
	 * 	Thrown if all child references of this directory are occupied.
	 * @throws NotADirectoryException
	 * 
	 */
	public void makeFile(String name)
			throws IllegalArgumentException, FullDirectoryException, NotADirectoryException {
		for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++) {
			if (cursor.getNode(i) != null && cursor.getNode(i).getName().equals(name))
				throw new IllegalArgumentException("This name exists in this directory,"
						+ " please try another name");
		}
		cursor.addChild(new DirectoryNode(name, true));
	}
	
	/**
	 * Returns the string representation of the path to the node from root.
	 * 
	 * @param name
	 * @return
	 */
	public String pathToNode(String name) {
		DirectoryNode target = findNode(name, root);
		if (target == null)
			return "ERROR: No such file exits.";
		String rev = reversedPathToNode("", root, target);
		String[] revArr = rev.split("/");
		String res = "";
		for (int i = revArr.length-1; i >= 0; i--) {
			if (i != 0)
				res += revArr[i] + "/";
			else
				res += revArr[i];
		}
		return res;
	}
	
	/**
	 * Moves the cursor to its current parent directory.
	 * 
	 * @throws NotADirectoryException
	 */
	public void moveToParent() throws NotADirectoryException {
		if (cursor == root) {
			System.out.println("ERROR: Already at root directory.");
			return;
		}
		String[] currLocation = presentWorkingDirectory().split("/");
		resetCursor();
		for (int i = 1; i < currLocation.length - 1; i++) {
			changeDirectory(currLocation[i]);
		}
	}
	
	/**
	 * Move the cursor to directory.
	 * 
	 * @param path
	 * @throws NotADirectoryException
	 */
	public void moveToDirectory(String path) throws NotADirectoryException {
		String[] targetPath = path.split("/");
		DirectoryNode target = findNode(targetPath[targetPath.length-1], root);
		if (target == null) {
			System.out.println("No such file exists.");
			return;
		}
		resetCursor();
		for (int i = 1; i < targetPath.length; i++) {
			changeDirectory(targetPath[i]);
		}
	}
	
	//TODO: Implement this.
	public void moveNodeFromTo(String path, String path2) {
		String[] originalPath = path.split("/");
		DirectoryNode tempCursor = findNode(originalPath[originalPath.length-1], root);
		
	}
}
