package hw2_TrainManager;
/**
 * This class creates a <code>TrainCar</code> object that
 * contains a length in meters (double), a weight in tons (double), 
 * and a load reference (ProductLoad). The load variable of the 
 * train may be null, which indicates that the train car is empty 
 * and contains no product.
 * 
 * @author Zhenting Ling
 *      CSE214.RO1
 *      e-mail: zhenting.ling@stonybrook.edu
 *      Stony Brook ID: 114416612
 *      
 */
public class TrainCar {
	
	// A double that represents the length of this TrainCar in meters.
	private double carLength;
	
	// A double that represents the weight of this TrainCar in tons.
	private double carWeight;
	
	// The product that is loaded on this TrainCar.
	private ProductLoad load;
	
	/**
	 * Returns an instance of <code>TrainCar</code>.
	 */
	public TrainCar() {
		
	}
	
	/**
	 * Returns an instance of <code>TrainCar</code> with the
	 * following parameters as its data field.
	 * 
	 * @param carLength
	 * @param carWeight
	 */
	public TrainCar(double carLength, double carWeight) throws IllegalArgumentException {
		if (carLength < 0 || carWeight < 0)
			throw new IllegalArgumentException();
		else {
			this.carLength = carLength;
			this.carWeight = carWeight;
		}
			
	}
	
	/**
	 * Returns an instance of <code>TrainCar</code> with the
	 * following parameters as its data field.
	 * 
	 * @param carLength
	 * @param carWeight
	 * @param load
	 */
	public TrainCar(double carLength, double carWeight, ProductLoad load) {
		this.carLength = carLength;
		this.carWeight = carWeight;
		this.load = load;
	}
		
	/**
	 * Getter for the length of this <code>TrainCar</code>
	 * instance.
	 * 
	 * @return
	 * 	The length of this <code>TrainCar</code>.
	 */
	public double getCarLength() {
		return carLength;
	}
	
	/**
	 * Getter for the weight of this <code>TrainCar</code>
	 * instance.
	 * 
	 * @return
	 * 	The weight of this <code>TrainCar</code>.
	 */
	public double getCarWeight() {
		return carWeight;
	}
	
	/**
	 * Getter for the product load of this <code>TrainCar</code>
	 * instance.
	 * 
	 * @return
	 * 	The product load of this <code>TrainCar</code>.
	 */
	public ProductLoad getProductLoad() {
		return load;
	}
	
	/**
	 * Setter for the product load of this <code>TrainCar</code>.
	 * 
	 * @param newLoad
	 * 	A <code>ProductLoad</code> object that is intended to
	 *  be added on this <code>TrainCar</code>.
	 *  
	 * <dt>Postcondition:
	 * 		The load of this <code>TrainCar</code> is changed
	 * 		to the input product.
	 */
	public void setLoad(ProductLoad newLoad) {
		this.load = newLoad;
	}
	
	/**
	 * Indicator of whether this <code>TrainCar</code> is empty.
	 * 
	 * @return
	 * 	Returns true if there is no product load in this 
	 * 	<code>TrainCar</code> otherwise returns false.
	 */
	public boolean isEmpty() {
		return load == null;
	}
	
	/**
	 * ToString method for this <code>TrainCar</code> object.
	 * 
	 * toString NOTE: leave 14 characters for weight, value, and
	 * 	length.
	 */
	public String toString() {
		return new String(
				String.format("%14.1f%14.1f  |%s",
						carLength, carWeight,
						isEmpty()?"     Empty           0.0"
								+ "          0.00          "
								+ "NO":load.toString()));
	}
	
	/**
	 * Gets the sum weight of both the train car and its load.
	 * 
	 * @return
	 * 		Returns the total weight of this train car.
	 */
	public double getTotalWeight() {
		if (this.isEmpty())
			return carWeight;
		else
			return carWeight + load.getWeight();
	}

}
