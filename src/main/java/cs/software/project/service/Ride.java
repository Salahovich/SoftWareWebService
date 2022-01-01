package cs.software.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class Ride {
	private ArrayList<Event> events;
	private String source;
	private String destination;
	private Driver mainDriver;
	private ArrayList<Client> friends;
	private Client mainPassenger;
	private double cost;
	private Date date;
	private HashMap<Double, Driver> availableDrivers;
	
	Ride(){
		
	}
	Ride(String s, String d, Client c){
		this.source = s;
		this.destination = d;
		this.mainPassenger = c;
		this.date = new Date();
		this.friends = new ArrayList<>();
		this.availableDrivers = new HashMap<Double, Driver>();
		this.events = new ArrayList<>();
	}
	
	public void completeTheRide(Driver iDriver, double cost) {
		this.availableDrivers= null;
		this.setCost(cost);		
		
		this.mainDriver = iDriver;
		this.mainDriver.getWallet().deposit(cost);
		this.mainDriver.setIdle();
		this.mainDriver.addCompleteRide(this);		
		
		double discounted = makeDiscount(cost);
		
		this.mainPassenger.getWallet().deposit(discounted);
		this.mainPassenger.addRideToComplete(this);

	}
	public void addToMap(Driver iDriver, double cost) {
		availableDrivers.put(cost, iDriver);
	}
	
	public double makeDiscount(double cost) {
		double discounted = cost;
		if(DataBase.getData().hasDiscount(destination)) 
			discounted -= ((10/100)*cost); 
		if(this.mainPassenger.firstRide()) {
			discounted -=  ((10/100)*cost); 
			this.mainPassenger.notFirstRide();
		}
		if(this.friends.size() == 2)
			discounted -=  ((5/100)*cost); 
		//if(this.mainPassenger.getBirthDate().getDay() == date.getDay() && this.mainPassenger.getBirthDate().getMonth() == date.getMonth() )
			//discounted -=  ((10/100)*cost);
		return discounted;
	}
	// Setter Methods 
	public void setCost(double cost) {
		this.cost = cost;
	}
	public void setDriver(Driver iDriver) {
		this.mainDriver = iDriver;
	}
	public void addEvent(String name) {
		this.events.add(new Event(name, new Date()));
	}
	public boolean addFriends(String userName) {
		Client c = DataBase.getData().clientNameExists(userName);
		if(c!= null) {
			friends.add(c);
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
	public void displayAvailable() {
		
		System.out.println(availableDrivers.toString());
	}
	public void displayEvents() {
		for(Event e : this.events)
			System.out.println(e.toString());
	}
	public String toString() {
		return "Source= " + this.getSource() + ", Destination= " + this.getDestination() + ", Date= " + this.date.toString();
	}
	public String displayRide() {
		return "Source= " + this.getSource() + "\nDestination= " + this.getDestination() + "\nCost= " + this.getCost() + 
				"\nDriver= " + this.getDriver().getUserName() + ", Date= " + this.date.toString();
	}
}
