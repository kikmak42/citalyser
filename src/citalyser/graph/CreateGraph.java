/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph;

/**
 *
 * @author sahil
 */
import citalyser.Initialiser;
import citalyser.graph.util.graphData;
import citalyser.graph.util.graphObject;
import citalyser.graph.util.nodeInfo;
import citalyser.model.Author;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.PaperCollectionResult;
import citalyser.ui.DisplayController;
import citalyser.ui.control.DisplayControllerImpl;
import citalyser.util.Config;
import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.LayoutDecorator;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.samples.PluggableRendererDemo.VoltageTips;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.CachingLayout;
import edu.uci.ics.jung.visualization.layout.ObservableCachingLayout;
import edu.uci.ics.jung.visualization.layout.PersistentLayoutImpl;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;
import org.apache.log4j.PropertyConfigurator;

public class CreateGraph {

    static Logger logger = Logger.getLogger(CreateGraph.class.getName());
    public static VisualizationViewer<nodeInfo, String> vv;
    public static SimpleGraphView2 sgv;
    public static Layout<nodeInfo, String> layout;
    public static JFrame frame;
    public static nodeInfo baseNode;
    public static graphData generateGraphObject;
    public static File settingsDirectory;
    public static File CacheDirectory;
    private static DisplayController displayController;

    public static DisplayController getDisplayController() {
        return displayController;
    }

    public static void main(String args[]) {

        PropertyConfigurator.configure("log4j.properties");

        /* initialise the software */
        Initialiser.init();

        /* Load the Config File*/
        Config.init(settingsDirectory);
        displayController = new DisplayControllerImpl();
        displayController.initializeDisplay();
        Paper paper = new Paper();
        paper.setTitle("Removal of Cr (VI) from aqueous solution: Electrocoagulation vs chemical coagulation");
        Author a = new Author("AK Golder");
        ArrayList<Author> arr = new ArrayList<>();
        arr.add(a);
        paper.setAuthors(arr);
        paper.setInfo("AK Golder");
        paper.setCitedByUrl("http://scholar.google.co.in/scholar?cites=1547993743289210385&as_sdt=2005&sciodt=0,5&hl=en");
        CreateGraph cg = new CreateGraph(paper);
    }

    public CreateGraph(Paper paper) {
        generateGraphObject = new graphData();
        this.baseNode = generateGraphObject.getbaseNode(paper);
        logger.debug("@@##$$:" + this.baseNode.citationurl);
        Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(this.baseNode.citationurl).build();
//        QueryHandler.getInstance().getQueryResult(q);
//        generateGraphObject.getNodeArray(((PaperCollectionResult)QueryHandler.getInstance().getQueryResult(q)).getContents());

        sgv = new SimpleGraphView2(); // This builds the graph
        //sgv
        layout = new SpringLayout<>(sgv.g2);

        populateGraph(generateGraphObject.getNodeArray(((PaperCollectionResult) QueryHandler.getInstance().getQueryResult(q)).getContents()));
// Layout<V, E>, BasicVisualizationServer<V,E>
        layout.setSize(new Dimension(600, 600));
        layout.setGraph(sgv.g2);

        vv = new VisualizationViewer<>(layout);

        vv.setPreferredSize(new Dimension(350, 350));
// Setup up a new vertex to paint transformer...
        Transformer<nodeInfo, Paint> vertexPaint;
        vertexPaint = new Transformer<nodeInfo, Paint>() {
            public Paint transform(nodeInfo i) {
                if (i.id == baseNode.id) {
                    return Color.PINK;
                } else {
                    return Color.orange;
                }
            }
        };
        // Set up a new stroke Transformer for the edges
        final float dash[] = {1.0f};
        Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer =
                new Transformer<String, Stroke>() {
                    public Stroke transform(String s) {
                        return new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
                    }
                };
        Transformer<nodeInfo, String> vertextrans =
                new Transformer<nodeInfo, String>() {
                    @Override
                    public String transform(nodeInfo i) {
                        return i.Title.substring(0, 20) + "...";
                    }
                };


        Transformer<nodeInfo, String> datatrans =
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
                        return new Rectangle(100, 50);
                    }
                };


        //vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderContext().setVertexShapeTransformer(shapetrans);
        //MyVerte 
        //Font f = new Font(null, style, size)
        // vv.getRenderContext().setVertexShapeTransformer(shapetrans);
        // vv.getRenderContext().set
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(vertextrans);
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);



        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        //vv.setGraphMouse(gMouse); //Add the mouse to our Visualization-Viewer.
        //PluggableGraphMouse pgm = new PluggableGraphMouse();
        gm.add(new PickingGraphMousePlugin());
        // gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON3_MASK));
        gm.add(new PopupGraphMousePlugin());
        // gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1 / 1.1f, 1.1f));
        // gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK));
        gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.9f));
        vv.setGraphMouse(gm);
        vv.setVertexToolTipTransformer(datatrans);
        vv.setToolTipText("<html><center>Use the mouse wheel to zoom<p>Click and Drag the mouse to pan<p>Shift-click and Drag to Rotate</center></html>");
        frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    public static void populateGraph(graphObject go) {
        go.baseInfo = CreateGraph.baseNode;
        for (nodeInfo i : go.arr) {
            sgv.g2.addVertex(i);
        }
        for (nodeInfo i : go.arr) {
            sgv.g2.addEdge("" + i.id + "-" + go.baseInfo, go.baseInfo, i);
        }
    }
}
