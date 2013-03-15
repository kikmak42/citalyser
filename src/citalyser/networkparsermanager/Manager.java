/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.networkparsermanager;
import citalyser.Main;
import citalyser.cache.CacheHandler;
import citalyser.model.UrlComposer;
import citalyser.networking.HttpConnection;
import citalyser.parsing.Parser;
import citalyser.queryresult.PaperCollectionResult;
import citalyser.queryresult.QueryResult;
import javax.swing.text.Utilities;
import org.apache.log4j.Logger;
/**
 *
 * @author NEHAL
 */
public class Manager {
    private static Logger logger = Logger.getLogger(Manager.class.getName());
    /* Query Case - GEN_AUTH */
    public QueryResult getAuthorPapersFromScholar(String url)
    {
        String html = HttpConnection.getUrlText(url);
        logger.info("GettingAuthorPapers from Network"+url);
        return Parser.extractInfo(html);
    }
    
    /* Query Case - GEN_JOURN */
    public QueryResult getJournalPapersFromScholar(String url)
    {
        String html = HttpConnection.getUrlText(url);
        return Parser.extractInfo(html);
    }
    
    /* Query Case - MET_AUTH */
    public QueryResult getAuthorList(String url)
    {
         String html = HttpConnection.getUrlText(url);
        return Parser.getAuthors(html);
    }
    
    /* Query Case - MET_JOURN */
    public QueryResult getJournalList(String url)
    {
         String html = HttpConnection.getUrlText(url);
        return null;
    }
    
    /* Query Case - AUTH_PROF */
    public QueryResult getCompleteAuthorFromMetric(String url)
    {
         String html = HttpConnection.getUrlText(url);
        return null;
    }
    
    /* Query Case - JOURN_PROF */
    public QueryResult getCompleteJournalFromMetric(String url)
    {
         String html = HttpConnection.getUrlText(url);
        return null;
    }
}
