package cs.software.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String addDriver(@RequestBody Driver c) {	
		if(DataBase.getData().driverNameExists(c.getUserName()) ==null) {
			Admin.addToPendingDrivers(c);
			return "the driver has been added to pending queue.";
		}else
			return "The driver userName is unavailable";
	}
	
	@GetMapping("drivers/signin")
	public String signIn(@RequestParam String userName, @RequestParam String password) {
		current = DataBase.getData().DriverExists(userName, password);
		if(current != null) {
			return "Welcome Driver" + userName + " to system."
			+ "1- T Show available rides" + "2- Show Client Ratings" + "3- Show Favorite Areas" + "4-  Add favortite Area";
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
	@PostMapping("drivers/{userName}/4")
	public String addFavAreas(@PathVariable String userName, @RequestBody String area) {
		if(current != null && current.getUserName().equals(userName)) {				
			current.setFavArea(area);
			return "Area has been added successfully.";
		}else
			return "you must sign in first";
	}
	@GetMapping("drivers/{userName}/3")
	public String showAreas(@PathVariable String userName) {
		if(current != null) {
			return current.displayFavAreas();
		}else
			return "You must sign in first.";
	}
}
