/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control.switchers;

import citalyser.Main;
import citalyser.model.PaperCollection;
import citalyser.queryresult.AuthorListResult;
import citalyser.queryresult.AuthorResult;
import citalyser.queryresult.JournalListResult;
import citalyser.queryresult.JournalResult;
import citalyser.queryresult.PaperCollectionResult;
import citalyser.queryresult.QueryResult;
import citalyser.ui.control.DisplayMaster;
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
    
    public void render(QueryResult<?> queryResult) {
        if (queryResult instanceof AuthorListResult) {
            
        } else if (queryResult instanceof AuthorResult) {
            
        } else if (queryResult instanceof JournalListResult) {
            
        } else if (queryResult instanceof JournalResult) {
            
        } else if (queryResult instanceof PaperCollectionResult) {
            logger.debug("PaperCollection size : " + ((PaperCollection) queryResult.getContents()).getPapers().size());
            displayMaster.renderPaperCollection((PaperCollection) queryResult.getContents());
        } else {
            Main.getDisplayController().displayErrorMessage("Invalid Query Result Type " + queryResult);
        }
    }

}
