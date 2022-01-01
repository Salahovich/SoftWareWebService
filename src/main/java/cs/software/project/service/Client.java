package cs.software.project.service;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class Client extends User implements Ratable{

	private ArrayList<Ride> pendingRides = new ArrayList<Ride>();
	private ArrayList<Ride> completeRides = new ArrayList<Ride>();
	private boolean firstRide = true;
	
	public Client(){
		super();
	}
	public Client(String userName, String password, String mobile, String date){
		super(userName, password, mobile, date);
	}
	
	public Client(String userName, String password, String mobile, String date, String email){
		super(userName, password, mobile, date, email);
	}
	
	@Override
	public void rateDriver(Driver iDriver, int rate) {
		iDriver.addToMyRatings(this, rate);
		iDriver.clacAvergae(rate);
	}
	
	public Ride getPendingRide(int index) {
		return pendingRides.get(index);
	}
	public Ride getCompleteRide(int index) {
		return completeRides.get(index);
	}
	public void addRideToPending(Ride iRide) {
		pendingRides.add(iRide);
	}
	public void addRideToComplete(Ride iRide) {
		pendingRides.remove(iRide);
		completeRides.add(iRide);
	}
	public int getSizeOfPending() {
		return pendingRides.size();
	}
	public int getSizeOfCompleted() {
		return completeRides.size();
	}
	public String displayCompleted() {
		return this.completeRides.toString();
	}
	public boolean haveEnoughMoney(double cost) {
		if(getWallet().getBalance() < cost)
			return false;
		return true;
	}
	public void notFirstRide() {
		firstRide = false;
	}
	public boolean firstRide() {
		return firstRide;
	}
	public String displayPending() {
		return this.pendingRides.toString();
	}

	@Override
	public String toString() {
		return "userName= " + this.getUserName();
	}
}
