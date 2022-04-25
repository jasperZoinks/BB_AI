package pack;

import java.util.ArrayList;
import java.util.Random;

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
		//Verdeelt de autos over de zones op een zeer eenvoudige manier
		ArrayList<Auto> autos = opl.getAutos(); 
		ArrayList<Zone> zones = opl.getZones();
		int j=0;
		for(int i=0; i<autos.size(); i++)
		{
			autos.get(i).setZone(zones.get(j++));
			if(j==zones.size()) {j=0;}
		}
		this.opl.setAutos(autos);
		this.opl.setZones(zones);
	}
	
	public void verdeelAutosRandom() {
		ArrayList<Auto> autos = opl.getAutos(); 
		ArrayList<Zone> zones = opl.getZones();
		int j=0;
		Random rand = new Random();	//de seed van deze random fctie is een gekregen argument
		int upperRandom=opl.getZones().size();		
		int randomInt;
		for(int i=0; i<autos.size(); i++)
		{
			randomInt=rand.nextInt(upperRandom);
			autos.get(i).setZone(zones.get(randomInt));
		}
		this.opl.setAutos(autos);
		this.opl.setZones(zones);
	}
	
	public void koppelReq() {
		//Doet een eerste poging om alle request te koppelen aan een auto. (zonder rekening te houden met buurzones)
		ArrayList<Request> req = opl.getReq();
		for(int i=0; i<req.size();i++)
		{
			ArrayList<Auto> autos = req.get(i).getAutos();
			for(int j=0; j<autos.size(); j++)
			{
				Auto a = autos.get(j);
				if(a.getZone().getId()==req.get(i).getZID().getId())//ligt auto in zone?
				{
					//kijken of auto op dat tijdstip vrij is
					if(a.isFree(req.get(i).getDag(), req.get(i).getStarttijd(), req.get(i).getDuurtijd()))
					{
						//auto toewijzen
						req.get(i).wijsToe(a);
						break;
					}
				}
			}
		}
	}
}
