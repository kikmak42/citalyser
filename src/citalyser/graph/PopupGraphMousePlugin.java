/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph;

import citalyser.Main;
import citalyser.graph.util.nodeInfo;
import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.PaperCollectionResult;
import edu.uci.ics.jung.visualization.control.GraphMousePlugin;

/**
 *
 * @author sahil
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

/**
 *
 * @author sahil
 */
class PopupGraphMousePlugin extends AbstractPopupGraphMousePlugin implements MouseListener {

    public PopupGraphMousePlugin() {
        this(MouseEvent.BUTTON3_MASK);
    }

    public PopupGraphMousePlugin(int modifiers) {
        super(modifiers);
    }

    /**
     * If this event is over a station (vertex), pop up a menu to allow the user
     * to perform a few actions; else, pop up a menu over the layout/canvas
     *
     * @param e
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void handlePopup(MouseEvent e) {
        final Point2D p = e.getPoint();
        final Point2D ivp = p;
        JPopupMenu popup = new JPopupMenu();
        String center = "Center to Node";
        System.out.println("mouse event!");


        GraphElementAccessor<nodeInfo, String> pickSupport = CreateGraph.vv.getPickSupport();
        System.out.println("GraphElementAccessor!");
       
            final nodeInfo pickV = pickSupport.getVertex(CreateGraph.vv.getGraphLayout(), ivp.getX(), ivp.getY());
            if (pickV != null) {
                System.out.println(pickV.id);
                popup.add(new AbstractAction("Go to this") {
                    public void actionPerformed(ActionEvent e) {
                        CreateGraph.baseNode = pickV;
                        System.out.println("person added");
                        Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(pickV.citationurl).numResult(20).build();
                        CreateGraph.populateGraph(CreateGraph.generateGraphObject.getNodeArray(((PaperCollectionResult) QueryHandler.getInstance().getQueryResult(q)).getContents()));
                        CreateGraph.layout.setGraph(CreateGraph.sgv.g2);
                        CreateGraph.frame.repaint();
                    }
                });//new abstraction
//                popup.add(new AbstractAction("View info") {
//                    public void actionPerformed(ActionEvent e) {
//                        Main.getDisplayController().displayErrorMessage(pickV.EntireInfo);
//                    }
//                });

                popup.show(CreateGraph.vv, e.getX(), e.getY());

            
        }///if picksupport



    }//handlePopup(MouseEvent e)
}//PopupGraphMousePlugin
