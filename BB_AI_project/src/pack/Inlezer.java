package pack;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Inlezer {
	private String path="\\src\\100_5_14_25.csv";
	ArrayList <Request> allReq= new ArrayList<Request>();
	ArrayList <Auto> allAuto = new ArrayList<Auto>();
	ArrayList <Zone> allZone = new ArrayList<Zone>();
	
	public void readIn() {
	    try {
			String localDir = System.getProperty("user.dir");
			File file = new File(localDir + this.getPath());
			System.out.println(file.getAbsolutePath());
	    	
			//first: read in all requests
	        Scanner myReader = new Scanner(file);
	        String data = myReader.nextLine();
	        String[] parts = data.split(":");
	        int countRequests=Integer.parseInt(parts[1].trim());
	        
	        //offset van 100 lijnen "toevoegen"
	        for(int i=0;i<Integer.parseInt(parts[1].trim());i++) {
	        	myReader.nextLine();
	        }
	        String data2 = myReader.nextLine();
	        String[] parts2 = data2.split(":");
	        System.out.println(parts2[1].trim());
	        this.readZones(myReader, Integer.parseInt(parts2[1].trim()));
	        
	        String data3 = myReader.nextLine();
	        String[] parts3 = data3.split(":");
	        int countCar=Integer.parseInt(parts3[1].trim());
	        this.readCar(myReader,countCar);
	        
	        
	        
	        
	        Scanner myReader2 = new Scanner(file);
	        myReader2.nextLine();	//1 lijn overslaan opnieuw, dit is de lijn dat zegt van 100 requests
	        this.readReq(myReader2,countRequests);
	        
	        myReader.close();
	        
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
	public void readReq(Scanner reader,int countReq) {
		for(int i=0;i<countReq;i++) {
			String data = reader.nextLine();
			System.out.println(data);
			String[] allParts=data.split(";");
			
			//hebben nu 8 velden
			for(int j=0; j<allParts.length;j++) {
				//Request nReq = new Request();
				
			}
			
			
		}
	}
	
	public void readCar(Scanner reader,int countCar) {
		for(int i=0; i<countCar;i++){
			Auto temp = new Auto("car"+i,null);
			this.allAuto.add(temp);
		}
		for(int j=0;j<this.allAuto.size();j++) {
			System.out.println(this.allAuto.get(j).toString());
		}
	}
	
	public void readZones(Scanner reader,int countZone) {
				
		for(int i=0;i<countZone;i++) {
			Zone temp =new Zone(null,"z"+i,null);
			this.allZone.add(temp);
		}
		for(int j=0; j<countZone;j++) {
			
			String data = reader.nextLine();
			
			String[] allParts=data.split(";");
			String[] zoneNeigh=allParts[1].split(",");
			
			ArrayList<Zone> allNeigh=new ArrayList<Zone>();
			
			for(int k=0; k<zoneNeigh.length;k++) {
				String a = zoneNeigh[k];
				a = a.replace("z","");
				int zoneIndex = Integer.parseInt(a);
				//adding these indexes to the zone
				allNeigh.add(this.allZone.get(zoneIndex));
			}
			
			this.allZone.get(j).setBuurZones(allNeigh);		
		}
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static void main(String[] args) {
		Inlezer nInlezer=new Inlezer();
		nInlezer.readIn();
	}
	
}
