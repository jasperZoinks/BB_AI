package pack;

import java.util.ArrayList;

public class Request {

	private String ID;
	private Zone ZID;
	private int dag;
	private int starttijd;
	private int duurtijd;
	private ArrayList<Auto> autos;
	private int P1;
	private int P2;
	
	public Request(String ID, Zone ZID, int dag, int starttijd, int duurtijd, ArrayList<Auto> autos, int P1, int P2){
		this.ID = ID;
		this.ZID = ZID;
		this.dag = dag;
		this.starttijd = starttijd;
		this.duurtijd = duurtijd;
		this.autos = autos;
		this.P1 = P1;
		this.P2 = P2;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Zone getZID() {
		return ZID;
	}

	public void setZID(Zone zID) {
		ZID = zID;
	}

	public int getDag() {
		return dag;
	}

	public void setDag(int dag) {
		this.dag = dag;
	}

	public int getStarttijd() {
		return starttijd;
	}

	public void setStarttijd(int starttijd) {
		this.starttijd = starttijd;
	}

	public int getDuurtijd() {
		return duurtijd;
	}

	public void setDuurtijd(int duurtijd) {
		this.duurtijd = duurtijd;
	}

	public ArrayList<Auto> getAutos() {
		return autos;
	}

	public void setAutos(ArrayList<Auto> autos) {
		this.autos = autos;
	}

	public int getP1() {
		return P1;
	}

	public void setP1(int p1) {
		P1 = p1;
	}

	public int getP2() {
		return P2;
	}

	public void setP2(int p2) {
		P2 = p2;
	}
	
	
}
