/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control.masters;

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
import citalyser.ui.control.DisplayMaster;
import citalyser.ui.model.ContentRenderer;
import citalyser.ui.utils.UiUtils;
import citalyser.ui.visualization.MainFrame;
import citalyser.util.CommonUtils;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class NavigationMaster {

    private MainFrame mainFrame;
    private DisplayMaster displayMaster;
    private static Logger logger = Logger.getLogger(NavigationMaster.class.getName());

    public NavigationMaster(MainFrame mainFrame, DisplayMaster displayMaster) {
        this.mainFrame = mainFrame;
        this.displayMaster = displayMaster;
    }

    public void cancelButtonClicked() {
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        /*synchronized (displayMaster.getThreads()) {
            for (Thread thread : displayMaster.getThreads()) {
                if (thread != null) {
                    if (thread.isAlive()) {
                        thread.interrupt();
                    }
                }
                displayMaster.getThreads().remove(thread);
            }
        }*/
        displayMaster.getExecutorService().shutdownNow();
        logger.debug("Trying to shut down...");
        try {
            displayMaster.getExecutorService().awaitTermination(1000, TimeUnit.MICROSECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        logger.debug("Shutting down successful...");
        Main.getDisplayController().displayStatusMessage("Cancelled");
        displayMaster.setExecutorService(Executors.newCachedThreadPool());
    }

    public void addThread(Thread thread) {
        /*synchronized (displayMaster.getThreads()) {
            if (thread != null) {
                displayMaster.getThreads().add(thread);
            }
        }*/
    }

    /**
     * This method is invoked to fetch the citations of a paper from google
     * Scholar
     */
    public void tableClicked(Paper paper) {
        cancelButtonClicked();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
        final Paper myPaper = paper;
        /* Initialise the citation panel */
        displayMaster.getCitationListHistory().clear();
        displayMaster.getCitationListHistory().addPaper(paper);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().hideNextButton();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel()
                .getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel()
                .initPanel(displayMaster.getCitationListHistory().getCurrentPosition(), myPaper.getTitle());
        /* Show Loading sign in Citation panel*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel()
                .getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel()
                .getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
        /* Show the Side panel*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true, 0.5);

        //Thread thread = new Thread() {
        displayMaster.getExecutorService().submit(new Runnable() {
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
                        displayMaster.getRenderMaster().renderCitationsList(contentRenderer, q, pc.getPapers());
                    } else {
                        UiUtils.displayQueryEmptyMessage(contentRenderer, q.flag, myPaper.getTitle());
                    }
                } else {
                    /* Show Loading sign in Citation panel*/
                    UiUtils.displayResultNullMessage(q.flag, myPaper.getTitle());
                    mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel()
                            .getDetailsDisplayPanel().getLowerDetailsDisplayPanel().stopLoading();


                }
            }
        });
        /*thread.start();
        synchronized (displayMaster.getThreads()) {
            displayMaster.getThreads().add(thread);
        }*/
    }

    /**
     * This method is invoked to fetch the citations of a metric paper from
     * google Metrics
     */
    public void metricTableClicked(Paper paper) {
        cancelButtonClicked();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true, 0.5);
        final Paper myPaper = paper;
        /* initialise the citation panel and history*/
        displayMaster.getCitationListHistory().clear();
        displayMaster.getCitationListHistory().addPaper(paper);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().hideNextButton();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel()
                .getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel()
                .initPanel(displayMaster.getCitationListHistory().getCurrentPosition(), myPaper.getTitle());
        /* Show Loading sign in Citation panel*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().showLoading();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
        /* Show the side panel*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true, 0.5);
        /* Disable the Search button*/
        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().updateOnQueryStart();

        //Thread thread = new Thread() {
        displayMaster.getExecutorService().submit(new Runnable() {
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
                        displayMaster.getRenderMaster().renderCitationsList(contentRenderer, q, pc.getPapers());
                    } else {
                        UiUtils.displayQueryEmptyMessage(contentRenderer, q.flag, myPaper.getTitle());
                    }
                }
                mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().updateOnQueryComplete();
                /* Show Loading sign in Citation panel*/
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().stopLoading();

            }
        });
        /*thread.start();
        synchronized (displayMaster.getThreads()) {
            displayMaster.getThreads().add(thread);
        }*/
    }

    public void tableClicked(Journal journal) {
        cancelButtonClicked();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        final Journal myJournal = journal;
        logger.debug("2345::::" + myJournal.getH5index());
        displayMaster.setQueryName(journal.getName());
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true, 0.5);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().setNameJounal(true);
        /* Show Loading sign*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().showLoading();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel().showLoading();
        /* Disable the Search button*/
        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().updateOnQueryStart();

        //Thread thread = new Thread() {
        displayMaster.getExecutorService().submit(new Runnable() {
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
                        displayMaster.getRenderMaster().renderJournal(contentRenderer, q, journ);
                        displayMaster.getRenderMaster().renderJournalProfile(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), q, journ);
                    } else {
                        UiUtils.displayQueryEmptyMessage(contentRenderer, q.flag, myJournal.getName());
                    }
                } else {
                    UiUtils.displayResultNullMessage(q.flag, myJournal.getName());
                    mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().stopLoading();
                    mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel().stopLoading();
                    mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().updateOnQueryComplete();
                }
            }
        });
        /*thread.start();
        synchronized (displayMaster.getThreads()) {
            displayMaster.getThreads().add(thread);
        }*/
    }

    public void authorGridEntityClicked(Author author) {
        cancelButtonClicked();
        
        /* Update the UI elements to reflect the change.*/
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().clearAll();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().clearAll();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(true, 0.5);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().setNameJounal(false);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().showLoading();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel().showLoading();
        /* Disable the Search button*/
        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().updateOnQueryStart();

        final String authorName = author.getName();
        final String myId = author.getId();
        displayMaster.setQueryName(author.getName());
        final int numResults = Constants.MaxResultsNum.AUTHOR_PAPERS.getValue();
        //Thread thread = new Thread() {
        displayMaster.getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                //displayMaster.getThreads().add(this);
                Query q = new Query.Builder("").flag(QueryType.AUTH_PROF).ID(myId).numResult(numResults).build();
                UiUtils.displayQueryStartInfoMessage(q.flag, authorName);
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult instanceof AuthorResult) {
                    UiUtils.displayQueryCompleteInfoMessage(q.flag, queryResult.getNumContents(), authorName);
                    displayMaster.getQueryResultRenderingHandler().render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(), q, queryResult);
                    displayMaster.getRenderMaster().renderAuthorProfile(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), q, (Author) queryResult.getContents());
                    mainFrame.getRegularDisplayPanel().getSidebarPanel().getAuthorListPanel().displayAuthors(((Author) (queryResult.getContents())).getPaperCollection());
                } else {
                    UiUtils.displayResultNullMessage(q.flag, authorName);
                    mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().stopLoading();
                    mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel().stopLoading();
                }
                mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().updateOnQueryComplete();
            }
        });
        //thread.start();
    }

    public void citationListClicked(Paper paper) {
        cancelButtonClicked();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
        final Paper myPaper = paper;
        displayMaster.getCitationListHistory().addPaper(paper);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().hideNextButton();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().showPreviousButton();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(displayMaster.getCitationListHistory().getCurrentPosition(), myPaper.getTitle());
        //new Thread() {
        displayMaster.getExecutorService().submit(new Runnable() {
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
                        displayMaster.getRenderMaster().renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), q, pc.getPapers());
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                    }
                } else {
                    //Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                }
            }
        });//.start();
    }

    public void citationListMoreButtonClicked(int startMarker, final JLabel jLabel) {
        cancelButtonClicked();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        final Paper myPaper = displayMaster.getCitationListHistory().getCurrentPaper();
        final int startMarker1 = startMarker;
        //new Thread() {
        displayMaster.getExecutorService().submit(new Runnable() {
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
                        displayMaster.getRenderMaster().renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), q, pc.getPapers());
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                    }
                } else {
                    //Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                }
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().showNormalMoreButton();
            }
        });//.start();
    }

    public void authorPaperTableMoreButtonClicked(final Query q, final JButton button) {
        cancelButtonClicked();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        final int numResults = Constants.MaxResultsNum.AUTHOR_PAPERS.getValue();
        final String name = displayMaster.getQueryName();
        //Thread thread = new Thread() {
        displayMaster.getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                //displayMaster.getThreads().add(this);
                final int start = q.num_results;
                //Query q1 = new Query.Builder("").flag(q.flag).ID(q.ID)
                //                .numResult(numResults).startResult(numResults).build();
                logger.debug("Start : " + q.start_result + " Num :" + q.num_results);
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult instanceof AuthorResult) {
                    UiUtils.displayQueryCompleteInfoMessage(q.flag, q.start_result + queryResult.getNumContents(), name);
                    displayMaster.getQueryResultRenderingHandler().render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(), q, queryResult);
                    //renderProfile(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), q, (Author) queryResult.getContents());
                } else {
                    button.setText("More");
                    button.setIcon(null);
                    //Main.getDisplayController().displayErrorMessage("Unknown Error while fetching Author Details.");
                }
            }
        });
        //thread.start();
    }

    public void metricPaperTableMoreButtonClicked(final PaperCollection paperCollection, final Query q, final JButton button) {
        cancelButtonClicked();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        final int numResults = q.num_results;
        final String name = displayMaster.getQueryName();
        //Thread thread = new Thread() {
        displayMaster.getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                //displayMaster.getThreads().add(this);
                final int start = q.num_results;
                ContentRenderer contentRenderer = mainFrame.getRegularDisplayPanel().getDataVisualizationPanel()
                        .getContentDisplayPanel().getCentralContentDisplayPanel();
                logger.debug("Start : " + q.start_result + " Num :" + q.num_results);
                QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
                if (queryResult != null) {
                    UiUtils.displayQueryCompleteInfoMessage(q.flag, q.start_result + queryResult.getNumContents(), name);
                    Journal journ = (Journal) queryResult.getContents();
                    displayMaster.getRenderMaster().renderJournal(contentRenderer, q, journ);
                    journ.setPaperCollection(paperCollection);
                    displayMaster.getRenderMaster().renderJournalProfile(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel(), q, journ);
                } else {
                    button.setText("More");
                    button.setText(null);
                    //Main.getDisplayController().displayErrorMessage("Unknown Error while fetching Author Details.");
                }
            }
        });
        //thread.start();
    }

    public void citationListPreviousButtonClicked() {
        cancelButtonClicked();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        if (!displayMaster.getCitationListHistory().isCurrentPositionFirst()) {
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().showNextButton();
            final Paper myPaper = displayMaster.getCitationListHistory().gotoPreviousPaper();
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(displayMaster.getCitationListHistory().getCurrentPosition(), myPaper.getTitle());
            //new Thread() {
            displayMaster.getExecutorService().submit(new Runnable() {
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
                            displayMaster.getRenderMaster().renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), q, pc.getPapers());
                            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                        }
                    } else {
                        Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                    }
                }
            });//.start();
            if (displayMaster.getCitationListHistory().isCurrentPositionFirst()) {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().hidePreviousButton();
            }
        }
    }

    public void citationListNextButtonClicked() {
        cancelButtonClicked();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);

        if (!displayMaster.getCitationListHistory().isCurrentPositionLast()) {
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().clearAll();
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().showPreviousButton();
            final Paper myPaper = displayMaster.getCitationListHistory().gotoNextPaper();
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().addListTitle(displayMaster.getCitationListHistory().getCurrentPosition(), myPaper.getTitle());
            //new Thread() {
            displayMaster.getExecutorService().submit(new Runnable() {
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
                            displayMaster.getRenderMaster().renderCitationsList(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel(), q, pc.getPapers());
                            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().flipToLowerDetailsDisplayPanel();
                        }
                    } else {
                        Main.getDisplayController().displayErrorMessage("Null QueryResult on Listclicked...");
                    }
                }
            });//.start();
        }
        if (displayMaster.getCitationListHistory().isCurrentPositionLast()) {
            mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getLowerDetailsDisplayPanel().getCollapsibleListDisplayPanel().hideNextButton();
        }
    }

    public void displayGraphSelected(Paper selectedPaper, boolean isMetric) {
        final Paper myPaper = selectedPaper;
        final boolean myIsMetric = isMetric;
        new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getGraphViewPanel().setPaper(myPaper, myIsMetric);
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().flipToGraphDisplayPanel();
            }
        }.start();

    }
    
    public void gotoGraphPrev() {
        
        new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getGraphViewPanel().gotoPrevious();
            }
        }.start();

    }
    
    public void gotoGraphNext() {
        
        new Thread() {
            @Override
            public void run() {
                mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getGraphViewPanel().gotoNext();
            }
        }.start();

    }
}
