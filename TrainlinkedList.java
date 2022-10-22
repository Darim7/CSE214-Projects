package hw2_TrainManager;

/**
 * Creates a <code>TrainlinkedList</code> object which implements
 * a Double-Linked List ADT.
 * 
 * @author Zhenting Ling
 *      CSE214.RO1
 *      e-mail: zhenting.ling@stonybrook.edu
 *      Stony Brook ID: 114416612
 *      
 */
public class TrainlinkedList {
	
	private TrainCarNode head;  // The head of the linked list.
	
	private TrainCarNode tail;  // The tail of the linked list.
	
	private int size; 		    // The size of this linked list.
	
	// The cursor, which represents the current node of the linked list.
	private TrainCarNode cursor;
	
	private double trainLength; // Length of the whole train in meters.
	
	private double trainWeight; // Weight of the whole train in tons.
	
	// Total value of product carried by the train.
	private double trainValue;
	
	/* Whether or not there is a dangerous product on one of the
	 * TrainCar objects on the train. */
	private boolean trainIsDangerous;
	
	private int numDangerousCar;// Number of dangerous cars in train.
	
	/**
	 * Constructs an instance of the TrainLinkedList with no
	 * <code>TrainCar</code> objects in it.
	 * 
	 * <dt>Postcondition:
	 * 		-This <code>TrainLinkedList</code> has been initialized
	 * 		to an empty linked list.
	 * 		-Head, tail, and cursor are all set to null.
	 */
	public TrainlinkedList() {
		head = null;
		tail = null;
		cursor = null;
		size = 0;
		trainLength = 0;
		trainWeight = 0;
		trainValue = 0;
		trainIsDangerous = false;
	}
	
	/**
	 * Places car in the node currently referenced by the cursor.
	 * 
	 * @return
	 * 		Returns the <code>TrainCar</code> object that is stored
	 * 		in the cursor.
	 * 
	 * @throws NullPointerException
	 * 		Indicates that the cursor is pointing to nothing.
	 */
	public TrainCar getCursorData() throws NullPointerException {
		if (cursor == null)
			throw new NullPointerException();
		return cursor.getCar();
	}
	
	/**
	 * Places car in the node currently referenced by the cursor.
	 * 
	 * <dt>Precondition:
	 * 		The cursor is not null (the list is not empty).
	 * 
	 * <dt>Postcondition:
	 * 		The cursor node now contains a reference to car as its data.
	 * 
	 * @param car
	 * 		The <code>TrainCar</code> object that is intended
	 * 		to be placed in this cursor.
	 */
	public void SetCursorData(TrainCar car)
			throws NullPointerException {
		if (cursor == null)
			throw new NullPointerException();
		
		trainStatUpdate(cursor.getCar(), car);
		
		cursor.setCar(car);
	}
	
	/**
	 * Moves the cursor to point at the next <code>TrainCarNode</code>.
	 * 
	 * <dt>Preconditions:
	 * 		The list is not empty (cursor is not null) and cursor
	 * 		does not currently reference the tail of the list.
	 * 
	 * <dt>Postconditions:
	 * 		The cursor has been advanced to the next TrainCarNode,
	 * 		or has remained at the tail of the list.
	 */
	public void cursorForward() {
		if (cursor.getNext() != null)
			cursor = cursor.getNext();
	}
	
	/**
	 * Same method as cursorForward() but specialized for prompting.
	 * @param dummyStr
	 */
	public void cursorForward(String dummyStr) {
		if (cursor.getNext() != null) {
			cursor = cursor.getNext();
			System.out.println("\nCursor moved forward.");
		} else
			System.out.println("\nNo next car, cannot move cursor forward.");
	}
	
	/**
	 * Moves the cursor to point at the previous <code>TrainCarNode</code>.
	 * 
	 * <dt>Preconditions:
	 * 		The list is not empty (cursor is not null) and the 
	 * 		cursor does not currently reference the head of the list.
	 * 
	 * <dt>Postconditions:
	 * 		The cursor has been moved back to the previous 
	 * 		<code>TrainCarNode</code>, or has remained at the
	 * 		head of the list.
	 */
	public void cursorBackward() {
		if (cursor.getPrev() != null)
			cursor = cursor.getPrev();
	}
	
