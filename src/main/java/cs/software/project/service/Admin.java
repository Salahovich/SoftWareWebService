package cs.software.project.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Admin{
	
	static List<Driver> pendingDrivers = new LinkedList<Driver>();
	private DataBase myData = DataBase.getData();
	
	public static void addToPendingDrivers(Driver iDriver) {
		pendingDrivers.add(iDriver);
	}
	public void showStateofRides() {
		ArrayList<Ride> rides = myData.getPendingRides();
		for(Ride r : rides) {
			System.out.println("Ride from " + r.getSource() + " to " + r.getDestination());
			r.displayEvents();
			System.out.println("********************************************");
		}
	}
	public static boolean AcceptDriver(String name) {
		Driver driver=null;
		for(Driver d : pendingDrivers) {
			if(d.getUserName().equals(name)) {
				driver = d;
			}
		}
		if(driver != null) {
			pendingDrivers.remove(driver);
			DataBase.getData().addDriverToSystem(driver);
			return true;
		}
		return false;
	}
	public void suspendDriver(String name) {
		myData.removeDriverFromSystem(name);
	}
	public void suspendClient(String name) {
		myData.removeClientFromSystem(name);
	}
	public void addDiscountArea(String area) {
		myData.addToDiscountAreas(area);
	}
	public static List<Driver> getPendingDrivers(){
		return pendingDrivers;
	}
	public void DisplayAllClients() {
		DataBase.getData().displayAllDrivers();
	}
	public void DisplayAllDrivers() {
		DataBase.getData().displayAllClients();
	}

}
