package ca.ubc.ece.cpen221.mp3.tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class BreadthFirstSearch_Test {
	
	Vertex A = new Vertex("A");
	Vertex B = new Vertex("B");
	Vertex C = new Vertex("C");
	Vertex D = new Vertex("D");
	Vertex E = new Vertex("E");
	Vertex F = new Vertex("F");
	
	@Test
	public void VertexStringTest() {
		Graph g1 = new AdjacencyMatrixGraph();
		
		g1.addVertex(A);
		g1.addVertex(B);
		g1.addVertex(C);
		g1.addVertex(D);
		
		g1.addEdge(A, B);
		g1.addEdge(B, C);
		g1.addEdge(C, D);
		
		Set<List<Vertex>> BFS = Algorithms.breadthFirstSearch(g1);
		
		Set<List<Vertex>> ansSet = new LinkedHashSet<List<Vertex>>();
		List<Vertex> ansList = new LinkedList<Vertex>();
		
		ansList.add(A);
		ansList.add(B);
		ansList.add(C);
		ansList.add(D);
		
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		
		ansList.add(B);
		ansList.add(C);
		ansList.add(D);
		
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();

		ansList.add(C);
		ansList.add(D);
		
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		
		ansList.add(D);

		ansSet.add(ansList);
		
		assertEquals(ansSet, BFS);
		
	}

	@Test
	public void triangleWithTwoBranches (){
		Graph g2 = new AdjacencyMatrixGraph();
		
		g2.addVertex(A);
		g2.addVertex(B);
		g2.addVertex(C);
		g2.addVertex(D);
		g2.addVertex(E);
		
		g2.addEdge(A, B);
		g2.addEdge(A, C);
		g2.addEdge(E, A);
		g2.addEdge(C, A);
		g2.addEdge(C, B);
		g2.addEdge(C, D);
		
		Set<List<Vertex>> BFS = Algorithms.breadthFirstSearch(g2);
		
		Set<List<Vertex>> ansSet = new LinkedHashSet<List<Vertex>>();
		List<Vertex> ansList = new LinkedList<Vertex>();
		
		ansList.add(A);
		ansList.add(B);
		ansList.add(C);
		ansList.add(D);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(B);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(C);
		ansList.add(A);
		ansList.add(B);
		ansList.add(D);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(D);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(E);
		ansList.add(A);
		ansList.add(B);
		ansList.add(C);
		ansList.add(D);
		ansSet.add(ansList);
		
		assertEquals(ansSet, BFS);
	}
	
	@Test
	public void triangleWithLongBranch(){
		Graph g3 = new AdjacencyMatrixGraph();
		
		g3.addVertex(A);
		g3.addVertex(B);
		g3.addVertex(C);
		g3.addVertex(D);
		g3.addVertex(E);
		g3.addVertex(F);
		
		g3.addEdge(A, B);
		g3.addEdge(B, C);
		g3.addEdge(C, B);
		g3.addEdge(C, A);
		g3.addEdge(A, D);
		g3.addEdge(D, E);
		g3.addEdge(E, F);
		
		Set<List<Vertex>> BFS = Algorithms.breadthFirstSearch(g3);
		
		Set<List<Vertex>> ansSet = new LinkedHashSet<List<Vertex>>();
		List<Vertex> ansList = new LinkedList<Vertex>();
		
		ansList.add(A);
		ansList.add(B);
		ansList.add(D);
		ansList.add(C);
		ansList.add(E);
		ansList.add(F);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(B);
		ansList.add(C);
		ansList.add(A);
		ansList.add(D);
		ansList.add(E);
		ansList.add(F);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(C);
		ansList.add(A);
		ansList.add(B);
		ansList.add(D);
		ansList.add(E);
		ansList.add(F);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(D);
		ansList.add(E);
		ansList.add(F);
		ansSet.add(ansList);

		ansList = new LinkedList<Vertex>();
		ansList.add(E);
		ansList.add(F);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(F);
		ansSet.add(ansList);
		
		assertEquals(ansSet, BFS);
	}
	
	@Test
	public void treeGraph (){
		Graph g3 = new AdjacencyMatrixGraph();
		
		g3.addVertex(A);
		g3.addVertex(B);
		g3.addVertex(C);
		g3.addVertex(D);
		g3.addVertex(E);
		g3.addVertex(F);
		
		g3.addEdge(A, B);
		g3.addEdge(A, C);
		g3.addEdge(A, D);
		g3.addEdge(A, E);
		g3.addEdge(D, F);
		
		Set<List<Vertex>> BFS = Algorithms.breadthFirstSearch(g3);
		
		Set<List<Vertex>> ansSet = new LinkedHashSet<List<Vertex>>();
		List<Vertex> ansList = new LinkedList<Vertex>();
		
		ansList.add(A);
		ansList.add(B);
		ansList.add(C);
		ansList.add(D);
		ansList.add(E);
		ansList.add(F);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(B);
		ansSet.add(ansList);

		ansList = new LinkedList<Vertex>();
		ansList.add(C);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(D);
		ansList.add(F);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(E);
		ansSet.add(ansList);
		
		ansList = new LinkedList<Vertex>();
		ansList.add(F);
		ansSet.add(ansList);
		
		assertEquals(ansSet, BFS);
	}
}
