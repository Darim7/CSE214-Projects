package hw2_TrainManager;
/**
 * This class creates a <code>ProductLoad</code> object that
 * represents a product load, which contains the product name 
 * (String), its weight in tons (double), its value in 
 * dollars (double), and whether the product is 
 * dangerous or not (boolean), of a <code>TrainCar</code> object.
 *
 * @author Zhenting Ling
 *      CSE214.RO1
 *      e-mail: zhenting.ling@stonybrook.edu
 *      Stony Brook ID: 114416612
 *      
 */
public class ProductLoad {
	
	// A string that represents the product's name.
	private String name;
	
	// A double that represents the product's weight in tons.
	private double weight;
	
	// A double that represents the product's value in dollars.
	private double value;
	
	// A boolean that indicates whether the product is dangerous.
	private boolean dangerous;
	
	/**
	 * Returns an instance of <code>ProductLoad</code>.
	 */
	public ProductLoad() {
		
	}
	
	/**
	 * Returns an instance of <code>ProductLoad</code>
	 * with the following input data fields.
	 * 
	 * @param name
	 * @param weight
	 * @param value
	 * @param isDangerous
	 * @throws IllegalArgumentException
	 */
	public ProductLoad
	(String name, double weight, double value, boolean isDangerous) throws IllegalArgumentException {
		setName(name);
		setWeight(weight);
		setValue(value);
		this.setDangerous(isDangerous);
	}
	
	/**
	 * Getter for the name of the <code>ProductLoad</code> instance.
	 * 
	 * @return
	 * 	Returns the name of this <code>ProductLoad</code> 
	 * 	instance.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the weight of the <code>ProductLoad</code> instance.
	 * 
	 * @return
	 * 	Returns the weight of this <code>ProductLoad</code> 
	 * 	instance.
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Getter for the value of the <code>ProductLoad</code> instance.
	 * 
	 * @return
	 * 	Returns the value of this <code>ProductLoad</code> 
	 * 	instance.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Getter for whether the <code>ProductLoad</code> instance
	 * is dangerous.
	 * 
	 * @return
	 * 	Returns true if this <code>ProductLoad</code> is dangerous;
	 * 	otherwise, returns false.
	 */
	public boolean isDangerous() {
		return dangerous;
	}
	
	/**
	 * Sets the name of this <code>ProductLoad</code>.
	 * 
	 * @param name
	 * 	A string that represents the new name of this
	 * 	<code>ProductLoad</code>.
	 * 
	 * <dt>Postcondition:
	 * 		The <code>name</code> of this <code>ProductLoad</code>
	 *  	is changed to the input string.
	 */
	public void setName(String newName) {
		this.name = newName;
	}
	
	/**
	 * Sets the weight of this <code>ProductLoad</code>.
	 * 
	 * @param newWeight
	 * 	A double that represents the new weight of this
	 * 	<code>ProductLoad</code>.
	 *
	 * @throws IllegalArgumentException
	 * 	Indicates that the input is invalid.
	 * 	
	 * <dt>Postcondition:
	 * 		The <code>weight</code> of this <code>ProductLoad</code>
	 *  	is changed to the input double.
	 */
	public void setWeight(double newWeight) 
			throws IllegalArgumentException {
		if (newWeight >= 0)
			weight = newWeight;
		else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Sets the value of this <code>ProductLoad</code>.
	 * 
	 * @param newValue
	 * 	A double that represents the value of this
	 *  <code>ProductLoad</code>.
	 *  
	 * @throws IllegalArgumentException
	 * 	Indicates that the input is invalid.
	 *  
	 * <dt>Postcondition:
	 * 		The <code>value</code> of this <code>ProductLoad</code>
	 *  	is changed to the input double.
	 */
	public void setValue(double newValue) 
			throws IllegalArgumentException {
		if (newValue >= 0)
			value = newValue;
		else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Sets whether this <code>ProductLoad</code> is dangerous.
	 * 
	 * @param isDangerous
	 * 	A boolean that indicates whether this 
	 * 	<code>ProductLoad</code> is dangerous.
	 * 
	 * <dt>Postcondition:
	 * 		The boolean value that indicates whether this 
	 * 		<code>ProductLoad</code> is dangerous is changed
	 * 		to the input boolean value.
	 */
	public void setDangerous(boolean isDangerous) {
		dangerous = isDangerous;
	}
	
	/**
	 * ToString method for this <code>ProductLoad</code> object.
	 * 
	 * toString NOTE: Leave 14 characters for weights, length,
	 * 	value.
	 */
	public String toString() {
		return new String(
				String.format("%10s%14.1f%,14.2f%12s",
						name, weight, value, 
						isDangerous()? "YES": "NO"));
	}

}
