/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control.masters;

import citalyser.ui.visualization.panels.ExternalPanel;
import citalyser.util.CProxy;

/**
 *
 * @author Tanmay Patil
 */
public class SettingsMaster {
    
    private ExternalPanel extraPanel;

    public SettingsMaster(ExternalPanel extraPanel) {
        this.extraPanel = extraPanel;
    }

    public void openAddNewProxyWindow() {
        extraPanel.getSettingsPanel().flip();
        extraPanel.getSettingsPanel().getProxyPanel().setEditMode(false);
    }

    public void cancelNewProxyButtonClicked() {
        extraPanel.getSettingsPanel().flip();
    }

    public void removeSelectedProxyEntry() {
        if (extraPanel.getSettingsPanel().getProxyListPanel().getProxyTable().getSelectedRow() != -1) {
            extraPanel.getSettingsPanel().getProxyListPanel().getProxyList().remove(extraPanel.getSettingsPanel().getProxyListPanel().getProxyTable().getSelectedRow());
            ((javax.swing.table.DefaultTableModel) extraPanel.getSettingsPanel().getProxyListPanel().getProxyTable().getModel()).removeRow(extraPanel.getSettingsPanel().getProxyListPanel().getProxyTable().getSelectedRow());
            citalyser.util.Config.setProxyList(extraPanel.getSettingsPanel().getProxyListPanel().getProxyList());
        }
    }

    public void openEditExistingProxyWindow() {
        extraPanel.getSettingsPanel().flip();
        extraPanel.getSettingsPanel().getProxyPanel().setPreviousProxyData(extraPanel.getSettingsPanel().getProxyListPanel().getProxyList().remove(extraPanel.getSettingsPanel().getProxyListPanel().getProxyTable().getSelectedRow()));
        ((javax.swing.table.DefaultTableModel) extraPanel.getSettingsPanel().getProxyListPanel().getProxyTable().getModel()).removeRow(extraPanel.getSettingsPanel().getProxyListPanel().getProxyTable().getSelectedRow());
        citalyser.util.Config.setProxyList(extraPanel.getSettingsPanel().getProxyListPanel().getProxyList());
        extraPanel.getSettingsPanel().getProxyPanel().setEditMode(true);
    }

    public void singleProxySettingsConfirmed(CProxy proxy) {
        if (proxy == null) {
            //TODO: System, Autodetect and No Proxy
            return;
        }
        extraPanel.getSettingsPanel().getProxyListPanel().getProxyList().add(proxy);
        String[] rowData = new String[3];
        rowData[0] = proxy.getHostName();
        rowData[1] = Integer.toString(proxy.getPort());
        rowData[2] = proxy.getUsername();
        ((javax.swing.table.DefaultTableModel) extraPanel.getSettingsPanel().getProxyListPanel().getProxyTable().getModel()).addRow(rowData);
        citalyser.util.Config.setProxyList(extraPanel.getSettingsPanel().getProxyListPanel().getProxyList());
        extraPanel.getSettingsPanel().flip();
        extraPanel.getSettingsPanel().getProxyPanel().setPreviousProxyData(new CProxy("", 0, "", ""));
    }
    
}
