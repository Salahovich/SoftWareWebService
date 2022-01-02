package cs.software.project.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class Ride {
	private ArrayList<Event> events;
	private String source;
	private String destination;
	private Driver mainDriver;
	private ArrayList<String> friends;
	private Client mainPassenger;
	private double cost;
	private Date date;
	private HashMap<Double, Driver> availableDrivers;
	
	Ride(){
		this.date = new Date();
		this.friends = new ArrayList<>();
		this.availableDrivers = new HashMap<Double, Driver>();
		this.events = new ArrayList<>();
	}
	Ride(String s, String d, Client c){
		this();
		this.source = s;
		this.destination = d;
		this.mainPassenger = c;
	}
	
	public void completeTheRide(Driver iDriver, double cost) {
		this.availableDrivers= null;
		this.setCost(cost);		
		
		this.mainDriver = iDriver;
		this.mainDriver.getWallet().deposit(cost);
		this.mainDriver.setIdle();
		this.mainDriver.addCompleteRide(this);		
		
		double discounted = makeDiscount(cost);

		this.mainPassenger.getWallet().withdraw(discounted);
		this.mainPassenger.addRideToComplete(this);

	}
	public void addToMap(Driver iDriver, double cost) {
		availableDrivers.put(cost, iDriver);
	}
	
	@SuppressWarnings("deprecation")
	public double makeDiscount(double cost) {
		double discounted = cost;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = formatter.parse(this.mainPassenger.getBirthDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(DataBase.getData().hasDiscount(destination)) {
			discounted -= 0.1*cost; 
		}
		if(this.mainPassenger.firstRide()) {
			discounted -=  0.1*cost; 
			this.mainPassenger.notFirstRide();
		}
		if(this.friends.size() == 2)
			discounted -=  0.05*cost; 
		if(d.getDay() == 5)
			discounted -= 0.05*cost;
		if(d.getDay() == date.getDay() && d.getMonth() == date.getMonth() )
			discounted -=  0.1*cost;
		return discounted;
	}
	// Setter Methods 
	public void setCost(double cost) {
		this.cost = cost;
	}
	public void setDriver(Driver iDriver) {
		this.mainDriver = iDriver;
	}
	public void setPassenger(Client iClient) {
		this.mainPassenger = iClient;
	}
	public void addEvent(String name) {
		this.events.add(new Event(name, new Date()));
	}
	public void addFriend(String userName) {
		if(DataBase.getData().clientNameExists(userName)!= null) {
			this.friends.add(userName);
		}
	}
	public boolean eventHappened(String e) {
		for(Event ev : events) {
			if(ev.getName().equals(e))
				return true;
		}
		return false;
	}
	// Getters Methods
	public Driver getOffer(double cost) {
		return availableDrivers.get(cost);
	}
	public String getSource() {
		return this.source;
	}
	public String getDestination() {
		return this.destination;
	}
	public Driver getDriver() {
		return this.mainDriver;
	}
	public double getCost() {
		return this.cost;
	}
	public int getSizeOfAvailable() {
		return availableDrivers.size();
	}
	public String displayAvailable() {
		
		return 	availableDrivers.toString();
	}
	public String displayEvents() {
		return this.events.toString();
	}
	
	public String toString() {
		return "Source= " + this.getSource() + "\nDestination= " + this.getDestination() + "\nCost= " + this.getCost() + 
				"\nDriver= " + this.getDriver() + ", Date= " + this.date.toString()  + ", Passengers= " + this.friends.toString();
	}

}
