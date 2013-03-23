
package citalyser.ui.control.masters;

import citalyser.Constants;
import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryResult;
import citalyser.model.query.QueryType;
import citalyser.ui.control.DisplayMaster;
import citalyser.ui.visualization.MainFrame;
import citalyser.ui.visualization.panels.common.SearchPanel;
import java.awt.Color;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class SearchMaster {

    private MainFrame mainFrame;
    private DisplayMaster displayMaster;
    private static Logger logger = Logger.getLogger(SearchMaster.class.getName());

    public SearchMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
        this.mainFrame = displayMaster.getMainFrame();
    }

    public void searchKeyPressed(SearchPanel searchPanel, char key) {
        if (key == java.awt.event.KeyEvent.VK_ENTER) {
            searchButtonClicked(searchPanel);
        }
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

    public void searchButtonClicked(SearchPanel searchPanel) {
        final SearchPanel mySearchPanel = searchPanel;
        if (searchPanel.equals(mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel())) {
            if (!searchPanel.getSearchString().equals(" Enter Your Search Query Here") && !searchPanel.getSearchString().equals("")) {
                Thread thread = new Thread() {

                    @Override
                    public void run() {
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().clearAll();
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(false);
                        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().showLoading();
                        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().setButtonEnabled(false);
                        
                        QueryResult globalResult = null, currResult;
                        int totalCount = mainFrame.getRegularDisplayPanel().getToolsPanel().getNumResults();
                        int count = Constants.MaxResultsNum.AUTHOR_LIST.getValue(), start = 0;
                        logger.debug("Count : "+count);
                        while (!Thread.interrupted()) {
                            logger.debug("Start : " + start);
                            if (start >= totalCount) {
                               // currResult = QueryHandler.getInstance().getQueryResult(createQuery(mySearchPanel, start, totalCount - start));
                               // globalResult.appendContents(currResult.getContents());
                               // displayMaster.getQueryResultRenderingHandler().render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(), currResult);
                                break;
                            }
                            currResult = QueryHandler.getInstance().getQueryResult(createQuery
                                            (mySearchPanel, start, Math.min(count,totalCount - start)));
                            if(currResult == null)
                                break;
                            if (start == 0) {
                                globalResult = currResult;
                            } else {
                                globalResult.appendContents(currResult.getContents());
                            }
                            displayMaster.getQueryResultRenderingHandler().render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(), currResult);
                            start += count;
                        }
                        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().setButtonEnabled(true);
                    }
                };
                thread.start();
                displayMaster.addThread(thread);
            }
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

    public Query createQuery(SearchPanel searchPanel, int start, int count) {

        if (displayMaster.checkAuthorMode()) {
            if (searchPanel.getRadioButtonInfo()) {
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.MET_AUTH).startResult(start).numResult(count).minYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getValue()).maxYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getUpperValue()).build();
            } else {
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.GEN_AUTH).startResult(start).numResult(count).minYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getValue()).maxYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getUpperValue()).build();
            }
        } else {
            if (searchPanel.getRadioButtonInfo()) {
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.GEN_JOURN).startResult(start).numResult(count).minYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getValue()).maxYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getUpperValue()).build();
            } else {
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.MET_JOURN).startResult(start).numResult(count).minYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getValue()).maxYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getUpperValue()).sortFlag(searchPanel.getComboSelection()).build();
            }
        }
    }
 /*   
    public Query handleUserQuery(SearchPanel searchPanel, int start, int count)
    {
        if (displayMaster.checkAuthorMode()) {
            if (searchPanel.getRadioButtonInfo()) {
                //Search for Metrics Author 
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.MET_AUTH).startResult(start).numResult(count).minYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getValue()).maxYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getUpperValue()).build();
            } else {
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.GEN_AUTH).startResult(start).numResult(count).minYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getValue()).maxYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getUpperValue()).build();
            }
        } else {
            if (searchPanel.getRadioButtonInfo()) {
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.GEN_JOURN).startResult(start).numResult(count).minYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getValue()).maxYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getUpperValue()).build();
            } else {
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.MET_JOURN).startResult(start).numResult(count).minYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getValue()).maxYear(displayMaster.getMainFrame().getRegularDisplayPanel().getSidebarPanel().getRangeSlider().getUpperValue()).sortFlag(searchPanel.getComboSelection()).build();
            }
        }
    }*/
}
