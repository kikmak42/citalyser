package citalyser.queryhandler;

import citalyser.cache.CacheHandler;
import citalyser.model.UrlComposer;
import citalyser.networking.HttpConnection;
import citalyser.parsing.Parser;
import citalyser.queryresult.AuthorListResult;
import citalyser.queryresult.AuthorResult;
import citalyser.queryresult.JournalListResult;
import citalyser.queryresult.JournalResult;
import citalyser.queryresult.PaperCollectionResult;
import citalyser.queryresult.QueryResult;
import citalyser.cache.CacheHandler;
import javax.swing.text.Utilities;

/**
 *
 * @author sony
 */

public class QueryHandler {
    
    private CacheHandler cacheHandler;
    private Parser parser;
    private UrlComposer url;
    private String queryUrl;
    
    public QueryHandler()
    {
        cacheHandler = new CacheHandler();
        url = new UrlComposer();
        //parser = new Extractdata();
    }
    public QueryResult getQueryResult(Query q){
        
        switch(q.flag){
            case GEN_AUTH: 
                queryUrl = UrlComposer.getGenAuthUrl(q);
                return cacheHandler.getAuthorPapersFromScholar(queryUrl);
            case GEN_JOURN:
                queryUrl = UrlComposer.getGenJournUrl(q);
                return cacheHandler.getJournalPapersFromScholar(queryUrl);
            case MET_AUTH:
                queryUrl = UrlComposer.getMetAuthUrl(q);
                return cacheHandler.getAuthorList(queryUrl);
            case MET_JOURN:
                queryUrl = UrlComposer.getMetJournUrl(q);
                return cacheHandler.getJournalList(queryUrl);
            case AUTH_PROF:
                queryUrl = UrlComposer.getAuthProfUrl(q);
                return cacheHandler.getCompleteAuthorFromMetric(queryUrl);
            case JOURN_PROF:
                queryUrl = UrlComposer.getJournProfUrl(q);
                return cacheHandler.getCompleteJournalFromMetric(queryUrl);
            default : 
                return null;
        }
        
    }
}
