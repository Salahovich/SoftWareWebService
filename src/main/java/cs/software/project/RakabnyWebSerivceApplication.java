package cs.software.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import cs.software.project.service.DataBase;
import cs.software.project.service.Driver;
import cs.software.project.service.Client;

@SpringBootApplication
public class RakabnyWebSerivceApplication {

	public static void main(String[] args) {
		DataBase.getData().addDriverToSystem(new Driver("ahmed","123456","021323","2001-3-1","@gmail.com","fdsfds","fdsfserew"));
	/***/	DataBase.getData().addClientToSystem(new Client("mido","123456","021323","2001-3-1","@gmail.com"));
	/***/	DataBase.getData().addClientToSystem(new Client("ali","123456","021323","2001-3-1","@gmail.com"));
		SpringApplication.run(RakabnyWebSerivceApplication.class, args);

	}

}
