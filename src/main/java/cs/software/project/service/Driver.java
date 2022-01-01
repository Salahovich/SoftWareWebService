package cs.software.project.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

public class Driver extends User implements OnlyDrivers{

	private String license;
	private String nationalId;
	private boolean busy = false;
	private HashMap<Ratable, Integer> myRatings = new HashMap<Ratable, Integer>();
	private int sumOfRate;
	private double averageRating;
	private ArrayList<Ride> completeRides= new ArrayList<Ride>();
	private Queue<Ride> pendingRides= new LinkedList<Ride>();
	private ArrayList<String> favAreas = new ArrayList<String>();
	
	public Driver(){
		super();
	}
	public Driver(String userName, String password, String mobile, String date, String license, String nationalId){
		super(userName, password, mobile, date);
		this.license = license;
		this.nationalId = nationalId;
	}
	public Driver(String userName, String password, String mobile, String date, String email, String license, String nationalId){
		super(userName, password, mobile, date, email);
		this.license = license;
		this.nationalId = nationalId;
	}
	
	// Setters
	public void setLicense(String license) {
		this.license = license;
	}
	
	public void setId(String id) {
		this.nationalId = id;
	}
	
	public void addCompleteRide(Ride myRide) {
		completeRides.add(myRide);
	}
	
	public void clacAvergae(int rate) {
		this.sumOfRate += rate;
		this.averageRating = (double) this.sumOfRate /  myRatings.size();
	}
	// Getters 
	/**
	 * 
	 * @return
	 */
	public String getNationalId() {
		return this.nationalId;
	}
	/**
	 * 
	 * @return
	 */
	public String getLicense() {
		return this.license;
	}
	/**
	 * 
	 * @return
	 */
	public double getAvgRate() {
		
		return this.averageRating;
	}
	
	public void setBusy() {
		busy = true;
	}
	public void setIdle() {
		busy = false;
	}
	public void addToMyRatings(Ratable iClient, int rate){
		 myRatings.put(iClient, rate);
	}
	/**
	 * 
	 * @param source
	 * @return
	 */

	public boolean isInMyArea(String source) {
		return favAreas.contains(source);
	}
	
	public void setFavArea(String area) {
			favAreas.add(area);		
	}
	/**
	 * 
	 */
	public void setOffer() {
		Scanner scanner = new Scanner(System.in);
		double cost;
		while(!pendingRides.isEmpty()) {
			System.out.println(pendingRides.peek().toString());
			System.out.println("Enter your offer for this Ride: ");
			cost = scanner.nextDouble();
			pendingRides.peek().addToMap(this, cost);
			pendingRides.peek().addEvent("Captain added price");
			pendingRides.poll();
		}
	}
	public String displayRatings() {
		return this.myRatings.toString();
	}
	public String displayFavAreas() {
		return this.favAreas.toString();
	}
	/**
	 * 
	 */
	@Override
	public void newRide(Ride myRide) {
		if(isInMyArea(myRide.getSource()) && !busy) {
			pendingRides.add(myRide);
		};
	}
	
	@Override 
	public String toString() {
		return "userName= " + this.getUserName() + ", AvgRate= " + this.getAvgRate();
	}
	public String displayData() {
		return "userName= " + this.getUserName() + "\nNationalId= " + this.getNationalId() + "\nLicense= " + this.getLicense() + "\nMobile= " + this.getMobile() +
				"\nAvgRate= " + this.getAvgRate();
	}
}
