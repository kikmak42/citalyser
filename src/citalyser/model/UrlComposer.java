package citalyser.model;

import citalyser.Main;
import citalyser.queryhandler.Query;
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
        URL +="as_sauthors=" + ""+ "&" ;
        URL +="as_publication=" + query_name + "&";
        URL +="as_ylo=" + q.min_year + "&";
        URL +="as_yhi=" + q.max_year + "&";
        URL +="num=" + q.num_results + "&";
        URL += "scisbd=" + q.sort_flag + "&";
        URL +="btnG=&hl=en&as_sdt=1%2C5&as_vis=1";
        return URL;
    }
    
    public static String getMetAuthUrl(Query q)
    {
        String query_name = q.name;
        String URL = new String();
        URL = "http://scholar.google.co.in/citations?hl=en&";
        URL += "view_op=search_authors";
        URL += "&mauthors=" + query_name;
        return URL;
    }
    
    public static String getMetJournUrl(Query q)
    {
        String query_name = q.name;
        String URL = new String();
        URL = "http://scholar.google.co.in/citations?hl=en&";
        URL += "view_op=search_venues";
        URL += "&vq=" + query_name;
        return URL;
    }
    
    public static String getAuthProfUrl(Query q)
    {
        String URL = new String();
        URL = "http://scholar.google.co.in/citations?hl=en&";
        URL += "view_op=list_works&pagesize=100";
        URL += "&user=" + q.ID;
        return URL;
    }
    
    public static String getJournProfUrl(Query q)
    {
        String query_name = q.name;
        String URL = new String();
        URL  = "http://scholar.google.co.in/citations?hl=en&";
        URL += "vq=en&view_op=list_hcore&";
        URL += "venue=" + q.ID;
        return URL;
    }
    
    public static Query encodeQueryParameters(Query q)
    {
        try{
            q.name = URLEncoder.encode(q.name,"ISO-8859-1");
            return q;
        }catch(Exception ex){
            logger.error("Error encoding URI : " + ex.getMessage());
            return q;
        }
    }
        
}