	/**
	 * Same method as cursorBackward() but specialized for prompting.
	 * @param dummyStr
	 */
	public void cursorBackward(String dummyStr) {
		if (cursor.getPrev() != null) {
			cursor = cursor.getPrev();
			System.out.println("\nCursor moved backward.");
		} else
			System.out.println("\nNo previous car, cannot move cursor backward.");
	}
	
	/**
	 * Inserts a car into the train after the cursor position.
	 * 
	 * @param newCar
	 * 		the new <code>TrainCar</code> to be inserted into the train.
	 * 
	 * <dt>Preconditions:
	 * 		This <code>TrainCar</code> object has been instantiated.
	 * 
	 * <dt>Postconditions:
	 * 		The new TrainCar has been inserted into the train
	 * 		after the position of the cursor.
	 * 		
	 * 		All TrainCar objects previously on the train are 
	 * 		still on the train, and their order has been preserved. 
	 * 		
	 * 		The cursor now points to the inserted car.
	 */
	public void insertAfterCursor(TrainCar newCar) 
			throws IllegalArgumentException {
		if (newCar == null)
			throw new IllegalArgumentException();
		TrainCarNode newNode = new TrainCarNode(newCar);
		
		if (cursor == null) {
			head = newNode;
			tail = newNode;
			cursor = newNode;
		} else if (cursor.getNext() == null) {
			newNode.setPrev(cursor);
			cursor.setNext(newNode);
			cursor = newNode;
			tail = newNode;
		} else {
			newNode.setPrev(cursor);
			newNode.setNext(cursor.getNext());
			cursor.getNext().setPrev(newNode);
			cursor.setNext(newNode);
			cursor = newNode;
		}
		trainStatUpdate(null, newCar);
	}
	
	/**
	 * Removes the TrainCarNode referenced by the cursor and
	 * returns the TrainCar contained within the node.
	 * 
	 * <dt>Preconditions:
	 * 		The cursor is not null.
	 * 
	 * <dt>Postconditions:
	 * 		The TrainCarNode referenced by the cursor has been
	 * 		removed from the train.
	 * 
	 * 		The cursor now references the next node, or the
	 * 		previous node if no next node exists.
	 * 
	 * @return
	 * 	Returns the removed TrainCar.
	 * 		
	 */
	public TrainCar removeCursor() {
		if (cursor != null) {
			TrainCar res = cursor.getCar();
			removeNode(cursor);
			return res;
		}
		
		return null;
	}
	
	/**
	 * Determines the number of TrainCar objects currently on the train.
	 * 
	 * @return
	 * 	The number of <code>TrainCar</code> objects on this train.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns the total length of the train in meters.
	 * 
	 * @return
	 * 	The sum of the lengths of each TrainCar in the train.
	 */
	public double getLength() {
		return trainLength;
	}
	
	/**
	 * Returns the total value of product carried by the train.
	 * 
	 * @return
	 * 	The sum of the values of each TrainCar in the train.
	 */
	public double getValue() {
		return trainValue;
	}
	
	/**
	 * Returns the total weight in tons of the train. Note that 
	 * the weight of the train is the sum of the weights of each
	 * empty TrainCar, plus the weight of the ProductLoad carried
	 * by that car.
	 * 
	 * @return
	 * 	The sum of the weight of each TrainCar plus the sum of the
	 *  ProductLoad carried by that car.
	 */
	public double getWeight() {
		return trainWeight;
	}
	
	/**
	 * Whether or not there is a dangerous product on one of the
	 * TrainCar objects on the train.
	 * 
	 * @return
	 * 	Returns true if the train contains at least one TrainCar
	 *  carrying a dangerous ProductLoad, false otherwise.
	 */
	public boolean isDangerous() {
		return trainIsDangerous;
	}

