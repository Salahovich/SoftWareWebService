package cs.software.project.service;


import java.util.ArrayList;

public class RideController {
	
	private static ArrayList<OnlyDrivers> myDrivers = new ArrayList<OnlyDrivers>();
	
	
	public static void notifyDrivers(Ride myRide) {
		for(OnlyDrivers only : myDrivers) {
			only.newRide(myRide);
		}
	}
	public static void addDriver(OnlyDrivers myDriver) {
		myDrivers.add(myDriver);
	}
	public static void removeDriver(OnlyDrivers myDriver) {
		myDrivers.remove(myDriver);
	}
}
