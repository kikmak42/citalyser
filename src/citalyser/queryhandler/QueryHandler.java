package citalyser.queryhandler;


import citalyser.cache.CacheHandler;
import citalyser.model.UrlComposer;
import citalyser.parsing.Parser;
import citalyser.queryresult.QueryResult;
import org.apache.log4j.Logger;


/**
 *
 * @author sony
 */

public class QueryHandler {
    private static Logger logger = Logger.getLogger(QueryHandler.class.getName());
    private CacheHandler cacheHandler;
    private Parser parser;
    private UrlComposer url;
    private String queryUrl;
    
    private QueryHandler()
    {
        cacheHandler = new CacheHandler();
        url = new UrlComposer();
        //parser = new Extractdata();
    }
    
    private static class QueryHandlerHolder {
        private static final QueryHandler queryHandler = new QueryHandler();
    }

    public static QueryHandler getInstance() {
        return QueryHandlerHolder.queryHandler;
    }    
    
    public QueryResult<?> getQueryResult(Query q){
        
        switch(q.flag){
            case GEN_AUTH: 
                logger.debug("Getting GEN_AUTH");
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
