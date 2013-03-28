/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.sidebarpanel;

import citalyser.model.PaperCollection;
import citalyser.model.query.QueryResult;
import citalyser.ui.control.DisplayMaster;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kaushik
 */
public class AuthorListPanel extends javax.swing.JPanel {

    private PaperCollection paperCollection;
    private ArrayList<Integer> rowNumbers;

    /**
     * Creates new form AuthorListPanel
     */
    public AuthorListPanel() {
        initComponents();
    }

    public void setList(ArrayList<String> authors) {
        if(authors == null || authors.size() == 0)
            return ;
        DefaultListModel list = new DefaultListModel();
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i) != "") {
                System.out.println("[" + authors.get(i) + "]");
                JCheckBox jCheckBox = new JCheckBox();
                jCheckBox.setText(authors.get(i));
                jCheckBox.setSelected(false);
                jCheckBox.setPreferredSize(new Dimension(100, 20));
                jCheckBox.setBorder(new EmptyBorder(5, 2, 2, 5));
                jCheckBox.setBorderPainted(true);
                list.add(i, jCheckBox);
                //list.addElement(authors.get(i));          
            }
        }
        jList1.setModel(list);
        displayMaster.showArticleSearch(false);
    }

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
    }
    
    private DisplayMaster displayMaster;

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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<javax.swing.JCheckBox>();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(59, 89, 152));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Check Authors to Highlight Papers");
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(181, 20));
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 5));
        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 5));
        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);

        jList1.setBackground(new java.awt.Color(157, 157, 255));
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.setCellRenderer(new citalyser.ui.visualization.panels.regulardisplaypanel.sidebarpanel.authorlistpanel.AuthorListCellRenderer());
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (jList1.getModel().getElementAt(jList1.locationToIndex(evt.getPoint())).isSelected()) {
            jList1.getModel().getElementAt(jList1.locationToIndex(evt.getPoint())).setSelected(false);
            displayMaster.displayStatusMessage("Unticked : " + jList1.getModel().getElementAt(jList1.locationToIndex(evt.getPoint())).getText());
            ArrayList<Integer> rows = paperCollection.getAuthorPos(jList1.getModel().getElementAt(jList1.locationToIndex(evt.getPoint())).getText());
            displayMaster.getMainFrame().getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().getTableDisplayPanel().getPaperTableDisplayPanel().filterTableDeselect(rows);
        } else {
            jList1.getModel().getElementAt(jList1.locationToIndex(evt.getPoint())).setSelected(true);
            displayMaster.displayStatusMessage("Ticked : " + jList1.getModel().getElementAt(jList1.locationToIndex(evt.getPoint())).getText());
            ArrayList<Integer> rows = paperCollection.getAuthorPos(jList1.getModel().getElementAt(jList1.locationToIndex(evt.getPoint())).getText());
            displayMaster.getMainFrame().getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().getTableDisplayPanel().getPaperTableDisplayPanel().filterTableSelect(rows);
            for (int i : rows) {
                //rowNumbers.set(i, rowNumbers.get(i) + 1);
            }
        }
        jList1.repaint();
    }//GEN-LAST:event_jList1MouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<javax.swing.JCheckBox> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void displayAuthors(QueryResult result) {
        if (result == null) {
            return;
        }
        PaperCollection paperCollection = result.getPaperCollection();
        if (paperCollection != null) {
            setList(paperCollection.extractAuthors());
            this.paperCollection = paperCollection;
            for (int i = 0; i < paperCollection.getPapers().size(); i++) {
                rowNumbers.add(i, 0);
            }
            displayMaster.showArticleSearch(false);
        }
    }

    public void clear() {
        try {
            DefaultListModel tm = (DefaultListModel) jList1.getModel();
            tm.removeAllElements();
            paperCollection.removePaper();
        } catch (Exception e) {
        }

    }
}
