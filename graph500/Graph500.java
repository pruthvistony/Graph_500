package graph500;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph500 {

	static final int NUMBER_OF_TIMES_BFS = 64;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int scale = 4;
		if(args.length > 0) { 
			try {
				scale = Integer.parseInt(args[0]);
			}catch(NumberFormatException ex) {
				System.out.println("The number passed is not a number running the banchmark with default scale = " + scale);
			}
		}
		List<List<Integer>> ijw = KroneckerGenerator.generateKroneckerEdges(scale, 16);
		System.out.println("Edges generated");
		//System.out.println("---------------------------------");
		long startTime = System.nanoTime();
		Graph g = KroneckerGenerator.generateKroneckerDirectedGraph(ijw, scale);
		long endTime = System.nanoTime();
		System.out.println("Graph generated");
		System.out.println("Graph generation took :: " + (endTime-startTime)/1000000 + " ms");
		ArrayList<Integer> degreeMoreThan1Vertices = new ArrayList<Integer>();
		int noOfVertices = (int)Math.pow(2.0, (double)scale);
		for(int i=0;i<noOfVertices;i++) {
			if(g.getVertex(i).size()>= 1) {
				degreeMoreThan1Vertices.add(i);
			}
		}
		//System.out.println(degreeMoreThan1Vertices.toString());
		Collections.shuffle(degreeMoreThan1Vertices);
		int no_of_bfs_run = degreeMoreThan1Vertices.size() < 64? degreeMoreThan1Vertices.size() : NUMBER_OF_TIMES_BFS;
		for(int i=0;i<no_of_bfs_run;i++) {
			startTime = System.nanoTime();
			int[] parent = g.BFS(degreeMoreThan1Vertices.get(i));
			endTime = System.nanoTime();
			System.out.println("BFS no " + (i+1) + " successful");
			System.out.println("BFS no " + (i+1) + " took :: " + (endTime-startTime)/1000000 + " ms");
			//System.out.println("----");
			ArrayList<Integer> validationVertices = degreeMoreThan1Vertices;
			Collections.shuffle(validationVertices);
			int j=0;
			while(parent[validationVertices.get(j)] == -1 || parent[validationVertices.get(j)] == validationVertices.get(j)) { j++;}
			if(g.validate(parent, validationVertices.get(j))) {
				System.out.println("BFS no " + (i+1) + " Validated successful");
			}else {
				System.out.println("BFS no " + (i+1) + " Validation not successful");
			}
		}
		
	}

}
