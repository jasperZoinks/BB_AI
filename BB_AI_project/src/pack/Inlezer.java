package pack;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Inlezer {
	ArrayList <Request> allReq= new ArrayList<Request>();
	ArrayList <Auto> allAuto = new ArrayList<Auto>();
	ArrayList <Zone> allZone = new ArrayList<Zone>();
	
	public Oplossing readIn(String path) {
	    try {
			String localDir = System.getProperty("user.dir");
			File file = new File(localDir + path);
			//System.out.println(file.getAbsolutePath());
	    	
			//first: read in all requests
	        Scanner myReader = new Scanner(file);
	        String data = myReader.nextLine();
	        String[] parts = data.split(":");
	        int countRequests=Integer.parseInt(parts[1].trim());
	        
	        //offset van 100 lijnen "toevoegen"
	        for(int i=0;i<Integer.parseInt(parts[1].trim());i++) {
	        	myReader.nextLine();
	        }
	        //zones & aanliggende zones uitlezen
	        String data2 = myReader.nextLine();
	        String[] parts2 = data2.split(":");
	        //System.out.println(parts2[1].trim());
	        this.readZones(myReader, Integer.parseInt(parts2[1].trim()));
	        
	        //correct aantal auto's aanmaken
	        String data3 = myReader.nextLine();
	        String[] parts3 = data3.split(":");
	        int countCar=Integer.parseInt(parts3[1].trim());
	        this.readCar(myReader,countCar);
	        
	        //de requests inlezen en in structuur steken
	        Scanner myReader2 = new Scanner(file);
	        myReader2.nextLine();	//1 lijn overslaan opnieuw, dit is de lijn dat zegt van 100 requests
	        this.readReq(myReader2,countRequests);
	        
	        myReader.close();
	        myReader2.close();
	        
	        
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }

        Oplossing ret = new Oplossing(this.allReq,this.allZone,this.allAuto);
        return ret;
	}
	public void readReq(Scanner reader,int countReq) {
		for(int i=0;i<countReq;i++) {
			String data = reader.nextLine();
			String[] allParts=data.split(";");
			
				//is bien als string
			String resID= allParts[0];
				//moet object zone
			String b = allParts[1];
			b=b.replace("z", "");
			int zoneIndex = Integer.parseInt(b);
			Zone zoneID= this.allZone.get(zoneIndex);
				//moet int zijn
			int dag= Integer.parseInt(allParts[2]);
				//moet int zijn
			int startT= Integer.parseInt(allParts[3]);
				//moet int zijn
			int duurT= Integer.parseInt(allParts[4]);
				//mogelijke voertuigen
			String[] allPossibleCar = allParts[5].split(",");
			ArrayList<Auto> carAdd = new ArrayList<Auto>();
			for(int j=0;j<allPossibleCar.length;j++) {
				String a = allPossibleCar[j];
				a = a.replace("car","");
				int carIndex = Integer.parseInt(a);
				carAdd.add(this.allAuto.get(carIndex));
			}	
				//moet int zijn
			int pen1= Integer.parseInt(allParts[6]);
				//moet int zijn
			int pen2= Integer.parseInt(allParts[7]);
			Request nReq = new Request(resID,zoneID,dag,startT,duurT,carAdd,pen1,pen2);
			this.allReq.add(nReq);		
			
		}
	}
	
	public void readCar(Scanner reader,int countCar) {
		for(int i=0; i<countCar;i++){
			Auto temp = new Auto("car"+i);
			this.allAuto.add(temp);
		}
		/*
		for(int j=0;j<this.allAuto.size();j++) {
			System.out.println(this.allAuto.get(j).toString());
		}
		*/
	}
	
	public void readZones(Scanner reader,int countZone) {
		
		//alle zones aanmaken
		for(int i=0;i<countZone;i++) {
			Zone temp =new Zone(null,"z"+i,null,0);
			this.allZone.add(temp);
		}
		
		//zones correct aan elkaar linken
		for(int j=0; j<countZone;j++) {
			
			String data = reader.nextLine();
			
			String[] allParts=data.split(";");
			String[] zoneNeigh=allParts[1].split(",");
			
			//list met alle zones aanmaken
			ArrayList<Zone> allNeigh=new ArrayList<Zone>();
			for(int k=0; k<zoneNeigh.length;k++) {
				String a = zoneNeigh[k];
				a = a.replace("z","");
				int zoneIndex = Integer.parseInt(a);
				this.allZone.get(zoneIndex).setIdInt(zoneIndex);
				//adding these indexes to the zone
				allNeigh.add(this.allZone.get(zoneIndex));
			}
			//deze lijst aan de auto toewijzen
			this.allZone.get(j).setBuurZones(allNeigh);		
		}
	}
}
