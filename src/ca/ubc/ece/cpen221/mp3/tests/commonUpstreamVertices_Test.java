package ca.ubc.ece.cpen221.mp3.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyListGraph;
import ca.ubc.ece.cpen221.mp3.graph.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class commonUpstreamVertices_Test {

	Vertex A = new Vertex("A");
	Vertex B = new Vertex("B");
	Vertex C = new Vertex("C");
	Vertex D = new Vertex("D");
	Vertex E = new Vertex("E");

	
	@Test
	public void comUpstrVertTest1() {
		Graph g1 = new AdjacencyMatrixGraph();
		//Graph g1 = new AdjacencyListGraph(); note: line above can be changed to this
		//to test the other Graph implementation.
		
		g1.addVertex(A);
		g1.addVertex(B);
		g1.addVertex(C);
		g1.addVertex(D);
		g1.addVertex(E);
		
		g1.addEdge(C, A);
		g1.addEdge(D, A);
		g1.addEdge(C, B);
		g1.addEdge(D, B);
		g1.addEdge(E, B);
		g1.addEdge(A, E);
		
		List<Vertex> comUpstrVerts = Algorithms.commonUpstreamVertices(g1, A, B);
		
		List<Vertex> ansList = new LinkedList<Vertex>();
		ansList.add(C);
		ansList.add(D);
		
		assertEquals(ansList, comUpstrVerts);

	}

	@Test
	public void comUpstrVertTest2 (){
		Graph g2 = new AdjacencyMatrixGraph();
		//Graph g2 = new AdjacencyListGraph(); note: line above can be changed to this
		//to test the other Graph implementation.

		g2.addVertex(A);
		g2.addVertex(B);
		g2.addVertex(C);
		g2.addVertex(D);
		g2.addVertex(E);

		g2.addEdge(A, B);
		g2.addEdge(A, D);
		g2.addEdge(B, A);
		g2.addEdge(D, B);
		g2.addEdge(E, B);
		g2.addEdge(C, A);

		List<Vertex> comUpstrVerts = Algorithms.commonUpstreamVertices(g2, A, B);
		
		List<Vertex> ansList = new LinkedList<Vertex>();
		
		assertEquals(ansList, comUpstrVerts);		
	}
	
	@Test
	public void comUpstrVertTest3(){
		Graph g3 = new AdjacencyMatrixGraph();
		//Graph g3 = new AdjacencyListGraph(); note: line above can be changed to this
		//to test the other Graph implementation.

		g3.addVertex(A);
		g3.addVertex(B);
		g3.addVertex(C);
		g3.addVertex(D);
		g3.addVertex(E);

		g3.addEdge(A, D);
		g3.addEdge(D, A);
		g3.addEdge(C, A);
		g3.addEdge(C, E);
		g3.addEdge(D, B);
		g3.addEdge(E, B);
		g3.addEdge(C, B);
		g3.addEdge(B, C);
		g3.addEdge(E, A);

		List<Vertex> comUpstrVerts = Algorithms.commonUpstreamVertices(g3, A, B);
		
		List<Vertex> ansList = new LinkedList<Vertex>();
		ansList.add(C);
		ansList.add(D);
		ansList.add(E);
		
		assertEquals(ansList, comUpstrVerts);		
	}
}
