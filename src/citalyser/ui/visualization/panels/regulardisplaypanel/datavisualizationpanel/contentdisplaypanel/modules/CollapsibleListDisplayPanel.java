/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules;

import citalyser.ui.control.DisplayMaster;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.listdisplaypanel.CollapsibleListEntityPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.listdisplaypanel.ListEntityPanel;
import java.util.Vector;

/**
 *
 * @author kaushik
 */
public class CollapsibleListDisplayPanel extends javax.swing.JPanel {

    /**
     * Creates new form CollapsibleListDisplayPanel
     */
    public CollapsibleListDisplayPanel() {
        collapsibleListEntityPanels = new Vector<>();
        initComponents();
    }
    
    public void addCollapsibleListEntityPanel(CollapsibleListEntityPanel collapsibleListEntityPanel) {
        collapsibleListEntityPanel.setDisplayMaster(displayMaster);
        collapsibleListEntityPanels.add(collapsibleListEntityPanel);
        jPanel1.add(collapsibleListEntityPanel);
       // jXTaskPaneContainer.add(collapsibleListEntityPanel);
    }

    public Vector<CollapsibleListEntityPanel> getListEntityPanels() {
        return collapsibleListEntityPanels;
    }

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
        for (CollapsibleListEntityPanel collapsiblelistEntityPanel : collapsibleListEntityPanels) {
            collapsiblelistEntityPanel.setDisplayMaster(displayMaster);
        }
    }

    public DisplayMaster getDisplayMaster() {
        return displayMaster;
    }
    
    private DisplayMaster displayMaster;
    private Vector<CollapsibleListEntityPanel> collapsibleListEntityPanels;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
