package ca.ubc.ece.cpen221.mp3.graph;

import java.util.*;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class Algorithms {

	/**
	 * *********************** Algorithms ****************************
	 * 
	 * Please see the README for the machine problem for a more detailed
	 * specification of the behavior of each method that one should implement.
	 */

	/**
	 * Returns the shortest distance (i.e. smallest number of edges)
	 * from vertex a to b. If vertex a = b, returns 0. If no path from
	 * a to b exists, returns -1.
	 * 
	 * @param graph
	 * @param a
	 * @param b
	 * @return shortestDistance least number of edges to go from a to b.
	 *
	 */
	public static int shortestDistance(Graph graph, Vertex a, Vertex b) {
		if(a.equals(b)){
			return 0;
		}
		
		Stack<Vertex> vertexStack = new Stack<Vertex>();
		int shortestDist = graph.getVertices().size(); //longest possible route plus 1 (impossible)
		Map<Vertex, Integer> vertexDistCount = new HashMap<Vertex, Integer>();
		int distCount = 0;
		Vertex vertex;
		List<Vertex> downstrVertices;
		boolean isPathFound = false;
		
		//push first vertex on stack, distCount starts at 0,
		//save vertex-distCount pair in map
		vertexStack.push(a);
		distCount = 0;
		vertexDistCount.put(a, 0);
		
		while(!vertexStack.isEmpty()){
			
			vertex = vertexStack.peek();
			downstrVertices = graph.getDownstreamNeighbors(vertex);
			
			int vIndex = 0;
			
			//find index of first downstream vertex not visited yet
			while(downstrVertices.size() != 0
					&& (vIndex < downstrVertices.size())
					&& vertexDistCount.containsKey(downstrVertices.get(vIndex))){
				
				vIndex++;
			}
			
			//if vIndex was not incremented out of bounds of downstrVertices,
			//i.e. an unvisited downstream vertex was found,
			//add it to stack, increment its distCount from parent vertex's,
			//and save vertex-distCount pair in map
			Vertex downV;
			if(vIndex < downstrVertices.size()){
				downV = downstrVertices.get(vIndex);
			
					vertexStack.push(downV);
					distCount = vertexDistCount.get(vertex);
					distCount++;
					vertexDistCount.put(downV, distCount);
					
					//if downV happens to be b, check if running distance is
					//shorter than shortestDist and update if so
					if((downV.equals(b)) && (distCount < shortestDist)){
						isPathFound = true;
						shortestDist = distCount;
					}
			}else{
				vertexStack.pop();
			}
		}
		
		if(isPathFound == true){
			return shortestDist;
		}else{
			//throw new IllegalArgumentException();
			return -1;
		}
	}
	
	/**
	 * Requires: 
	 * Effects: Returns a set of lists of vertices such that each vertex has a list
	 * of the breadth first search graph traversal starting from itself
	 * 
	 * @param g
	 * @return set of lists of vertices such that each vertex has a list
	 * of the breadth first search graph traversal starting from itself
	 */
	public static Set<List<Vertex>> breadthFirstSearch(Graph g){
		List<Vertex> vertexList = g.getVertices();
		
		Set<List<Vertex>> traverseSet = new LinkedHashSet<List<Vertex>>();
		
		Queue<Vertex> vertexQueue = new LinkedList<Vertex>();
		List<Vertex> visitedVs = new LinkedList<Vertex>();
		
		List<Vertex> downstreamVertices = new LinkedList<Vertex>();
		
		for(int vIndex = 0; vIndex < vertexList.size(); vIndex++){
			
			Vertex vertex = vertexList.get(vIndex);
			
			vertexQueue.add(vertex);
			visitedVs.add(vertex);

			while(!vertexQueue.isEmpty()){

				downstreamVertices = g.getDownstreamNeighbors(vertexQueue.peek());
				for(int downVIndex = 0; downVIndex < downstreamVertices.size(); downVIndex++){
					if(!visitedVs.contains(downstreamVertices.get(downVIndex))){
						vertexQueue.add(downstreamVertices.get(downVIndex));
						visitedVs.add(downstreamVertices.get(downVIndex));
					}
				}
				vertexQueue.remove();
			}
			
			traverseSet.add(visitedVs);
			visitedVs = new LinkedList<Vertex>();
		}
		
		return traverseSet;
	}
	
	/**
	 * Requires: vertices a and b are in graph g
	 * Effects: returns a list of common upstream vertices u such that u -> a
	 * and u-> b
	 * 
	 * @param g
	 * @param a
	 * @param b
	 * @return List of common upstream vertices u such that edge u -> a and u -> b 
	 * exists
	 * 
	 */
	public static List<Vertex> commonUpstreamVertices(Graph g, Vertex a, Vertex b){
		List<Vertex> upstrVertsA = g.getUpstreamNeighbors(a);
		
		List<Vertex> upstrVertsB = g.getUpstreamNeighbors(b);

		List<Vertex> comUpstrVerts = new LinkedList<Vertex>();
		
		for(int listAIndex = 0; listAIndex < upstrVertsA.size(); listAIndex++){
			
			for(int listBIndex = 0; listBIndex < upstrVertsB.size(); listBIndex++){
				
				if(upstrVertsA.get(listAIndex) == upstrVertsB.get(listBIndex)){
					comUpstrVerts.add(upstrVertsA.get(listAIndex));
				}
			}
		}
		
		return comUpstrVerts;
	}

	/**
	 * Requires: 
	 * Effects: Returns a set of lists of vertices such that each vertex has a list
	 * of the depth first search graph traversal starting from itself
	 * 
	 * @param g
	 * @return set of lists of vertices such that each vertex has a list
	 * of the depth first search graph traversal starting from itself
	 */
    public static Set<List<Vertex>> Depthfirstsearch(Graph graph){
        Set<List<Vertex>> DFS = new HashSet<List<Vertex>>();
        List<Vertex> visited;
        for(Vertex currentVertex : graph.getVertices()){
           
           visited = new ArrayList<Vertex>();
     
           visited.add(currentVertex);
          
           DFS.add(DFS(currentVertex,graph,visited));
        }
        return DFS;
    }
    
    /**
     * Requires: v is in graph and visited is a list of vertices in graph visited
     * Effects: Returns a list of vertices of the depth first search graph traversal
     * starting from the specified vertex v.
     * @param v
     * @param graph
     * @param visited
     * @return list of vertices of the depth first search graph traversal
     * starting from the specified vertex v.
     */
    public static List<Vertex> DFS(Vertex v, Graph graph, List<Vertex> visited ){
       
        for(Vertex c:graph.getDownstreamNeighbors(v)){
            
            if(!visited.contains(c)){
            
            visited.add(c);
            visited = DFS(c,graph,visited);
            }
              
        }
        return visited;
    }
    
    /**
     * Requires: a and b is in G
     * Effects: Returns a list of common downstream vertices of a and b
     * @param G
     * @param a
     * @param b
     * @return list of common downstream vertices of a and b
     */
    public static List<Vertex> Commondownstreamvertices(Graph G,Vertex a,Vertex b){
    	
        List<Vertex> downstreamvertices = new ArrayList<Vertex>();
        
        for(Vertex x: G.getDownstreamNeighbors(a)){
        	
            if(G.getDownstreamNeighbors(b).contains(x))
                    downstreamvertices.add(x);
        }
       return downstreamvertices;    
    }
}
