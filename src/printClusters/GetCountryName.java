package printClusters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class GetCountryName {
	
	public static void main (String[] args){
		
		String clusters = "data/clustersOutput.0.5.csv";
		String countryID = "data/countryID.txt";
		
		FileInputStream f = null;
		FileInputStream g = null;
		int line = 0;
		int line1 = 0;
		
		HashMap<String, String> cID = new HashMap<String, String>();
		
		try {
			g = new FileInputStream (countryID);
			Scanner sc = new Scanner (g);
			
			while (sc.hasNext()) {

				//Reading lines
				String dataline = sc.nextLine();
				line1 = line1 + 1;
				//System.out.println("line: " + line);

				//if line is empty, then continue
				if (dataline.equals("")) continue;

				//if the first character is "#" write the info line continue
				if (Character.toString(dataline.charAt(0)).equals("#")) {
					continue;
				}

				//split the data line at ',' 
				String[] lineParts1 = dataline.split(",");
				
				cID.put(lineParts1[1].trim(), lineParts1[2].trim());
			}
			
			sc.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			f = new FileInputStream (clusters);
			Scanner s = new Scanner (f);
			while (s.hasNext()) {

				//Reading lines
				String dataline = s.nextLine();
				line = line + 1;
				//System.out.println("line: " + line);

				//if line is empty, then continue
				if (dataline.equals("")) continue;

				//if the first character is "#" write the info line continue
				if (Character.toString(dataline.charAt(0)).equals("#")) {
					continue;
				}

				//split the data line at ',' 
				String[] lineParts = dataline.split(",");
				
				int l = lineParts.length;
				
				for (int i=0; i<l; i++){
					System.out.println(cID.get(lineParts[i].trim()) );
				}
				System.out.println("\n\n");

			}
			s.close();
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
