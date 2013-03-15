/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/

package citalyser.cache;

import citalyser.Constants;
import citalyser.queryresult.AuthorListResult;
import citalyser.queryresult.AuthorResult;
import citalyser.queryresult.JournalListResult;
import citalyser.queryresult.JournalResult;
import citalyser.queryresult.PaperCollectionResult;
import citalyser.queryresult.QueryResult;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import org.apache.log4j.Logger;


public class CacheHandler {
   
    private static Logger logger = Logger.getLogger(CacheHandler.class.getName());
    private CacheHandler cacheHandler;
    public CacheHandler()
    {
        cacheHandler = new CacheHandler();
    }
   
    public  Object getObject(String url)
    {
        String hashCode = getSHA1(url);
        FileInputStream file;
        try{
            file = new FileInputStream(hashCode);
        } catch(FileNotFoundException e) {
            System.out.println(e);
            return null;
        }
        Object result;
        try (ObjectInputStream ObjIn = new ObjectInputStream(file)) {
            result = ObjIn.readObject();
        }  catch(Exception e) {
            System.out.println(e);
            return null;
        }
        return result;
    }
    
    public int setObject(QueryResult qr, String url)
    {
        String hashCode = getSHA1(url);
        try (ObjectOutputStream ObjOut = new ObjectOutputStream(new FileOutputStream(hashCode))) {
            ObjOut.writeObject(qr);
        } catch(Exception e) {
            System.out.println(e);
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
                for (int i=0; i < 8; i++) {
                        result += Integer.toString(( theDigest[i] & 0xff ) + 0x100, 16).substring(1);
                }
                return result;
        } catch (Exception e) {
                e.printStackTrace();
        }
        return result;
   }
    
       /* Query Case - GEN_AUTH */
    public QueryResult getAuthorPapersFromScholar(String queryUrl)
    {
        Object cacheResult = cacheHandler.getObject(queryUrl);
        if(cacheResult!=null) {
            return (PaperCollectionResult)cacheResult;
        }
        
        //TODO web query handler
        return null;
    }
    
    /* Query Case - GEN_JOURN */
    public QueryResult getJournalPapersFromScholar(String queryUrl)
    {
        Object cacheResult = cacheHandler.getObject(queryUrl);
        if(cacheResult!=null) {
            return (PaperCollectionResult)cacheResult;
        }
        
        //TODO web query handler
        
        return null;
    }
    
    /* Query Case - MET_AUTH */
    public QueryResult getAuthorList(String queryUrl)
    {
        Object cacheResult = cacheHandler.getObject(queryUrl);
        if(cacheResult!=null) {
            return (AuthorListResult)cacheResult;
        }
        
        //TODO web query handler
        return null;
    }
    
    /* Query Case - MET_JOURN */
    public QueryResult getJournalList(String queryUrl)
    {
        Object cacheResult = cacheHandler.getObject(queryUrl);
        if(cacheResult!=null) {
            return (JournalListResult)cacheResult;
        }
        
        //TODO web query handler
        return null;
    }
    
    /* Query Case - AUTH_PROF */
    public QueryResult getCompleteAuthorFromMetric(String queryUrl)
    {
        Object cacheResult = cacheHandler.getObject(queryUrl);
        if(cacheResult!=null) {
            return (AuthorResult)cacheResult;
        }
        
        //TODO web query handler
        return null;
    }
    
    /* Query Case - JOURN_PROF */
    public QueryResult getCompleteJournalFromMetric(String queryUrl)
    {
        Object cacheResult = cacheHandler.getObject(queryUrl);
        if(cacheResult!=null) {
            return (JournalResult)cacheResult;
        }
        
        //TODO web query handler
        return null;
    }
}   
