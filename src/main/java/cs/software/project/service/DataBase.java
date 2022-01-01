package cs.software.project.service;

import java.util.ArrayList;

public class DataBase {

		private static DataBase allData;
		private ArrayList<Driver> allDrivers;
		private ArrayList<Client> allClients;
		private ArrayList<Ride> completeRides;
		private ArrayList<Ride> pendingRides;
		private ArrayList<String> discountAreas;
		
		private DataBase() {
				allDrivers = new ArrayList<Driver>();
				allClients = new ArrayList<Client>();
				completeRides = new ArrayList<Ride>();
				pendingRides = new ArrayList<Ride>();
				discountAreas = new ArrayList<String>();
		}
		
		public static DataBase getData() {
			if(allData == null)
				allData = new DataBase();
			
			return allData;
		}
		
		public Driver DriverExists(String userName, String password) {
			for(Driver iDriver: allDrivers) {
				if(iDriver.getUserName().equals(userName) && iDriver.getPassword().equals(password))
					return iDriver;
			}
			return null;
		}
		public Client ClientExists(String userName, String password) {
			for(Client iClient: allClients) {
				if(iClient.getUserName().equals(userName) && iClient.getPassword().equals(password))
					return iClient;
			}
			return null;
		}
		public Driver driverNameExists(String name) {
			for(Driver iDriver: allDrivers) {
				if(iDriver.getUserName().equals(name))
					return iDriver;
			}
			return null;
		}
		public Client clientNameExists(String name) {
			for(Client iClient: allClients) {
				if(iClient.getUserName().equals(name))
					return iClient;
			}
			return null;
		}
		public void addDriverToSystem(Driver iDriver) {
			allDrivers.add(iDriver);
			RideController.addDriver(iDriver);
		}
		public void addClientToSystem(Client iClient) {
			allClients.add(iClient);
		}
		public void removeDriverFromSystem(String name) {
			Driver iDriver = driverNameExists(name);
			if(iDriver != null) {
				allDrivers.remove(iDriver);
				RideController.removeDriver(iDriver);
				
			}else {
				System.out.println("There's no such a driver.");
			}
		}
		public void removeClientFromSystem(String name) {
			Client iClient = clientNameExists(name);
			if(iClient != null) {
				allClients.remove(iClient);			
			}else {
				System.out.println("There's no such a client.");
			}
		}

		public void addRideToPending(Ride iRide) {
			pendingRides.add(iRide);
		}
		public void addRideToComplete(Ride iRide) {
			pendingRides.remove(iRide);
			completeRides.add(iRide);
		}

		public void addToDiscountAreas(String area) {
			discountAreas.add(area);
		}
		public void deleteFromDiscountAreas(String area) {
			discountAreas.remove(area);
		}
		public boolean hasDiscount(String area) {
			return discountAreas.contains(area);
		}
		public Ride getPendingRide(int index){
			return this.pendingRides.get(index);
		}
		public String displayPendingRides(){
			return this.pendingRides.toString();
		}
		public String displayAllDrivers() {
			return allDrivers.toString();
		}
		public String displayAllClients() {
			return allClients.toString();
		}
		
	}

