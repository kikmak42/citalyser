/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/

package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.listdisplaypanel;

import citalyser.model.Paper;
import citalyser.ui.control.DisplayMaster;
import citalyser.ui.utils.UiUtils;
import java.awt.Cursor;
import org.apache.log4j.Logger;

public class PaperDetailsPanel extends javax.swing.JPanel {
    
    static Logger logger = Logger.getLogger(PaperDetailsPanel.class);
    /**
     * Creates new form PaperDetailsPanel
     */
    private DisplayMaster displayMaster;
    private Paper paper;
    public PaperDetailsPanel(Paper p) {
        this.paper = p;
        initComponents();
        this.viewCitationsLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.paperTitleLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.viewCitationsLbl.setText("<html><a>View Citations</a></html>");
        this.paperAbstractLbl.setText("");
        this.paperCitedByLbl.setText("");
        this.viewCitationsLbl.setVisible(false);
    }
    
    public void render()
    {
        this.paperTitleLbl.setText("<html>"+paper.getTitle()+"</html>");
        this.paperinfolbl.setText("<html>"+paper.getInfo()+"<html>");
        if(paper.getAbstract() != null)
            this.paperAbstractLbl.setText("<html>"+paper.getAbstract()+"<html>");
        if(!paper.getIsFromMetric())
            this.paperCitedByLbl.setText("Cited By : " + paper.getNumCites());
        
        if(paper.getNumCites() > 0 && !paper.getIsFromMetric())
        {
            this.viewCitationsLbl.setVisible(true);
        }
    }
    public void setDisplayMaster(DisplayMaster displayMaster)
    {
        this.displayMaster = displayMaster;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewCitationsLbl = new org.jdesktop.swingx.JXLabel();
        paperTitleLbl = new javax.swing.JLabel();
        paperinfolbl = new javax.swing.JLabel();
        paperAbstractLbl = new javax.swing.JLabel();
        paperCitedByLbl = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        viewCitationsLbl.setForeground(new java.awt.Color(51, 0, 255));
        viewCitationsLbl.setText("View Citations");
        viewCitationsLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        viewCitationsLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewCitationsLblMouseClicked(evt);
            }
        });

        paperTitleLbl.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        paperTitleLbl.setForeground(new java.awt.Color(0, 0, 255));
        paperTitleLbl.setText("jLabel1");
        paperTitleLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paperTitleLblMouseClicked(evt);
            }
        });

        paperinfolbl.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        paperinfolbl.setForeground(new java.awt.Color(0, 153, 51));
        paperinfolbl.setText("jLabel1");

        paperAbstractLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        paperAbstractLbl.setText("jLabel1");

        paperCitedByLbl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        paperCitedByLbl.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(paperAbstractLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paperinfolbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paperTitleLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(paperCitedByLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addComponent(viewCitationsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paperTitleLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paperinfolbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paperAbstractLbl)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewCitationsLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paperCitedByLbl))
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void viewCitationsLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewCitationsLblMouseClicked
        displayMaster.getNavigationMaster().citationListClicked(paper);
    }//GEN-LAST:event_viewCitationsLblMouseClicked

    private void paperTitleLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paperTitleLblMouseClicked
        // TODO add your handling code here:
        logger.debug("Paper Url : " + paper.getUrl());
        if(paper.getUrl()!=null)
            UiUtils.openInBrowser(paper.getUrl());
        else
            displayMaster.displayStatusMessage("We do not have a link for this Paper.");
    }//GEN-LAST:event_paperTitleLblMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel paperAbstractLbl;
    private javax.swing.JLabel paperCitedByLbl;
    private javax.swing.JLabel paperTitleLbl;
    private javax.swing.JLabel paperinfolbl;
    private org.jdesktop.swingx.JXLabel viewCitationsLbl;
    // End of variables declaration//GEN-END:variables
}
