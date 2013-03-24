/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.communication.networkparsermanager;

import citalyser.communication.networking.HttpClient;
import citalyser.communication.networking.HttpConnection;
import citalyser.dataextraction.parsing.Parser;
import citalyser.model.query.queryresult.ImageResult;
import citalyser.model.query.QueryResult;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;

/**
 *
 * @author NEHAL
 */
public class Manager {

    private static Logger logger = Logger.getLogger(Manager.class.getName());
    private Parser parser;
    /* Query Case - GEN_AUTH */
    public Manager() {
        parser = new Parser();
    }

//    public QueryResult getAuthorPapersFromScholar(String url) {
//        logger.info("GettingAuthorPapers from Network - " + url);
//        String html = HttpClient.getUrlText(url);
//
//        if (html != null) {
//            return parser.extractGeneralQuery(html);
//        } else {
//            logger.info("null result form httpconnection");
//            return null;
//        }
//    }
//
//    /* Query Case - GEN_JOURN */
//    public QueryResult getJournalPapersFromScholar(String url) {
//        logger.info("GettingJournalPapers from Network - " + url);
//        String html = HttpClient.getUrlText(url);
//        if (html != null) {
//            return parser.extractGeneralQuery(html);
//        } else {
//            logger.info("null result form httpconnection");
//            return null;
//        }
//    }
//
//    /* Query Case - MET_AUTH */
//    public QueryResult getAuthorList(String url) {
//        logger.info("GettingAuthorList from Network - " + url);
//        String html = HttpClient.getUrlText(url);
//        if (html != null) {
//            return parser.getAuthorList(html);
//        } else {
//            logger.info("null result from httpconnection");
//            return null;
//        }
//    }
//
//    /* Query Case - MET_JOURN */
//    public QueryResult getJournalList(String url) {
//        logger.info("GettingJournalList from Network - " + url);
//        String html = HttpClient.getUrlText(url);
//        if (html != null) {
//            return parser.extractJournalListFromMetric(html);
//        } else {
//            logger.info("null result from httpconnection");
//            return null;
//        }
//    }
//
//    /* Query Case - AUTH_PROF */
//    public QueryResult getCompleteAuthorFromMetric(String url) {
//        String html = HttpClient.getUrlText(url);
//        if (html != null) {
//            return new Parser().extractAuthorProfileInfo(html);
//        } else {
//            return null;
//        }
//    }
//
//    /* Query Case - JOURN_PROF */
//    public QueryResult getCompleteJournalFromMetric(String url) {
//        String html = HttpClient.getUrlText(url);
//        if (html != null) {
//            return new Parser().extractMetricJournalInfo(html);
//        } else {
//            return null;
//        }
//    }
//
//    /* Query Case - IMAGE_FROM_LINK */
//    public QueryResult getImageFromLink(String url) {
//        BufferedImage img = HttpClient.getImageFromUrl(url);
//        if (img != null) {
//            QueryResult<ImageIcon> q = new ImageResult();
//            q.setContents(new ImageIcon(img));
//            return q;
//        }
//        return null;
//
//    }
//
//    /* Query Case - CITATIONS_LIST */
//    public QueryResult getCitationsList(String url) {
//        logger.info("GettingCitationsList from Network - " + url);
//        String html = HttpClient.getUrlText(url);
//        if (html != null) {
//            return parser.extractGeneralQuery(html);
//        } else {
//            logger.info("null result from httpconnection");
//            return null;
//        }
//    }

    /* ----------------Original Functions --------------*/
    public QueryResult getAuthorPapersFromScholar(String url) {
        logger.info("GettingAuthorPapers from Network - " + url);
        String html = HttpConnection.getUrlText(url);

        if (html != null) {
            return parser.extractGeneralQuery(html);
        } else {
            logger.info("null result form httpconnection");
            return null;
        }
    }

    /* Query Case - GEN_JOURN */
    public QueryResult getJournalPapersFromScholar(String url) {
        logger.info("GettingJournalPapers from Network - " + url);
        String html = HttpConnection.getUrlText(url);
        if (html != null) {
            return parser.extractGeneralQuery(html);
        } else {
            logger.info("null result form httpconnection");
            return null;
        }
    }

    /* Query Case - MET_AUTH */
    public QueryResult getAuthorList(String url) {
        logger.info("GettingAuthorList from Network - " + url);
        String html = HttpConnection.getUrlText(url);
        if (html != null) {
            return parser.getAuthorList(html);
        } else {
            logger.info("null result from httpconnection");
            return null;
        }
    }

    /* Query Case - MET_JOURN */
    public QueryResult getJournalList(String url) {
        logger.info("GettingJournalList from Network - " + url);
        String html = HttpConnection.getUrlText(url);
        if (html != null) {
            return parser.extractJournalListFromMetric(html);
        } else {
            logger.info("null result from httpconnection");
            return null;
        }
    }

    /* Query Case - AUTH_PROF */
    public QueryResult getCompleteAuthorFromMetric(String url) {
        String html = HttpConnection.getUrlText(url);
        if (html != null) {
            return new Parser().extractAuthorProfileInfo(html);
        } else {
            return null;
        }
    }

    /* Query Case - JOURN_PROF */
    public QueryResult getCompleteJournalFromMetric(String url) {
        String html = HttpConnection.getUrlText(url);
        if (html != null) {
            return new Parser().extractMetricJournalInfo(html);
        } else {
            return null;
        }
    }

    /* Query Case - IMAGE_FROM_LINK */
    public QueryResult getImageFromLink(String url) {
        BufferedImage img = HttpConnection.getImageFromUrl(url);
        if (img != null) {
            QueryResult<ImageIcon> q = new ImageResult();
            q.setContents(new ImageIcon(img));
            return q;
        }
        return null;

    }

    /* Query Case - CITATIONS_LIST */
    public QueryResult getCitationsList(String url) {
        logger.info("GettingCitationsList from Network - " + url);
        String html = HttpConnection.getUrlText(url);
        if (html != null) {
            return parser.extractGeneralQuery(html);
        } else {
            logger.info("null result from httpconnection");
            return null;
        }
    }
    
    /* Query Case - CITATIONS_LIST */
    public QueryResult getCitationsListFromMetric(String url) {
        logger.info("GettingCitationsList from Metric  - " + url);
        String html = HttpConnection.getUrlText(url);
        if (html != null) {
            return parser.extractCitationResultFromMetric(html);
        } else {
            logger.info("null result from httpconnection");
            return null;
        }
    }
}
