
/*
 * BasicSearchPanel.java
 *
 * Created on Mar 25, 2013, 9:59:08 PM
 */
package citalyser.ui.visualization.panels.common.searchpanel;

import citalyser.ui.control.DisplayMaster;
import citalyser.ui.visualization.panels.common.SearchPanel;
import java.awt.CardLayout;
import java.awt.Color;

/**
 *
 * @author Tanmay Patil
 */
public class BasicSearchPanel extends javax.swing.JPanel {

    /** Creates new form BasicSearchPanel */
    public BasicSearchPanel() {
        initComponents();
    }

    public void resetProgressBar()
    {
        jProgressBar1.setValue(0);
    }
    public void completeProgressBar()
    {
        jProgressBar1.setValue(jProgressBar1.getMaximum());
    }
    
    public void enableSearchBox(boolean value)
    {
        this.searchBox.setEnabled(value);
    }
    public void setSearchPanel(SearchPanel searchPanel) {
        this.searchPanel = searchPanel;
    }    

    public void requestSearchFieldFocus() {
        this.searchBox.requestFocus();
    }
    
    public void setSearchString(String searchString) {
        searchBox.setForeground(Color.BLACK);
        searchBox.setText(searchString);
    }

    public String getSearchString() {
        return searchBox.getText();
    }

    public void setButtonEnabled(boolean value) {
        if (value) {
            ((CardLayout) jPanel3.getLayout()).first(jPanel3);
            ((CardLayout) jPanel7.getLayout()).first(jPanel7);
        } else {
            ((CardLayout) jPanel3.getLayout()).last(jPanel3);
            ((CardLayout) jPanel7.getLayout()).last(jPanel7);
        }
    }
    
    public void updateProgressBar(int value) {
        if (value > -1 && value < 101) {
            jProgressBar1.setValue(value);
        }
    }

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
    }
    
    private boolean empty = true;
    private SearchPanel searchPanel;
    private DisplayMaster displayMaster;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchBox = new javax.swing.JTextField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        searchBox.setForeground(new java.awt.Color(102, 102, 102));
        searchBox.setText(" Enter Your Search Query Here");
        searchBox.setMargin(new java.awt.Insets(2, 8, 2, 2));
        searchBox.setPreferredSize(new java.awt.Dimension(500, 20));
        searchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBoxActionPerformed(evt);
            }
        });
        searchBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchBoxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchBoxFocusLost(evt);
            }
        });
        searchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchBoxKeyPressed(evt);
            }
        });
        add(searchBox);
        add(filler2);

        jPanel7.setLayout(new java.awt.CardLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/citalyser/ui/visualization/resources/SearchButtonIcon.PNG"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1, "card3");

        jProgressBar1.setMaximumSize(new java.awt.Dimension(32767, 31));
        jProgressBar1.setPreferredSize(new java.awt.Dimension(77, 31));
        jPanel7.add(jProgressBar1, "card2");

        add(jPanel7);
        add(filler1);

        jPanel3.setLayout(new java.awt.CardLayout());
        jPanel3.add(jLabel1, "card3");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/citalyser/ui/visualization/resources/cancel.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, "card2");

        jButton2.setVisible(false);
        add(jPanel3);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        displayMaster.getSearchMaster().searchButtonClicked(searchPanel);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBoxFocusGained
        if (empty) {
            searchBox.setText("");
        } else {
            searchBox.setSelectionStart(0);
            searchBox.setSelectionEnd(searchBox.getText().length());
        }
        searchBox.setForeground(Color.BLACK);
    }//GEN-LAST:event_searchBoxFocusGained

    private void searchBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBoxFocusLost
        if (searchBox.getText().trim().isEmpty()) {
            searchBox.setForeground(new Color(102, 102, 102));
            searchBox.setText(" Enter Your Search Query Here");
            empty = true;
        } else {
            empty = false;
        }
    }//GEN-LAST:event_searchBoxFocusLost

    private void searchBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBoxKeyPressed
        displayMaster.getSearchMaster().searchKeyPressed(searchPanel, evt.getKeyChar());
    }//GEN-LAST:event_searchBoxKeyPressed

    private void searchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBoxActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        displayMaster.getNavigationMaster().cancelButtonClicked();
        searchPanel.updateOnQueryComplete();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField searchBox;
    // End of variables declaration//GEN-END:variables
}
