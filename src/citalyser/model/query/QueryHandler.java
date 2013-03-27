package citalyser.model.query;

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

    private QueryHandler() {
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

    public QueryResult<?> getQueryResult(Query q) {
        q = UrlComposer.encodeQueryParameters(q);
        if (Thread.interrupted()) {
            return null;
        }
        switch (q.flag) {
            case GEN_AUTH:
                logger.debug("Min Year : " + q.min_year + " Max Year: " + q.max_year);
                logger.debug("Getting GEN_AUTH");
                queryUrl = UrlComposer.getGenAuthUrl(q);
                if (Thread.interrupted()) {
                    return null;
                }
                return cacheHandler.getAuthorPapersFromScholar(queryUrl);
            case GEN_JOURN:
                queryUrl = UrlComposer.getGenJournUrl(q);
                if (Thread.interrupted()) {
                    return null;
                }
                return cacheHandler.getJournalPapersFromScholar(queryUrl);
            case MET_AUTH:
                if (q.start_result > 0 && (q.url == null || "".equals(q.url))) {
                    return null;
                } else if (q.start_result > 0) {
                    queryUrl = q.url;
                } else {
                    queryUrl = UrlComposer.getMetAuthUrl(q);
                }
                if (Thread.interrupted()) {
                    return null;
                }
                return cacheHandler.getAuthorList(queryUrl);
            case MET_JOURN:
                queryUrl = UrlComposer.getMetJournUrl(q);
                // logger.debug("@@##$$"+queryUrl);
                if (Thread.interrupted()) {
                    return null;
                }
                return cacheHandler.getJournalList(queryUrl);
            case AUTH_PROF:
                queryUrl = UrlComposer.getAuthProfUrl(q);
                if (Thread.interrupted()) {
                    return null;
                }
                return cacheHandler.getCompleteAuthorFromMetric(queryUrl);
            case JOURN_PROF:
                queryUrl = q.url + "&cstart=" + q.start_result;
                return cacheHandler.getCompleteJournalFromMetric(queryUrl);
            case IMAGE_FROM_LINK:
                logger.debug("Url : " + q.url);
                queryUrl = q.url;
                return cacheHandler.getImageFromLink(queryUrl);
            case CITATIONS_LIST:
                queryUrl = q.url + "&start=" + q.start_result + "&num=" + q.num_results + "&as_vis=1&as_sdt=1%2C5";
                return cacheHandler.getCitationsList(queryUrl);
            case CITATIONS_LIST_METRIC:
                queryUrl = q.url + "&cstart=" + q.start_result + "&as_vis=1&as_sdt=1%2C5";
                return cacheHandler.getCitationsListFromMetric(queryUrl);
            case ADV_SRCH:
                queryUrl = UrlComposer.getAdvancedSearchUrl(q);
                return cacheHandler.getAdvancedSearchResult(queryUrl);
            default:
                return null;
        }

    }
}
