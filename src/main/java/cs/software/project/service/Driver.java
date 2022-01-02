package cs.software.project.service;

import java.util.HashMap;
import java.util.ArrayList;


public class Driver extends User implements OnlyDrivers{
	
	private String license;
	private String nationalId;
	private boolean busy = false;
	private HashMap<Ratable, Integer> myRatings = new HashMap<Ratable, Integer>();
	private int sumOfRate;
	private double averageRating;
	private ArrayList<Ride> completeRides= new ArrayList<Ride>();
	private ArrayList<Ride> pendingRides= new ArrayList<Ride>();
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
	public boolean setOffer(int index, double cost) {
		if(index>= 1 && index<=pendingRides.size()) {			
			pendingRides.get(index-1).addToMap(this, cost);
			pendingRides.get(index-1).addEvent("Captain added price");
			pendingRides.remove(index-1);
			return true;
		}
		return false;
	}
	/**
	 * 
	 */
	public String displayAvailable() {
		return this.pendingRides.toString();
	
	}
	public String displayComplete() {
		return this.completeRides.toString();
	
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
