package cs.software.project.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import cs.software.project.service.Admin;
import cs.software.project.service.DataBase;

@RestController
public class AdminController {

	Admin myAdmin = new Admin();
	@GetMapping("/admin")
	public String menu() {
		return "Welcome Admin to Rakabny"+
		"\n1- Accept pending drivers"+
		"\n2- Delete driver"+
		"\n3- Delete client"+
		"\n4- Show Rides State"+
		"\n5- Add 10% discount to areas";
	}
	
	@DeleteMapping("/admin/3/{userName}")
	public String deleteClient(@PathVariable String userName) {
		if(DataBase.getData().clientNameExists(userName) != null) {
			Admin.suspendClient(userName);
			return "Client has been deleted succesfully";
		}else {
			return "There's such a client";
		}
	}
	
	@DeleteMapping("/admin/2/{userName}")
	public String deleteDriver(@PathVariable String userName) {
		if(DataBase.getData().driverNameExists(userName) != null) {
			Admin.suspendDriver(userName);
			return "Driver has been deleted succesfully";
		}else {
			return "There's such a driver";
		}
	}
	
	@PutMapping("/admin/5/{area}")
	public String addAreas(@PathVariable String area) {
		Admin.addDiscountArea(area);
		return "Area added succesfully.";
	}
	@GetMapping("/admin/4")
	public String showPenidingRides() {
		return Admin.DisplayAllPendingRides();
	}
	@GetMapping("/admin/4/{index}")
	public String showRidesState(@PathVariable int index) {
		return Admin.showStateOfRide(index-1);
	}
	@GetMapping("/admin/1")
	public String showPendingDrivers() {
		
		return Admin.getPendingDrivers().toString();
	}
	@GetMapping("/admin/1/{userName}")
	public String acceptPendingDriver(@PathVariable String userName) {
		if(Admin.AcceptDriver(userName))
			return "Driver " + userName +"has been added successfully to database";
		else
			return "There's no such a driver.";
	}
}
