package pack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main extends Thread{
	private String inputfile;
	private String outputfile;
	int duur;
	int nr;
	
	public Main(String inputfile, String outputfile, int duur, int nr) {
		this.inputfile = inputfile;
		this.outputfile = outputfile;
		this.duur = duur;
		this.nr = nr;
	}
	
	@Override
	public void run() {
		System.out.println("Thread " + this.nr);
		Inlezer nInlezer = new Inlezer();
		String[] s = outputfile.split("\\.");
		String outputfilename = s[0] +"_"+ this.nr +"."+ s[1];
		Uitschrijver writer = new Uitschrijver(outputfilename);
		String pathToFile = this.inputfile;
		Oplossing firstSol=nInlezer.readIn(pathToFile);
		InitOpl initOpl = new InitOpl(firstSol);
		
		initOpl.verdeelAutos();
		try {
			writer.Schrijven(initOpl.getOpl());
		} catch (IOException e) {
			System.out.println("Wegschrijven mislukt.");
		}
		
		long startTime = System.currentTimeMillis();
		
		//Optie 1
		/*
		Oplossing bestOpl = initOpl.getOpl();
		bestOpl.setKost(30000);
		Oplossing newOpl =bestOpl;		
		
		int counter=0;
		while(System.currentTimeMillis()-startTime) < (this.duur*1000)) {
			counter++;
			newOpl = bestOpl.makeChanges();
						
			if(newOpl.getKost()<bestOpl.getKost()) {
				//System.out.println("--------");
				//System.out.println("better solution found!");
				//System.out.println("Old Cost: "+ bestOpl.getKost()+" , New Cost : "+newOpl.getKost());
				
				bestOpl=newOpl;
				this.writeSolution(bestOpl);
				System.out.println(bestOpl.getKost());
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
		while((System.currentTimeMillis()-startTime) < (this.duur*1000)) //Local Search met maximale duur
		{
			ArrayList<Oplossing> potOpl = new ArrayList<Oplossing>();
			System.out.println("===");
			for(int i = 0; i<bestOpl.getAutos().size(); i++) //LS adhv het verplaatsen van auto's
			{
				Oplossing newOpl = bestOpl.duplicate();
				newOpl.changeOne(i);
				newOpl.koppelReq(newOpl);
				potOpl.add(newOpl);				
			}
			for(int i = 0; i<bestOpl.getReq().size(); i++) //LS adhv het wijzigen van de volgorde van requests
			{
				Oplossing newOpl = bestOpl.duplicate();
				
				newOpl.changeOrder(i);
				//newOpl.changeOrder();
				newOpl.changeOrderWithinReq(i);
				newOpl.koppelReq(newOpl);
				
				potOpl.add(newOpl);				
			}
			for(int i = 0;i<potOpl.size(); i++) //Check of er een betere oplossing gevonden is
			{ 
				if(potOpl.get(i).getKost() < bestOpl.getKost())
				{
					//System.out.println("better solution found!");
					//System.out.println("Old Cost: "+ bestOpl.getKost()+" , New Cost : " + potOpl.get(i).getKost());
					
					bestOpl = potOpl.get(i);
					teller = 0;
					
					if(bestOpl.getKost() < allTimeBestOpl.getKost()) //Schrijf de beste oplossing tot nu toe weg
					{
						allTimeBestOpl = bestOpl.duplicate();
						allTimeBestOpl.koppelReq(allTimeBestOpl);
						try {
							writer.Schrijven(allTimeBestOpl);
						} catch (IOException e) {
							System.out.println("Wegschrijven mislukt.");
						}
						System.out.println(allTimeBestOpl.getKost());
					}
				}
			}
			if(teller > 150) //Wanneer er 150 keer geen verbetering komt -> maak bewust een random beweging
			{
				teller = 0;
				int tmpKost = bestOpl.getKost();
				bestOpl.changeOne((int)(Math.random() * bestOpl.getAutos().size()));
				bestOpl.koppelReq(bestOpl);
				//System.out.println("\n random change");
				//System.out.println("Old Cost: "+ tmpKost+" , New Cost : " + bestOpl.getKost());
			}
			teller++;
		}
	}

	public static void main(String[] args) {
		String inputfile = "in.txt";
		String outputfile = "out.txt";
		int duur = 5;
		int threads = 1;
		int seed = 0;
		try
		{
			inputfile = args[0];
			outputfile = args[1];
			duur = Integer.parseInt(args[2]);
			seed = Integer.parseInt(args[3]); //thanks voor de seed
			threads = Integer.parseInt(args[4]);
			
			Main m = null;
			for(int i=0; i<threads; i++)
			{
				m = new Main(inputfile, outputfile, duur, i);
				m.start();
			}
			
			while(true) {
				if(!m.isAlive())
				{
					System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
					TimeUnit.MILLISECONDS.sleep(100);
					Uitschrijver uit = new Uitschrijver(outputfile);
					uit.finalOplossing(threads);
					break;
				}
			}
			
		}
		catch (Exception e)
		{
			System.out.println("Iets mis met de argumenten.");
		}
	}
}
