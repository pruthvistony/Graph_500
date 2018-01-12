package graph500;

// Java program to print BFS traversal from a given source vertex.
// BFS(int s) traverses vertices reachable from s.
import java.util.*;

 
// This class represents a directed graph using adjacency list
// representation
class Graph
{
    private int V;   // No. of vertices
    private LinkedList<Integer> adj[]; //Adjacency Lists
 
    // Constructor
    @SuppressWarnings({ "unchecked", "rawtypes" })
	Graph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }
 
    // Function to add an edge into the graph
    void addEdge(int v,int w)
    {
        adj[v].add(w);
    }
    
    LinkedList<Integer> getVertex(int v) {
    	return adj[v];
    }
    
    public boolean validate(int[] parent, int validation_vertex) {
    	boolean validated = true;
    	long i=1;
    	while(parent[validation_vertex] != validation_vertex) {
    		validation_vertex = parent[validation_vertex];
    		if(++i > V || validation_vertex == -1) {
    			validated = false;
    			break;
    		}
    	}
    	return validated;
    }
 
    // prints BFS traversal from a given source s
    int[] BFS(int s)
    {
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean visited[] = new boolean[V];
        
        int parent[] = new int[V];
        Arrays.fill(parent, -1);
 
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();
 
        // Mark the current node as visited and enqueue it
        visited[s]=true;
        queue.add(s);
        parent[s] = s;
        
        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            //System.out.print(s+" ");
 
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                    parent[n] = s;
                }
            }
        }
        return parent;
    }
 
    public static void main(String args[])
    {
    	
        Graph g = new Graph(4);
 
        g.addEdge(0, 1);
        //g.addEdge(0, 2);
        //g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);
 
        g.BFS(2);
    }
}
