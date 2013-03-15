package citalyser.queryhandler;

import citalyser.cache.CacheHandler;
import citalyser.model.PaperCollection;
import citalyser.model.Apibackend;
import citalyser.parsing.*;
import citalyser.networking.HttpConnection;
import citalyser.queryresult.QueryResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.*;

/**
 *
 * @author sony
 */


public class QueryHandler {
    
    private CacheHandler cacheHandler;
    
    public QueryHandler()
    {
        cacheHandler = new CacheHandler();
    }
    public QueryResult getDetails(Query q){
        String URL = new String();
        //String query_name = q.name.replace(' ', '+');   // check
        String query_name = q.name;
        //TableModel retval = new DefaultTableModel();
        QueryResult qResult;


        switch(q.flag){
            case GEN_AUTH: 
                URL =  "http://scholar.google.co.in/scholar?";
                URL +=  "start="+ q.start_result +"&";
                URL +=  "as_q=" + "" + "&" ;
                URL +=  "as_epq=" + "" + "&";
                URL +=  "as_oq=" + "" + "&";
                URL += "as_eq=" + "" + "&";
                URL +="as_occt=" + "" + "&";
                URL +="as_sauthors=" + query_name + "&" ; //q.name may contain spaces
                URL +="as_publication=" + "" + "&";
                URL +="as_ylo=" + q.min_year + "&";
                URL +="as_yhi=" + q.max_year + "&";
                URL +="num=" + q.num_results + "&";
                URL += "scisbd=" + q.sort_flag + "&";
                URL +="btnG=&hl=en&as_sdt=1%2C5";
                break;
                
            case GEN_JOURN:
                URL =  "http://scholar.google.co.in/scholar?";
                URL  +=  "start="+ q.start_result +"&";
                URL  +=  "as_q=" + "" + "&" ;
                URL  +=  "as_epq=" + "" + "&";
                URL  +=  "as_oq=" + "" + "&";
                URL += "as_eq=" + "" + "&";
                URL +="as_occt=" + "" + "&";
                URL +="as_sauthors=" + ""+ "&" ;
                URL +="as_publication=" + query_name + "&";
                URL +="as_ylo=" + q.min_year + "&";
                URL +="as_yhi=" + q.max_year + "&";
                URL +="num=" + q.num_results + "&";
                URL += "scisbd=" + q.sort_flag + "&";
                URL +="btnG=&hl=en&as_sdt=1%2C5";
                break;
                
            case MET_JOURN:
                URL = "http://scholar.google.co.in/citations?hl=en&";
                URL += "view_op=search_venues";
                URL += "&vq=" + query_name;
                break;
                
            case MET_AUTH:
                URL = "http://scholar.google.co.in/citations?hl=en&";
                URL += "view_op=search_authors";
                URL += "&mauthors=" + query_name;
                break;
                
            case AUTH_PROF:
                URL = "http://scholar.google.co.in/citations?hl=en&";
                URL += "view_op=list_works&pagesize=100";
                URL += "&user=" + q.ID;
                break;
                
            case JOURN_PROF:
                URL  = "http://scholar.google.co.in/citations?hl=en&";
                URL += "vq=en&view_op=list_hcore&";
                URL += "venue=" + q.ID;
                break;
        }
        

        //NETWORK FUNCTION CALLED HERE
        
        //html = HttpConnection.getUrlText("http://scholar.google.co.in/scholar?hl=en&q=animesh+mukherjee&btnG=&as_sdt=1%2C5&as_sdtp=");
        //html = HttpConnection.getUrlText(URL);
        
        //p = Extractdata.extractInfo(html);
        
        qResult = cacheHandler.getQueryResult(URL,q.flag);
        return qResult;
    }
    
    /*private static String getDummyHtml() {
        String returnValue = "";
        FileReader file = null;

        try {
            file = new FileReader("C:/Users/sony/Desktop/the 'present'/opensoft 2013/html.html");
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            while ((line = reader.readLine()) != null) {
                returnValue += line + "\n";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    // Ignore issues during closing 
                }
            }
        }
        return returnValue;
    }*/
}