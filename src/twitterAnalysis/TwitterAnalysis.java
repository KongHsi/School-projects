package twitterAnalysis;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyListGraph;
import ca.ubc.ece.cpen221.mp3.graph.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class TwitterAnalysis{

	/**
	 * Requires: args[0] is a formatted document describing a graph where each line is
	 * a -> b, such that an edge exists from vertex a to b, i.e. a follows b. args[1] is a formatted
	 * document that requests queries on the minimum number of retweets for user1's retweet
	 * to appear in user2's twitter feed or a list of common users followed between two users.
	 * args[1] must be formatted as follows: each line begins with "numRetweets" or
	 * "commonInfluencers" then followed by a space, user1, a space, user2, and "?".
	 * 
	 * Effects: prints out a result of each query in the format as follows:
	 * first line: "query: " followed by the query line without question mark.
	 * second line: "<result>";
	 * middle lines: an int in one line, multiple users (one per line), or no line at all
	 * second-to-last line: "<\result>"
	 * last line: "";
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[ ] args){
		Graph g = new AdjacencyListGraph();
		Scanner graphScanner;
		BufferedWriter outputWriter;
		
		final String GRAPH_FILE = "MakeGraphTextFile1.txt";
		//final String GRAPH_FILE = "twitter.txt";
		final String GRAPH_FILE_FOLDER = "datasets";
		final String QUERY_FILE_FOLDER = "datasets";
		final String QUERY_OUTPUT_FOLDER = "datasets";
		final String QUERY_FILE = args[0];
		final String OUTPUT_FILE = args[1];
		final String QUERY_TYPE_1 = "commonInfluencers";
		final String QUERY_TYPE_2 = "numRetweets";
		final Charset ENCODING = StandardCharsets.UTF_8;
		
		Path graphFile = Paths.get(GRAPH_FILE_FOLDER, GRAPH_FILE);
		try{
			graphScanner = new Scanner(graphFile);
			g = makeGraph(graphScanner);
			
		}catch(Exception IOException){
			System.out.println("Error reading graph file");
		}
		
		try{
			String lineString;
			String[] stringArray;
			Vertex u1;
			Vertex u2;
			
			Path path = FileSystems.getDefault().getPath(QUERY_FILE_FOLDER, QUERY_FILE);
		    BufferedReader queryReader = Files.newBufferedReader(path, ENCODING);
		    
		    Path queryOutputFile = Paths.get(QUERY_OUTPUT_FOLDER, OUTPUT_FILE);
		    outputWriter = Files.newBufferedWriter(queryOutputFile, StandardOpenOption.WRITE);
		    
			while((lineString = queryReader.readLine()) != null){
				
				stringArray = lineString.split("\\s");
				
				if(stringArray.length == 4){
					
					u1 = new Vertex(stringArray[1]);
					u2 = new Vertex(stringArray[2]);
					if(stringArray[3].contentEquals("?")){
									
						if(stringArray[0].trim().contains(QUERY_TYPE_1)){
								
							outputWriter.write("query: " + QUERY_TYPE_1 + " " + u1.getLabel() + " " + u2.getLabel());
							outputWriter.newLine();
							outputWriter.write("<result>");
							outputWriter.newLine();
										
							List<String> comInFlu = commonInfluencers(g, u1, u2);
							for(String influencer: comInFlu){
								outputWriter.write(influencer);
								outputWriter.newLine();
							}
								
						}else if(stringArray[0].trim().contains(QUERY_TYPE_2)){
								
							outputWriter.write("query: " + QUERY_TYPE_2 + " " + u1.getLabel() + " " + u2.getLabel());
							outputWriter.newLine();
							outputWriter.write("<result>");
							outputWriter.newLine();
								
							int shortestDist = numRetweets(g, u1, u2);
										
							outputWriter.write("" + shortestDist);
							outputWriter.newLine();
						}
						outputWriter.write("</result>");
						outputWriter.newLine();
						outputWriter.newLine();
					}
				}
			}
			outputWriter.close();
		}catch(Exception IOException){
			System.out.println("Problem with others.");
		}
	}
	
	/**
	 * Requires: user1 and user2 must be in g
	 * Effects: returns minimum number of retweets for user1's tweet to appear in
	 * user2's twitter feed. If user1 is user2, returns 0. If user1's tweet will
	 * never appear in user2's feed, returns -1.
	 * @param g
	 * @param user1
	 * @param user2
	 * @return minimum number of retweets for user1's tweet to appear in user2's
	 * twitter feed.
	 */
	public static int numRetweets(Graph g, Vertex v1, Vertex v2){
		int shortestDist = Algorithms.shortestDistance(g, v1, v2);;
		
		return shortestDist;
	}

	/**
	 * Requires: scanner is scanning a correctly formatted document that
	 * describes a graph, on each line, a -> b such that there is an
	 * edge from vertex a to b.
	 * Effects: returns a graph as described by scanner's document
	 * 
	 * @param scanner
	 * @return graph as described by document from scanner
	 */
	public static Graph makeGraph(Scanner scanner){
		Graph g = new AdjacencyListGraph();
		
		String user1;
		String user2;
		
		while(scanner.hasNext()){
			user1 = scanner.next();
			scanner.skip(" -> ");
			user2 = scanner.next();
			
			Vertex v1 = new Vertex(user1);
			Vertex v2 = new Vertex(user2);
			
			try{
				List<Vertex> allVertices = g.getVertices();
				
				if(!allVertices.contains(v1)){
					g.addVertex(v1);
				}
				
				if(!allVertices.contains(v2)){
					g.addVertex(v2);
				}
				
				if(!g.edgeExists(v1, v2)){
					g.addEdge(v1, v2);
				}
			}catch(Exception IllegalArgumentException){}
		}
		return g;
	}
	
	
	/**
	 * Requires: a and b is in g
	 * Effects: returns a list of users followed by both a and b.
	 * @param g
	 * @param a
	 * @param b
	 * @return list of users follewed by both a and b.
	 */
    public static List<String>commonInfluencers(Graph g,Vertex a, Vertex b){
        List<Vertex> commonVertex = new ArrayList<Vertex>();
        List<String> commonString = new ArrayList<String>();
        commonVertex =Algorithms.Commondownstreamvertices(g, a, b);
        for(Vertex x: commonVertex)
            commonString.add(x.getLabel());
        
        return commonString;
    }

}