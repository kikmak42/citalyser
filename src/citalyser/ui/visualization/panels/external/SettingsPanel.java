/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SettingsPanel.java
 *
 * Created on Mar 11, 2013, 9:34:32 PM
 */
package citalyser.ui.visualization.panels.external;

import citalyser.ui.control.DisplayMaster;
import citalyser.ui.visualization.panels.external.settingspanel.ProxyListPanel;
import citalyser.ui.visualization.panels.external.settingspanel.ProxyPanel;
import java.awt.BorderLayout;

/**
 *
 * @author Tanmay Patil
 */
public class SettingsPanel extends javax.swing.JPanel {

    /** Creates new form SettingsPanel */
    public SettingsPanel() {
        initComponents();
        cachePanel = new citalyser.ui.visualization.panels.external.settingspanel.CachePanel();
        jPanel2.add(cachePanel, BorderLayout.CENTER);
    }

    public void flip() {
        ((java.awt.CardLayout) jPanel1.getLayout()).next(jPanel1);
    }

    public ProxyListPanel getProxyListPanel() {
        return proxyListPanel;
    }

    public ProxyPanel getProxyPanel() {
        return proxyPanel;
    }

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
        proxyListPanel.setDisplayMaster(displayMaster);
        proxyPanel.setDisplayMaster(displayMaster);
        cachePanel.setDisplayMaster(displayMaster);
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

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        proxyListPanel = new citalyser.ui.visualization.panels.external.settingspanel.ProxyListPanel();
        proxyPanel = new citalyser.ui.visualization.panels.external.settingspanel.ProxyPanel();
        jPanel2 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));

        jLabel1.setText("Settings");

        jPanel1.setLayout(new java.awt.CardLayout());
        jPanel1.add(proxyListPanel, "card2");
        jPanel1.add(proxyPanel, "card3");

        jTabbedPane1.addTab("Proxy", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Cache", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(248, Short.MAX_VALUE))
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private citalyser.ui.visualization.panels.external.settingspanel.CachePanel cachePanel;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private citalyser.ui.visualization.panels.external.settingspanel.ProxyListPanel proxyListPanel;
    private citalyser.ui.visualization.panels.external.settingspanel.ProxyPanel proxyPanel;
    // End of variables declaration//GEN-END:variables
}
