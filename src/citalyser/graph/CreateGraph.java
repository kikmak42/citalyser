/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph;

/**
 *
 * @author sahil
 */
import citalyser.graph.util.graphData;
import citalyser.graph.util.graphObject;
import citalyser.graph.util.nodeInfo;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.PaperCollectionResult;
import citalyser.ui.DisplayController;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.GraphViewPanel;
import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.File;
import org.apache.log4j.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.collections15.Transformer;

public class CreateGraph {

    static Logger logger = Logger.getLogger(CreateGraph.class.getName());
    public VisualizationViewer<nodeInfo, String> vv;
    public SimpleGraphView2 sgv;
    public Layout<nodeInfo, String> layout;
    public Layout<nodeInfo, String> layouttop;
    public JPanel panel;
    public JFrame frame;
    public boolean isMetric;
    public nodeInfo baseNode;
    public boolean nullResultFlag;
    public graphData generateGraphObject;
    public static File settingsDirectory;
    public static File CacheDirectory;
    public GraphViewPanel gvp;
    private static DisplayController displayController;

    public static DisplayController getDisplayController() {
        return displayController;
    }

    public CreateGraph(Paper paper, GraphViewPanel gvp, boolean isMetric) {
        generateGraphObject = new graphData();
        this.gvp = gvp;
        this.isMetric = isMetric;
        this.baseNode = generateGraphObject.getbaseNode(paper);
        gvp.getGraphHistory().addnodeInfo(this.baseNode);
        gvp.getjLabel2().setText(gvp.getGraphHistory().getnodeList());
        sgv = new SimpleGraphView2(); // This builds the graph
        layout = new SpringLayout<>(sgv.g2);
        layouttop = new AggregateLayout<>(layout);
        layout.setSize(new Dimension(600, 600));
        layout.setGraph(sgv.g2);
        layouttop.setSize(new Dimension(600, 600));
        layouttop.setGraph(sgv.g2);
        vv = new VisualizationViewer<>(layouttop);
        vv.setPreferredSize(new Dimension(350, 350));
        Query q;
        if (this.isMetric) {
            q = new Query.Builder("").flag(QueryType.CITATIONS_LIST_METRIC).Url(this.baseNode.citationurl).numResult(20).build();
        } else {
            q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(this.baseNode.citationurl).numResult(20).build();
        }
        PaperCollectionResult queriedPapercollection = (PaperCollectionResult) QueryHandler.getInstance().getQueryResult(q);
        if (queriedPapercollection != null) {
            nullResultFlag = false;
            PaperCollection pc = (queriedPapercollection.getContents());
            populateGraph(generateGraphObject.getNodeArray(pc));

            Transformer<nodeInfo, Paint> vertexPaint;
            vertexPaint = new Transformer<nodeInfo, Paint>() {
                @Override
                public Paint transform(nodeInfo i) {
                    return new Color(255, 255, 128, 128);

                }
            };
            // Set up a new stroke Transformer for the edges

            Transformer<nodeInfo, String> vertextrans =
                    new Transformer<nodeInfo, String>() {
                        @Override
                        public String transform(nodeInfo i) {
                            return i.Title.split(" ")[0];
                        }
                    };

            Transformer<nodeInfo, String> vt =
                    new Transformer<nodeInfo, String>() {
                        @Override
                        public String transform(nodeInfo i) {
                            return i.EntireInfo;
                        }
                    };
            Transformer<nodeInfo, Shape> shapetrans =
                    new Transformer<nodeInfo, Shape>() {
                        @Override
                        public Shape transform(nodeInfo i) {
                            return new Rectangle(150, 20);
                        }
                    };

            vv.getRenderContext().setVertexShapeTransformer(shapetrans);
            vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
            vv.getRenderContext().setVertexLabelTransformer(vertextrans);
            vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
            vv.getPickedVertexState();
            gvp.getjLabel1().setText("<html>" + this.baseNode.Title);

            DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
            gm.add(new AnimatedPickingGraphMousePlugin());
            gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
            gm.add(new PickingGraphMousePlugin());
            gm.add(new PopupGraphMousePlugin(this, gvp));
            gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.9f));
            vv.setGraphMouse(gm);
            vv.setVertexToolTipTransformer(vt);
            vv.setToolTipText("<html><center>Use the mouse wheel to zoom<p>Click and Drag the mouse to pan<p>Shift-click and Drag to Rotate</center></html>");

        } else {
            nullResultFlag = true;
        }


    }

    public void populateGraph(graphObject go) {
        go.baseInfo = this.baseNode;
        for (nodeInfo i : go.arr) {
            sgv.g2.addVertex(i);
        }
        for (nodeInfo i : go.arr) {
            sgv.g2.addEdge("" + i.id + "-" + go.baseInfo, go.baseInfo, i);
        }
    }

    public void populateOnPrevNext(nodeInfo base) {
        this.baseNode = base;

        Query q;
        if (this.isMetric) {
            q = new Query.Builder("").flag(QueryType.CITATIONS_LIST_METRIC).Url(base.citationurl).numResult(20).build();
        } else {
            q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(base.citationurl).numResult(20).build();
        }
        PaperCollectionResult queriedPapercollection = (PaperCollectionResult) QueryHandler.getInstance().getQueryResult(q);
        if (queriedPapercollection != null) {
            nullResultFlag = false;
            PaperCollection pc = (queriedPapercollection.getContents());
            sgv.g2 = new DirectedOrderedSparseMultigraph<nodeInfo, String>();
            populateGraph(generateGraphObject.getNodeArray(pc));
        } else {
            nullResultFlag = true;
        }
    }

    public void addToGraph(graphObject go) {
        go.baseInfo = this.baseNode;

        for (nodeInfo i : go.arr) {
            sgv.g2.addVertex(i);
        }
        for (nodeInfo i : go.arr) {
            sgv.g2.addEdge("" + i.id + "-" + go.baseInfo, go.baseInfo, i);
        }
    }

    public VisualizationViewer getVisualizationViewer() {
        return this.vv;
    }
}
