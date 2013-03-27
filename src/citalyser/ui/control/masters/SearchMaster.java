package citalyser.ui.control.masters;

import citalyser.Constants;
import citalyser.model.Author;
import citalyser.model.PaperCollection;
import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryResult;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.AuthorListResult;
import citalyser.ui.control.DisplayMaster;
import citalyser.ui.model.ContentRenderer;
import citalyser.ui.utils.UiUtils;
import citalyser.ui.visualization.MainFrame;
import citalyser.ui.visualization.panels.common.SearchPanel;
import java.awt.Color;
import java.util.ArrayList;
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
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
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
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(false);
        if (searchPanel.equals(mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel())) {
            if (!searchPanel.getSearchString().equals(" Enter Your Search Query Here") && !searchPanel.getSearchString().equals("")) {
                handleUserQuery(searchPanel);
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

    private void fetchResults(final Query q, final int maxResultsAtOneTime, final int numResults) {
                
        /* Clear all panels*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().clearAll();
        mainFrame.getRegularDisplayPanel().getSidebarPanel().clearAll();
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().displayDetailsDisplayPanel(false,0.0);
        /* Show Loading sign in the central panel*/
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().showLoading();
        /* Update the Search Panel on query Init*/
        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().updateOnQueryStart();
                
        Thread thread = new Thread() {

            @Override
            public void run() {
                String searchQuery = q.name;
                QueryResult globalResult = null, currResult;
                int totalCount = numResults;
                int count = maxResultsAtOneTime;
                int start = 0;
                int recvCount = 0;
                ContentRenderer dataContentRenderer = mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel();
                ContentRenderer profileContentRenderer = mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getDetailsDisplayPanel().getUpperDetailsDisplayPanel();
                //int state = 0;
                logger.debug("TotalCount : " + totalCount);
                while (!Thread.interrupted()) 
                {
                    logger.debug("Start : " + start + "--  Count : " + count);
                    if (start >= totalCount) 
                        break;
              
                    q.start_result = start;
                    q.num_results = Math.min(count, totalCount - start);
                    
                    currResult = QueryHandler.getInstance().getQueryResult(q);
                    if (currResult == null) {
                        logger.debug("Current result is null");
                        break;
                    }
                    
                    if (start == 0) 
                        globalResult = currResult;
                    else 
                        globalResult.appendContents(currResult.getContents());
                    
                    recvCount+=currResult.getNumContents();
                    
                    /* Hack for further fetching of results in case of Author Grid*/
                    //---------------------------------------------------------------------------------------
                    if (q.flag == QueryType.MET_AUTH && currResult instanceof AuthorListResult) {
                        ArrayList<Author> authors = (ArrayList<Author>) (currResult.getContents());
                        int numResults = currResult.getNumContents();
                        if(numResults > 0)
                            q.url = authors.get(0).getNextLink();
                        
                    }
                    //-----------------------------------------------------------------------------------------
                    if (Thread.interrupted()) {
                        break;
                    }
                    mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().updateProgressBar((recvCount*100)/totalCount);
                    displayMaster.getQueryResultRenderingHandler().render(dataContentRenderer, q, currResult);
                    displayMaster.getQueryResultRenderingHandler().renderProfile(profileContentRenderer, q, globalResult);
                    start += count;
                    /* Results have finished . No need to fetch more results.*/
                    if(recvCount < start)
                        break;
                }
                /* Update the search panel*/
                mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().updateOnQueryComplete();
                    
                // Query Completed. 
                if(globalResult == null)
                {
                    //Result is null
                    /* Show Loading sign in the central panel*/
                    mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel().stopLoading();
                    UiUtils.displayResultNullMessage(q.flag, searchQuery);
                }
                else
                {
                    /* If no results, show EmptyResult Message */
                    if(recvCount == 0) {
                        UiUtils.displayQueryEmptyMessage(dataContentRenderer,q.flag, searchQuery);
                    }
                    /* Show Query Completion Message*/
                    UiUtils.displayQueryCompleteInfoMessage(q.flag,recvCount,searchQuery);
                }
            }
        };
        thread.start();
        displayMaster.getNavigationMaster().addThread(thread);
    }

    private void handleUserQuery(SearchPanel searchPanel) {
        /* Input from the User Parameters */
        int maxResults;
        String searchQuery = searchPanel.getSearchString();
        int numResults = displayMaster.getNumberOfResults();
        String min_year = displayMaster.getMainFrame().getRegularDisplayPanel().getHeaderPanel().getSearchPanel().getMinYear();
        String max_year = displayMaster.getMainFrame().getRegularDisplayPanel().getHeaderPanel().getSearchPanel().getMaxYear();
        int minYear = 0;
        int maxYear = 0;
        int minyear = 0;
        int maxyear = 0;
        boolean year_empty = false;
        /*if(min_year.equals("")&& max_year.equals("")){
            year_empty = true;
            //minYear = min_year;
            //maxYear = max_year;
            
        }
        else{            
            try{
                minyear = Integer.parseInt(min_year);
            }
            catch(Exception e){
                minyear = 1800;
                displayMaster.getMainFrame().getRegularDisplayPanel().getHeaderPanel().getSearchPanel().setMinYear(minyear);
                
            }
            try{
                maxyear = Integer.parseInt(max_year);
            }
            catch(Exception e){
                maxyear =2013;
                displayMaster.getMainFrame().getRegularDisplayPanel().getHeaderPanel().getSearchPanel().setMaxYear(maxyear);
            }
            if(minyear>maxyear){
                maxYear = minyear;
                minYear = maxyear;
            }
            
        }*/
        boolean sortByYear = searchPanel.isSortByYear();
        boolean isAuthorQuery = displayMaster.checkAuthorMode();
        boolean isMetricQuery = mainFrame.getRegularDisplayPanel().getHeaderPanel().isMetric();
        
        String minYearStr = searchPanel.getMinYear(), maxYearStr = searchPanel.getMaxYear();

        /* Process the query*/
        Query q;
        if (isAuthorQuery) 
        {
            if (isMetricQuery) {
                //Search for Metrics Author
                maxResults = Constants.MaxResultsNum.AUTHOR_LIST.getValue();
                    q = new Query.Builder(searchQuery).flag(QueryType.MET_AUTH).minYear(minYearStr).maxYear(maxYearStr).Url(null).build();
                }
             else {
                //Search for General Author papers
                maxResults = Constants.MaxResultsNum.GENERAL_LIST.getValue();
                if(year_empty==true){
                    q = new Query.Builder(searchQuery).flag(QueryType.GEN_AUTH).sortFlag(sortByYear).build();
                }
                else{
                    q = new Query.Builder(searchQuery).flag(QueryType.GEN_AUTH).minYear(minYearStr).maxYear(maxYearStr).sortFlag(false).build();
                }
                UiUtils.displayQueryStartInfoMessage(q.flag, searchQuery);
            }
 
        } else {
            // Journal Query
            if (isMetricQuery) {
                //Fetch Journal Papers from Metric
                maxResults = Constants.MaxResultsNum.METRICS_JOURNAL_PAPERS.getValue();
                numResults = maxResults;
                    q = new Query.Builder(searchQuery).flag(QueryType.MET_JOURN).minYear(minYearStr).maxYear(maxYearStr).sortFlag(sortByYear).build();
                
            } else {
                //Fetch Journals from General Google Scholar
                maxResults = Constants.MaxResultsNum.JOURNAL_LIST.getValue();
                if(year_empty == true){
                    q = new Query.Builder(searchQuery).flag(QueryType.GEN_JOURN).sortFlag(sortByYear).build();
                }
                else{
                    q = new Query.Builder(searchQuery).flag(QueryType.GEN_JOURN).minYear(minYearStr).maxYear(maxYearStr).sortFlag(false).build();
                }
            }
        }
        UiUtils.displayQueryStartInfoMessage(q.flag, searchQuery);
        fetchResults(q, maxResults, numResults);
    }
    
    /* This method has been deprecated now. Do not use this method.*/
    /*   public Query createQuery(SearchPanel searchPanel, int start, int count) {
    
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
    } */
}