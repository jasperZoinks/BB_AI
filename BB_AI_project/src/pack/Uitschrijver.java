package pack;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Uitschrijver {

	private String outputfile;

	public Uitschrijver(String outputfile) {
		this.outputfile = outputfile;
	}
	
	public String getOutputfile() {
		return outputfile;
	}

	public void setOutputfile(String outputfile) {
		this.outputfile = outputfile;
	}

	public void Schrijven(Oplossing opl) throws IOException {
		FileWriter fw = new FileWriter(this.outputfile);
		
		//write Auto-to-Zone
		fw.write(Integer.toString(opl.getKost()) + "\n");
		fw.write("+Vehicle assignments\n");
		ArrayList<Auto> autos = opl.getAutos();
		for (int i = 0; i < autos.size(); i++) {
			String s = autos.get(i).getId() +";"+ autos.get(i).getZone().getId()  + "\n";
			fw.write(s);
		}
		//write gelukte requests
		fw.write("+Assigned requests\n");
		ArrayList<Request> req = opl.getReq();
		for (int i = 0; i < req.size(); i++) {
			if(req.get(i).getToegAuto()!=null)
			{
				String s = req.get(i).getID() +";"+ req.get(i).getToegAuto().getId()  + "\n";
				fw.write(s);
			}
		}
		//write mislukte requests
		fw.write("+Unassigned requests\n");
		for (int i = 0; i < req.size(); i++) {
			if(req.get(i).getToegAuto()==null)
			{
				String s = req.get(i).getID()  + "\n";
				fw.write(s);
			}
		}
		fw.close();
		System.out.println("Weggeschrijven - OK");
	}
}
