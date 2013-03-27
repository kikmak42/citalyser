/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph;

/**
 *
 * @author sahil
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import citalyser.graph.util.nodeInfo;
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 *
 * @author abhishek
 */
public class SimpleGraphView2 {
    public Graph<nodeInfo, String> g2 ;
    public SimpleGraphView2() {
       
       g2 = new DirectedOrderedSparseMultigraph<nodeInfo, String>();
       // g2.addVertex((Integer) 1);
        
        
        System.out.println("The graph g2 = " + g2.toString());

    }
     
}
