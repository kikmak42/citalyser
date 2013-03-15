/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/

package citalyser.cache;

import citalyser.Constants;
import citalyser.queryresult.QueryResult;
import java.security.MessageDigest;
import org.apache.log4j.Logger;


public class CacheHandler {
   
    private static Logger logger = Logger.getLogger(CacheHandler.class.getName());
    public CacheHandler()
    {
    }
   
    public QueryResult getObject(String key,Class<? extends QueryResult<?>> queryResultType)
    {
        System.out.println(key);
        String hashcode = getSHA1(key);
        System.out.println(hashcode);
        return null;
    }
    
    public int setObject(QueryResult qr, String key)
    {
        return 0;
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
}   
