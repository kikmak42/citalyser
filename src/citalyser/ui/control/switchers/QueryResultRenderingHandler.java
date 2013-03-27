/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control.switchers;

import citalyser.Main;
import citalyser.model.Author;
import citalyser.model.Journal;
import citalyser.model.PaperCollection;
import citalyser.model.query.Query;
import citalyser.model.query.queryresult.AuthorListResult;
import citalyser.model.query.queryresult.AuthorResult;
import citalyser.model.query.queryresult.JournalListResult;
import citalyser.model.query.queryresult.JournalResult;
import citalyser.model.query.queryresult.PaperCollectionResult;
import citalyser.model.query.QueryResult;
import citalyser.model.query.QueryType;
import citalyser.ui.control.DisplayMaster;
import citalyser.ui.model.ContentRenderer;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class QueryResultRenderingHandler {
    
    private static Logger logger = Logger.getLogger(QueryResultRenderingHandler.class.getName());

    public QueryResultRenderingHandler(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
    }
    
    private DisplayMaster displayMaster;
    
    public void render(ContentRenderer contentRenderer, Query query, QueryResult<?> queryResult) {
        if (queryResult instanceof AuthorListResult) {
            displayMaster.getRenderMaster().render(contentRenderer, query, (ArrayList<Author>) queryResult.getContents());
        } else if (queryResult instanceof AuthorResult) {
            displayMaster.getRenderMaster().render(contentRenderer, query, (Author) queryResult.getContents());
        } else if (queryResult instanceof JournalListResult) {
            displayMaster.getRenderMaster().renderJournalMetrics(contentRenderer, query, (ArrayList<Journal>) queryResult.getContents());
        } else if (queryResult instanceof JournalResult) {
            //displayMaster.render(contentRenderer, query, (Journal) queryResult.getContents());
        } else if (queryResult instanceof PaperCollectionResult) {
            //Render table
            displayMaster.getRenderMaster().render(contentRenderer, query, (PaperCollection) queryResult.getContents());

        } else {
            //Main.getDisplayController().displayErrorMessage("Oops!! Something went Wrong.We are sorry for your inconvenience.");
        }
    }

    public void renderProfile(ContentRenderer contentRenderer, Query query, QueryResult<?> queryResult)
    {
        if (queryResult instanceof AuthorListResult) {
            //displayMaster.render(contentRenderer, query, (ArrayList<Author>) queryResult.getContents());
        } else if (queryResult instanceof AuthorResult) {
            //displayMaster.render(contentRenderer, query, (Author) queryResult.getContents());
        } else if (queryResult instanceof JournalListResult) {
            //displayMaster.renderJournalMetrics(contentRenderer, query, (ArrayList<Journal>) queryResult.getContents());
        } else if (queryResult instanceof JournalResult) {
            displayMaster.getRenderMaster().renderJournalProfile(contentRenderer, query, (Journal) queryResult.getContents());
        } else if (queryResult instanceof PaperCollectionResult) {
            //Render table
            displayMaster.getRenderMaster().renderGeneralProfile(contentRenderer, query, (PaperCollection) queryResult.getContents());

        } else {
            //Main.getDisplayController().displayErrorMessage("Oops!! Something went Wrong.We are sorry for your inconvenience.");
        }
    }
}
