/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DataVisualizationPanel.java
 *
 * Created on Mar 17, 2013, 2:44:17 AM
 */
package citalyser.ui.visualization.panels.regulardisplaypanel;

import citalyser.ui.control.DisplayMaster;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.ContentDisplayPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.GraphViewPanel;
import java.awt.CardLayout;

/**
 *
 * @author Tanmay Patil
 */
public class DataVisualizationPanel extends javax.swing.JPanel {

    /** Creates new form DataVisualizationPanel */
    public DataVisualizationPanel() {
        initComponents();
    }

    public ContentDisplayPanel getContentDisplayPanel() {
        return contentDisplayPanel;
    }

    public GraphViewPanel getGraphViewPanel() {
        return graphViewPanel;
    }

    public void flipToGraphDisplayPanel() {
        ((CardLayout) getLayout()).last(this);
    }    

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
        contentDisplayPanel.setDisplayMaster(displayMaster);
        graphViewPanel.setDisplayMaster(displayMaster);
    }
    
    private DisplayMaster displayMaster;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentDisplayPanel = new citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.ContentDisplayPanel();
        graphViewPanel = new citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.GraphViewPanel();

        setLayout(new java.awt.CardLayout());
        add(contentDisplayPanel, "card3");
        add(graphViewPanel, "card3");
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.ContentDisplayPanel contentDisplayPanel;
    private citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.GraphViewPanel graphViewPanel;
    // End of variables declaration//GEN-END:variables

    public void clearAll() {
        contentDisplayPanel.clearAll();
        displayMaster.clearCitationHistory();
        System.gc();
    }
}
