package pack;

import java.util.*;

public class Auto {
	private String id;
	private ArrayList<Request> allRequests;
	private Zone zone;
	private static final int minInDag = 1440;
	
	public Auto(String id, ArrayList<Request> allRequests) {
		this.id=id;
		this.allRequests = new ArrayList<Request>();
		this.zone=null;
	}
	
	@Override
	public String toString() {
		return "Auto [id=" + id + ", allRequests=" + allRequests + ", zone=" + zone + "]";
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
	
	public void addReq(Request req) {
		this.allRequests.add(req);
	}
	
	public boolean isFree(int dag, int starttijd, int duurtijd) {
		int startpunt = dag*minInDag + starttijd;
		int eindpunt = startpunt + duurtijd;
		for(int i = 0; i < this.allRequests.size(); i++)
		{
			int start = this.allRequests.get(i).getDag()*minInDag + this.allRequests.get(i).getStarttijd();
			int eind = start + this.allRequests.get(i).getDuurtijd();
			if(!(eind < startpunt || start > eindpunt))
			{
				return false;
			}
		}
		return true;
	}
	

}
