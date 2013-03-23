/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TableDisplayPanel.java
 *
 * Created on Mar 15, 2013, 1:44:48 AM
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules;

import citalyser.model.Journal;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.ui.control.DisplayMaster;
import citalyser.util.CommonUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class TableDisplayPanel extends javax.swing.JPanel {
    
    private static Logger logger = Logger.getLogger(DisplayMaster.class.getName());
    /** Creates new form TableDisplayPanel */
    public TableDisplayPanel() {
        initComponents();
    }

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
    }

    public DisplayMaster getDisplayMaster() {
        return displayMaster;
    }

    public void setTable(PaperCollection paperCollection, TableModel tm) {

        for (int i = 0; i < tm.getRowCount(); i++) {
            Vector row = ((Vector) (((DefaultTableModel) tm).getDataVector().elementAt(i)));
            if (row != null) {
                if (this.paperCollection != null) {
                    row.set(0, new Integer(this.paperCollection.getPapers().size() + (Integer) row.elementAt(0)));
                }
            }
            ((DefaultTableModel) jTable1.getModel()).addRow(row);
        }
        tableModel = jTable1.getModel();

        if (this.paperCollection != null) {
            for (Paper paper : paperCollection.getPapers()) {
                this.paperCollection.addPaper(paper);
            }
        } else {
            this.paperCollection = paperCollection;
        }
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(250);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(32);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(65);
        jTable1.repaint();
        displayMaster.renderJournal(displayMaster.getMainFrame().getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), this.paperCollection);
    }

    public void setJournalMetricsTable(ArrayList<Journal> journal, TableModel tm) {
        tableModel = tm;
        jTable1.setModel(tm);
        jTable1.repaint();
        journals = journal;
    }
    private DisplayMaster displayMaster;
    private PaperCollection paperCollection;
    private ArrayList<Journal> journals;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTable1.setForeground(new java.awt.Color(51, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No.", "Title", "Year", "#Citations", "Authors", "Journals"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(204, 204, 255));
        jTable1.setIntercellSpacing(new java.awt.Dimension(4, 5));
        jTable1.setRowHeight(25);
        jTable1.setSelectionBackground(new java.awt.Color(50, 93, 167));
        jTable1.setShowVerticalLines(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText("Export To CSV");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(this);
        //System.out.println("chooser:"+chooser.getSelectedFile().getName());
        try {
            File results = chooser.getSelectedFile();
            CommonUtils.exportToCsv(tableModel, results);
        } catch (NullPointerException npe) {
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
//<<<<<<< HEAD
         logger.info("jModel1MouseClicked"+jTable1.rowAtPoint(evt.getPoint()));
        if(journals !=null){
            displayMaster.tableClicked(journals.get(jTable1.rowAtPoint(evt.getPoint())));
            journals=null;
        }else{ 
  //      displayMaster.tableClicked(paperCollection.getPapers().get(jTable1.rowAtPoint(evt.getPoint())));
//=======
        if (jTable1.rowAtPoint(evt.getPoint()) > -1) {
            displayMaster.tableClicked(paperCollection.getPapers().get(jTable1.rowAtPoint(evt.getPoint())));
//>>>>>>> e41abd1eaec0c7db901f6fe0228289422dd7e98c
        }}
    }//GEN-LAST:event_jTable1MouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    private TableModel tableModel;

    public void clear() {
        while (jTable1.getModel().getRowCount() > 0) {
            ((DefaultTableModel) jTable1.getModel()).removeRow(0);
        }
        tableModel = jTable1.getModel();
    }
}
