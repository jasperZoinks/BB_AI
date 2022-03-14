package pack;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Uitschrijver {

	private Oplossing opl;

	public Uitschrijver(Oplossing opl) {
		this.opl = opl;
	}

	public Oplossing getOpl() {
		return opl;
	}

	public void setOpl(Oplossing opl) {
		this.opl = opl;
	}
	
	public void Schrijven() throws IOException {
		FileWriter fw = new FileWriter("out.txt");
		
		//write Auto-to-Zone
		fw.write(Integer.toString(opl.getReq().size()));
		fw.write("+Vehicle assignments");
		ArrayList<Auto> autos = this.opl.getAutos();
		for (int i = 0; i < autos.size(); i++) {
			String s = autos.get(i).getId() +";"+ autos.get(i).getZone().getId();
			fw.write(s);
		}
		//write gelukte requests
		fw.write("+Assigned requests");
		ArrayList<Request> req = this.opl.getReq();
		for (int i = 0; i < req.size(); i++) {
			if(req.get(i).getToegAuto()!=null)
			{
				String s = req.get(i).getID() +";"+ req.get(i).getToegAuto().getId();
				fw.write(s);
			}
			
		}
		//write mislukte requests
		fw.write("+Unassigned requests");
		for (int i = 0; i < req.size(); i++) {
			if(req.get(i).getToegAuto()==null)
			{
				String s = req.get(i).getID();
				fw.write(s);
			}
		}
	 
		fw.close();
	}
}
