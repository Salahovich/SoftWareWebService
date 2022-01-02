package cs.software.project.service;


import java.util.Date;

public class Event {

	private String eventName;
	private Date eventDate;
	
	Event(String name, Date date){
		this.eventName = name;
		this.eventDate = date;
	}
	
	public String getName() {
		return this.eventName;
	}
	public Date getEventDate() {
		return this.eventDate;
	}
	
	@SuppressWarnings("deprecation")
	public String toString() {
		return eventName + " at " + eventDate.getHours() + ":" + eventDate.getMinutes();
	}
}

