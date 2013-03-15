/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control.switchers;

import citalyser.Main;
import citalyser.queryresult.AuthorListResult;
import citalyser.queryresult.AuthorResult;
import citalyser.queryresult.JournalListResult;
import citalyser.queryresult.JournalResult;
import citalyser.queryresult.PaperCollectionResult;
import citalyser.queryresult.QueryResult;

/**
 *
 * @author Tanmay Patil
 */
public class QueryResultRenderingHandler {
    
    public static void render(QueryResult<?> queryResult) {
        if (queryResult instanceof AuthorListResult) {
            
        } else if (queryResult instanceof AuthorResult) {
            
        } else if (queryResult instanceof JournalListResult) {
            
        } else if (queryResult instanceof JournalResult) {
            
        } else if (queryResult instanceof PaperCollectionResult) {
            
        } else {
            Main.getDisplayController().displayErrorMessage("Invalid Query Result Type");
        }
    }

}
