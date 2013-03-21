/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control;

import citalyser.Main;
import citalyser.model.Author;
import citalyser.model.Journal;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryResult;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.AuthorResult;
import citalyser.ui.control.masters.SearchMaster;
import citalyser.ui.control.masters.SettingsMaster;
import citalyser.ui.control.switchers.QueryResultRenderingHandler;
import citalyser.ui.model.ContentRenderer;
import citalyser.ui.model.ListModelHandler;
import citalyser.ui.model.TableModelHandler;
import citalyser.util.CProxy;

import citalyser.ui.visualization.MainFrame;
import citalyser.ui.visualization.panels.ExternalPanel;
import citalyser.ui.visualization.panels.common.SearchPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.griddisplaypanel.GridEntityPanel;
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
    private ExternalPanel extraPanel;
    private SettingsMaster settingsMaster;
    private SearchMaster searchMaster;
    private QueryResultRenderingHandler queryResultRenderingHandler;
    private static Logger logger = Logger.getLogger(DisplayMaster.class.getName());

    public DisplayMaster() {
        mainFrame = new MainFrame();
        mainFrame.setDisplayMaster(this);
        mainFrame.setVisible(true);
        extraPanel = new ExternalPanel();
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
        searchMaster.addAutoCompleteSuggestions(suggestions);
    }

    public void addAutoCompleteSuggestion(String suggestion) {
        searchMaster.addAutoCompleteSuggestion(suggestion);
    }

    public void emptyAutoCompleteSuggestions() {
        searchMaster.emptyAutoCompleteSuggestions();
    }

    public boolean checkAuthorMode() {
        return mainFrame.getRegularDisplayPanel().getHeaderPanel().isAuthorSearchMode();
    }

    public void tableClicked(Paper paper) {
        final Paper myPaper = paper;
        new Thread() {

            @Override
            public void run() {
                Query q =new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(myPaper.getcitedByUrl()).build();
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) {
                    PaperCollection pc = (PaperCollection) queryResult.getContents();
                    if (myPaper != null) {
                        renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), pc.getPapers());
                    }
                } else {
                    Main.getDisplayController().displayErrorMessage("Null QueryResult on Tableclicked...");
                }
            }
            
        }.start();
    }

    public void authorGridEntityClicked(String id) {

        final String myId = id;

        new Thread() {
            @Override
            public void run() {
                Query q = new Query.Builder("").flag(QueryType.AUTH_PROF).ID(myId).build();
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult instanceof AuthorResult)
                {
                    queryResultRenderingHandler.render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(),queryResult);
                    renderProfile(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(),(Author)queryResult.getContents());
                }
                else
                {
                    Main.getDisplayController().displayErrorMessage("Unknown Error while fetching Author Details.");
                }
            }
        }.start();
    }

    //*****************************************************************************//
    //**************************** Rendering Functions ****************************//
    //*****************************************************************************//
    public void render(ContentRenderer contentRenderer, ArrayList<Author> arrayList) {
        if (arrayList != null) {
            contentRenderer.getGridDisplayPanel().clear();
            for (Author author : arrayList) {
                contentRenderer.getGridDisplayPanel().addGridEntityPanel(new GridEntityPanel(author));
            }
            contentRenderer.flipToGridDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Author List");
        }
    }

    public void renderJournalMatrics(ContentRenderer contentRenderer, ArrayList<Journal> arrayList) {
        if (arrayList != null) {
            contentRenderer.getTableDisplayPanel().setJournalMetricsTable(arrayList, TableModelHandler.getTableModel(arrayList));
            contentRenderer.flipToTableDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Journal List");
        }
    }
    public void render(ContentRenderer contentRenderer, Author author) {
        if (author != null) {
            contentRenderer.getTableDisplayPanel().setTable(author.getPaperCollection(), TableModelHandler.getTableModel(author.getPaperCollection()));
            contentRenderer.flipToTableDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Author");
        }
    }

    public void render(ContentRenderer contentRenderer, PaperCollection paperCollection) {
        if (paperCollection != null) {
            contentRenderer.getTableDisplayPanel().setTable(paperCollection, TableModelHandler.getTableModel(paperCollection));
            contentRenderer.flipToTableDisplayPanel();
        } else {
            //TODO: Need to call api back
            Main.getDisplayController().displayErrorMessage("Null Paper Collection");
        }
    }

    public void renderCitationsList(ContentRenderer contentRenderer, ArrayList<Paper> papers) {
        if (papers != null) {
            contentRenderer.getListDisplayPanel().setList(ListModelHandler.getListModel(papers));
            contentRenderer.flipToListDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Citations List");
        }
    }
    
    public void renderProfile(ContentRenderer contentRenderer, Author author) {
        if (author != null) {
            contentRenderer.getProfileDisplayPanel().displayAuthorProfile(author);
            contentRenderer.flipToProfileDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Author");
        }
    }

    public void citationListClicked() {
        System.out.println("Citation Clicked");
    }
}
