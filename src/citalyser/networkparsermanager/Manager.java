/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.networkparsermanager;
import citalyser.cache.CacheHandler;
import citalyser.model.UrlComposer;
import citalyser.networking.HttpConnection;
import citalyser.parsing.Parser;
import citalyser.queryresult.PaperCollectionResult;
import citalyser.queryresult.QueryResult;
import javax.swing.text.Utilities;
/**
 *
 * @author NEHAL
 */
public class Manager {
    
    /* Query Case - GEN_AUTH */
    QueryResult getAuthorPapersFromScholar(String url)
    {
        String html = HttpConnection.getUrlText(url);
        if(html!=null)
            return Parser.extractInfo(html);
        else
        return null;
    }
    
    /* Query Case - GEN_JOURN */
    QueryResult getJournalPapersFromScholar(String url)
    {
        String html = HttpConnection.getUrlText(url);
        if(html!=null)
        return Parser.extractInfo(html);
        else
            return null;
    }
    
    /* Query Case - MET_AUTH */
    QueryResult getAuthorList(String url)
    {
         String html = HttpConnection.getUrlText(url);
         if(html==null)
        return Parser.getAuthors(html);
         else
             return null;
    }
    
    /* Query Case - MET_JOURN */
    QueryResult getJournalList(String url)
    {
         String html = HttpConnection.getUrlText(url);
        return null;
    }
    
    /* Query Case - AUTH_PROF */
    QueryResult getCompleteAuthorFromMetric(String url)
    {
         String html = HttpConnection.getUrlText(url);
         if(html!=null)
             return Parser.extractAuthorProfileInfo(html);
         else
        return null;
    }
    
    /* Query Case - JOURN_PROF */
    QueryResult getCompleteJournalFromMetric(String url)
    {
         String html = HttpConnection.getUrlText(url);
        return null;
    }
}
