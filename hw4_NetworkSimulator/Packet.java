package hw4;
/**
 * An object class <code>Packet</code> which represents a packet
 * that will be sent through the network.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class Packet {
	private static int packetCount = 0;

	private int id;
	private int packetSize;
	private int timeArrive;
	private int timeToDest;

	/**
	 * Instantiate a <code>Packet</code> object.
	 */
	public Packet() {
		packetCount++;
		id = packetCount;
	}
	
	/**
	 * Instantiate a <code>Packet</code> object.
	 * 
	 * @param packetSize the size of the packet.
	 * @param timeArrive the time which this packet arrives.
	 */
	public Packet(int timeArrive, int packetSize) {
		packetCount++;
		id = packetCount;
		setTimeArrive(timeArrive);
		setPacketSize(packetSize);
		setTimeToDest(packetSize/100);
	}

	/**
	 * Number of packets created.
	 * 
	 * @return the number of all packets that was created.
	 */
	public int totalPacketCount() {
		return packetCount;
	}

	/**
	 * Getter for the data field id.
	 * 
	 * @return the packet id of this packet.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter for the data field packetSize.
	 * 
	 * @return the size of this packet.
	 */
	public int getPacketSize() {
		return packetSize;
	}

	/**
	 * Getter for the data field timeArrive.
	 * 
	 * @return the time of arrival of this packet.
	 */
	public int getTimeArrive() {
		return timeArrive;
	}

	/**
	 * Getter for the data field timeToDest.
	 * 
	 * @return the time which this packet arrives the destination.
	 */
	public int getTimeToDest() {
		return timeToDest;
	}

	/**
	 * Setter for the data field id.
	 * 
	 * @param Id
	 */
	public void setId(int Id) {
		this.id = Id;
	}

	/**
	 * Setter for the data field packetSize.
	 * 
	 * @param packetSize of this packet.
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}

	/**
	 * Setter for the data field timeArrive.
	 * 
	 * @param timeArrive of this packet.
	 */
	public void setTimeArrive(int timeArrive) {
		this.timeArrive = timeArrive;
	}

	/**
	 * Setter for the data field timeToDest.
	 * 
	 * @param timeToDest of this packet.
	 */
	public void setTimeToDest(int timeToDest) {
		this.timeToDest = timeToDest;
	}
	
	/**
	 * Helper method that indicates this packet is being processed
	 * and the timeToDest will be decremented if it is greated than
	 * zero, which indicates that it is one time unit closer to be
	 * ready to be sent.
	 */
	public void process() {
		if (timeToDest > 0)
			timeToDest--;
	}

	/**
	 * @return a string representation of this packet.
	 */
	public String toString() {
		return String.format("[%d, %d, %d]", id, timeArrive, timeToDest);
	}
	
	/**
	 * Resets the packet count to 0. Use when another simulation begins.
	 */
	public static void resetAll() {
		packetCount = 0;
	}
	
}
