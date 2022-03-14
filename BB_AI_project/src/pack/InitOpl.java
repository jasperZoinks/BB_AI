package pack;

import java.util.ArrayList;

public class InitOpl {

	private Oplossing opl;
	
	public InitOpl(Oplossing opl) {
		this.opl = opl;
	}

	public Oplossing getOpl() {
		return opl;
	}

	public void setOpl(Oplossing opl) {
		this.opl = opl;
	}
	
	public void verdeelAutos() {
		ArrayList<Auto> autos = opl.getAutos();
		int aantAutos = autos.size(); 
		ArrayList<Zone> zones = opl.getZones();
		int aantZones = zones.size();
		int aantalZones = opl.getZones().size();
		int j=0;
		for(int i=0; i<aantAutos; i++)
		{
			autos.get(i).setZone(zones.get(j++));
			zones.get(j).addGeparkeerdeAutos(autos.get(i));
			if(j==zones.size()) {j=0;}
		}
	}
	
	public void koppelReq() {
		
	}
}
