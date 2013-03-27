package citalyser.model;

import citalyser.Main;
import citalyser.model.query.Query;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.log4j.Logger;


/**
 *
 * @author rajkumar
 */
public class UrlComposer {
     private static Logger logger = Logger.getLogger(UrlComposer.class.getName());
    public static String getGenAuthUrl(Query q)
    {
        String query_name = q.name;
        //query_name = query_name.replaceAll(" ", "+");
        String URL = new String();
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
        URL +="btnG=&hl=en&as_sdt=1%2C5&as_vis=1";
        logger.debug(URL);
        return URL;
   } 
    
    public static String getGenJournUrl(Query q)
    {
        String query_name = q.name;
        String URL = new String();
        URL =  "http://scholar.google.co.in/scholar?";
        URL  +=  "start="+ q.start_result +"&";
        URL  +=  "as_q=" + "" + "&" ;
        URL  +=  "as_epq=" + "" + "&";
        URL  +=  "as_oq=" + "" + "&";
        URL += "as_eq=" + "" + "&";
        URL +="as_occt=" + "" + "&";
        URL +="as_sauthors=" + "" + "&" ;
        URL +="as_publication=" + query_name + "&";
        URL +="as_ylo=" + q.min_year + "&";
        URL +="as_yhi=" + q.max_year + "&";
        URL +="num=" + q.num_results + "&";
        URL += "scisbd=" + q.sort_flag + "&";
        URL +="btnG=&hl=en&as_sdt=1%2C5&as_vis=1";
        logger.debug(URL);
        return URL;
    }
    
    public static String getMetAuthUrl(Query q)
    {
        String query_name = q.name;
        String URL = new String();
        URL = "http://scholar.google.co.in/citations?hl=en&";
        URL += "view_op=search_authors";
        URL += "&mauthors=" + query_name;
        logger.debug(URL);
        return URL;
    }
    
    public static String getMetJournUrl(Query q)
    {
        String query_name = q.name;
        String URL = new String();
        URL = "http://scholar.google.co.in/citations?hl=en&";
        URL += "view_op=search_venues";
        URL += "&vq=" + query_name;
        logger.debug(URL);
        return URL;
    }
    
    public static String getAuthProfUrl(Query q)
    {
        String URL = new String();
        URL = "http://scholar.google.co.in/citations?hl=en&";
        URL += "view_op=list_works&pagesize="+q.num_results;
        URL += "&user=" + q.ID;
        URL += "&cstart=" + q.start_result;
        logger.debug(URL);
        return URL;
    }
    
    public static String getJournProfUrl(Query q)
    {
        String query_name = q.name;
        String URL = new String();
        URL  = "http://scholar.google.co.in/citations?hl=en&";
        URL += "vq=en&view_op=list_hcore&";
        URL += "venue=" + q.ID;
        logger.debug(URL);
        return URL;
    }
    
    public static String getAdvancedSearchUrl(Query q){
        String query_name = q.name;
        String URL = new String();
        URL =  "http://scholar.google.co.in/scholar?";
        URL +=  "start="+ q.start_result +"&";
        URL +=  "as_q=" + q.allwords + "&" ;
        URL +=  "as_epq=" + q.exactphrase + "&";
        URL +=  "as_oq=" + q.atleastoneofthese + "&";
        URL += "as_eq=" + q.exceptthese + "&";
        URL +="as_occt=" + q.occurwhere + "&";
        URL +="as_sauthors=" + q.authors + "&" ;
        URL +="as_publication=" + q.publishedat + "&";
        URL +="as_ylo=" + q.min_year + "&";
        URL +="as_yhi=" + q.max_year + "&";
        URL +="num=" + q.num_results + "&";
        URL += "scisbd=" + q.sort_flag + "&";
        URL +="btnG=&hl=en&as_sdt=1%2C5&as_vis=1";
        logger.debug(URL);
        return URL;
    }
    
    public static Query encodeQueryParameters(Query q)
    {
        if(q.min_year == "0")
            q.min_year = "";
        if(q.max_year == "0")
            q.max_year = "";
        try{
            q.name = URLEncoder.encode(q.name,"ISO-8859-1");
            return q;
        }catch(Exception ex){
            logger.error("Error encoding URI : " + ex.getMessage());
            return q;
        }
    }
        
}
