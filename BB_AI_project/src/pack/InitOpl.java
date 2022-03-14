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
		ArrayList<Zone> zones = opl.getZones();
		int j=0;
		for(int i=0; i<autos.size(); i++)
		{
			autos.get(i).setZone(zones.get(j++));
			//zones.get(j).addGeparkeerdeAutos(autos.get(i));
			if(j==zones.size()) {j=0;}
		}
	}
	
	public void koppelReq() {
		ArrayList<Request> req = opl.getReq();
		for(int i=0; i<req.size();i++)
		{
			ArrayList<Auto> autos = req.get(i).getAutos();
			for(int j=0; j<autos.size(); j++)
			{
				Auto a = autos.get(j);
				if(a.getZone().getId()==req.get(i).getZID().getId())//ligt auto in zone
				{
					if(a.isFree(req.get(i).getDag(), req.get(i).getStarttijd(), req.get(i).getDuurtijd()))
					{
						req.get(i).wijsToe(a);
						break;
					}
				}
			}
			
		}
	}
}