	/**
	 * This is a helper method to make updating the train's status
	 * easier. This helper method can be used in both adding and
	 * removing.
	 * 
	 * @param oldCar
	 * 	The <code>TrainCar</code> that you want to remove/replace
	 * 	into the train. If the action is pure adding/inserting,
	 * 	fill in this parameter with <code>null</code>.
	 * 
	 * @param newCar
	 * 	The <code>TrainCar</code> that you want to
	 * 	add/insert/replace into the train. If the action is
	 * 	pure removing, fill in this parameter with <code>null</code>.
	 */
	public void trainStatUpdate(TrainCar oldCar, TrainCar newCar) {
		
		// Variables that store all the status of the to be
		// replaced car.
		double oldLength, oldWeight, oldValue;
		
		// variables that store all the status of the new car
		// that is about to replace the old car.
		double newLength, newWeight, newValue;
		
		if (oldCar == null) {
			oldLength = 0;
			oldWeight = 0;
			oldValue = 0;
		} else {
			oldLength = oldCar.getCarLength();
			oldWeight = oldCar.getTotalWeight();
			if (!oldCar.isEmpty()) {
				oldValue = oldCar.getProductLoad().getValue();
				if (oldCar.getProductLoad().isDangerous())
					numDangerousCar--;
			}
			else
				oldValue = 0;

			size--;
		}
		
		if (newCar == null) {
			newLength = 0;
			newWeight = 0;
			newValue = 0;
		} else {
			newLength = newCar.getCarLength();
			newWeight = newCar.getTotalWeight();
			if (!newCar.isEmpty()) {
				newValue = newCar.getProductLoad().getValue();
				if (newCar.getProductLoad().isDangerous())
					numDangerousCar++;
			} else
				newValue = 0;
			
			size++;
		}
		
		// Update train length.
		trainLength += newLength - oldLength;
		
		// Update train weight.
		trainWeight += newWeight - oldWeight;
		
		// Update train value.
		trainValue += newValue - oldValue;
		
		// Update whether train is dangerous.
		if (numDangerousCar > 0)
			trainIsDangerous = true;
		else
			trainIsDangerous = false;
	}
	
	/**
	 * Helper method that sets the load of the car that the 
	 * cursor points at.
	 * 
	 * @param load
	 * 	The load that is to be placed in the car that the cursor
	 * points at.
	 */
	public void setCursorCarLoad(ProductLoad load) {
		cursor.getCar().setLoad(load);
		trainWeight += load.getWeight();
		trainValue += load.getValue();
		if (load.isDangerous())
			numDangerousCar++;
		if (numDangerousCar > 0)
			trainIsDangerous = true;
	}
	
	/**
	 * Searches the list for all ProductLoad objects with the 
	 * indicated name and sums together their weight and value 
	 * (Also keeps track of whether the product is dangerous or 
	 * not), then prints a single ProductLoad record to the console.
	 * 
	 * @param name
	 * 	The name of the <code>ProductLoad</code> to find on the train.
	 * 
	 */
	public void findProduct(String name) {
		TrainCarNode nodePtr = head;
		int count = 0;
		double prodWeight = 0;
		double prodValue = 0;
		boolean prodIsDangerous = false;
		
		while (nodePtr != null) {
			ProductLoad prod = nodePtr.getCar().getProductLoad();
			
			// Check if the current car is one of the targets.
			if (prod.getName().equals(name)) {
				count++;
				prodWeight += prod.getWeight();
				prodValue += prod.getValue();
				prodIsDangerous = prod.isDangerous();
			}
			
			// Increment pointer
			nodePtr = nodePtr.getNext();
		}
		
		// Check final outputs.
		if (count > 0) {
			String msg = new String(String.format
					("\nThe following products were found on %d cars:", count));
			
			// Print number of cars carrying the target product.
			System.out.println(msg);
			
			System.out.println(showProduct(name, prodWeight, prodValue, prodIsDangerous));
			
		} else { // No car contains this product.
			System.out.println(new String(String.format(
					"\nNo record of %s on board train.", name)));
		}
	}
	
