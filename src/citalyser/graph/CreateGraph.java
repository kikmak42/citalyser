/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph;

/**
 *
 * @author sahil
 */
import citalyser.graph.util.graphObject;
import citalyser.graph.util.nodeInfo;
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
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

public class CreateGraph {

    public static VisualizationViewer<nodeInfo, String> vv;
    public static SimpleGraphView2 sgv;
    public static Layout<nodeInfo, String> layout;
    public static JFrame frame;
    public static nodeInfo baseNode;

    public CreateGraph(nodeInfo n) {
        this.baseNode = n;
        sgv = new SimpleGraphView2(); // This builds the graph
// Layout<V, E>, BasicVisualizationServer<V,E>
        layout = new SpringLayout<>(sgv.g2);
        layout.setSize(new Dimension(600, 600));
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
                        return i.Title.substring(0, 20)+"...";
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
        layout.setGraph(sgv.g2);
        frame.repaint();
    }
}
