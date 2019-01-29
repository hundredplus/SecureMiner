package ppdm.test;

import java.io.FileWriter;
import java.util.Random;

public class DataFileGen {
	
	public static void write2File(String fileName, int noTran){
		try{
			FileWriter fWriter = new FileWriter(fileName,false);			
			fWriter.write("@DataSize\n" + noTran + "\n@Data\n");
			Random rand = new Random();
			
			for (int i=0; i < noTran; i++){		
                            fWriter.write(Integer.toString(rand.nextInt(10)) + "\n");					
			}
			
			
			fWriter.close();
			
		}catch (Exception e) {
			System.out.println("Write to file error: " + e.toString());
			System.exit(1);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		write2File("D:\\Data\\data4.ddff", 1000000);
	}

}