	/**
	 * Prints a neatly formatted table of the car number, 
	 * car length, car weight, load name, load weight, 
	 * load value, and load dangerousness for all of the car on 
	 * the train.
	 */
	public void printManifest() {
		TrainCarNode nodePtr = head;
		String category = "    CAR:                               LOAD:\n";
		String columnCategory = "      Num   Length (m)    "
				+ "Weight (t)  |    Name      Weight (t)     "
				+ "Value ($)   Dangerous\n";
		String separator = "    ================================"
				+ "==+===================================================\n";
		
		System.out.print("\n" + category + columnCategory + separator);
		
		for (int i = 1; i <= size; i++) {
				System.out.print(new String(String.format
						(" %s    %d", (nodePtr == cursor)?"->":"  ", i)));
				System.out.print(nodePtr.toString() + "\n");
				nodePtr = nodePtr.getNext();
		}
		
		if (size == 0) {
			System.out.print(new String(String.format
					(" %s    %d", "  ", 0)));
			System.out.println(new String(
					String.format("%14.1f%14.1f  |%s",
							0, 0, "     Empty           0.0"
									+ "          0.00          ")));
		}

	}
	
	/**
	 * Removes all dangerous cars from the train, maintaining
	 * the order of the cars in the train.
	 * 
	 * <dt>Postconditions:
	 * 		All dangerous cars have been removed from this train.
	 * 		The order of all non-dangerous cars must be maintained
	 *		upon the completion of this method.
	 */
	public void removeDangerousCars() {
		TrainCarNode nodePtr = head;
		
		while (nodePtr != null) {
			boolean CarIsDangerous =
					nodePtr.getCar().getProductLoad().isDangerous();
			
			if (CarIsDangerous) {
				removeNode(nodePtr);
			}
			
			nodePtr = nodePtr.getNext();
		}
		
		System.out.println
		("\nDangerous cars successfully removed from the train.");
	}
	
	/**
	 * Returns a string that represents the TrainlinkedList.
	 */
	public String toString() {
		return new String(String.format("\nTrain: %d cars, "
				+ "%.1f meters, %.1f tons, $%,.2f value, %s.", size(), 
				getLength(), getWeight(), getValue(), 
				isDangerous()?"DANGEROUS":"not dangerous"));
	}
	
	/**
	 * Helper method to remove a node.
	 * 
	 * @param node
	 * 	The node that the is to be removed
	 */
	public void removeNode(TrainCarNode node) {
		
		if (node.getPrev() == null && node.getNext() == null) {
			cursor = null;
			head = null;
			tail = null;
		} else if (node.getPrev() == null) {
			if (cursor == node)
				cursorForward();
			node.getNext().setPrev(null);
			head = node.getNext();
		
		} else if (node.getNext() == null) {
			if (cursor == node)
				cursorBackward();
			node.getPrev().setNext(null);
			tail = node.getPrev();
			
		} else {
			if (cursor == node)
				cursorForward();
			node.getPrev().setNext(node.getNext());
			node.getNext().setPrev(node.getPrev());
		}
		
		trainStatUpdate(node.getCar(), null);
	}
	
	/**
	 * Helper method to show the product that is on board train, or removed from train.
	 * @param name
	 * @param weight
	 * @param value
	 * @param isDangerous
	 * @return
	 * 	Returns a string that represents the product on train.
	 */
	public String showProduct(String name, double weight, double value, boolean isDangerous) {
		// Print table headings.
		String heading = "        Name      Weight (t)"
				+ "     Value ($)   Dangerous\n";
		String separator = "    ============================"
				+ "=======================\n";
		
		// Print data of the product.
		String info = new String(String.format
				("%14s%14.1f%,14.2f%12s",
						name, weight, value,
						isDangerous?"YES":"NO"));
		return "\n" + heading + separator + info;
	}
	
	/**
	 * Helper method for showing an empty car.
	 * @return
	 * 	Returns a preset string that shows the car is empty.
	 */
	public String showEmptyCar() {
		// Print table headings.
		String heading = "        Name      Weight (t)"
				+ "     Value ($)   Dangerous\n";
		String separator = "    ============================"
				+ "=======================\n";
		
		// Print data of the product.
		String info = new String(String.format
				("%14s%14.1f%,14.2f%12s",
						"Empty", 0.0, 0.0, "NO"));
		return "\n" + heading + separator + info;
	}
}

