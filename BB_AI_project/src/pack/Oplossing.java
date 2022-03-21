package pack;

import java.util.ArrayList;
import java.util.Random;

public class Oplossing {

	private static int IDteller = 0;
	private int ID;
	private ArrayList<Request> req;
	private ArrayList<Zone> zones;
	private ArrayList<Auto> autos;
	private int kost;
	
	public Oplossing(ArrayList<Request> req, ArrayList<Zone> zones, ArrayList<Auto> autos) {
		this.ID = Oplossing.IDteller++;
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
	public void cleanUp(ArrayList<Auto> cleanedCar,ArrayList<Request> cleanedReq) {
		for(int i=0;i<this.getAutos().size();i++) {
			Auto a=new Auto(this.getAutos().get(i).getId());
			cleanedCar.add(a);
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
	
	public void makeChanges() {
		//algoritme om veranderingen te maken
		//veranderingen die kunnen gedaan worden:
		//		-auto aan andere zone toewijzen
		//		-een andere mogelijke auto toewijzen van de mogelijke requests
		
		Random rand = new Random();	//de seed van deze random fctie is een gekregen argument
		int upperRandom=5;
		int randomInt;
		int nextSpot;
		int currentSpot;
		
		//nog proper maken van de items:
		ArrayList<Auto> cleanedCar = new ArrayList<Auto>();
		ArrayList<Request> cleanedReq = new ArrayList<Request>();
		this.cleanUp(cleanedCar, cleanedReq);
		//nog oppassen met die static die word aangepast!
		Oplossing newOpl = new Oplossing(cleanedReq, this.getZones(),cleanedCar);
		
		for(int i=0;i<this.getAutos().size();i++) {
			randomInt=rand.nextInt(upperRandom);
			//probleem: zone = null, zitten wel in de variabele maar krijgen geen waarde.
			currentSpot=newOpl.getAutos().get(i).getZone().getIdInt();
			nextSpot=currentSpot+randomInt;
			
			//als het over de max van mogelijke zones gaat
			if(nextSpot>this.getZones().size()) {
				nextSpot=nextSpot-this.getZones().size();
			}
			//auto zijn zone aanpassen
			newOpl.getAutos().get(i).setZone(this.getZones().get(nextSpot));
		}
		//nu hebben we een nieuwe oplossing gemaakt
		//de reservaties nu ook veranderen. ook direct kost berekenen, anders een geknoei
		this.koppelReq(newOpl);
		
		
	}
	
	public void koppelReq(Oplossing toCheck) {
		//ArrayList<Request> req = toCheck.getReq().get(i); //hoe we nu aan de requests geraken
		boolean carMatchReq=false;
		boolean toegewezen=false;
		for(int i=0; i<toCheck.getReq().size();i++)
		{			
			toegewezen=false;
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
	
	
	
}
