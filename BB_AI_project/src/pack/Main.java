package pack;

import java.io.IOException;

public class Main {
	
	public Main() {
		Inlezer nInlezer=new Inlezer();
		String pathToFile = "\\src\\100_5_14_25.csv";
		Oplossing firstSol=nInlezer.readIn(pathToFile);
		System.out.println(firstSol.toString());
		InitOpl initOpl = new InitOpl(firstSol);
		initOpl.verdeelAutos();
		
		Oplossing bestOpl= initOpl.getOpl().makeChanges();
		Oplossing newOpl =bestOpl;
		
		
		int counter=0;
		while(true) {
			counter++;
			newOpl = bestOpl.makeChanges();
			if(newOpl.getKost()<=bestOpl.getKost()) {
				System.out.println("--------");
				System.out.println("better solution found!");
				System.out.println("Old Cost: "+ bestOpl.getKost()+" New Cost : "+newOpl.getKost());
				System.out.println("--------");
				bestOpl=newOpl;
				if(bestOpl.getKost()<10000)
					break;
			}
			if( (counter%1000000)==0) {
				counter=0;
				System.out.println("No Better solution this kost : "+newOpl.getKost());
			}
			
		}
		
		initOpl.koppelReq();
		Uitschrijver writer = new Uitschrijver(firstSol);
		try {
			writer.Schrijven();
			System.out.println("InitOpl --> weggeschreven!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Main m = new Main();

	}

}
