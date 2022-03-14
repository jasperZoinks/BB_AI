package pack;

import java.util.ArrayList;

public class Oplossing {

	private static int IDteller = 0;
	private int ID;
	private ArrayList<Request> req;
	private ArrayList<Zone> zones;
	private ArrayList<Auto> autos;
	private int kost;
	
	public Oplossing(int ID, ArrayList<Request> req, ArrayList<Zone> zones, ArrayList<Auto> autos) {
		this.ID = Oplossing.IDteller++;
		this.req = req;
		this.zones = zones;
		this.autos = autos;
		this.kost = 0;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public ArrayList<Request> getReq() {
		return req;
	}

	public void setReq(ArrayList<Request> req) {
		this.req = req;
	}

	public ArrayList<Zone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
	}

	public ArrayList<Auto> getAutos() {
		return autos;
	}

	public void setAutos(ArrayList<Auto> autos) {
		this.autos = autos;
	}

	public int getKost() {
		return kost;
	}

	public void setKost(int kost) {
		this.kost = kost;
	}
	
	
	
	
}
