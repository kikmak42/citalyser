/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/

package citalyser.cache;

import citalyser.Constants;
import citalyser.model.Apibackend;
import citalyser.queryresult.QueryResult;

public class CacheHandler {
    
    private Apibackend apibackend;
    
    public CacheHandler()
    {
        apibackend = new Apibackend();
    }
    
    public QueryResult getQueryResult(String url,Constants.queryType q)
    {
        return apibackend.getQueryResult(url,q);
    }
}
