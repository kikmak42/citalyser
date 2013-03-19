/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/
package citalyser.dataextraction.cache;

import citalyser.Main;
import citalyser.communication.networkparsermanager.Manager;
import citalyser.model.query.queryresult.AuthorListResult;
import citalyser.model.query.queryresult.AuthorResult;
import citalyser.model.query.queryresult.ImageResult;
import citalyser.model.query.queryresult.JournalListResult;
import citalyser.model.query.queryresult.JournalResult;
import citalyser.model.query.queryresult.PaperCollectionResult;
import citalyser.model.query.QueryResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class CacheHandler {

    private static Logger logger = Logger.getLogger(CacheHandler.class.getName());
    private Manager manager;

    public CacheHandler() {
        manager = new Manager();
    }

    private Object getObject(String url) {
        logger.debug("URL: " + url);
        String hashCode = getSHA1(url);
        File file = new File(Main.CacheDirectory, hashCode);
        logger.debug("File name : " + file.getAbsolutePath());
        FileInputStream finputstream;
        try {
            finputstream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("Cache file not found: " + e.getMessage());
            return null;
        }
        Object result;
        try (ObjectInputStream ObjIn = new ObjectInputStream(finputstream)) {
            result = ObjIn.readObject();
        } catch (Exception e) {
            logger.error("Error reading Cache file  : " + e.getMessage());
            return null;
        }
        return result;
    }

    public int setObject(QueryResult qr, String url) {
        if(qr == null )
            return 0;
        String hashCode = getSHA1(url);
        File file = new File(Main.CacheDirectory, hashCode);
        logger.info("Setting Cache at : " + file.getAbsolutePath());
        try (ObjectOutputStream ObjOut = new ObjectOutputStream(new FileOutputStream(file))) {
            ObjOut.writeObject(qr);
            ObjOut.flush();
            ObjOut.close();
        } catch (Exception e) {
            logger.error("Error writing Cache : " + e.getMessage());
            return 0;
        } finally {
            return 1;
        }
    }

    private String getSHA1(String key) {
        String result = "";
        try {
            byte[] bytesofMessage = key.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            final byte[] theDigest = md.digest(bytesofMessage);
            for (int i = 0; i < 8; i++) {
                result += Integer.toString((theDigest[i] & 0xff) + 0x100, 16).substring(1);
            }
            return result;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /* Query Case - GEN_AUTH */
    public QueryResult getAuthorPapersFromScholar(String queryUrl) {

        Object cacheResult = getObject(queryUrl);
        if (cacheResult != null) {
            logger.info("Getting GEN_AUTH - Cache hit");
            PaperCollectionResult q = (PaperCollectionResult) cacheResult;
            return q;
        } else {
            logger.info("Getting GEN_AUTH - Cache miss.");
            QueryResult q = manager.getAuthorPapersFromScholar(queryUrl);
            setObject(q, queryUrl);
            logger.debug(q);
            return q;
        }
    }

    /* Query Case - GEN_JOURN */
    public QueryResult getJournalPapersFromScholar(String queryUrl) {
        Object cacheResult = getObject(queryUrl);
        if (cacheResult != null) {
            logger.info("Getting GEN_JOURN - Cache hit");
            return (PaperCollectionResult) cacheResult;
        } else {
            logger.info("Getting GEN_JOURN - Cache miss.");
            QueryResult q = manager.getJournalPapersFromScholar(queryUrl);
            setObject(q, queryUrl);
            return q;
        }
    }

    /* Query Case - MET_AUTH */
    public QueryResult getAuthorList(String queryUrl) {
        Object cacheResult = getObject(queryUrl);
        if (cacheResult != null) {
            logger.info("Getting MET_AUTH - Cache hit");
            return (AuthorListResult) cacheResult;
        } else {
            logger.info("Getting MET_AUTH - Cache miss");
            QueryResult q = manager.getAuthorList(queryUrl);
            setObject(q, queryUrl);
            return q;
        }
    }

    /* Query Case - MET_JOURN */
    public QueryResult getJournalList(String queryUrl) {
        Object cacheResult = getObject(queryUrl);
        if (cacheResult != null) {
            logger.info("Getting MET_JOURN - Cache hit");
            return (JournalListResult) cacheResult;
        } else {
            logger.info("Getting MET_JOURN - Cache miss");
            QueryResult q = manager.getJournalList(queryUrl);
            setObject(q, queryUrl);
            return q;
        }
    }

    /* Query Case - AUTH_PROF */
    public QueryResult getCompleteAuthorFromMetric(String queryUrl) {
        Object cacheResult = getObject(queryUrl);
        if (cacheResult != null) {
            logger.info("Getting AUTH_PROF - Cache hit");
            return (AuthorResult) cacheResult;
        } else {
            logger.info("Getting AUTH_PROF - Cache miss");
            QueryResult q = manager.getCompleteAuthorFromMetric(queryUrl);
            setObject(q, queryUrl);
            return q;
        }
    }

    /* Query Case - JOURN_PROF */
    public QueryResult getCompleteJournalFromMetric(String queryUrl) {
        Object cacheResult = getObject(queryUrl);
        if (cacheResult != null) {
            logger.info("Getting JOURN_PROF - Cache hit");
            return (JournalResult) cacheResult;
        } else {
            logger.info("Getting JOURN_PROF - Cache miss");
            QueryResult q = manager.getCompleteJournalFromMetric(queryUrl);
            setObject(q, queryUrl);
            return q;
        }
    }
    
    public QueryResult getImageFromLink(String queryUrl)
    {
        Object cacheResult = getObject(queryUrl);
        if (cacheResult != null) {
            logger.info("Getting IMAGE_FROM_LINK - Cache hit");
            return (ImageResult)cacheResult;
        } else {
            logger.info("Getting IMAGE_FROM_LINK - Cache miss");
            QueryResult q = manager.getImageFromLink(queryUrl);
            setObject(q, queryUrl);
            return q;
        }
    }

    public QueryResult getCitationsList(String queryUrl) {
        Object cacheResult = getObject(queryUrl);
        if (cacheResult != null) {
            logger.info("Getting CITATIONS_LIST - Cache hit");
            return (PaperCollectionResult)cacheResult;
        } else {
            logger.info("Getting CITATIONS_LIST - Cache miss");
            QueryResult q = manager.getCitationsList(queryUrl);
            setObject(q, queryUrl);
            return q;
        }
    }
    
}
