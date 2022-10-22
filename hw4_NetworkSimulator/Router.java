package hw4;
/**
 * An object class <code>Router</code> which represents 
 * a router in the network.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class Router extends java.util.ArrayDeque<Packet>{
    
	private int id;
	
	/**
	 * Instantiate a router object.
	 */
    public Router(){}
    
    /**
     * Instantiate a router object with an id.
     * @param id
     */
    public Router(int id) {
    	this.id = id;
    }
    
    /**
     * this method should loop through the list Intermediate
     * routers. Find the router with the most free buffer
     * space (contains least Packets), and return the index
     * of the router. If there are multiple routers, any
     * corresponding indices will be acceptable.
     * 
     * @param routers
     * 	a collection of routers.
     * @param maxBufferSize
     * 	the number of packets that each router can take.
     * @return an integer that is the index of the router with
     * 	the most available capacity in the collection.
     * @throws NoRouterAvailableException if all the routers
     * 	in the input collection are full.
     */
    public static int sendPacketTo(Router[] routers, int maxBufferSize)
    		throws NoRouterAvailableException {
    	int index = 1;
    	for (int i = routers.length-1; i > 1; i--) {
    		if (routers[i].size() < routers[i-1].size())
    			index = i;
    	}
    	if (routers[index].size() >= maxBufferSize) throw new NoRouterAvailableException();
    	return index;
    }
    
    /**
     * ToString method of this router.
     * @return
     * 	a string representation of this router.
     */
    public String toString() {
    	String res = String.format("R%d: {", id);
    	Object[] objArr = toArray();
    	for (int i = 0; i < objArr.length; i++) {
    		if (i != objArr.length - 1)
    			res += (Packet)(objArr[i]) + ", ";
    		else
    			res += (Packet)(objArr[i]);
    		
    	}
    	return res + "}";
    }
}
