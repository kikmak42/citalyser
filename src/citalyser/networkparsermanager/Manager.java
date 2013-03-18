/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.networkparsermanager;

import citalyser.Config;
import citalyser.Initialiser;
import citalyser.Main;
import citalyser.cache.CacheHandler;
import citalyser.model.UrlComposer;
import citalyser.networking.HttpConnection;
import citalyser.parsing.Parser;
import citalyser.queryresult.ImageResult;
import citalyser.queryresult.PaperCollectionResult;
import citalyser.queryresult.QueryResult;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.text.Utilities;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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

    public QueryResult getAuthorPapersFromScholar(String url) {
        logger.info("GettingAuthorPapers from Network - " + url);
        String html = HttpConnection.getUrlText(url);

        if (html != null) {
            return parser.extractInfo(html);
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
            return parser.extractInfo(html);
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
            return parser.getAuthors(html);
        } else {
            logger.info("null result form httpconnection");
            return null;
        }
    }

    /* Query Case - MET_JOURN */
    public QueryResult getJournalList(String url) {
        String html = HttpConnection.getUrlText(url);
        return null;
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
        return null;
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
}
