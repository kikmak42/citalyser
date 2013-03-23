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
import citalyser.ui.control.masters.RenderMaster;
import citalyser.ui.control.masters.SearchMaster;
import citalyser.ui.control.masters.SettingsMaster;
import citalyser.ui.control.switchers.QueryResultRenderingHandler;
import citalyser.ui.model.CitationListHistory;
import citalyser.ui.model.ContentRenderer;
import citalyser.util.CProxy;

import citalyser.ui.visualization.MainFrame;
import citalyser.ui.visualization.panels.ExternalPanel;
import citalyser.ui.visualization.panels.common.SearchPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
    private RenderMaster renderMaster;
    private QueryResultRenderingHandler queryResultRenderingHandler;
    private static Logger logger = Logger.getLogger(DisplayMaster.class.getName());
    private CitationListHistory citationListHistory;
    private int numberOfResults = 100;
    private Vector<Thread> threads = new Vector<>();

    public DisplayMaster() {
        mainFrame = new MainFrame();
        mainFrame.setDisplayMaster(this);
        mainFrame.setVisible(true);
        extraPanel = new ExternalPanel();
        extraPanel.setDisplayMaster(this);
        settingsMaster = new SettingsMaster(extraPanel);
        searchMaster = new SearchMaster(this);
        renderMaster = new RenderMaster();
        settingsDialog = new javax.swing.JDialog(mainFrame);
        settingsDialog.setUndecorated(true);
        settingsDialog.setLocation(mainFrame.getRootPane().getX(), mainFrame.getRootPane().getY());
        settingsDialog.getContentPane().setLayout(new java.awt.BorderLayout());
        settingsDialog.getContentPane().add(extraPanel);
        settingsDialog.setPreferredSize(new java.awt.Dimension(1349, 692));
        settingsDialog.setBackground(new Color(0, 0, 0, 0));
        settingsDialog.pack();
        queryResultRenderingHandler = new QueryResultRenderingHandler(this);
        citationListHistory = new CitationListHistory();
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

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    public int getNumberOfResults() {
        return numberOfResults;
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
        //mainFrame.getRegularDisplayPanel().getToolsPanel().displayErrorMessage(message);
    }

    public void displayStatusMessage(String status) {
        mainFrame.getRegularDisplayPanel().getToolsPanel().displayStatusMessage(status);
    }

    public void clearStatusPanel() {
        mainFrame.getRegularDisplayPanel().getStatusDisplayPanel().displayStatus("");
        mainFrame.getRegularDisplayPanel().getStatusDisplayPanel().displayError("");
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

    public void cancelButtonClicked() {
        try {
            for (Thread thread : threads) {
                if (thread != null) {
                    if (thread.isAlive()) {
                        thread.interrupt();
                    }
                }
                threads.remove(thread);
            }
        } catch (ConcurrentModificationException ex) {
            
        }
    }

    public void addThread(Thread thread) {
        if (thread != null) {
            threads.add(thread);
        }
    }    

    public void tableClicked(Paper paper) {
        final Paper myPaper = paper;
        citationListHistory.clear();
        citationListHistory.addPaper(paper);
        //citationListHistory.printPapers();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(citationListHistory.getCurrentPosition(),myPaper.getTitle());
        Thread thread = new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(myPaper.getcitedByUrl()).build();
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) {
                PaperCollection pc = (PaperCollection) queryResult.getContents();
                    if (myPaper != null) {
                        logger.info("Paper Size:" + pc.getPapers().size());
                        renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), pc.getPapers());
                    }
                } else {
                    Main.getDisplayController().displayErrorMessage("Null QueryResult on Tableclicked...");
                }
            }
        };
        thread.start();
        threads.add(thread);
    }
    
    public void tableClicked(Journal journal) {
        final Journal myJournal = journal;
        //citationListHistory.clear();
        //citationListHistory.addPaper(paper);
        //citationListHistory.printPapers();
        Thread thread = new Thread() {
            @Override
            public void run() {
                Query q = new Query.Builder("").flag(QueryType.JOURN_PROF).Url(myJournal.getH5Link()).build();
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) {
                    Journal journ = (Journal) queryResult.getContents();
                    if (journ != null) {
                        render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(), journ);
                    }
                } else {
                    Main.getDisplayController().displayErrorMessage("Null QueryResult on Tableclicked...");
                }
            }
        };
        thread.start();
        threads.add(thread);
    }

    public void authorGridEntityClicked(String id) {


        final String myId = id;

        new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true);
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().showLoading();
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel().showLoading();
                Query q = new Query.Builder("").flag(QueryType.AUTH_PROF).ID(myId).build();
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult instanceof AuthorResult) {
                    queryResultRenderingHandler.render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(), queryResult);
                    renderProfile(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), (Author) queryResult.getContents());
                } else {
                    Main.getDisplayController().displayErrorMessage("Unknown Error while fetching Author Details.");
                }
            }
        }.start();
    }

    public void citationListClicked(Paper paper) {
        final Paper myPaper = paper;
        citationListHistory.addPaper(paper);
        
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(citationListHistory.getCurrentPosition(),myPaper.getTitle());
        new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
                Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(myPaper.getcitedByUrl()).build();
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) {
                    PaperCollection pc = (PaperCollection) queryResult.getContents();
                    if (myPaper != null) {
                        renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), pc.getPapers());
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                    }
                } else {
                    Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                }
            }
        }.start();
    }

    /*   public void journalProfile(String id) {
        
     final String myId = id;

     new Thread() {
     @Override
     public void run() {
     Query q = new Query.Builder("").flag(QueryType.JOURN_PROF).ID(myId).build();
     QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
     if (queryResult instanceof JournalResult)
     {
     queryResultRenderingHandler.render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(),queryResult);
     renderJournal(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(),(Journal)queryResult.getContents());
     }
     else
     {
     Main.getDisplayController().displayErrorMessage("Unknown Error while fetching Journal Details.");
     }
     }
     }.start();
     }*/
    //*****************************************************************************//
    //**************************** Rendering Functions ****************************//
    //*****************************************************************************//
    public void render(ContentRenderer contentRenderer, ArrayList<Author> arrayList) {
        renderMaster.render(contentRenderer, arrayList);
    }

    public void renderJournalMetrics(ContentRenderer contentRenderer, ArrayList<Journal> arrayList) {
        renderMaster.renderJournalMetrics(contentRenderer, arrayList);
    }

    public void render(ContentRenderer contentRenderer, Author author) {
        renderMaster.render(contentRenderer, author);
    }

    public void render(ContentRenderer contentRenderer, PaperCollection paperCollection) {
        renderMaster.render(contentRenderer, paperCollection);
    }
    
    public void render(ContentRenderer contentRenderer, Journal journal) {
        renderMaster.render(contentRenderer, journal.getPaperCollection());
    }

    public void renderCitationsList(ContentRenderer contentRenderer, ArrayList<Paper> papers) {
        renderMaster.renderCitationsList(contentRenderer, papers);
    }

    public void renderProfile(ContentRenderer contentRenderer, Author author) {
        renderMaster.renderProfile(contentRenderer, author);
    }

    public void renderJournal(ContentRenderer contentRenderer, PaperCollection papercollection) {
        renderMaster.renderJournal(contentRenderer, papercollection);
    }

    public void clearCitationHistory() {
      //  citationListHistory.clear();
    }

    public void citationListPrevious() {
        
        final Paper myPaper = citationListHistory.gotoPreviousPaper();        
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(citationListHistory.getCurrentPosition(),myPaper.getTitle());
        new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
                Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(myPaper.getcitedByUrl()).build();
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) {
                    PaperCollection pc = (PaperCollection) queryResult.getContents();
                    if (myPaper != null) {
                        renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), pc.getPapers());
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                    }
                } else {
                    Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                }
            }
        }.start();
    }

    public void citationListNext() {
        final Paper myPaper = citationListHistory.gotoNextPaper();
        
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(citationListHistory.getCurrentPosition(),myPaper.getTitle());
        new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
                Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(myPaper.getcitedByUrl()).build();
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) {
                    PaperCollection pc = (PaperCollection) queryResult.getContents();
                    if (myPaper != null) {
                        renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), pc.getPapers());
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                    }
                } else {
                    Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                }
            }
        }.start();
    }
}
