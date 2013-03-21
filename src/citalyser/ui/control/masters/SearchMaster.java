/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control.masters;

import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryType;
import citalyser.ui.control.DisplayMaster;
import citalyser.ui.visualization.MainFrame;
import citalyser.ui.visualization.panels.common.SearchPanel;
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
            new Thread() {

                @Override
                public void run() {
                    displayMaster.getQueryResultRenderingHandler().render(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel().getCentralContentDisplayPanel(), QueryHandler.getInstance().getQueryResult(createQuery(mySearchPanel)));
                }
            }.start();
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

    public Query createQuery(SearchPanel searchPanel) {
        if (displayMaster.checkAuthorMode()) {
            if(searchPanel.getRadioButtonInfo()){
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.MET_AUTH).numResult(20).minYear(1900).maxYear(2013).build();
            }else{
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.GEN_AUTH).numResult(20).minYear(1900).maxYear(2013).build();
            }
        } else {
            if(searchPanel.getRadioButtonInfo()){
                return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.GEN_JOURN).numResult(20).minYear(1900).maxYear(2013).build();
            }else{
                //return null; // Uncomment next Line after handling MET_JOURN querytype
               return new Query.Builder(searchPanel.getSearchString()).flag(QueryType.MET_JOURN).numResult(20).minYear(1900).maxYear(2013).build();
            }
        }
    }
}
