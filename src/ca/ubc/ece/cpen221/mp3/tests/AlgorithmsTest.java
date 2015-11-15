package ca.ubc.ece.cpen221.mp3.tests;

import static org.junit.Assert.*;


import java.util.*;

import org.junit.Test;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyListGraph;
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AlgorithmsTest {

    @Test
    public void testDepthfirstsearch1() {
        Graph test = new AdjacencyListGraph();
        Vertex v2 = new Vertex ("a");
        Vertex v3 = new Vertex ("b");
        Vertex v4 = new Vertex ("c");
  
        test.addVertex(v2);
        test.addVertex(v3);
        test.addVertex(v4);
     
        test.addEdge(v2,v3);
        test.addEdge(v3,v2);
        test.addEdge(v2,v4);
        test.addEdge(v4,v2);
        test.addEdge(v4,v3);
        test.addEdge(v3,v4);
     
        //Algorithms x = new Algorithms();
       
        List<Vertex> caseA= new ArrayList<Vertex>();
        List<Vertex> caseB= new ArrayList<Vertex>();
        List<Vertex> caseC= new ArrayList<Vertex>();
        caseA.add(v2);
        caseA.add(v3);
        caseA.add(v4);
        caseB.add(v3);
        caseB.add(v2);
        caseB.add(v4);
        caseC.add(v4);
        caseC.add(v2);
        caseC.add(v3);
        
        Set<List<Vertex>> testSet= new HashSet<List<Vertex>>();
        testSet.add(caseA);
        testSet.add(caseB);
        testSet.add(caseC);//I changed (caseB) to (caseC)
        
        assertEquals(Algorithms.Depthfirstsearch(test),testSet);
     
    }

    @Test
    public void testDepthfirstsearch2() {
        Graph test = new AdjacencyListGraph();
        Vertex v2 = new Vertex ("a");
        Vertex v3 = new Vertex ("b");
        Vertex v4 = new Vertex ("c");
        Vertex v5 = new Vertex ("d");
  
        test.addVertex(v2);
        test.addVertex(v3);
        test.addVertex(v4);
        test.addVertex(v5);
     
        test.addEdge(v2,v3);
        test.addEdge(v3,v2);
        test.addEdge(v2,v4);
        test.addEdge(v4,v2);
        test.addEdge(v4,v3);
        test.addEdge(v3,v4);
        test.addEdge(v4, v5);
        test.addEdge(v5,v4);
     
        //Algorithms x = new Algorithms();
       
        List<Vertex> caseA= new ArrayList<Vertex>();
        List<Vertex> caseB= new ArrayList<Vertex>();
        List<Vertex> caseC= new ArrayList<Vertex>();
        List<Vertex> caseD= new ArrayList<Vertex>();
        caseA.add(v2);
        caseA.add(v3);
        caseA.add(v4);
        caseA.add(v5);
        caseB.add(v3);
        caseB.add(v2);
        caseB.add(v4);
        caseB.add(v5);
        caseC.add(v4);
        caseC.add(v2);
        caseC.add(v3);
        caseC.add(v5);
        caseD.add(v5);
        caseD.add(v4);
        caseD.add(v2);
        caseD.add(v3);
        
        Set<List<Vertex>> testSet= new HashSet<List<Vertex>>();
        testSet.add(caseA);
        testSet.add(caseB);
        testSet.add(caseC);//I changed (caseB) to (caseC)
        testSet.add(caseD);
        assertEquals(Algorithms.Depthfirstsearch(test),testSet);
     
    }

   
}
