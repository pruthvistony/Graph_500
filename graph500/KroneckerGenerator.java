package graph500;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class KroneckerGenerator {

    static final double A = 0.57;
    static final double B = 0.19;
    static final double C = 0.19;
		
	public static List<List<Integer>> generateKroneckerEdges(int scale, int edgeFactor) {

		/* Set the number of vertices*/
		int n = (int) Math.pow(2.0,(double)scale);
		
		/* Set the number of edges*/
		int m = edgeFactor*n;
		
		/* Create index arrays.*/
		List<List<Integer>> ijw = new ArrayList<List<Integer>>(2);		
				
		double ab = A+B;
		double c_norm = C/(1-ab);
		double a_norm = A/ab;
		
		List<Integer> list_ii = new ArrayList<Integer>();
		List<Integer> list_jj = new ArrayList<Integer>();
		for(int i = 0;i<m;i++) {
			list_ii.add(1);
			list_jj.add(1);
		}
		for(int i=1; i<=scale; i++) {
			for(int j=0;j<m;j++) {
				int ii = Math.random() > ab ? 1:0;
				double prob_value = (ii == 1) ? c_norm : a_norm;
				int jj = Math.random() > prob_value ? 1:0;
				list_ii.set(j, list_ii.get(j) + (int)Math.pow(2.0,(double)(i-1))*ii);
				list_jj.set(j, list_jj.get(j) + (int)Math.pow(2.0,(double)(i-1))*jj);
			}
		}
		
		List<Integer> list = new ArrayList<Integer>();
		for(int i =1 ; i<=n ;i++ ) {
			list.add(i);
		}		
		Collections.shuffle(list);
		
		for(int i=0;i<m;i++) {
			list_ii.set(i,list.get(list_ii.get(i) -1));
			list_jj.set(i,list.get(list_jj.get(i) -1));
		}
		
		ijw.add(list_ii);
		ijw.add(list_jj);

		/*for(int i=0;i<m;i++) {
			System.out.println(list_ii.get(i) + "->" + list_jj.get(i));
		}*/
		return ijw;
	}
	
	public static Graph generateKroneckerDirectedGraph(List<List<Integer>> ijw, int scale){
		int noVertices = (int)Math.pow(2.0,(double)scale);
		Graph undirectedGraph = new Graph(noVertices);
		List<Integer> list_ii = ijw.get(0);
		List<Integer> list_jj = ijw.get(1);
		if(list_ii.isEmpty() || list_jj.isEmpty() && (list_ii.size() != list_jj.size())) {
			return undirectedGraph;
		}
		HashSet<String> vertexMappings = new HashSet<String>();
		for(int i = 0; i< list_ii.size();i++ ) {
			int startVertex = list_ii.get(i)-1;
			int endVertex = list_jj.get(i)-1;
			if( startVertex != endVertex ) {
				if(startVertex < endVertex) {
					vertexMappings.add(endVertex + "-" + startVertex);
				}else {
					vertexMappings.add(startVertex + "-" + endVertex);
				}
			}
		}		
		Iterator<String> vertexStrings = vertexMappings.iterator();
		while(vertexStrings.hasNext()) {
			String vertexString = vertexStrings.next();
			int startVertex = Integer.parseInt(vertexString.split("-")[0]);
			int endVertex = Integer.parseInt(vertexString.split("-")[1]);
			undirectedGraph.addEdge(startVertex, endVertex);
			undirectedGraph.addEdge(endVertex, startVertex);
		}
		//System.out.println(vertexMappings);
		return undirectedGraph;
	}
	
	
}
