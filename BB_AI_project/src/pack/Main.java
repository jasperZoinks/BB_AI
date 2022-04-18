package pack;

import java.io.IOException;

public class Main {
	
	public Main() {
		Inlezer nInlezer=new Inlezer();
		String pathToFile = "\\src\\210_5_44_25.csv";
		Oplossing firstSol=nInlezer.readIn(pathToFile);
		//System.out.println(firstSol.toString());
		InitOpl initOpl = new InitOpl(firstSol);
		initOpl.verdeelAutos();
		
		Oplossing bestOpl = initOpl.getOpl();
		bestOpl.setKost(30000);
		Oplossing newOpl =bestOpl;		
		
		int counter=0;
		while(true) {
			counter++;
			newOpl = bestOpl.makeChanges();
						
			if(newOpl.getKost()<bestOpl.getKost()) {
				System.out.println("--------");
				System.out.println("better solution found!");
				System.out.println("Old Cost: "+ bestOpl.getKost()+" , New Cost : "+newOpl.getKost());
				
				bestOpl=newOpl;
				this.writeSolution(bestOpl);
			}
			else
			{
				//System.out.println("Geen betere Opl " + bestOpl.getKost() + ", " + newOpl.getKost());
			}
			if( (counter%100000)==0) {
				counter=0;
				System.out.println("\tBRRrrRRrrRrR CalCuLATinG......");
			}
			
		}
	}
	
	public void writeSolution(Oplossing curOplossing) {
		Uitschrijver writer = new Uitschrijver(curOplossing);
		try {
			writer.Schrijven();
			System.out.println("oplossing --> weggeschreven!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Main m = new Main();

	}

}
