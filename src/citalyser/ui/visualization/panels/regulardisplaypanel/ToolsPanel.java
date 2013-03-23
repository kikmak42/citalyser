/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ToolsPanel.java
 *
 * Created on Mar 9, 2013, 5:49:46 AM
 */
package citalyser.ui.visualization.panels.regulardisplaypanel;

import citalyser.ui.control.DisplayMaster;
import citalyser.ui.control.masters.SearchMaster;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class ToolsPanel extends javax.swing.JPanel {

    private static Logger logger = Logger.getLogger(SearchMaster.class.getName());
    /** Creates new form ToolsPanel */
    public ToolsPanel() {
        initComponents();
    }
    
    public int getNumResults(){
        return Integer.parseInt(numResults.getText());
    }
    
    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
    }
    
    public void displayStatusMessage(String status) {
        if (status != null) {
            if (status.length() == 0) {
                status = " ";
            }
            jLabel1.setText(status);
        }
    }

    public void displayErrorMessage(String message) {
        if (message != null) {
            if (message.length() == 0) {
                message = " ";
            }
            jLabel1.setText(message);
        }
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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        numResults = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(50, 93, 167));

        jButton1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Button1");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);

        jButton2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Button2");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText(" ");

        numResults.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        numResults.setText("10");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Number of Results");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 554, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numResults, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(numResults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        numResults.getDocument().addDocumentListener( new DocumentListener()
            {
                public void changedUpdate(DocumentEvent e) { textChanged(e); }
                public void insertUpdate(DocumentEvent e) { textChanged(e); }
                public void removeUpdate(DocumentEvent e) { textChanged(e); }
                private void textChanged(DocumentEvent e)
                {
                    try {
                        displayMaster.setNumberOfResults(Integer.parseInt(numResults.getText()));
                        displayMaster.displayStatusMessage(" ");
                    } catch (NumberFormatException ex) {
                        displayMaster.setNumberOfResults(100);
                        displayMaster.displayStatusMessage("Please enter a valid integer");
                    }
                }
            } );
        }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField numResults;
    // End of variables declaration//GEN-END:variables
}
