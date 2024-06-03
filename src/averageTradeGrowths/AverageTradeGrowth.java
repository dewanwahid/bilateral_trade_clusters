package averageTradeGrowths;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class AverageTradeGrowth {
	
	/**
	 * Average Trade Growth
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao's Research Group, Computer Science, UBC Okanagan
	 * 
	 * Using library: jGrapht; available at:  www.jGrapht.org
	 * 
	 */

	public static HashMap<String, Integer> ID = new HashMap<String, Integer>(); 
	public static HashMap<Integer, String> ID_print = new HashMap<Integer, String>(); 


	public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> getAverageTradeGrowth(
			String inputFile, 
			String outputFile
			) {
		
		SimpleWeightedGraph<Integer, DefaultWeightedEdge> g = 
				new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		FileInputStream f = null;
		int line = 0; 

		//assign a numeric id to each country
		//HashMap<String, Integer> ID = new HashMap<String, Integer>(); 
		//HashMap<Integer, String> ID_print = new HashMap<Integer, String>(); 
		int cId = 0;

		//data reading
		try {
			f = new FileInputStream (inputFile);
			Scanner s = new Scanner (f);
			PrintWriter writer = new PrintWriter (outputFile);

			//output file heading
			writer.println("#Country,Country,AvgTradeGrowth"); 

			//reading data
			while (s.hasNext()) {

				//Reading lines
				String dataline = s.nextLine();
				line = line + 1;

				//if line is empty, then continue
				if (dataline.equals("")) continue;

				//if the first character is "#" write the info line
				if (Character.toString(dataline.charAt(0)).equals("#")) {
					writer.println(dataline);
				}

				//split the data line at ',' 
				String[] lineParts = dataline.split(",");

				//if lineParts lenght is not 3, then there is error in data line
				if (lineParts.length != 3) {
					System.out.println("Error in line: " + line);
					break;
				}
				//if lineParts length is 3, then read
				if (lineParts.length == 3){
					//System.out.println("line: " + line);
					if (lineParts[2].trim().equals("#DIV/0!") || Double.parseDouble(lineParts[2].trim()) == 0) {
						continue;
					}
					else {
						String src = lineParts[0].trim();
						String trg = lineParts[1].trim();

						double sign = Double.parseDouble(lineParts[2].trim());
						//System.out.println(src + ", " + trg + "; " + sign);

						//if src and trg same skip the line
						//otherwise go for it
						if (src.equals(trg)) continue;
						else {					
							int i; int j; //integer id for countries

							//if the src country already has an id
							//otherwise get a new id of src
							if (ID.containsKey(src)) {
								i = ID.get(src);
							}
							else {
								cId = cId + 1; ID.put(src, cId); i = cId;
								ID_print.put(cId, src);
							}

							//if the trg country already has an id
							//otherwise get a new id of src
							if (ID.containsKey(trg)) {
								j = ID.get(trg);
							}
							else {
								cId = cId + 1; ID.put(trg, cId); j = cId;
								ID_print.put(cId, trg);
							}

							//if graph contains edge (src, trg) continue
							if (g.containsEdge(i, j) || g.containsEdge(j, i)) continue;
							else {
								//add vertices and edge (src,trg) to the graph
								g.addVertex(i); g.addVertex(j); g.setEdgeWeight(g.addEdge(i, j), sign);

								//write this edge to output file
								writer.println(i+ "," + j + "," + sign);
								//System.out.println(i+ "," + j + "," + sign);
							}
						}
					}
				}
			}

			//printing country id in csv file
			writer.println("#Country ID");
			for (int i=1; i<=cId; i++){
				writer.println("ID," + i + "," + ID_print.get(i));
				System.out.println("ID," + i + "," + ID_print.get(i));
			}
			s.close();
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return g;	
	}

	public static void main (String [] args) {
		String inputFile = "data/AverageGrowth.v.1.1.csv";
		String outputFile = "data/AverageGrowth.v.1.2.clean.csv";
		SimpleWeightedGraph<Integer, DefaultWeightedEdge> g = getAverageTradeGrowth(inputFile, outputFile);
		System.out.println(g);
		//System.out.println(ID_print.get(1));
	}

}
