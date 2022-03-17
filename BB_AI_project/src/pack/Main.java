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
