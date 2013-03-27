/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TableDisplayPanel.java
 *
 * Created on Mar 15, 2013, 1:44:48 AM
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.tabledisplaypanel;

import citalyser.Main;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.model.query.Query;
import citalyser.ui.control.DisplayMaster;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.TableDisplayPanel;
import citalyser.util.CommonUtils;
import java.awt.Point;
import java.io.File;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Tanmay Patil
 */
public class PaperTableFromMetricDisplayPanel extends javax.swing.JPanel implements TableDisplayPanelInterface {

    private static Logger logger = Logger.getLogger(PaperTableFromMetricDisplayPanel.class.getName());
    Query lastQuery;
    private int numResults;
    
    /** Creates new form TableDisplayPanel */
    public PaperTableFromMetricDisplayPanel() {
        initComponents();
    }

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
    }

    public DisplayMaster getDisplayMaster() {
        return displayMaster;
    }

    public void setTable(Query q,PaperCollection paperCollection, TableModel tm) {

       numResults+=tm.getRowCount();
        this.lastQuery = q;
        q.start_result+=tm.getRowCount();
        showNormalMoreButton();
        if(tm.getRowCount() < q.num_results)
            hideMoreButton();
        else
            this.showMoreButton();
        
        if (jTable1.getModel().getRowCount() == 0) {
            disabledRow = -1;
            jTable1.setModel(tm);
        } else {
            for (int i = 0; i < tm.getRowCount(); i++) {
                Vector row = ((Vector) (((DefaultTableModel) tm).getDataVector().elementAt(i)));
                if (this.paperCollection != null) {
                    row.set(0, new Integer(this.paperCollection.getPapers().size() + (Integer) row.elementAt(0)));
                }
                ((DefaultTableModel) jTable1.getModel()).addRow(row);
            }
        }

        if (this.paperCollection != null) {
            for (Paper paper : paperCollection.getPapers()) {
                this.paperCollection.addPaper(paper);
            }
        } else {
            this.paperCollection = paperCollection;
        }
        jTable1.getColumnModel().getColumn(0).setMaxWidth(33);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(250);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(32);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(65);
        jTable1.repaint();
       // displayMaster.renderGeneralProfile(displayMaster.getMainFrame().getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), this.paperCollection);
    }
    
    private DisplayMaster displayMaster;
    private PaperCollection paperCollection;
    private int disabledRow, previousRow = -1;

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
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        moreButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTable1.setForeground(new java.awt.Color(51, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No.", "Title", "Year", "#Citations", "Authors", "Journals", "Link"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTable1MouseExited(evt);
            }
        });
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTable1MouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Export To CSV");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, java.awt.BorderLayout.CENTER);

        moreButton.setText("More Results");
        moreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreButtonActionPerformed(evt);
            }
        });
        jPanel1.add(moreButton, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.removeChoosableFileFilter(chooser.getFileFilter());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv files (*.csv)", "csv");
        chooser.setFileFilter(filter);
        chooser.showSaveDialog(this);
        //System.out.println("chooser:"+chooser.getSelectedFile().getName());
        try {
            File results = chooser.getSelectedFile();
            if(!results.getAbsolutePath().endsWith(".csv")) {
                results = new File(chooser.getSelectedFile()+".csv");
            }
            CommonUtils.exportToCsv(jTable1.getModel(), results);
        } catch (Exception e) {
            logger.info("Error in CSV file chooser PaperTableFromMetricDisplayPanel : "+e);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (jTable1.rowAtPoint(evt.getPoint()) > -1) {
            disabledRow = jTable1.rowAtPoint(evt.getPoint());
            Paper clickedPaper = paperCollection.getPapers().get(jTable1.rowAtPoint(evt.getPoint()));
            if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                if (jTable1.rowAtPoint(evt.getPoint()) != disabledRow) {
                    if(clickedPaper.getNumCites() > 0) {
                        displayMaster.getNavigationMaster().metricTableClicked(clickedPaper);
                    } else {
                       displayMaster.displayStatusMessage("Citation Count is 0 for this paper  :" + clickedPaper.getTitle());
                    }
                }
            } else {
                ((TableDisplayPanel) ((JPanel) ((JPanel) this.getParent()).getParent())).setPopUpLocation(evt.getPoint());
                ((TableDisplayPanel) ((JPanel) ((JPanel) this.getParent()).getParent())).setSelectedPaper(this, clickedPaper);
                ((TableDisplayPanel) ((JPanel) ((JPanel) this.getParent()).getParent())).getTableRightClickedPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered
        previousRow = jTable1.rowAtPoint(evt.getPoint());
        if (previousRow > -1) {
            displayMaster.showPaperInfo(paperCollection.getPapers().get(previousRow), new Point(evt.getLocationOnScreen().x + 10, evt.getLocationOnScreen().y + 10));
        }
    }//GEN-LAST:event_jTable1MouseEntered

    private void jTable1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseExited
        displayMaster.hidePaperInfo();
        previousRow = -1;
    }//GEN-LAST:event_jTable1MouseExited

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved
        //displayMaster.movePaperInfoTo(new Point(evt.getLocationOnScreen().x + 5, evt.getLocationOnScreen().y + 5));
        int currentRow = jTable1.rowAtPoint(evt.getPoint());
        if (currentRow > -1) {
            if (currentRow != previousRow) {
                displayMaster.showPaperInfo(paperCollection.getPapers().get(jTable1.rowAtPoint(evt.getPoint())), new Point(evt.getLocationOnScreen().x + 10, evt.getLocationOnScreen().y + 10));
            } else {
                displayMaster.movePaperInfoTo(new Point(evt.getLocationOnScreen().x + 10, evt.getLocationOnScreen().y + 10));
            }
            previousRow = currentRow;
        }
    }//GEN-LAST:event_jTable1MouseMoved

    private void moreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreButtonActionPerformed
        showLoadingMoreButton();
        displayMaster.getNavigationMaster().metricPaperTableMoreButtonClicked(this.paperCollection,lastQuery,moreButton);
    }//GEN-LAST:event_moreButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton moreButton;
    // End of variables declaration//GEN-END:variables

    public void clear() {
        numResults = 0;
        paperCollection = null;
        while (jTable1.getModel().getRowCount() > 0) {
            ((DefaultTableModel) jTable1.getModel()).removeRow(0);
        }
    }

    @Override
    public void callLeftClickedEvent(Point point) {
        if (jTable1.rowAtPoint(point) > -1) {
            Paper clickedPaper = paperCollection.getPapers().get(jTable1.rowAtPoint(point));
            if(clickedPaper.getNumCites() > 0) {
                displayMaster.getNavigationMaster().metricTableClicked(clickedPaper);
            } else {
               displayMaster.displayStatusMessage("Citation Count is 0 for this paper  :" + clickedPaper.getTitle());
            }
        }
    }
    
    public void showMoreButton() {
        moreButton.setVisible(true);
    }

    public void hideMoreButton() {
        moreButton.setVisible(false);
    }

    public void showLoadingMoreButton()
    {
        moreButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/citalyser/ui/visualization/resources/ajax-loader.gif")));
        moreButton.setText("");
    }
    public void showNormalMoreButton()
    {
        moreButton.setText("More");
        moreButton.setIcon(null);
    }

}
