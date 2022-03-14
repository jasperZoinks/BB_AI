package pack;

import java.util.ArrayList;


public class Zone {
	
	private ArrayList<Zone> buurZones; 
	private String id;
	private ArrayList<Auto> geparkeerdeAutos;
	
	
	public Zone(ArrayList<Zone> buurZones, String id, ArrayList<Auto> geparkeerdeAutos) {
		this.buurZones = buurZones;
		this.id = id;
		this.geparkeerdeAutos = geparkeerdeAutos;
	}
	
	public ArrayList<Zone> getBuurZones() {
		return buurZones;
	}
	public void setBuurZones(ArrayList<Zone> buurZones) {
		this.buurZones = buurZones;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<Auto> getGeparkeerdeAutos() {
		return geparkeerdeAutos;
	}
	public void setGeparkeerdeAutos(ArrayList<Auto> geparkeerdeAutos) {
		this.geparkeerdeAutos = geparkeerdeAutos;
	}

	
}
