package pack;

import java.util.*;

public class Auto {
	private String id;
	private ArrayList<Request> allRequests;
	private Zone zone;
	
	public Auto(String id, ArrayList<Request> allRequests) {
		this.id=id;
		this.allRequests=allRequests;
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<Request> getAllRequests() {
		return allRequests;
	}
	public void setAllRequests(ArrayList<Request> allRequests) {
		this.allRequests = allRequests;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	

}
