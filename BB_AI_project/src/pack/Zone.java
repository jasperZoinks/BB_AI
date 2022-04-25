package pack;

import java.util.ArrayList;


public class Zone {
	
	private ArrayList<Zone> buurZones; 
	private String id;
	private int idInt;
	
	
	public Zone(ArrayList<Zone> buurZones, String id, ArrayList<Auto> geparkeerdeAutos,int idInt) {
		this.buurZones = buurZones;
		this.id = id;
		this.idInt=idInt;
	}
	
	public int getIdInt() {
		return idInt;
	}


	public void setIdInt(int idInt) {
		this.idInt = idInt;
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
