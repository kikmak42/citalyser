/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules;

import citalyser.ui.control.DisplayMaster;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.listdisplaypanel.CollapsibleListEntityPanel;
import java.util.Vector;
import javax.swing.plaf.DimensionUIResource;

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
        //jPanel1.add(collapsibleListEntityPanel);

        jXTaskPaneContainer.add(collapsibleListEntityPanel);
        jXTaskPaneContainer.setMinimumSize(new DimensionUIResource(40, 40));

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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jXTaskPaneContainer = new org.jdesktop.swingx.JXTaskPaneContainer();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("jLabel1");

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(2, 2, 2)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jButton1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton2)
                .addComponent(jLabel1)));

        add(jPanel1, java.awt.BorderLayout.NORTH);
        add(jXTaskPaneContainer, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer;
    // End of variables declaration//GEN-END:variables

    public void clear() {
        jPanel1.removeAll();
        while (!collapsibleListEntityPanels.isEmpty()) {
            collapsibleListEntityPanels.remove(0);
        }
    }
}
