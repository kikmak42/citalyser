/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control;




import citalyser.queryhandler.Query;
import citalyser.queryhandler.QueryHandler;
import citalyser.ui.control.masters.SettingsMaster;
import citalyser.util.CProxy;

import citalyser.ui.util.TableModelCreator;
import citalyser.ui.visualization.MainFrame;
import citalyser.ui.visualization.panels.ExtraPanel;
import citalyser.ui.visualization.panels.common.SearchPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class DisplayMaster {
    private MainFrame mainFrame;
    private javax.swing.JDialog settingsDialog;
    private ExtraPanel extraPanel;
    
    private SettingsMaster settingsMaster;
    
    private static Logger logger = Logger.getLogger(DisplayMaster.class.getName());

    public DisplayMaster() {
        mainFrame = new MainFrame();
        mainFrame.setDisplayMaster(this);
        mainFrame.setVisible(true);
        extraPanel = new ExtraPanel();
        extraPanel.setDisplayMaster(this);
        settingsMaster = new SettingsMaster(extraPanel);
        settingsDialog = new javax.swing.JDialog(mainFrame);
        settingsDialog.setUndecorated(true);
        settingsDialog.setLocation(mainFrame.getRootPane().getX(), mainFrame.getRootPane().getY());
        settingsDialog.getContentPane().setLayout(new java.awt.BorderLayout());
        settingsDialog.getContentPane().add(extraPanel);
        settingsDialog.setPreferredSize(new java.awt.Dimension(1349, 692));
        settingsDialog.setBackground(new Color(0, 0, 0, 0));
        settingsDialog.pack();
    }

    public SettingsMaster getSettingsMaster() {
        return settingsMaster;
    }
    
    public void searchKeyPressed(SearchPanel searchPanel, char key) {
        if (searchPanel.equals(mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel())) {
            //TODO: Autocomplete
        } else {
            if (searchPanel.equals(mainFrame.getStartPanel().getAuthorSearchPanel())) {
                ((java.awt.CardLayout) mainFrame.getContentPane().getLayout()).last(mainFrame.getContentPane());
                mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().requestSearchFieldFocus();
                mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().setSearchString(key + "");
                mainFrame.getRegularDisplayPanel().getHeaderPanel().setAuthorSearchMode(true);
            } else {
                if (searchPanel.equals(mainFrame.getStartPanel().getJournalSearchPanel())) {
                    ((java.awt.CardLayout) mainFrame.getContentPane().getLayout()).last(mainFrame.getContentPane());
                    mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().requestSearchFieldFocus();
                    mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().setSearchString(key + "");
                    mainFrame.getRegularDisplayPanel().getHeaderPanel().setAuthorSearchMode(false);
                } else {
                    logger.info("Invalid key event : " + key);
                }
            }
        }
    }

    public void settingsButtonPressed() {
        settingsDialog.setVisible(true);
    }
    
    public void mainFrameChanged(int x, int y, int width, int height) {
        if (settingsDialog != null) {
            settingsDialog.setLocation(x, y);
            settingsDialog.setPreferredSize(new Dimension(width, height));
            settingsDialog.pack();
        }
    }

    public void authorModeClicked() {
        ((java.awt.CardLayout) mainFrame.getContentPane().getLayout()).last(mainFrame.getContentPane());
        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().requestSearchFieldFocus();
        mainFrame.getRegularDisplayPanel().getHeaderPanel().setAuthorSearchMode(true);
    }

    public void journalModeClicked() {
        ((java.awt.CardLayout) mainFrame.getContentPane().getLayout()).last(mainFrame.getContentPane());
        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().requestSearchFieldFocus();
        mainFrame.getRegularDisplayPanel().getHeaderPanel().setAuthorSearchMode(false);
    }

    public void searchButtonClicked(SearchPanel searchPanel) {
        if (searchPanel.equals(mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel())) {
            
            //mainFrame.getRegularDisplayPanel().getContentDisplayPanel().getTableDisplayPanel().setTable(TableModelCreator.getTableModel(QueryHandler.getDetails(null)));
        } else {
            if (searchPanel.equals(mainFrame.getStartPanel().getAuthorSearchPanel())) {
                ((java.awt.CardLayout) mainFrame.getContentPane().getLayout()).last(mainFrame.getContentPane());
                mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().requestSearchFieldFocus();
                mainFrame.getRegularDisplayPanel().getHeaderPanel().setAuthorSearchMode(true);
            } else {
                if (searchPanel.equals(mainFrame.getStartPanel().getJournalSearchPanel())) {
                    ((java.awt.CardLayout) mainFrame.getContentPane().getLayout()).last(mainFrame.getContentPane());
                    mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().requestSearchFieldFocus();
                    mainFrame.getRegularDisplayPanel().getHeaderPanel().setAuthorSearchMode(false);
                } else {
                    logger.info("Invalid search clicked event");
                }
            }
        }
    }
    
    public void settingsSaveAndClose() {
        settingsDialog.setVisible(false);
    }

    public void openAddNewProxyWindow() {
        settingsMaster.openAddNewProxyWindow();
    }

    public void cancelNewProxyButtonClicked() {
        settingsMaster.cancelNewProxyButtonClicked();
    }

    public void removeSelectedProxyEntry() {
        settingsMaster.removeSelectedProxyEntry();
    }

    public void openEditExistingProxyWindow() {
        settingsMaster.openEditExistingProxyWindow();
    }

    public void singleProxySettingsConfirmed(CProxy proxy) {
        settingsMaster.singleProxySettingsConfirmed(proxy);
    }

    public void displayErrorMessage(String message) {
        javax.swing.JOptionPane.showMessageDialog(mainFrame, message, "Error", javax.swing.JOptionPane.PLAIN_MESSAGE);
    }

    public void addAutoCompleteSuggestions(Vector<String> suggestions) {
        if (mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().isVisible()) {
            if (suggestions == null) {
                emptyAutoCompleteSuggestions();
            } else {
                for (String suggestion : suggestions) {
                    mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().getAutoCompleteSuggestions().add(suggestion);
                }
            }
        }
    }
    
    public void addAutoCompleteSuggestion(String suggestion) {
        if (mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().isVisible()) {
            if (suggestion != null) {
                mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().getAutoCompleteSuggestions().add(suggestion);
            }
        }
    }

    public void emptyAutoCompleteSuggestions() {
        if (mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().isVisible()) {
            mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().getAutoCompleteSuggestions().removeAllElements();
        }
    }

    public Query createQuery(SearchPanel searchPanel) {
        return null;
    }


}
