package cs.software.project.controller;

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
			return "Welcome client" + userName + " to system."
			+ "1- Take a ride" + "2- Display my Complete Rides" + "3- Display my Pending Rides" + "4- Deposit money to the wallet";
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
	
	@GetMapping("clients/{userName}/2")
	public String displayCompleted(@PathVariable String userName) {
		if(current != null && current.getUserName().equals(userName)) {
			return current.displayCompleted();
		}else
			return "You must sign in first.";
	}
	
	@GetMapping("clients/{userName}/3")
	public String displayPending(@PathVariable String userName) {
		if(current != null && current.getUserName().equals(userName)) {
			return current.displayPending();
		}else
			return "You must sign in first.";
	}
	
	@PutMapping("clients/{userName}/4/{value}")
	public String displayPending(@PathVariable String userName,@PathVariable int value) {
		if(current != null && current.getUserName().equals(userName)) {		
			current.getWallet().deposit(value);
			return "you have added " + value + "$ to your balance.";
		}else
			return "You must sign in first.";
	}
}
