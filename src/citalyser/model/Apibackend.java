/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model;

import citalyser.Constants.queryType;
import citalyser.networking.HttpConnection;
import citalyser.parsing.Extractdata;
import citalyser.queryresult.QueryResult;

/**
 *
 * @author rajkumar
 */
public class Apibackend {
    
    public QueryResult getQueryResult(String URL,queryType q){
        String html ;
        QueryResult qResult = null;
        html = HttpConnection.getUrlText(URL);
        switch(q){
            case GEN_AUTH:
//                qResult = Extractdata.extractInfo(html);
                break;
            case GEN_JOURN:
//                qResult = Extractdata.extractInfo(html);
                break;
            case MET_AUTH:

                break;
                
            case MET_JOURN:
                
                break;
                
            case AUTH_PROF:
                
                break;
                
            case JOURN_PROF:
                
                break;
        }
        return qResult;
    }
}
