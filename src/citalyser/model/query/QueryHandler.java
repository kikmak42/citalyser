package citalyser.model.query;


import citalyser.model.query.Query;
import citalyser.dataextraction.cache.CacheHandler;
import citalyser.model.UrlComposer;
import citalyser.dataextraction.parsing.Parser;
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
        q = UrlComposer.encodeQueryParameters(q);
        switch(q.flag){
            case GEN_AUTH: 
                logger.debug("Getting GEN_AUTH");
                queryUrl = UrlComposer.getGenAuthUrl(q);
                return cacheHandler.getAuthorPapersFromScholar(queryUrl);
            case GEN_JOURN:
                queryUrl = UrlComposer.getGenJournUrl(q);
                return cacheHandler.getJournalPapersFromScholar(queryUrl);
            case MET_AUTH:
                if(q.start_result>0 && (q.url==null || "".equals(q.url)))
                    return null;
                else if(q.start_result > 0)
                    queryUrl = q.url;
                else
                    queryUrl = UrlComposer.getMetAuthUrl(q);
                return cacheHandler.getAuthorList(queryUrl);
            case MET_JOURN:
                queryUrl = UrlComposer.getMetJournUrl(q);
               // logger.debug("@@##$$"+queryUrl);
                return cacheHandler.getJournalList(queryUrl);
            case AUTH_PROF:
                queryUrl = UrlComposer.getAuthProfUrl(q);
                return cacheHandler.getCompleteAuthorFromMetric(queryUrl);
            case JOURN_PROF:
                queryUrl = q.url;
                return cacheHandler.getCompleteJournalFromMetric(queryUrl);
            case IMAGE_FROM_LINK:
                queryUrl = q.url;
                return cacheHandler.getImageFromLink(queryUrl);
            case CITATIONS_LIST:
                queryUrl = q.url;
                return cacheHandler.getCitationsList(queryUrl);
            default : 
                return null;
        }
        
    }
}
