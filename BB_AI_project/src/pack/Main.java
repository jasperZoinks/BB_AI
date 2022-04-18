package pack;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	public Main() {
		Inlezer nInlezer=new Inlezer();
		String pathToFile = "\\src\\210_5_44_25.csv";
		Oplossing firstSol=nInlezer.readIn(pathToFile);
		//System.out.println(firstSol.toString());
		InitOpl initOpl = new InitOpl(firstSol);
		initOpl.verdeelAutos();
		this.writeSolution(initOpl.getOpl());
		
		//Optie 1
		/*
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
		*/
		
		//Optie 2
		Oplossing bestOpl = initOpl.getOpl();
		bestOpl.setKost(bestOpl.calcKost());
		Oplossing allTimeBestOpl = bestOpl.duplicate();
		allTimeBestOpl.koppelReq(allTimeBestOpl);

		int teller = 0;
		while(true)
		{
			ArrayList<Oplossing> potOpl = new ArrayList<Oplossing>();
			System.out.println("===");
			for(int i = 0; i<bestOpl.getAutos().size(); i++)
			{
				Oplossing newOpl = bestOpl.duplicate();
				newOpl.changeOne(i);
				newOpl.koppelReq(newOpl);
				potOpl.add(newOpl);				
			}
			for(int i = 0; i<bestOpl.getReq().size(); i++)
			{
				Oplossing newOpl = bestOpl.duplicate();
				newOpl.changeOrder(i);
				newOpl.koppelReq(newOpl);
				potOpl.add(newOpl);				
			}
			for(int i = 0;i<potOpl.size(); i++)
			{
				if(potOpl.get(i).getKost() < bestOpl.getKost())
				{
					System.out.println("better solution found!");
					System.out.println("Old Cost: "+ bestOpl.getKost()+" , New Cost : " + potOpl.get(i).getKost());
					
					bestOpl = potOpl.get(i);
					teller = 0;
					
					if(bestOpl.getKost() < allTimeBestOpl.getKost())
					{
						allTimeBestOpl = bestOpl.duplicate();
						allTimeBestOpl.koppelReq(allTimeBestOpl);
						this.writeSolution(allTimeBestOpl);
					}
				}
			}
			if(teller > 150)
			{
				teller = 0;
				int tmpKost = bestOpl.getKost();
				bestOpl.changeOne((int)(Math.random() * bestOpl.getAutos().size()));
				bestOpl.koppelReq(bestOpl);
				System.out.println("\n random change");
				System.out.println("Old Cost: "+ tmpKost+" , New Cost : " + bestOpl.getKost());
			}
			teller++;
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
