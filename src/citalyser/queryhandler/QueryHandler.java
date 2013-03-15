package citalyser.queryhandler;

import citalyser.cache.CacheHandler;
import citalyser.model.UrlComposer;
import citalyser.networking.HttpConnection;
import citalyser.parsing.Parser;
import citalyser.queryresult.PaperCollectionResult;
import citalyser.queryresult.QueryResult;
import javax.swing.text.Utilities;

/**
 *
 * @author sony
 */

public class QueryHandler {
    
    private CacheHandler cacheHandler;
    private Parser parser;
    
    public QueryHandler()
    {
        cacheHandler = new CacheHandler();
        //parser = new Extractdata();
    }
    public QueryResult getQueryResult(Query q){
        
        switch(q.flag){
            case GEN_AUTH: 
                return getAuthorPapersFromScholar(q);
            case GEN_JOURN:
                return getJournalPapersFromScholar(q);
            case MET_AUTH:
                return getAuthorList(q);
            case MET_JOURN:
                return getJournalList(q);
            case AUTH_PROF:
                return getCompleteAuthorFromMetric(q);
            case JOURN_PROF:
                return getCompleteJournalFromMetric(q);
            default : 
                return null;
        }
        
    }
    
    /* Query Case - GEN_AUTH */
    QueryResult getAuthorPapersFromScholar(Query q)
    {
        String queryUrl = UrlComposer.getGenAuthUrl(q);
        Object cacheResult = cacheHandler.getObject(queryUrl);
        if(cacheResult!=null)
            return (PaperCollectionResult)cacheResult;
        
        //return( HttpConnection.getUrlText(queryUrl));
    
        //qResult = cacheHandler.getQueryResult(URL,q.flag);
        //return qResult;
        //NETWORK FUNCTION CALLED HERE
        
        //html = HttpConnection.getUrlText("http://scholar.google.co.in/scholar?hl=en&q=animesh+mukherjee&btnG=&as_sdt=1%2C5&as_sdtp=");
        //html = HttpConnection.getUrlText(URL);
        
        //p = Extractdata.extractInfo(html);
        return null;
    }
    
    /* Query Case - GEN_JOURN */
    QueryResult getJournalPapersFromScholar(Query q)
    {
        return null;
    }
    
    /* Query Case - MET_AUTH */
    QueryResult getAuthorList(Query q)
    {
        return null;
    }
    
    /* Query Case - MET_JOURN */
    QueryResult getJournalList(Query q)
    {
        return null;
    }
    
    /* Query Case - AUTH_PROF */
    QueryResult getCompleteAuthorFromMetric(Query q)
    {
        return null;
    }
    
    /* Query Case - JOURN_PROF */
    QueryResult getCompleteJournalFromMetric(Query q)
    {
        return null;
    }
}