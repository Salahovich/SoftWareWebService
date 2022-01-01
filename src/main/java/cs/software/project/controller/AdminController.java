package cs.software.project.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import cs.software.project.service.Admin;
import cs.software.project.service.DataBase;
import cs.software.project.service.Driver;

@RestController
public class AdminController {

	@GetMapping("/admin")
	public String menu() {
		return "Welcome Admin to Rakabny"+
		"1- Accept pending drivers"+
		"2- Delete driver"+
		"3- Delete client"+
		"4- Show Rides State"+
		"5- Add 10% discount to areas";
	}
	
	@DeleteMapping("/admin/3/{userName}")
	public String deleteClient(@PathVariable String userName) {
		if(DataBase.getData().clientNameExists(userName) != null) {
			DataBase.getData().removeClientFromSystem(userName);
			return "Client has been deleted succesfully";
		}else {
			return "There's such a client";
		}
	}
	
	@DeleteMapping("/admin/2/{userName}")
	public String deleteDriver(@PathVariable String userName) {
		if(DataBase.getData().driverNameExists(userName) != null) {
			DataBase.getData().removeDriverFromSystem(userName);
			return "Driver has been deleted succesfully";
		}else {
			return "There's such a driver";
		}
	}
	
	@PostMapping("/admin/5")
	public String addAreas(@RequestBody String area) {
		DataBase.getData().addToDiscountAreas(area);
		return "Area added succesfully.";
	}
	@GetMapping("/admin/1")
	public String showPendingDrivers() {
		
		return Admin.getPendingDrivers().toString();
	}
	@GetMapping("/admin/1/{userName}")
	public String showPendingDrivers(@PathVariable String userName) {
		if(Admin.AcceptDriver(userName))
			return "Driver " + userName +"has been added successfully to database";
		else
			return "There's no such a driver.";
	}
}
