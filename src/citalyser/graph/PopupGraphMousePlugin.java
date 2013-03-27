/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph;

import citalyser.Main;
import citalyser.graph.util.nodeInfo;
import citalyser.model.PaperCollection;
import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.PaperCollectionResult;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.GraphViewPanel;
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
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import java.awt.IllegalComponentStateException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import org.apache.log4j.Logger;

/**
 *
 * @author sahil
 */
class PopupGraphMousePlugin extends AbstractPopupGraphMousePlugin implements MouseListener {

    static Logger logger = Logger.getLogger(PopupGraphMousePlugin.class.getName());
    private CreateGraph createGraph;
    private GraphViewPanel gvp;

    public PopupGraphMousePlugin(CreateGraph createGraph, GraphViewPanel gvp) {
        this.createGraph = createGraph;
        this.gvp = gvp;
    }

    public CreateGraph getCreateGraph() {
        return createGraph;
    }

    public void setCreateGraph(CreateGraph createGraph) {
        this.createGraph = createGraph;
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


        GraphElementAccessor<nodeInfo, String> pickSupport = createGraph.vv.getPickSupport();
        System.out.println("GraphElementAccessor!");

        final nodeInfo pickV = pickSupport.getVertex(createGraph.vv.getGraphLayout(), ivp.getX(), ivp.getY());
//        System.out.println("!@#$%^&*");
        if (pickV != null) {
            //System.out.println(pickV.id);

            if (pickV.id == createGraph.baseNode.id) {
                popup.add(new AbstractAction("Fetch More Citations") {
                    public void actionPerformed(ActionEvent e) {
                        //System.out.println("person added");

                        Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(pickV.citationurl).numResult(20).build();
                        PaperCollectionResult queriedPapercollection = (PaperCollectionResult) QueryHandler.getInstance().getQueryResult(q);
                        if (queriedPapercollection != null) {
                            createGraph.nullResultFlag = false;
                            PaperCollection pc = (queriedPapercollection.getContents());
                            createGraph.populateGraph(createGraph.generateGraphObject.getNodeArray(pc));
                            createGraph.sgv.g2 = new DirectedOrderedSparseMultigraph<nodeInfo, String>();

                            if (pickV.nocitation != 0) {
                                createGraph.baseNode = pickV;

                                createGraph.addToGraph(createGraph.generateGraphObject.getNodeArray(pc));
                                createGraph.layouttop.setGraph(createGraph.sgv.g2);
                                gvp.getjLabel1().setText("<html>" + pickV.Title);

                                gvp.getGraphHistory().addnodeInfo(pickV);
                                gvp.getjLabel2().setText(gvp.getGraphHistory().getnodeList());
                                gvp.getjButton2().setVisible(false);
                                gvp.getjButton1().setVisible(true);
                                createGraph.vv.repaint();
                            } else {
                                Main.getDisplayController().displayErrorMessage("Zero Citaions for this paper");
                            }

                        } else {
                            createGraph.nullResultFlag = true;
                        }
                    }
                });
            }



            popup.add(new AbstractAction("Fetch Citations") {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("person added");

                    Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(pickV.citationurl).numResult(20).build();
                    PaperCollectionResult queriedPapercollection = (PaperCollectionResult) QueryHandler.getInstance().getQueryResult(q);
                    if (queriedPapercollection != null) {
                        createGraph.nullResultFlag = false;
                        PaperCollection pc = (queriedPapercollection.getContents());
                        if (pickV.nocitation != 0) {
                            createGraph.baseNode = pickV;
                            createGraph.sgv.g2 = new DirectedOrderedSparseMultigraph<nodeInfo, String>();
                            createGraph.populateGraph(createGraph.generateGraphObject.getNodeArray(pc));
                            createGraph.layouttop.setGraph(createGraph.sgv.g2);
                            gvp.getjLabel1().setText("<html>" + pickV.Title);

                            gvp.getGraphHistory().addnodeInfo(pickV);
                            gvp.getjLabel2().setText(gvp.getGraphHistory().getnodeList());
                            gvp.getjButton2().setVisible(false);
                            gvp.getjButton1().setVisible(true);
                            createGraph.vv.repaint();
                        } else {
                            Main.getDisplayController().displayErrorMessage("Zero Citaions for this paper");
                        }
                    } else {
                        createGraph.nullResultFlag = true;
                    }
                }
            });

            popup.setLocation(e.getPoint());

            logger.debug(
                    "##:" + e.getX() + "@@:" + e.getY());
            popup.show(createGraph.vv, e.getX(), e.getY());


        }///if picksupport



    }//handlePopup(MouseEvent e)
}//PopupGraphMousePlugin
