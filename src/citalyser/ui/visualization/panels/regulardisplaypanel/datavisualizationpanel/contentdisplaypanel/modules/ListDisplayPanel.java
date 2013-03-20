/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ListDisplayPanel.java
 *
 * Created on Mar 17, 2013, 2:50:25 AM
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules;

import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.ui.control.DisplayMaster;
import java.util.ArrayList;
import javax.swing.ListModel;

/**
 *
 * @author Tanmay Patil
 */
public class ListDisplayPanel extends javax.swing.JPanel {
    private ArrayList<Paper> papers;

    /** Creates new form ListDisplayPanel */
    public ListDisplayPanel() {
        initComponents();
    }

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
    }

    public DisplayMaster getDisplayMaster() {
        return displayMaster;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<Paper>();

        setLayout(new java.awt.BorderLayout());

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                citationClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void citationClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_citationClicked
        displayMaster.citationListClicked(papers.get(jList1.getSelectedIndex()));
    }//GEN-LAST:event_citationClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<Paper> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void setList(ArrayList<Paper> papers, ListModel<Paper> listModel) {
        this.papers = papers;
        jList1.setModel(listModel);
        remove(jScrollPane1);
        setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(jList1);
        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }
}
