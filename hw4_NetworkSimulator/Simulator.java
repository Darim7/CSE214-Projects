package hw4;

import java.util.ArrayDeque;
import java.util.Scanner;
/**
 * This class contains the main method that tests your simulation.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class Simulator {	
	// Constants
	private static int MAX_PACKETS = 3;
	
	// Counters/Data trackers.
	private int totalServiceTime = 0;
	private int totalPacketsArrived = 0;
	private int packetsDropped = 0;
	
	// Input dependents.
	private int numIntRouters;
	private double arrivalProb;
	private int maxBufferSize;
	private int minPacketSize;
	private int maxPacketSize;
	private int bandwidth;
	private int duration;
	
	// Routers
	Router dispatcher;
	Router[] routers;
	
	/**
	 * Prompt the user for inputs to the simulator.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		boolean anotherSimulation = true;
		do {
			
			System.out.println("Starting simulator...\n");
			
			Simulator sim = new Simulator();
			Packet.resetAll();
			sim.promptInput("Enter the number of Intermediate routers: ", sim, input);
			sim.promptInput("\nEnter the arrival probability of a packet: ", sim, input);
			sim.promptInput("\nEnter the maximum buffer size of a router: ", sim, input);
			sim.promptInput("\nEnter the minimum size of a packet: ", sim, input);
			sim.promptInput("\nEnter the maximum size of a packet: ", sim, input);
			sim.promptInput("\nEnter the bandwidth size: ", sim, input);
			sim.promptInput("\nEnter the simulation duration: ", sim, input);
			
			sim.simulate();
			
			// Ask for another simulation.
			boolean falseInput = false;
			do {
				System.out.print("\nDo you want to try another simulation? (y/n): ");
				String choice = input.nextLine();
				if (choice.equalsIgnoreCase("y")) {
					anotherSimulation = true;
					falseInput = false;
				}
				else if (choice.equalsIgnoreCase("n")) {
					anotherSimulation = false;
					falseInput = false;
					System.out.print("\nProgram terminating successfully...");
				}
				else {
					System.out.println("\nPlease enter either \"y\" or \"n\"");
					falseInput = true;
					continue;
				}
			} while(falseInput);
			System.out.println();
		} while(anotherSimulation);
		input.close();
	}
	
	/**
	 * Instantiates a network simulator.
	 */
	public Simulator() {}
	
	/**
	 * Instantiate a network simulator with all the parameters
	 * required to run a simulation.
	 * 
	 * @param numIntRouters
	 * @param arrivalProb
	 * @param maxBufferSize
	 * @param minPacketSize
	 * @param maxPacketSize
	 * @param bandwidth
	 * @param duration
	 */
	public Simulator(int numIntRouters, double arrivalProb, 
			int maxBufferSize, int minPacketSize, int maxPacketSize, 
			int bandwidth, int duration) {
		this.numIntRouters = numIntRouters;
		this.arrivalProb = arrivalProb;
		this.maxBufferSize = maxBufferSize;
		this.minPacketSize = minPacketSize;
		this.maxPacketSize = maxPacketSize;
		this.bandwidth = bandwidth;
		this.duration = duration;
	}
	
	/**
	 * Calculate and return the average time each packet spends
	 * within the network.
	 * 
	 * @return the average service time per packet.
	 */
	public double simulate() {
		
		if (arrivalProb == 0) {
			System.out.println("\nNO SIMULATION");
			return 0;
		}
		
		if (duration == 0) {
			System.out.println("\nNO SIMULATION");
			return 0;
		}
		
		dispatcher = new Router();
		routers = new Router[numIntRouters+1];
		ArrayDeque<Integer> dequeueOrder = new ArrayDeque<Integer>();
		
		for (int routerID = 1; routerID <= numIntRouters; routerID++) {
			routers[routerID] = new Router(routerID);
		}
		
		for (int currentTime = 1; currentTime <= duration; currentTime++) {
			System.out.println(String.format("Time: %d", currentTime));
			
			// Event 1:
			// Determines whether there are packets arrives at dispatcher.
			for (int i = 0; i < MAX_PACKETS; i++) {
				if (Math.random() < arrivalProb) {
					Packet newPacket = new Packet(currentTime,
							randInt(minPacketSize, maxPacketSize-minPacketSize));
					System.out.println(String.format("Packet %d arrives "
							+ "at dispatcher with size %d.",
							newPacket.getId(), newPacket.getPacketSize()));
					dispatcher.add(newPacket);
				}
			}
			
			if (dispatcher.isEmpty())
				System.out.println("No packet arrived.");
			
			// Event 2:
			// Send packets to intermediate routers. (Determine whether to drop.)
			try {
				while (!dispatcher.isEmpty()) {
					int assignedRouterIndex = 
							Router.sendPacketTo(routers, maxBufferSize);
					Packet packetToIntRouter = dispatcher.remove();
					routers[assignedRouterIndex].add(packetToIntRouter);
					
					System.out.println(String.format("Packet %d sent to Router %d.",
							packetToIntRouter.getId(), assignedRouterIndex));
				}
			} catch (NoRouterAvailableException e) {
				while (!dispatcher.isEmpty()) {
					Packet droppedPacket = dispatcher.remove();
					packetsDropped++;
					System.out.println(String.format("Network is congested. "
							+ "Packet %d is dropped.", droppedPacket.getId()));
				}
			}
			
			// Event 3:
			// Process all the packets that are at the beginning of the queue.
			for (int i = 1; i <= numIntRouters; i++) {
				if (!routers[i].isEmpty())
					routers[i].peek().process();
			}
			
			// Event 4:
			// Find the index of the routers that has packet that is ready to be sent.
			for (int i = 1; i <= numIntRouters; i++) {
				if (!routers[i].isEmpty())
					if (routers[i].peek().getTimeToDest() == 0 &&!dequeueOrder.contains(i))
						dequeueOrder.add(i);
			}
			
			// Event 5:
			// Forward the three ready packets to the destination router.
			for (int i = 1; i <= bandwidth; i++) {
				if (!dequeueOrder.isEmpty()) {
					int routerReady = dequeueOrder.remove();
					Packet forwardReady = routers[routerReady].remove();
					int processTime = currentTime - forwardReady.getTimeArrive();
					totalServiceTime += processTime;
					System.out.println(String.format
							("Packet %d has successfully reached its destination: +%d",
									forwardReady.getId(), processTime));
					totalPacketsArrived++;
				}
			}
			
			
			// Print out the list of intermediate routers.
			for (int i = 1; i <= numIntRouters; i++) {
				System.out.println(routers[i]);
			}
			System.out.println();
		}
		
		double res = (double)totalServiceTime/totalPacketsArrived;
		
		System.out.println("Simulation ending...");
		System.out.println("Total service time: " + totalServiceTime);
		System.out.println("Total packets served: " + totalPacketsArrived);
		System.out.println(String.format("Average service time per packet: %.2f", res));
		System.out.println("Total packets dropped: " + packetsDropped);
		
		return res;
	}
	
	/**
	 * helper method that can generate a random number between
	 * minVal and maxVal, inclusively.
	 * 
	 * @param minVal
	 * @param maxVal
	 * @return the randomly generated number.
	 */
	private static int randInt(int minVal, int maxVal) {
		return (int)(Math.random()*(maxVal+1) + minVal);
	}
	
	/**
	 * Setter for number of intermediate routers.
	 * 
	 * @param numIntRouters
	 * @throws IllegalArgumentException indicates that the user input is illegal.
	 */
	public void setNumIntRouters(int numIntRouters) throws IllegalArgumentException {
		if (numIntRouters <= 0)
			throw new IllegalArgumentException("\nPlease enter a positive number "
					+ "for numer of intermediate routers.\n");
		this.numIntRouters = numIntRouters;
	}
	
	/**
	 * Setter for arrival probability.
	 * 
	 * @param arrivalProb
	 * @throws IllegalArgumentException indicates that the user input is illegal.
	 */
	public void setArrivalProbability(double arrivalProb) throws IllegalArgumentException {
		if (arrivalProb < 0 || arrivalProb > 1)
			throw new IllegalArgumentException("\nPlease enter a valid probability "
					+ "for arrival probability of a packet.");
		this.arrivalProb = arrivalProb;
	}
	
	/**
	 * Setter for maximum buffer size.
	 * 
	 * @param maxBufferSize
	 * @throws IllegalArgumentException indicates that the user input is illegal.
	 */
	public void setMaxBufferSize(int maxBufferSize) throws IllegalArgumentException {
		if (maxBufferSize <= 0)
			throw new IllegalArgumentException("\nPlease enter a positive number "
					+ "for maximum buffer size of a router.");
		this.maxBufferSize = maxBufferSize;
	}
	
	/**
	 * Setter for minimum packet size.
	 * 
	 * @param minPacketSize
	 * @throws IllegalArgumentException indicates that the user input is illegal.
	 */
	public void setMinPacketSize(int minPacketSize) throws IllegalArgumentException {
		if (minPacketSize < 0)
			throw new IllegalArgumentException("\nPlease enter a non-negative number "
					+ "for minimum size of a packet.");
		this.minPacketSize = minPacketSize;
	}
	
	/**
	 * Setter for maximum packet size.
	 * 
	 * @param maxPacketSize
	 * @throws IllegalArgumentException indicates that the user input is illegal.
	 */
	public void setMaxPacketSize(int maxPacketSize) throws IllegalArgumentException {
		if (maxPacketSize < 0)
			throw new IllegalArgumentException("\nPlease enter a non-negative number "
					+ "for maximum size of a packet.");
		else if (maxPacketSize < minPacketSize)
			throw new IllegalArgumentException("\nMaximum size of a packet can not be "
					+ "smaller than the minimum size of a packet. Please re-enter.");
		this.maxPacketSize = maxPacketSize;
	}
	
	/**
	 * Setter for bandwidth.
	 * 
	 * @param bandwidth
	 * @throws IllegalArgumentException indicates that the user input is illegal.
	 */
	public void setBandwidth(int bandwidth) throws IllegalArgumentException {
		if (bandwidth <= 0)
			throw new IllegalArgumentException("\nPlease enter a positive number for "
					+ "bandwidth size.");
		this.bandwidth = bandwidth;
	}
	
	/**
	 * Setter for duration.
	 * 
	 * @param duration
	 * @throws IllegalArgumentException indicates that the user input is illegal.
	 */
	public void setDuration(int duration) throws IllegalArgumentException {
		if (duration < 0)
			throw new IllegalArgumentException("\nPlease enter a valid time for duration.");
		this.duration = duration;
	}

	/**
	 * Helper method for prompting user input.
	 * 
	 * @param message
	 * @param sim
	 * @param input
	 */
	public void promptInput(String message, Simulator sim, Scanner input) {
		boolean falseInput = false;
		do {
			try {
				System.out.print(message);
				if (message.equals("Enter the number of Intermediate routers: "))
					sim.setNumIntRouters(Integer.parseInt(input.nextLine()));
				else if (message.equals("\nEnter the arrival probability of a packet: "))
					sim.setArrivalProbability(Double.parseDouble(input.nextLine()));
				else if (message.equals("\nEnter the maximum buffer size of a router: "))
					sim.setMaxBufferSize(Integer.parseInt(input.nextLine()));
				else if (message.equals("\nEnter the minimum size of a packet: "))
					sim.setMinPacketSize(Integer.parseInt(input.nextLine()));
				else if (message.equals("\nEnter the maximum size of a packet: "))
					sim.setMaxPacketSize(Integer.parseInt(input.nextLine()));
				else if (message.equals("\nEnter the bandwidth size: "))
					sim.setBandwidth(Integer.parseInt(input.nextLine()));
				else if (message.equals("\nEnter the simulation duration: "))
					sim.setDuration(Integer.parseInt(input.nextLine()));
				
				falseInput = false;
			} catch (NumberFormatException e) {
				System.out.println("\nPlease enter a valid input.");
				falseInput = true;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getLocalizedMessage());
				falseInput = true;
			}
		} while (falseInput);
	}
}