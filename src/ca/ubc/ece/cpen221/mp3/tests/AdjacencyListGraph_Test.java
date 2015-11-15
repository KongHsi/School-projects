package ca.ubc.ece.cpen221.mp3.tests;

import static org.junit.Assert.*;


import java.util.*;

import org.junit.Test;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyListGraph;
import ca.ubc.ece.cpen221.mp3.graph.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyListGraph_Test {

	Graph AdjListGraph;
	
	Vertex A = new Vertex("A");
	Vertex B = new Vertex("B");
	Vertex C = new Vertex("C");
	Vertex D = new Vertex("D");
	Vertex E = new Vertex("E");
	Vertex F = new Vertex("F");
	
	@Test
	public void cyclicTriangleTest() {

		AdjListGraph = new AdjacencyListGraph();
		//AdjListGraph = new AdjacencyMatrixGraph(); Using this line instead of previous
		// would allow for testing of the AdjacencyMatrixGraph() representation

		AdjListGraph.addVertex(A);
		AdjListGraph.addVertex(B);
		AdjListGraph.addVertex(C);
		
		AdjListGraph.addEdge(A, B);
		AdjListGraph.addEdge(B, C);
		AdjListGraph.addEdge(C, A);

		//testing edges
		assertTrue(AdjListGraph.edgeExists(A, B));
		assertTrue(AdjListGraph.edgeExists(B, C));
		assertTrue(AdjListGraph.edgeExists(C, A));
		
		assertFalse(AdjListGraph.edgeExists(B, A));
		assertFalse(AdjListGraph.edgeExists(C, B));
		assertFalse(AdjListGraph.edgeExists(A, C));
		
		//forming list of vertices to compare with graph's
		List<Vertex> answerList = new LinkedList<Vertex>();

		answerList.add(A);
		answerList.add(B);
		answerList.add(C);
		
		//testing if correct list of vertices is returned
		assertEquals(answerList, AdjListGraph.getVertices());
		
		//testing getDownstreamNeighbors method
		List<Vertex> downstrVList = new LinkedList<Vertex>();
		List<Vertex> downstrVListAns;
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(A);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(B);
		
		assertEquals(downstrVListAns, downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(B);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(C);
		
		assertEquals(downstrVListAns, downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(C);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(A);
		
		assertEquals(downstrVListAns, downstrVList);
		
		//testing getUpstreamNeighbors method
		List<Vertex> upstrVList = new LinkedList<Vertex>();
		List<Vertex> upstrVListAns;
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(A);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(C);
		
		assertEquals(upstrVListAns, upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(B);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(A);
		
		assertEquals(upstrVListAns, upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(C);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(B);

		assertEquals(upstrVListAns, upstrVList);

	}
	
	@Test
	public void noncyclicTriangleTest(){
		AdjListGraph = new AdjacencyListGraph();
		//AdjListGraph = new AdjacencyMatrixGraph(); Using this line instead of previous
		// would allow for testing of the AdjacencyMatrixGraph() representation
		
		AdjListGraph.addVertex(A);
		AdjListGraph.addVertex(B);
		AdjListGraph.addVertex(C);
		
		AdjListGraph.addEdge(A, B);
		AdjListGraph.addEdge(C, A);
		AdjListGraph.addEdge(C, B);
		
		assertTrue(AdjListGraph.edgeExists(A, B));
		assertTrue(AdjListGraph.edgeExists(C, A));
		assertTrue(AdjListGraph.edgeExists(C, B));
		
		assertFalse(AdjListGraph.edgeExists(B, A));
		assertFalse(AdjListGraph.edgeExists(A, C));
		assertFalse(AdjListGraph.edgeExists(B, C));
		
		List<Vertex> answerList = new LinkedList<Vertex>();
		
		answerList.add(A);
		answerList.add(B);
		answerList.add(C);

		assertEquals(answerList, AdjListGraph.getVertices());
		
		//testing getDownstreamNeighbors method
		List<Vertex> downstrVList = new LinkedList<Vertex>();
		List<Vertex> downstrVListAns;
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(A);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(B);
		
		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(B);
		downstrVListAns = new LinkedList<Vertex>();
		
		assertEquals(downstrVListAns, downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(C);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(A);
		downstrVListAns.add(B);
		
		assertEquals(downstrVListAns, downstrVList);

		//testing getUpstreamNeighbors method
		List<Vertex> upstrVList = new LinkedList<Vertex>();
		List<Vertex> upstrVListAns;
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(A);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(C);
		
		assertEquals(upstrVListAns, upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(B);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(A);
		upstrVListAns.add(C);
		
		assertEquals(upstrVListAns, upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(C);
		upstrVListAns = new LinkedList<Vertex>();
		
		assertEquals(upstrVListAns, upstrVList);
		
	}
	
	@Test
	public void oneVertexTest(){
		AdjListGraph = new AdjacencyListGraph();
		//AdjListGraph = new AdjacencyMatrixGraph(); Using this line instead of previous
		// would allow for testing of the AdjacencyMatrixGraph() representation
		
		AdjListGraph.addVertex(A);
		
		
		List<Vertex> answerList = new LinkedList<Vertex>();
		answerList.add(A);
		
		assertEquals(answerList, AdjListGraph.getVertices());
	}
	
	@Test
	public void uniDirectionalEdgeTest(){
		AdjListGraph = new AdjacencyListGraph();
		//AdjListGraph = new AdjacencyMatrixGraph(); Using this line instead of previous
		// would allow for testing of the AdjacencyMatrixGraph() representation
		
		AdjListGraph.addVertex(A);
		AdjListGraph.addVertex(B);
		
		AdjListGraph.addEdge(A, B);
		
		assertTrue(AdjListGraph.edgeExists(A, B));
		assertFalse(AdjListGraph.edgeExists(B, A));
		
		//testing getDownstreamNeighbors method
		List<Vertex> downstrVList = new LinkedList<Vertex>();
		List<Vertex> downstrVListAns;
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(A);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(B);
		
		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(B);
		downstrVListAns = new LinkedList<Vertex>();
		
		assertEquals(downstrVListAns, downstrVList);
		
		//testing getUpstreamNeighbors method
		List<Vertex> upstrVList = new LinkedList<Vertex>();
		List<Vertex> upstrVListAns;
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(A);
		upstrVListAns = new LinkedList<Vertex>();

		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(B);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(A);
		
		assertEquals(upstrVListAns,upstrVList);
	}
	
	public void biDirectionEdgeTest(){
		AdjListGraph = new AdjacencyListGraph();
		//AdjListGraph = new AdjacencyMatrixGraph(); Using this line instead of previous
		// would allow for testing of the AdjacencyMatrixGraph() representation
		
		AdjListGraph.addVertex(A);
		AdjListGraph.addVertex(B);
		
		AdjListGraph.addEdge(A, B);
		AdjListGraph.addEdge(B, A);
		
		assertTrue(AdjListGraph.edgeExists(A, B));
		assertTrue(AdjListGraph.edgeExists(B, A));
		
		List<Vertex> allVertices = AdjListGraph.getVertices();
		List<Vertex> answerList = new LinkedList<Vertex>();
		answerList.add(A);
		answerList.add(B);
		
		assertEquals(answerList, allVertices);
		
		//testing getDownstreamNeighbors method
		List<Vertex> downstrVList = new LinkedList<Vertex>();
		List<Vertex> downstrVListAns;
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(A);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(B);
		
		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(B);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(A);
		
		assertEquals(downstrVListAns,downstrVList);
		
		//testing getUpstreamNeighbors method
		List<Vertex> upstrVList = new LinkedList<Vertex>();
		List<Vertex> upstrVListAns;
				
		upstrVList = AdjListGraph.getUpstreamNeighbors(A);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(B);

		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(B);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(A);

		assertEquals(upstrVListAns,upstrVList);
	}

	public void fourVertexString(){
		AdjListGraph = new AdjacencyListGraph();
		//AdjListGraph = new AdjacencyMatrixGraph(); Using this line instead of previous
		// would allow for testing of the AdjacencyMatrixGraph() representation
		
		AdjListGraph.addVertex(A);
		AdjListGraph.addVertex(B);
		AdjListGraph.addVertex(C);
		AdjListGraph.addVertex(D);
		
		AdjListGraph.addEdge(A, B);
		AdjListGraph.addEdge(B, C);
		AdjListGraph.addEdge(C, D);
		
		
		assertTrue(AdjListGraph.edgeExists(A, B));
		assertTrue(AdjListGraph.edgeExists(B, C));
		assertTrue(AdjListGraph.edgeExists(C, D));
		
		//note: edge-checking not exhaustive from here
		assertFalse(AdjListGraph.edgeExists(B, A));
		assertFalse(AdjListGraph.edgeExists(D, C));
		assertFalse(AdjListGraph.edgeExists(A, D));
		assertFalse(AdjListGraph.edgeExists(D, B));
		assertFalse(AdjListGraph.edgeExists(C, B));


		
		List<Vertex> allVertices = AdjListGraph.getVertices();
		List<Vertex> answerList = new LinkedList<Vertex>();
		answerList.add(A);
		answerList.add(B);
		answerList.add(C);
		answerList.add(D);
		
		assertEquals(answerList, allVertices);
		
		//testing getDownstreamNeighbors method
		List<Vertex> downstrVList = new LinkedList<Vertex>();
		List<Vertex> downstrVListAns;
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(A);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(B);
		
		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(B);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(C);
		
		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(C);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(D);
		
		assertEquals(downstrVListAns,downstrVList);
		
		//testing getUpstreamNeighbors method
		List<Vertex> upstrVList = new LinkedList<Vertex>();
		List<Vertex> upstrVListAns;
						
		upstrVList = AdjListGraph.getUpstreamNeighbors(A);
		upstrVListAns = new LinkedList<Vertex>();

		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(B);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(A);

		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(C);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(B);

		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(D);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(C);

		assertEquals(upstrVListAns,upstrVList);
	}
	
	public void complexTest1(){
		AdjListGraph = new AdjacencyListGraph();
		//AdjListGraph = new AdjacencyMatrixGraph(); Using this line instead of previous
		// would allow for testing of the AdjacencyMatrixGraph() representation
		
		AdjListGraph.addVertex(A);
		AdjListGraph.addVertex(B);
		AdjListGraph.addVertex(C);
		AdjListGraph.addVertex(D);
		AdjListGraph.addVertex(E);
		
		AdjListGraph.addEdge(A, B);
		AdjListGraph.addEdge(C, B);
		AdjListGraph.addEdge(C, D);
		AdjListGraph.addEdge(E, A);
		AdjListGraph.addEdge(A, C);
		AdjListGraph.addEdge(C, A);
		
		assertTrue(AdjListGraph.edgeExists(A, B));
		assertTrue(AdjListGraph.edgeExists(C, B));
		assertTrue(AdjListGraph.edgeExists(C, D));
		assertTrue(AdjListGraph.edgeExists(E, A));
		assertTrue(AdjListGraph.edgeExists(A, C));
		assertTrue(AdjListGraph.edgeExists(C, A));
		
		//note: edge-checking not exhaustive from here
		assertFalse(AdjListGraph.edgeExists(B, A));
		assertFalse(AdjListGraph.edgeExists(D, C));
		assertFalse(AdjListGraph.edgeExists(A, D));
		assertFalse(AdjListGraph.edgeExists(D, B));
		assertFalse(AdjListGraph.edgeExists(C, E));
		assertFalse(AdjListGraph.edgeExists(E, B));

		List<Vertex> allVertices = AdjListGraph.getVertices();
		List<Vertex> answerList = new LinkedList<Vertex>();
		answerList.add(A);
		answerList.add(B);
		answerList.add(C);
		answerList.add(D);
		answerList.add(E);
		
		assertEquals(answerList, allVertices);
		
		//testing getDownstreamNeighbors method
		List<Vertex> downstrVList = new LinkedList<Vertex>();
		List<Vertex> downstrVListAns;
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(A);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(B);
		downstrVListAns.add(C);

		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(B);
		downstrVListAns = new LinkedList<Vertex>();

		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(C);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(A);
		downstrVListAns.add(B);
		downstrVListAns.add(D);

		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(D);
		downstrVListAns = new LinkedList<Vertex>();

		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(E);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(A);

		assertEquals(downstrVListAns,downstrVList);
		
		//testing getUpstreamNeighbors method
		List<Vertex> upstrVList = new LinkedList<Vertex>();
		List<Vertex> upstrVListAns;
								
		upstrVList = AdjListGraph.getUpstreamNeighbors(A);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(E);
		upstrVListAns.add(C);
		
		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(B);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(A);
		upstrVListAns.add(C);
		
		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(C);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(A);
		
		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(D);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(C);
		
		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(E);
		upstrVListAns = new LinkedList<Vertex>();
		
		assertEquals(upstrVListAns,upstrVList);
	}
	
	public void complexTest2(){
		AdjListGraph = new AdjacencyListGraph();
		//AdjListGraph = new AdjacencyMatrixGraph(); Using this line instead of previous
		// would allow for testing of the AdjacencyMatrixGraph() representation
		
		AdjListGraph.addVertex(A);
		AdjListGraph.addVertex(B);
		AdjListGraph.addVertex(C);
		AdjListGraph.addVertex(D);
		AdjListGraph.addVertex(E);
		AdjListGraph.addVertex(F);
		
		
		AdjListGraph.addEdge(A, B);
		AdjListGraph.addEdge(C, A);
		AdjListGraph.addEdge(A, D);
		AdjListGraph.addEdge(D, E);
		AdjListGraph.addEdge(E, F);
		
		assertTrue(AdjListGraph.edgeExists(A, B));
		assertTrue(AdjListGraph.edgeExists(C, A));
		assertTrue(AdjListGraph.edgeExists(A, D));
		assertTrue(AdjListGraph.edgeExists(D, E));
		assertTrue(AdjListGraph.edgeExists(E, F));
		
		//note: edge-checking not exhaustive from here
		assertFalse(AdjListGraph.edgeExists(F, E));
		assertFalse(AdjListGraph.edgeExists(D, C));
		assertFalse(AdjListGraph.edgeExists(B, D));
		assertFalse(AdjListGraph.edgeExists(F, D));
		assertFalse(AdjListGraph.edgeExists(E, A));
		
		List<Vertex> allVertices = AdjListGraph.getVertices();
		List<Vertex> answerList = new LinkedList<Vertex>();
		answerList.add(A);
		answerList.add(B);
		answerList.add(C);
		answerList.add(D);
		answerList.add(E);
		answerList.add(F);
		
		assertEquals(answerList, allVertices);
		
		//testing getDownstreamNeighbors method
		List<Vertex> downstrVList = new LinkedList<Vertex>();
		List<Vertex> downstrVListAns;
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(A);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(D);

		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(B);
		downstrVListAns = new LinkedList<Vertex>();

		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(C);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(A);

		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(D);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(E);

		assertEquals(downstrVListAns,downstrVList);

		downstrVList = AdjListGraph.getDownstreamNeighbors(E);
		downstrVListAns = new LinkedList<Vertex>();
		downstrVListAns.add(F);

		assertEquals(downstrVListAns,downstrVList);
		
		downstrVList = AdjListGraph.getDownstreamNeighbors(F);
		downstrVListAns = new LinkedList<Vertex>();

		assertEquals(downstrVListAns,downstrVList);
		
		//testing getUpstreamNeighbors method
		List<Vertex> upstrVList = new LinkedList<Vertex>();
		List<Vertex> upstrVListAns;
										
		upstrVList = AdjListGraph.getUpstreamNeighbors(A);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(C);
				
		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(B);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(A);
				
		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(C);
		upstrVListAns = new LinkedList<Vertex>();
				
		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(D);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(A);
				
		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(E);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(D);
				
		assertEquals(upstrVListAns,upstrVList);
		
		upstrVList = AdjListGraph.getUpstreamNeighbors(F);
		upstrVListAns = new LinkedList<Vertex>();
		upstrVListAns.add(E);
				
		assertEquals(upstrVListAns,upstrVList);
	}
}
