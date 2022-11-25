package hw6;

import java.util.Arrays;

import big.data.*;
/**
 * Creates a <code>AuctionTable</code> which is the database of
 * open auctions will be stored in a hash table to provide
 * constant time insertion and deletion.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class AuctionTable
extends java.util.Hashtable<String, Auction> implements java.io.Serializable {
	
	/**
	 * Uses the BigData library to construct an AuctionTable from a remote data source.
	 * 
	 * Preconditions:
	 * 	URL represents a data source which can be connected to using the BigData library.
	 * 	The data source has proper syntax. 	
	 * 
	 * @param url
	 * 	String representing the URL of the remote data source.
	 * @return The AuctionTable constructed from the remote data source.
	 * @throws IllegalArgumentException
	 * 	Thrown if the URL does not represent a valid datasource
	 * 	(can't connect or invalid syntax).
	 * 
	 * TODO: Refactor this.
	 */
	public static AuctionTable buildFromURL(String url)
			throws IllegalArgumentException {
		AuctionTable res = new AuctionTable();
		DataSource ds = getDataSourceFromURL(url);
		
		String[] sellers = new String[0];
		String[] ids = sellers;
		String[] bids = sellers;
		String[] times = sellers;
		String[] bidders = sellers;
		String[] memories = sellers;
		String[] hardDrives = sellers;
		String[] cpus = sellers;
		try {
			sellers = ds.fetchStringArray("listing/seller_info/seller_name");
			ids = ds.fetchStringArray("listing/auction_info/id_num");
			bids = ds.fetchStringArray("listing/auction_info/current_bid");
			times = ds.fetchStringArray("listing/auction_info/time_left");
			bidders = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
			memories = ds.fetchStringArray("listing/item_info/memory");
			hardDrives = ds.fetchStringArray("listing/item_info/hard_drive");
			cpus = ds.fetchStringArray("listing/item_info/cpu");
			formatStrArr(sellers);
			formatStrArr(bidders);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid URL", e);
		}
		
		for (int i = 0; i < sellers.length; i++) {
			double bid = getBidFromURL(bids[i]);
			int time = getTimeFromURL(times[i]);
			String itemInfo = getInfoFromURL(cpus[i], memories[i], hardDrives[i]);
			
			Auction newAuc = new Auction(ids[i].isBlank()?"N/A":ids[i],
					sellers[i].isBlank()?"N/A":sellers[i],
							bidders[i].isBlank()?"N/A":bidders[i],
									bid, time, itemInfo.isBlank()?"N/A":itemInfo);
			
			res.put(ids[i], newAuc);
		}
		
		return res;
	}
	
	/**
	 * Manually posts an auction, and add it into the table.
	 * 
	 * Postconditions:
	 * 	The item will be added to the table if all given parameters are correct.
	 * 
	 * @param auctionID
	 * 	the unique key for this object
	 * @param auction
	 * 	The auction to insert into the table with the corresponding auctionID
	 * @throws IllegalArgumentException
	 * 	If the given auctionID is already stored in the table.
	 */
	public void putAuction(String auctionID, Auction auction)
			throws IllegalArgumentException {
		if(containsKey(auctionID))
			throw new IllegalArgumentException(String.format
				("Auction ID: %s already exists, please try again.", auctionID));
		put(auctionID, auction);
	}
	
	/**
	 * Get the information of an Auction that contains the given ID as key
	 * 
	 * @param auctionID
	 * 	the unique key for this object
	 * @return
	 * 	An Auction object with the given key, null otherwise.
	 */
	public Auction getAuction(String auctionID) throws IllegalArgumentException {
		return get(auctionID);
	}
	
	/**
	 * Simulates the passing of time. Decrease the timeRemaining of all Auction
	 * objects by the amount specified. The value cannot go below 0.
	 * 
	 * Postconditions:
	 * 	All auction in the table have their timeRemaining timer decreased.
	 * 	If the original value is less than the decreased value, set the value to 0.
	 * 
	 * @param numHours
	 * 	the number of hours to decrease the timeRemaining value by.
	 * @throws IllegalArgumentException
	 * 	If the given numHours is non positive
	 */
	public void letTimePass(int numHours) throws IllegalArgumentException{
		if (numHours <= 0) throw new IllegalArgumentException("Time cannot be non-positive.");
		Object[] aucArr = values().toArray();
		for (int i = 0; i < aucArr.length; i++) {
			Auction auc = (Auction)aucArr[i];
			auc.decrementTimeRemaining(numHours);
		}
	}
	
	/**
	 * Iterates over all Auction objects in the table and removes them
	 * if they are expired (timeRemaining == 0).
	 * 
	 * Postconditions:
	 * 	Only open Auction remain in the table.
	 */
	public void removeExpiredAuctions() {
		Object[] keys = this.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			if (get(keys[i]).isClosed())
				remove(keys[i]);
		}
	}
	
	/**
	 * Prints the AuctionTable in tabular form.
	 */
	public void printTable() {
		Object[] auctions = values().toArray();
		sortAuctions(auctions);
		
		// Print Table Titles.
		System.out.println("\n Auction ID |      Bid   |        Seller         |"
				+ "          Buyer          |    Time   |  Item Info");
		// Print  lines.
		System.out.println("=================================================="
				+ "============================================================"
				+ "=====================");
		for (int i = 0; i < auctions.length; i++) {
			Auction auc = (Auction)auctions[i];
			System.out.println(auc.toTableString());
		}
	}
	
	/**
	 * Helper method to get the DataSource while checking the input format.
	 * 
	 * @param url
	 * @return
	 * @throws IllegalArgumentException
	 */
	private static DataSource getDataSourceFromURL(String url)
			throws IllegalArgumentException {
		try {
			DataSource ds = DataSource.connectXML(url).load();
			return ds;
		} catch (DataSourceException e) {
			throw new IllegalArgumentException("Invalid URL", e);
		}
	}
	
	/**
	 * Helper method to get time in hours from the source string.
	 * 
	 * @param timeStr
	 * @return
	 */
	private static int getTimeFromURL(String timeStr) {
		int time = 0;
		String[] strArr = timeStr.split(" ");
		for (int i = 0; i < strArr.length; i++) {
			if (strArr[i].contains("day"))
				time += 24 * Integer.parseInt(strArr[i-1]);
			else if (strArr[i].contains("hour") || strArr[i].contains("hrs"))
				time += Integer.parseInt(strArr[i-1]);
		}
		return time;
	}
	
	/**
	 * Helper method to get the item information.
	 * 
	 * @param cpu
	 * @param memory
	 * @param hardDrive
	 * @return
	 */
	private static String getInfoFromURL(String cpu, String memory, String hardDrive) {
		if (cpu.isBlank() && memory.isBlank() && hardDrive.isBlank()) return "N/A";
		String res = "";
		if (!cpu.isBlank())
			res += cpu + " - ";
		else
			res += "N/A" + " - ";
		if (!memory.isBlank())
			res += memory + " - ";
		else
			res += "N/A" + " - ";
		if (!hardDrive.isBlank())
			res += hardDrive + " - ";
		else
			res += "N/A";
		return res;
	}
	
	/**
	 * Helper method to convert the bid in the source into double.
	 * 
	 * @param bidStr
	 * @return
	 */
	private static double getBidFromURL(String bidStr) {
		String res = "";
		String[] strArr = bidStr.split("");
		for (int i = 1; i < strArr.length; i++) {
			if (!strArr[i].equals(","))
				res += strArr[i];
		}
		return Double.parseDouble(res);
	}
	
	/**
	 * Helper method that sorts an array of auction by their ID.
	 * @param objArr
	 */
	private static void sortAuctions(Object[] objArr) {
		boolean sorted = false;
		while(!sorted) {
			sorted = true;
			for (int i = 0; i < objArr.length-1; i++) {
				Auction auc1 = (Auction)objArr[i];
				Auction auc2 = (Auction)objArr[i+1];
				int auc1ID = Integer.parseInt(auc1.getAuctionID());
				int auc2ID = Integer.parseInt(auc2.getAuctionID());
				if (auc1ID < auc2ID) {
					objArr[i] = auc2;
					objArr[i+1] = auc1;
					sorted = false;
				}
			}
		}
	}
	
	/**
	 * formats all the strings in the strings array into
	 * strings without excessive strings.
	 * 
	 * @param arr
	 */
	private static void formatStrArr(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].matches("[\\S]*[\\s]+[\\S]*")) {
				arr[i] = arr[i].replaceAll("[\\s]+", " ");
			}
		}
	}
}
