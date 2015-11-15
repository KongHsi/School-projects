package ca.ubc.ece.cpen221.mp3.graph;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

import java.util.*;

public class AdjacencyMatrixGraph implements Graph {

    
    
    private List<Vertex> vertexList = new LinkedList<Vertex>();
    
    private String vertexPair;
    private Map<String, Integer> vertexMatrix = new TreeMap<String, Integer>();
    
    /**
     * Adds a vertex to the graph.
     */
    public void addVertex(Vertex v){
        if(vertexList.contains(v)){
            throw new IllegalArgumentException();
        }
        
        for(int vIndex = 0; vIndex < vertexList.size(); vIndex++){
            vertexPair = (v.getLabel() + " " + vertexList.get(vIndex).getLabel());
            vertexMatrix.put(vertexPair, new Integer(0));
            
            vertexPair = (vertexList.get(vIndex).getLabel() + " " + v.getLabel());
            vertexMatrix.put(vertexPair, new Integer(0));
        }
        
        vertexPair = (v.getLabel() + " " + v.getLabel());
        vertexMatrix.put(vertexPair, new Integer(0));
        
        vertexList.add(v);
        
    }

    /**
     * Adds an edge from v1 to v2.
     *
     */
    public void addEdge(Vertex v1, Vertex v2){
        if(!vertexList.contains(v1) && !vertexList.contains(v2)){
            throw new IllegalArgumentException();
        }
        
        vertexPair = v1.getLabel() + " " + v2.getLabel();
        
        vertexMatrix.put(vertexPair, 1);
    }

    /**
     * Check if there is an edge from v1 to v2.
     * Postcondition: return
     * true iff an edge from v1 connects to v2
     */
    public boolean edgeExists(Vertex v1, Vertex v2){
        
        return (vertexMatrix.get(v1.getLabel() + " " + v2.getLabel())) == 1;
    }

    /**
     * Get an array containing all downstream vertices adjacent to v.   
     * 
     * Postcondition: returns a list containing each vertex w such that there is
     * an edge from v to w. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no downstream neighbors.
     */
    public List<Vertex> getDownstreamNeighbors(Vertex v){
        
        if(!vertexList.contains(v)){
            throw new IllegalArgumentException();
        }
        
        List<Vertex> downstreamVertices = new LinkedList<Vertex>();
        
        for(int vIndex = 0; vIndex < vertexList.size(); vIndex++){
            
            if(edgeExists(v, vertexList.get(vIndex))){
                downstreamVertices.add(vertexList.get(vIndex));
            }
        }
        
        return downstreamVertices;
    }

    /**
     * Get an array containing all upstream vertices adjacent to v.
     *
     * 
     * Postcondition: returns a list containing each vertex u such that there is
     * an edge from u to v. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no upstream neighbors.
     */
    public List<Vertex> getUpstreamNeighbors(Vertex v){
        
        if(!vertexList.contains(v)){
            throw new IllegalArgumentException();
        }
        
        List<Vertex> upstreamVertices = new LinkedList<Vertex>();
        
        for(int vIndex = 0; vIndex < vertexList.size(); vIndex++){
            
            if(edgeExists(vertexList.get(vIndex), v)){
                upstreamVertices.add(vertexList.get(vIndex));
            }
        }
        
        return upstreamVertices;
    }

    /**
     * Get all vertices in the graph.
     *
     * Postcondition: returns a list containing all vertices in the graph. This
     * method should return a list of size 0 iff the graph has no vertices.
     */
    public List<Vertex> getVertices(){
        List<Vertex> vertexListCopy = new LinkedList<Vertex>();
        
        for(int vIndex = 0; vIndex < vertexList.size(); vIndex++){
            vertexListCopy.add(vertexList.get(vIndex));
        }
        
        return vertexListCopy;
    }
}