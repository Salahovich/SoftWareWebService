package cs.software.project.controller;

import java.util.Scanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cs.software.project.service.*;

@RestController
public class ClientController {
	
	private Client current;
	private Ride iRide;
	private Scanner scanner = new Scanner(System.in);
	@GetMapping("/clients/search/{userName}")
	public Client getClient(@PathVariable String userName) {
		DataBase.getData().displayAllClients();
		return DataBase.getData().clientNameExists(userName);
	}
	
	@PostMapping("/clients/signup")
	public String addClient(@RequestBody Client c) {	
		if(DataBase.getData().clientNameExists(c.getUserName()) ==null) {
			DataBase.getData().addClientToSystem(c);
			return "the client has been added successfully to database.";
		}else
			return "The client userName is unavailable";
	}
	
	@GetMapping("clients/signin")
	public String signIn(@RequestParam String userName, @RequestParam String password) {
		current = DataBase.getData().ClientExists(userName, password);
		if(current != null) {
			return "Welcome client " + current.getUserName() + " to system." + " -- Balance = " + current.getWallet().getBalance()
					+ "\n1- Take a ride" + 
					"\n2- Display my Complete Rides" 
					+ "\n3- Display my Pending Rides" 
					+ "\n4- Deposit money to the wallet";
		}else
			return "there's no such a client";
	}
	
	@GetMapping("clients/menu")
	public String signIn() {
		if(current != null) {
			return "Welcome client " + current.getUserName() + " to system." + " -- Balance = " + current.getWallet().getBalance()
			+ "\n1- Take a ride" + 
			"\n2- Display my Complete Rides" 
			+ "\n3- Display my Pending Rides" 
			+ "\n4- Deposit money to the wallet";
		}else
			return "there's no such a client";
	}
	@GetMapping("clients/signout")
	public String signOut() {
		if(current != null) {
			current = null;
			return "you have been signed out.";
		}else
			return "you must sign in first";
	}
	
	@PostMapping("clients/1")
	public String addNewRide(@RequestBody Ride iRide) {
		iRide.setPassenger(current);
		this.iRide= iRide; 
		current.addRideToPending(iRide);
		DataBase.getData().addRideToPending(iRide);
		RideController.notifyDrivers(iRide);
		return "Searching for drivers.";
	}
	@PutMapping("clients/1/{friend}")
	public String addFriend(@PathVariable String friend) {
		if(iRide != null) {
			iRide.addFriend(friend);
			return "Friend added.";
		}else
			return "take a ride first.";
	}
	@GetMapping("clients/2")
	public String displayCompleted() {
		if(current != null) {
			return current.displayCompleted();
		}else
			return "You must sign in first.";
	}
	@PutMapping("clients/2/{index}/{value}")
	public String rateDriver(@PathVariable int index, @PathVariable int value) {
		if(current != null) {
			if(index >=1 && index<=current.getSizeOfCompleted()) {
				Driver temp = current.getCompleteRide(index-1).getDriver();
				current.rateDriver(temp, value);
				return "You have rated the driver successfully.";
			}
			else
				return "please, enter a correct ride number.";
		}else
			return "please, sign in first.";
	}
	@GetMapping("clients/3")
	public String displayPending() {
		if(current != null) {
			return current.displayPending();
		}else
			return "You must sign in first.";
	}
	
	@GetMapping("clients/3/{index}")
	public String chooseRide(@PathVariable int index) {
		if(current != null && index >=1 && index<=current.getSizeOfPending()) {
			iRide = current.getPendingRide(index-1);
			return iRide.displayAvailable();
		}else
			return "You must sign in first.";
	}
	
	@PutMapping("clients/3/{index}/{offer}")
	public String chooseOffer(@PathVariable int index,@PathVariable double offer) {
		if(iRide != null) {
			Driver temp = iRide.getOffer(offer);
			if(!current.haveEnoughMoney(offer))
				return "you have not enough money";
			temp.setBusy();
			if(!iRide.eventHappened("User accepted the ride"))
				iRide.addEvent("User accepted the ride");
			return "the driver on his way";
		}else
			return "You must choose ride first";
	}
	@PutMapping("clients/3/{index}/{offer}/arrived")
	public String arrived(@PathVariable int index, @PathVariable int offer) {
		if(!iRide.eventHappened("Driver arrived at user's location"))
			iRide.addEvent("Driver arrived at user's location");
		return "Driver arrived at user's location.";
	}
	@PutMapping("clients/3/{index}/{offer}/reached")
	public String reached(@PathVariable int index, @PathVariable int offer) {
		iRide.addEvent("Driver reached to user's location");
		Driver d = iRide.getOffer(offer);
		d.setIdle();
		iRide.completeTheRide(d, offer);
		DataBase.getData().addRideToComplete(iRide);
		iRide = null;
		return "You have finished the ride";
	}
	@PutMapping("clients/4/{value}")
	public String displayPending(@PathVariable int value) {
		if(current != null) {		
			current.getWallet().deposit(value);
			return "you have added " + value + "$ to your balance.";
		}else
			return "You must sign in first.";
	}
}
