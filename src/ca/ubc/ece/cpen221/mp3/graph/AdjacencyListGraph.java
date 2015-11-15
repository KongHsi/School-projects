package ca.ubc.ece.cpen221.mp3.graph;

import java.util.*;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyListGraph implements Graph {

    private Map<Vertex,ArrayList<Vertex>> vertices= new LinkedHashMap<Vertex,ArrayList<Vertex>>();

    /**
     * Adds a vertex to the graph.
     *
     * Precondition: v is not already a vertex in the graph
     */
    public void addVertex(Vertex v){
        vertices.put(v, new ArrayList<Vertex>());
              
    };

    /**
     * Adds an edge from v1 to v2.
     *
     * Precondition: v1 and v2 are vertices in the graph
     */
    public void addEdge(Vertex v1, Vertex v2){
        vertices.get(v1).add(v2);
        
    }
    
    
    
    /**
     * Check if there is an edge from v1 to v2.
     *
     * Precondition: v1 and v2 are vertices in the graph Postcondition: return
     * true iff an edge from v1 connects to v2
     */
    public boolean edgeExists(Vertex v1, Vertex v2){
        return vertices.get(v1).contains(v2);
    }

    /**
     * Get an array containing all downstream vertices adjacent to v.
     *
     * Precondition: v is a vertex in the graph
     * 
     * Postcondition: returns a list containing each vertex w such that there is
     * an edge from v to w. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no downstream neighbors.
     */
    public List<Vertex> getDownstreamNeighbors(Vertex v){
        ArrayList<Vertex> downStream = new ArrayList<Vertex>();
        for(int count=0;count<vertices.get(v).size();count++)
            downStream.add(vertices.get(v).get(count));
        return downStream;
    }
    /**
     * Get an array containing all upstream vertices adjacent to v.
     *
     * Precondition: v is a vertex in the graph
     * 
     * Postcondition: returns a list containing each vertex u such that there is
     * an edge from u to v. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no upstream neighbors.
     */
    public List<Vertex> getUpstreamNeighbors(Vertex v){
        ArrayList<Vertex> upStream = new ArrayList<Vertex>();
      for(Vertex x: vertices.keySet()){
          if(vertices.get(x).contains(v))
              upStream.add(x);
    }
      return upStream;
    }
    /**
     * Get all vertices in the graph.
     *
     * Postcondition: returns a list containing all vertices in the graph. This
     * method should return a list of size 0 iff the graph has no vertices.
     */
    public List<Vertex> getVertices(){
        ArrayList<Vertex> allVertices = new ArrayList<Vertex>();
        for(Vertex x: vertices.keySet())
            allVertices.add(x);
        
        return allVertices;
        }
}
