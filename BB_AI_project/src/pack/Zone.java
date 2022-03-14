package pack;

import java.util.ArrayList;


public class Zone {
	
	private ArrayList<Zone> buurZones; 
	private String id;
	
	
	public Zone(ArrayList<Zone> buurZones, String id, ArrayList<Auto> geparkeerdeAutos) {
		this.buurZones = buurZones;
		this.id = id;
		
	}
	
	
	@Override
	public String toString() {
		String tot ="------\n"+"id = "+ this.id+"\n";
		tot+="geburen: ";
		for(int i=0;i<buurZones.size();i++) {
			tot+=buurZones.get(i).getId()+" ";
		}
		tot+="\n------\n";
		return tot;
		
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

	
}
