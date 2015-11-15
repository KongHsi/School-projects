package ca.ubc.ece.cpen221.mp3.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class ShortestDistance_Test {
	
	Vertex A = new Vertex("A");
	Vertex B = new Vertex("B");
	Vertex C = new Vertex("C");
	Vertex D = new Vertex("D");
	Vertex E = new Vertex("E");
	Vertex F = new Vertex("F");

	@Test
	public void vertexStringTest() {
		Graph g1 = new AdjacencyMatrixGraph();
		int shortestDistance;
		
		g1.addVertex(A);
		g1.addVertex(B);
		g1.addVertex(C);
		g1.addVertex(D);
		
		g1.addEdge(A, B);
		g1.addEdge(B, C);
		g1.addEdge(C, D);
		
		shortestDistance = Algorithms.shortestDistance(g1, A, D);
		assertEquals(3, shortestDistance);
		
		shortestDistance = Algorithms.shortestDistance(g1, B, C);
		assertEquals(1, shortestDistance);		
		
		shortestDistance = Algorithms.shortestDistance(g1, D, B);
		assertEquals(-1, shortestDistance);
	}

	@Test
	public void triangleAndBranchTest(){
		Graph g2 = new AdjacencyMatrixGraph();
		int shortestDistance;
		
		g2.addVertex(A);
		g2.addVertex(B);
		g2.addVertex(C);
		g2.addVertex(D);
		g2.addVertex(E);
		g2.addVertex(F);
		
		g2.addEdge(A, B);
		g2.addEdge(B, C);
		g2.addEdge(C, B);
		g2.addEdge(C, A);
		g2.addEdge(A, D);
		g2.addEdge(D, E);
		g2.addEdge(E, F);
		
		shortestDistance = Algorithms.shortestDistance(g2, A, F);
		assertEquals(3, shortestDistance);
		
		shortestDistance = Algorithms.shortestDistance(g2, C, F);
		assertEquals(4, shortestDistance);
		
		shortestDistance = Algorithms.shortestDistance(g2, B, D);
		assertEquals(3, shortestDistance);
		
		shortestDistance = Algorithms.shortestDistance(g2, D, B);
		assertEquals(-1, shortestDistance);

	}
	
	public void changingGraph1(){
		Graph g3 = new AdjacencyMatrixGraph();
		int shortestDistance;
		
		g3.addVertex(A);
		g3.addVertex(B);
		g3.addVertex(C);
		g3.addVertex(D);
		g3.addVertex(E);
		
		g3.addEdge(A, B);
		g3.addEdge(B, C);
		g3.addEdge(C, D);
		g3.addEdge(D, E);

		shortestDistance = Algorithms.shortestDistance(g3, A, E);
		assertEquals(4, shortestDistance);
		
		//adding a vertex and a shorter route
		g3.addVertex(F);
		g3.addEdge(A, F);
		g3.addEdge(F, D);
		
		shortestDistance = Algorithms.shortestDistance(g3, A, E);
		assertEquals(3, shortestDistance);
		
		//adding another edge as shortcut
		g3.addEdge(F, E);
		
		shortestDistance = Algorithms.shortestDistance(g3, A, E);
		assertEquals(2, shortestDistance);
		
		//adding shortest possible shortcut
		g3.addEdge(A, E);
		
		shortestDistance = Algorithms.shortestDistance(g3, A, E);
		assertEquals(1, shortestDistance);
	}
	
	@Test
	public void test1(){
		Graph g4 = new AdjacencyMatrixGraph();
		int shortestDistance;
		
		g4.addVertex(A);
		g4.addVertex(B);
		g4.addVertex(C);
		g4.addVertex(D);
		g4.addVertex(E);
		
		g4.addEdge(A, B);
		g4.addEdge(B, C);
		g4.addEdge(C, D);
		g4.addEdge(E, D);
		g4.addEdge(C, E);
		g4.addEdge(A, D);
		
		shortestDistance = Algorithms.shortestDistance(g4, A, E);
		assertEquals(3, shortestDistance);
	}
}
