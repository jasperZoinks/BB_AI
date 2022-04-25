package pack;

import java.util.ArrayList;
import java.util.Random;

public class Oplossing {

	//private static int IDteller = 0;
	private int ID;
	private ArrayList<Request> req;
	private ArrayList<Zone> zones;
	private ArrayList<Auto> autos;
	private int kost;
	
	public Oplossing(ArrayList<Request> req, ArrayList<Zone> zones, ArrayList<Auto> autos) {
		//this.ID = Oplossing.IDteller++;
		this.req = req;
		this.zones = zones;
		this.autos = autos;
		this.kost = 0;
	}

	@Override
	public String toString() {
		return "Oplossing [ID=" + ID + ", req=" + req.size() + ", zones=" + zones.size() + ", autos=" + autos.size() + ", kost=" + kost
				+ "]";
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
	
	public int calcKost() {
		int kost = 0;
		
		for(int i = 0; i<this.getReq().size(); i++)
		{
			if(this.getReq().get(i).getToegAuto()==null)
			{
				kost += this.getReq().get(i).getP1();
			}
			else
			{
				if(this.getReq().get(i).getToegAuto().getZone().getId() != this.getReq().get(i).getZID().getId())
				{
					kost += this.getReq().get(i).getP2();
				}
			}
		}
		
		return kost;
	}
	
	public void cleanUp(ArrayList<Auto> cleanedCar,ArrayList<Request> cleanedReq) {
		if(cleanedCar!=null) {
			for(int i=0;i<this.getAutos().size();i++) {
				//lege requests aan koppelen
				Auto a=new Auto(this.getAutos().get(i).getId(),new ArrayList<Request>(),this.getAutos().get(i).getZone());
				cleanedCar.add(a);
			}
		}
		for(int i=0;i<this.getReq().size();i++) {
			Request r = new Request(this.getReq().get(i).getID(),
					this.getReq().get(i).getZID(),
					this.getReq().get(i).getDag(),
					this.getReq().get(i).getStarttijd(),
					this.getReq().get(i).getDuurtijd(),
					this.getReq().get(i).getAutos(),
					this.getReq().get(i).getP1(),
					this.getReq().get(i).getP2());
			cleanedReq.add(r);
		}
		
	}
	
	public Oplossing makeChanges() {
		//algoritme om veranderingen te maken
		//veranderingen die kunnen gedaan worden:
		//		-auto aan andere zone toewijzen
		//		-een andere mogelijke auto toewijzen van de mogelijke requests
		
		Random rand = new Random();	//de seed van deze random fctie is een gekregen argument
		int upperRandom=this.getZones().size();			
		int randomInt;
		
		//nog proper maken van de items:
		ArrayList<Auto> cleanedCar = new ArrayList<Auto>();
		ArrayList<Request> cleanedReq = new ArrayList<Request>();
		this.cleanUp(cleanedCar, cleanedReq);

		Oplossing newOpl = new Oplossing(cleanedReq, this.getZones(),cleanedCar);
		
		//auto's hun plaats aanpassen

		for(int i=0;i<newOpl.getAutos().size();i++) {
			randomInt=rand.nextInt(upperRandom);
			//auto zijn zone aanpassen,
			newOpl.getAutos().get(i).setZone(newOpl.getZones().get(randomInt));
		}
		//nu hebben we een nieuwe oplossing gemaakt
		this.koppelReq(newOpl);
		
		//nu het beste van de 2 onthouden, de huidige oplossing en de nieuwe oplossing
		Oplossing bothWorlds= bestOf2Worlds(newOpl);
		this.koppelReq(bothWorlds);
		
		return bothWorlds;
		
	}
	
	public void koppelReq(Oplossing toCheck) {
		/*
			-Functie krijgt een gevormde oplossing en berekend de kost van deze oplossing
		*/
		
		//ArrayList<Request> req = toCheck.getReq().get(i); //hoe we nu aan de requests geraken
		boolean carMatchReq=false;
		boolean toegewezen=false;
		//over elke request
		for(int i=0; i<toCheck.getReq().size();i++)
		{			
			toegewezen=false;
			//over elke auto
			nextCarCheck:
			for(int j=0; j<toCheck.getAutos().size(); j++)
			{
				Auto a = toCheck.getAutos().get(j);
				//naar volgende zaken kijken: 1) in correcte zone (of naburig) & 2) auto past bij request & 3) auto beschikbaar
				
				//2) kijken of de auto bij deze request past
				carMatchReq=false;
				for(int l=0;l<toCheck.getReq().get(i).getAutos().size();l++) {
					//indien zelfde id -> mag het aan deze request toegewezen worden
					if (a.getId()==toCheck.getReq().get(i).getAutos().get(l).getId()) {
						carMatchReq=true;
					}
				}
				if(!carMatchReq)
					continue;	//volgende proberen.
				
				//1) auto in correcte zone?
				//eigen zone check
				if(a.getZone().getIdInt()==toCheck.getReq().get(i).getZID().getIdInt()) {
					//zitten in de zone waar de auto oorspronkelijk staat
					
					//3) is auto beschikbaar?
					if(a.isFree(toCheck.getReq().get(i).getDag(), toCheck.getReq().get(i).getStarttijd(), toCheck.getReq().get(i).getDuurtijd()))
					{
						//auto toewijzen
						toCheck.getReq().get(i).wijsToe(a);
						//request ook aan auto toewijzen
						a.getAllRequests().add(toCheck.getReq().get(i));
						toegewezen=true;
						break;
						//geen penalty aan de oplossing toewijzen
					}
				}
				//loop over alle buurzones
				for(int k=0;k<toCheck.getReq().get(i).getZID().getBuurZones().size();k++) {
					//indien hetzelfde
					if(a.getZone().getIdInt()==toCheck.getReq().get(i).getZID().getBuurZones().get(k).getIdInt()) {
						//zitten in buurzone
						
						//3) is auto beschikbaar?
						if(a.isFree(toCheck.getReq().get(i).getDag(), toCheck.getReq().get(i).getStarttijd(), toCheck.getReq().get(i).getDuurtijd()))
						{
							//auto toewijzen
							toCheck.getReq().get(i).wijsToe(a);
							//request ook aan auto toewijzen
							a.getAllRequests().add(toCheck.getReq().get(i));
							//kost toewijzen aan deze oplossing.
							toCheck.setKost(toCheck.getKost()+toCheck.getReq().get(i).getP2());
							toegewezen=true;
							break nextCarCheck;	//deze request is dus toegewezen en breken helemaal eruit, naar volgende request.
						}
					}
					
				}
			}
			//kijken of de request is toegewezen
			if(!toegewezen){
				//niet toegewezen, kost p1 erbij tellen
				toCheck.setKost(toCheck.getKost()+toCheck.getReq().get(i).getP1());
			}
			
		}
	}
	
	
	public Oplossing bestOf2Worlds(Oplossing newOpl) {
		
		ArrayList<Auto> cleanedCar = new ArrayList<Auto>();
		ArrayList<Request> cleanedReq = new ArrayList<Request>();
		this.cleanUp(null, cleanedReq);	//auto moet nu niet overgeschreven worden
		
		for(int i=0;i<newOpl.getAutos().size();i++) {
			Auto a;
			if(newOpl.getAutos().get(i).getAllRequests().size()<this.getAutos().get(i).getAllRequests().size()) {
				//betekend dat de nieuwe oplossing aan minder requests voldoet -> neem dan terug de oude oplossing
				a=new Auto(this.getAutos().get(i).getId(),new ArrayList<Request>(),this.getAutos().get(i).getZone());
			}
			else {
				a=new Auto(newOpl.getAutos().get(i).getId(),new ArrayList<Request>(),newOpl.getAutos().get(i).getZone());
			}
			cleanedCar.add(a);
			
		}
		Oplossing comboOpl = new Oplossing(cleanedReq, this.getZones(),cleanedCar);
		return comboOpl;
	}
	
	//Dupliceer een oplossing
	public Oplossing duplicate() {
		ArrayList<Auto> cleanedCar = new ArrayList<Auto>();
		ArrayList<Request> cleanedReq = new ArrayList<Request>();
		this.cleanUp(cleanedCar, cleanedReq);
		
		Oplossing dup;
		dup = new Oplossing(cleanedReq, this.zones, cleanedCar);
		
		return dup;
	}
	
	//Verplaats de i-de naar een random zone
	public void changeOne(int i) {
		int newZoneID = (int) (Math.random() * this.zones.size());
		this.autos.get(i).setZone(this.zones.get(newZoneID));
	}
	
	//wissel het i-de request met een random request
	public void changeOrder(int i) {
		int newPlace = (int) (Math.random() * this.req.size());
		Request tmp = this.req.get(newPlace);
		this.req.set(newPlace, this.req.get(i));
		this.req.set(i, tmp);
		
	}
	
}
