package graphStatistics;

import java.util.ArrayList;
import java.util.Collection;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import graphImporter.ReadGraph_w_edgeList;

public class getInfo {
	
	/**
	 * 
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao's Research Group, Computer Science, UBC Okanagan
	 * 
	 **/

	public static void main (String[] agrs) {

		//Read the graph
		String inputFile = "data/AverageGrowth.v.1.3.csv";
		DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g = ReadGraph_w_edgeList.fromFile(inputFile);

		countPosAndNegEdgeNums(g);
		countPosAndNegTriangleNums(g);
		//System.out.println(getNeighborsOf(4, g));
	}

	/**
	 * Get all neighbors of a vertex v in the networks g
	 **/

	@SuppressWarnings("unused")
	private static Collection<Integer> getNeighborsOf(int v, DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g){
		Collection<Integer> nbrSet = new ArrayList<Integer>();
		if (g.containsVertex(v)) {
			for (DefaultWeightedEdge e : g.incomingEdgesOf(v)){
				int i = g.getEdgeSource(e);
				if (!nbrSet.contains(i)){
					nbrSet.add(i);
				}
				else continue;
			}
			for (DefaultWeightedEdge e: g.outgoingEdgesOf(v)) {
				int j = g.getEdgeTarget(e);
				if (!nbrSet.contains(j)){
					nbrSet.add(j);
				}
				else continue;
			}

		}
		else System.out.println("This vertex does not contains in G");
		return nbrSet ;

	}
	
	/**
	 * Counting positive and negative triangles in the networks g
	 **/

	private static void countPosAndNegTriangleNums(DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g) {
		
		int n = g.vertexSet().size();
		int posTri = 0;
		int negTri = 0;

		for (int i=1; i<=n; i++){
			for (int j=i+1; j<=n; j++){
				for(int k=j+1; k<=n; k++){
					//System.out.println(i + ", " + j + ", " + k);
					double e1 =0; double e2 = 0; double e3 = 0;
					if (g.containsEdge(i, j)){
						e1 = g.getEdgeWeight(g.getEdge(i, j));
					}
					else if (g.containsEdge(j, i)){
						e1 = g.getEdgeWeight(g.getEdge(j, i));
					}
					else e1 = 0;
					
					if (g.containsEdge(j, k)){
						e2 = g.getEdgeWeight(g.getEdge(j, k));
					}
					else if (g.containsEdge(k, j)){
						e2 = g.getEdgeWeight(g.getEdge(k, j));
					}
					else e2 = 0;
					
					if (g.containsEdge(i, k)) {
						e3 = g.getEdgeWeight(g.getEdge(i, k));
					}
					else if (g.containsEdge(k, i)){
						e3 = g.getEdgeWeight(g.getEdge(k, i));
					}
					else e3 = 0;
					
					double t = e1 * e2 * e3;
					if (t>0) posTri+=1;
					else if (t<0) negTri+=1;
					else continue;
				}
			}
		}
		
		System.out.println("Positive Triangles: " + posTri);
		System.out.println("Negative Triangles: " + negTri);
	}

	/**
	 * Counting positive and negative edges in the networks g
	 **/
	
	private static void countPosAndNegEdgeNums(DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g) {

		int n = g.vertexSet().size();
		int m = g.edgeSet().size();
		int pos = 0;
		int neg = 0;

		for (DefaultWeightedEdge e : g.edgeSet()) {
			if (g.getEdgeWeight(e) > 0) {
				pos+=1;
			}
			else if (g.getEdgeWeight(e) < 0) {
				neg+=1;
			}
			else continue;
		}

		System.out.println("n : " + n);
		System.out.println("m : " + m);
		System.out.println("pos : " + pos);
		System.out.println("neg : " + neg);

	}

}
