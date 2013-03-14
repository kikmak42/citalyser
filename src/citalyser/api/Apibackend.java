/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.api;

import citalyser.constants.queryType;
import citalyser.networking.HttpConnection;
import citalyser.parsing.Extractdata;

/**
 *
 * @author rajkumar
 */
public class Apibackend {
    public PaperCollection getResults(String URL,queryType q){
        String html ;
        PaperCollection p = null;
        html = HttpConnection.getUrlText(URL);
        switch(q){
            case GEN_AUTH:
                p = Extractdata.extractInfo(html);
                break;
            case GEN_JOURN:
                p = Extractdata.extractInfo(html);
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
        return p;
    }
}
