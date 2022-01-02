package cs.software.project.service;

import java.util.LinkedList;
import java.util.List;

public class Admin{
	
	static List<Driver> pendingDrivers = new LinkedList<Driver>();
	static DataBase myData = DataBase.getData();
	
	public static void addToPendingDrivers(Driver iDriver) {
		pendingDrivers.add(iDriver);
	}
	public static String showStateOfRide(int index) {
		Ride r =myData.getPendingRide(index);
		return "Ride from " + r.getSource() + " to " + r.getDestination() + ": "+
		r.displayEvents();
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
	public static void suspendDriver(String name) {
		myData.removeDriverFromSystem(name);
	}
	public static void suspendClient(String name) {
		myData.removeClientFromSystem(name);
	}
	public static void addDiscountArea(String area) {
		myData.addToDiscountAreas(area);
	}
	public static List<Driver> getPendingDrivers(){
		return pendingDrivers;
	}
	public static String DisplayAllPendingRides() {
		return DataBase.getData().displayPendingRides();
	}
	public static String DisplayAllClients() {
		return DataBase.getData().displayAllDrivers();
	}
	public static String DisplayAllDrivers() {
		return DataBase.getData().displayAllClients();
	}

}
