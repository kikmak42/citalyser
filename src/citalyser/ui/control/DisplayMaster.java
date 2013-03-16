/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control;

import citalyser.Main;
import citalyser.model.Author;
import citalyser.model.PaperCollection;
import citalyser.ui.control.masters.SearchMaster;
import citalyser.ui.control.masters.SettingsMaster;
import citalyser.ui.control.switchers.QueryResultRenderingHandler;
import citalyser.ui.model.TableModelCreator;
import citalyser.util.CProxy;

import citalyser.ui.visualization.MainFrame;
import citalyser.ui.visualization.panels.ExtraPanel;
import citalyser.ui.visualization.panels.common.SearchPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.contentsdisplaypanel.griddisplaypanel.GridEntityPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
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
    private SearchMaster searchMaster;
    
    private QueryResultRenderingHandler queryResultRenderingHandler;
    
    private static Logger logger = Logger.getLogger(DisplayMaster.class.getName());

    public DisplayMaster() {
        mainFrame = new MainFrame();
        mainFrame.setDisplayMaster(this);
        mainFrame.setVisible(true);
        extraPanel = new ExtraPanel();
        extraPanel.setDisplayMaster(this);
        settingsMaster = new SettingsMaster(extraPanel);
        searchMaster = new SearchMaster(this);
        settingsDialog = new javax.swing.JDialog(mainFrame);
        settingsDialog.setUndecorated(true);
        settingsDialog.setLocation(mainFrame.getRootPane().getX(), mainFrame.getRootPane().getY());
        settingsDialog.getContentPane().setLayout(new java.awt.BorderLayout());
        settingsDialog.getContentPane().add(extraPanel);
        settingsDialog.setPreferredSize(new java.awt.Dimension(1349, 692));
        settingsDialog.setBackground(new Color(0, 0, 0, 0));
        settingsDialog.pack();
        queryResultRenderingHandler = new QueryResultRenderingHandler(this);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public SettingsMaster getSettingsMaster() {
        return settingsMaster;
    }

    public QueryResultRenderingHandler getQueryResultRenderingHandler() {
        return queryResultRenderingHandler;
    }
    
    public void searchKeyPressed(SearchPanel searchPanel, char key) {
        searchMaster.searchKeyPressed(searchPanel, key);
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
        searchMaster.searchButtonClicked(searchPanel);
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

    public void renderPaperCollection(PaperCollection paperCollection) {
        mainFrame.getRegularDisplayPanel().getContentDisplayPanel().getTableDisplayPanel().setTable(TableModelCreator.getTableModel(paperCollection));
    }

    public void renderAuthorList(ArrayList<Author> arrayList) {
        if (arrayList != null) {
            for (Author author : arrayList) {
                mainFrame.getRegularDisplayPanel().getContentDisplayPanel().getGridDisplayPanel().addGridEntityPanel(new GridEntityPanel(author));
            }
            mainFrame.getRegularDisplayPanel().getContentDisplayPanel().flipToGridDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Author List");
        }
    }


}
