package citalyser.ui.control;

import citalyser.Constants;
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
import citalyser.ui.utils.UiUtils;
import citalyser.util.CProxy;

import citalyser.ui.visualization.MainFrame;
import citalyser.ui.visualization.panels.ExternalPanel;
import citalyser.ui.visualization.panels.common.SearchPanel;
import citalyser.ui.visualization.panels.external.AbstractDisplayPanel;
import citalyser.util.CommonUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class DisplayMaster {

    private MainFrame mainFrame;
    private javax.swing.JDialog settingsDialog, abstractDialog;
    private ExternalPanel extraPanel;
    private AbstractDisplayPanel abstractDisplayPanel;
    private SettingsMaster settingsMaster;
    private SearchMaster searchMaster;
    private RenderMaster renderMaster;
    private QueryResultRenderingHandler queryResultRenderingHandler;
    private static Logger logger = Logger.getLogger(DisplayMaster.class.getName());
    private CitationListHistory citationListHistory;
    private int numberOfResults = 100;
    private final Vector<Thread> threads = new Vector<>();
    private boolean showPaperPreview = true;
    private String query_name;

    public DisplayMaster() {
        mainFrame = new MainFrame();
        mainFrame.setDisplayMaster(this);
        mainFrame.setVisible(true);
        extraPanel = new ExternalPanel();
        abstractDisplayPanel = new AbstractDisplayPanel();
        extraPanel.setDisplayMaster(this);
        settingsMaster = new SettingsMaster(extraPanel);
        searchMaster = new SearchMaster(this);
        renderMaster = new RenderMaster();
        abstractDialog = new JDialog(mainFrame);
        abstractDialog.setUndecorated(true);
        abstractDialog.setFocusable(false);
        abstractDialog.setModal(false);
        abstractDialog.setBackground(new Color(0, 0, 0, 0));
        abstractDialog.getContentPane().setLayout(new BorderLayout());
        abstractDialog.getContentPane().add(abstractDisplayPanel);
        abstractDialog.pack();
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
        if (status != null) {
            mainFrame.getRegularDisplayPanel().getToolsPanel().displayStatusMessage(status);
        }
    }

    public void displayInfoMessage(String info) {
        if (info != null) {
            mainFrame.getRegularDisplayPanel().getToolsPanel().displayInfoMessage(info);
        }
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

    public void showPaperInfo(Paper paper, Point point) {
        /*final Paper myPaper = paper;
         final Point myPoint = point;
         final Thread thread = new Thread() {
        
         @Override
         public void run() {
         Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(myPaper.getcitedByUrl()).build();
         QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
         if (queryResult != null) {
         PaperCollection pc = (PaperCollection) queryResult.getContents();
         if (myPaper != null) {
         abstractDisplayPanel.setPaper(pc.maximumCitedPaper());
         abstractDialog.setLocation(myPoint);
         abstractDialog.setVisible(true);
         abstractDialog.repaint();
         }
         } else {
         abstractDialog.setVisible(false);
         }
         }
        
         };
         thread.start();
         new Thread() {
        
         @Override
         public void run() {
         try {
         Thread.sleep(100);
         } catch (InterruptedException ex) {
         ex.printStackTrace();
         }
         if (thread.isAlive()) {
         thread.stop();
         }
         }
        
         }.start();*/
        if (showPaperPreview) {
            abstractDisplayPanel.setPaper(paper);
            abstractDialog.setLocation(point);
            abstractDialog.setVisible(true);
            abstractDialog.repaint();
        }
    }

    public void hidePaperInfo() {
        abstractDialog.setVisible(false);
    }

    public void movePaperInfoTo(Point point) {
        if (showPaperPreview) {
            abstractDialog.setLocation(point);
            abstractDialog.repaint();
        }
    }

    public void cancelButtonClicked() {
        synchronized (threads) {
            for (Thread thread : threads) {
                if (thread != null) {
                    if (thread.isAlive()) {
                        thread.interrupt();
                    }
                }
                threads.remove(thread);
            }
        }
    }

    public void addThread(Thread thread) {
        synchronized (threads) {
            if (thread != null) {
                threads.add(thread);
            }
        }
    }

    /* This method is invoked to fetch the citations of a paper from google Scholar*/
    public void tableClicked(Paper paper) {
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
        final Paper myPaper = paper;
        /* Initialise the citation panel */
        citationListHistory.clear();
        citationListHistory.addPaper(paper);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel()
                .getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel()
                .initPanel(citationListHistory.getCurrentPosition(), myPaper.getTitle());
        /* Show Loading sign in Citation panel*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel()
                .getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel()
                .getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
        /* Show the Side panel*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true,0.5);

        Thread thread = new Thread() {
            @Override
            public void run() {
                ContentRenderer contentRenderer = mainFrame.getRegularDisplayPanel().getDataVisualizationPanel()
                        .getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel();

                Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(myPaper.getcitedByUrl())
                        .startResult(0)
                        .numResult(Constants.MaxResultsNum.CITATION_LIST.getValue())
                        .build();
                UiUtils.displayQueryStartInfoMessage(q.flag, myPaper.getTitle());
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                UiUtils.displayQueryCompleteInfoMessage(q.flag, 0, "");
                if (queryResult != null) {
                    if (queryResult.getNumContents() > 0) {
                        PaperCollection pc = (PaperCollection) queryResult.getContents();
                        contentRenderer.getCollapsibleListDisplayPanel().addEntityCount(pc.getPapers().size());
                        renderCitationsList(contentRenderer, q, pc.getPapers());
                    } else {
                        UiUtils.displayQueryEmptyMessage(contentRenderer, q.flag, myPaper.getTitle());
                    }
                }
            }
        };
        thread.start();
        synchronized (threads) {
            threads.add(thread);
        }
    }
    /* This method is invoked to fetch the citations of a metric paper from google Metrics*/

    public void metricTableClicked(Paper paper) {
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true,0.5);
        final Paper myPaper = paper;
        /* initialise the citation panel and history*/
        citationListHistory.clear();
        citationListHistory.addPaper(paper);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel()
                .getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel()
                .initPanel(citationListHistory.getCurrentPosition(), myPaper.getTitle());
        /* Show Loading sign in Citation panel*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
        /* Show the side panel*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true,0.5);
        Thread thread = new Thread() {
            @Override
            public void run() {
                Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST_METRIC).Url(myPaper.getcitedByUrl())
                        .startResult(0)
                        .numResult(Constants.MaxResultsNum.CITATION_LIST.getValue())
                        .build();
                UiUtils.displayQueryStartInfoMessage(q.flag, myPaper.getTitle());
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                ContentRenderer contentRenderer = mainFrame.getRegularDisplayPanel().getDataVisualizationPanel()
                        .getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel();
                UiUtils.displayQueryCompleteInfoMessage(q.flag, 0, "");
                if (queryResult != null) {
                    if (queryResult.getNumContents() > 0) {
                        PaperCollection pc = (PaperCollection) queryResult.getContents();
                        renderCitationsList(contentRenderer, q, pc.getPapers());
                    } else {
                        UiUtils.displayQueryEmptyMessage(contentRenderer, q.flag, myPaper.getTitle());
                    }
                }
            }
        };
        thread.start();
        synchronized (threads) {
            threads.add(thread);
        }
    }

    public void tableClicked(Journal journal) {
        final Journal myJournal = journal;
        this.query_name = journal.getName();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true,0.75);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().setNameJounal(true);
        Thread thread = new Thread() {
            @Override
            public void run() {
                Query q = new Query.Builder("").flag(QueryType.JOURN_PROF).Url(myJournal.getH5Link())
                                    .numResult(Constants.MaxResultsNum.METRICS_JOURNAL_PAPERS.getValue()).build();
                UiUtils.displayQueryStartInfoMessage(q.flag, myJournal.getName());
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) {
                    ContentRenderer contentRenderer = mainFrame.getRegularDisplayPanel().getDataVisualizationPanel()
                            .getContentDisplayPanel().getCentralContentDisplayPanel();
                    UiUtils.displayQueryCompleteInfoMessage(q.flag, queryResult.getNumContents(), myJournal.getName());
                    int numResults = queryResult.getNumContents();
                    if (numResults > 0) {
                        Journal journ = (Journal) queryResult.getContents();
                        render(contentRenderer, q, journ);
                        renderJournalProfile(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), q, journ);
                    } else {
                        UiUtils.displayQueryEmptyMessage(contentRenderer, q.flag, myJournal.getName());
                    }
                } else {
                    Main.getDisplayController().displayErrorMessage("Unknown Error while Fetching Journal Papers.");
                }
            }
        };
        thread.start();
        synchronized (threads) {
            threads.add(thread);
        }
    }

    public void authorGridEntityClicked(Author author) {

        final String authorName = author.getName();
        final String myId = author.getId();
        this.query_name = author.getName();
        final int numResults = Constants.MaxResultsNum.AUTHOR_PAPERS.getValue();
        Thread thread = new Thread() {
            @Override
            public void run() {
                //cancelButtonClicked();
                threads.add(this);
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().clearAll();
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true,0.5);
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().setNameJounal(false);
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().showLoading();
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel().showLoading();
                Query q = new Query.Builder("").flag(QueryType.AUTH_PROF).ID(myId).numResult(numResults).build();
                UiUtils.displayQueryStartInfoMessage(q.flag, authorName);
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult instanceof AuthorResult) {
                    UiUtils.displayQueryCompleteInfoMessage(q.flag, queryResult.getNumContents(), authorName);
                    queryResultRenderingHandler.render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(), q, queryResult);
                } else {
                    //Main.getDisplayController().displayErrorMessage("Unknown Error while fetching Author Details.");
                }
            }
        };
        thread.start();
    }
    
    public void citationListClicked(Paper paper) {
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
        final Paper myPaper = paper;
        citationListHistory.addPaper(paper);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().showPreviousButton();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(citationListHistory.getCurrentPosition(), myPaper.getTitle());
        new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
                Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(myPaper.getcitedByUrl())
                        .startResult(0)
                        .numResult(Constants.MaxResultsNum.CITATION_LIST.getValue())
                        .build();
                UiUtils.displayQueryStartInfoMessage(q.flag, myPaper.getTitle());
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                UiUtils.displayQueryCompleteInfoMessage(q.flag, 0, "");
                if (queryResult != null) {
                    PaperCollection pc = (PaperCollection) queryResult.getContents();
                    if (myPaper != null) {
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addEntityCount(pc.getPapers().size());
                        renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), q, pc.getPapers());
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                    }
                } else {
                    //Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                }
            }
        }.start();
    }

    //*****************************************************************************//
    //**************************** Rendering Functions ****************************//
    //*****************************************************************************//
    public void render(ContentRenderer contentRenderer, Query query, ArrayList<Author> arrayList) {
        renderMaster.render(contentRenderer, query, arrayList);
    }

    public void renderJournalMetrics(ContentRenderer contentRenderer, Query query, ArrayList<Journal> arrayList) {
        renderMaster.renderJournalMetrics(contentRenderer, query, arrayList);
    }

    public void render(ContentRenderer contentRenderer, Query query, Author author) {
        renderMaster.render(contentRenderer, query, author);
    }

    public void render(ContentRenderer contentRenderer, Query query, PaperCollection paperCollection) {
        /* Render Table Data*/
        renderMaster.render(contentRenderer, query, paperCollection);
    }

    public void render(ContentRenderer contentRenderer, Query query, Journal journal) {
        renderMaster.renderJournalPaperCollection(contentRenderer, query, journal.getPaperCollection());
    }

    public void renderCitationsList(ContentRenderer contentRenderer, Query query, ArrayList<Paper> papers) {
        renderMaster.renderCitationsList(contentRenderer, query, papers);
    }


    public void renderProfile(ContentRenderer contentRenderer, Query query, Author author) {
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true,0.5);
        renderMaster.renderAuthorProfile(contentRenderer, query, author);

    }
    /* This method is not used anywhere. Deprecated.*/

    public void renderGeneralProfile(ContentRenderer contentRenderer, Query query, PaperCollection papercollection) {
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true,0.75);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToUpperDetailsDisplayPanel();
        if(query.flag == QueryType.GEN_JOURN){
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().setNameJounal(true);
        }
        else{
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().setNameJounal(false);
        }
        renderMaster.renderGeneralProfile(contentRenderer, query, papercollection);
    }

    public void renderJournalProfile(ContentRenderer contentRenderer, Query query, Journal journal) {
        renderMaster.renderJournalProfile(contentRenderer, query, journal);
    }

    public void clearCitationHistory() {
        //  citationListHistory.clear();
    }

    public void citationListPrevious() {
        if (!citationListHistory.isCurrentPositionFirst()) {
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().showNextButton();
            final Paper myPaper = citationListHistory.gotoPreviousPaper();
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(citationListHistory.getCurrentPosition(), myPaper.getTitle());
            new Thread() {
                @Override
                public void run() {
                    mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
                    Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST)
                            .Url(myPaper.getcitedByUrl())
                            .startResult(0)
                            .numResult(Constants.MaxResultsNum.CITATION_LIST.getValue())
                            .build();
                    QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                    if (queryResult != null) {
                        PaperCollection pc = (PaperCollection) queryResult.getContents();
                        if (myPaper != null) {
                            renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), q, pc.getPapers());
                            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                        }
                    } else {
                        Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                    }
                }
            }.start();
            if (citationListHistory.isCurrentPositionFirst()) {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().hidePreviousButton();
            }
        }
    }

    public void citationListNext() {

        if (!citationListHistory.isCurrentPositionLast()) {
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().showPreviousButton();
            final Paper myPaper = citationListHistory.gotoNextPaper();
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(citationListHistory.getCurrentPosition(), myPaper.getTitle());
            new Thread() {
                @Override
                public void run() {
                    mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
                    Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST)
                            .startResult(0)
                            .numResult(Constants.MaxResultsNum.CITATION_LIST.getValue())
                            .Url(myPaper.getcitedByUrl()).build();
                    QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                    if (queryResult != null) {
                        PaperCollection pc = (PaperCollection) queryResult.getContents();
                        if (myPaper != null) {
                            renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), q, pc.getPapers());
                            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                        }
                    } else {
                        Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                    }
                }
            }.start();
        }
        if (citationListHistory.isCurrentPositionLast()) {
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().hideNextButton();
        }
    }

    public void citationListMoreButtonClicked(int startMarker, final JLabel jLabel) {
        final Paper myPaper = citationListHistory.getCurrentPaper();
        final int startMarker1 = startMarker;
        new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().showLoadingMoreButton();
                QueryType queryFlag = CommonUtils.getQueryFlagFromUrl(myPaper.getcitedByUrl());
                Query q = new Query.Builder("").flag(queryFlag)
                        .Url(myPaper.getcitedByUrl())
                        .startResult(startMarker1)
                        .numResult(Constants.MaxResultsNum.CITATION_LIST.getValue())
                        .build();
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) {
                    PaperCollection pc = (PaperCollection) queryResult.getContents();
                    if (pc.getPapers().size() < Constants.MaxResultsNum.CITATION_LIST.getValue()) {
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().removeMoreButton();
                    }
                    if (myPaper != null) {
                        renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), q, pc.getPapers());
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                    }
                } else {
                    //Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                }
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().showNormalMoreButton();
            }
        }.start();
    }

    public void showLoading() {
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().showLoading();
    }

    public void setDisplayPreview(boolean selected) {
        showPaperPreview = selected;
    }
    public SearchMaster getSearchMaster() {
        return searchMaster;
    }
    
    public void authorPaperTableMoreButtonClicked(final Query q, final JButton button) {
        final int numResults = Constants.MaxResultsNum.AUTHOR_PAPERS.getValue();
        final String name = this.query_name;
        Thread thread = new Thread() {
            @Override
            public void run() {
                //cancelButtonClicked();
                threads.add(this);
                final int start = q.num_results; 
                //Query q1 = new Query.Builder("").flag(q.flag).ID(q.ID)
                //                .numResult(numResults).startResult(numResults).build();
                logger.debug("Start : " + q.start_result + " Num :" + q.num_results);
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult instanceof AuthorResult) {
                    UiUtils.displayQueryCompleteInfoMessage(q.flag, q.start_result+queryResult.getNumContents(),name);
                    queryResultRenderingHandler.render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(), q, queryResult);
                    //renderProfile(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), q, (Author) queryResult.getContents());
                } else {
                    button.setText("More");
                    button.setIcon(null);
                    //Main.getDisplayController().displayErrorMessage("Unknown Error while fetching Author Details.");
                }
            }
        };
        thread.start();
    }
    
    public void metricPaperTableMoreButtonClicked(final PaperCollection paperCollection,final Query q,final JButton button) {
        final int numResults = q.num_results;
        final String name = this.query_name;
        Thread thread = new Thread() {
            @Override
            public void run() {
                //cancelButtonClicked();
                threads.add(this);
                final int start = q.num_results; 
                ContentRenderer contentRenderer = mainFrame.getRegularDisplayPanel().getDataVisualizationPanel()
                            .getContentDisplayPanel().getCentralContentDisplayPanel();
                logger.debug("Start : " + q.start_result + " Num :" + q.num_results);
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) 
                {
                    UiUtils.displayQueryCompleteInfoMessage(q.flag, q.start_result+queryResult.getNumContents(),name);
                    Journal journ = (Journal) queryResult.getContents();
                    render(contentRenderer, q,journ);
                    journ.setPaperCollection(paperCollection);
                    renderJournalProfile(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), q, journ);
                } else {
                    button.setText("More");
                    button.setText(null);
                    //Main.getDisplayController().displayErrorMessage("Unknown Error while fetching Author Details.");
                }
            }
        };
        thread.start();
    }
}
