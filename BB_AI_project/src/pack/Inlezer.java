package pack;
import java.io.*;
import java.util.Scanner;

public class Inlezer {
	private String path="../100_5_14_25.csv";
	
	public void readIn() {
	    try {
	        File myObj = new File(this.getPath());
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
	          String data = myReader.nextLine();
	          System.out.println(data);
	        }
	        myReader.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
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
	}
	
}
