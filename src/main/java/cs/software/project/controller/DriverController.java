package cs.software.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cs.software.project.service.Admin;
import cs.software.project.service.DataBase;
import cs.software.project.service.Driver;

@RestController
public class DriverController {

	Driver current;
	@GetMapping("/drivers/search/{userName}")
	public Driver getDriver(@PathVariable String userName) {
		DataBase.getData().displayAllClients();
		return DataBase.getData().driverNameExists(userName);
	}
	
	@PostMapping("/drivers/signup")
	public String signUp(@RequestBody Driver c) {	
		if(DataBase.getData().driverNameExists(c.getUserName()) ==null) {
			Admin.addToPendingDrivers(c);
			return "the driver has been added to pending queue.";
		}else
			return "The driver userName is unavailable";
	}
	
	@GetMapping("drivers/menu")
	public String menu() {
		if(current != null) {
			return "Welcome driver " + current.getUserName() + " to system." + " -- Balance = " + current.getWallet().getBalance()+
			"\n1- Show Available Rides" +
			"\n 2- Show Complete Rides" + 
			"\n 3- Show Client Ratings" + 
			"\n 4- Show Favorite Areas" + 
			"\n 5- Add Favortite Area";
		}else
			return "there's no such a driver";
	}
	
	@GetMapping("drivers/signin")
	public String signIn(@RequestParam String userName, @RequestParam String password) {
		current = DataBase.getData().DriverExists(userName, password);
		if(current != null) {
			return "Welcome driver " + current.getUserName() + " to system." + " -- Balance = " + current.getWallet().getBalance()+
					"\n1- Show Available Rides" +
					"\n2- Show Complete Rides" + 
					"\n3- Show Client Ratings" + 
					"\n4- Show Favorite Areas" + 
					"\n5- Add Favortite Area";
		}else
			return "there's no such a driver";
	}
	@GetMapping("drivers/signout")
	public String signOut() {
		if(current != null) {
			current = null;
			return "you have been signed out.";
		}else
			return "you must sign in first";
	}
	@GetMapping("drivers/1")
	public String showAvailable() {
		if(current != null) {
			return current.displayAvailable();
		}else
			return "You must sign in first.";		
	}
	@PutMapping("drivers/1/{index}/{cost}")
	public String setCost(@PathVariable int index, @PathVariable double cost) {
		if(current != null) {		
			if(current.setOffer(index, cost)) {
				return "offer has been added successfully";
			}else
				return "please choose correct ride";
		}else
			return "You must sign in first.";	
	}
	@GetMapping("drivers/2")
	public String showComlete() {
		if(current != null) {
			return current.displayComplete().toString();
		}else
			return "You must sign in first.";
	}
	@GetMapping("drivers/3")
	public String showRatings() {
		if(current != null) {
			return current.displayRatings();
		}else
			return "You must sign in first.";
	}
	@GetMapping("drivers/4")
	public String showAreas() {
		if(current != null) {
			System.out.println(current.displayFavAreas());
			return current.displayFavAreas();
		}else
			return "You must sign in first.";
	}
	
	@PutMapping("drivers/5/{area}")
	public String addFavAreas(@PathVariable String area) {
		if(current != null) {		
			current.setFavArea(area);
			return "Area has been added successfully.";
		}else
			return "you must sign in first";
	}
}
