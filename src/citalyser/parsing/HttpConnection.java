/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hall;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author aravind
 */

public class HttpConnection {

    private static HttpURLConnection connection;
    private static ArrayList<String> hostnames = new ArrayList<String>(3);
    
    /**
     * 
     * Makes an HTTP request to the specified URL.
     * @param requestURL 
     *              the URL of the remote server
     * @param hostname
     *              the host name to be used
     * @throws IOException 
     *              thrown if any I/O error occurred
     */
    public static void connectUrl(String requestURL, String hostname)
        throws IOException {

        URL url = new URL(requestURL);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, 8080));
        connection = (HttpURLConnection) url.openConnection(proxy); 
        connection.setInstanceFollowRedirects(true);
        
        //connection.connect();
        //InputStream is = connection.getInputStream();
        //System.out.println(is);
        //System.out.println(connection.getURL());
        connection.setFollowRedirects(true);
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.setReadTimeout(10000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "text/html; charset=ISO-8859-1");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");        
    }

    /**
     *  Fetches the html page content of the url using set of proxies
     * @param url The url from which content to be fetched
     * @return The html response content of the connected url
     */
    public static String getUrlText(String url) {
        
        StringBuffer urlResponse = null;
        hostnames.add("10.3.100.212");
        hostnames.add("10.3.100.211");
        hostnames.add("144.16.192.213");
        hostnames.add("144.16.192.216");
        hostnames.add("144.16.192.217");
        hostnames.add("144.16.192.218");
        hostnames.add("144.16.192.245");
        hostnames.add("144.16.192.247");
        
        String hostname;
        try {
            hostname = hostnames.remove(0);
            connectUrl(url,hostname);
            hostnames.add(hostname);
             
            /* Check for errors in response codes*/
            int responseCode = connection.getResponseCode();
            
            while (responseCode != 200)
            {
                hostname = hostnames.remove(0);
                connectUrl(url,hostname);
                hostnames.add(hostname);
                responseCode = connection.getResponseCode();
            }
            
            /* Saving the html content */
            DataInputStream response = new DataInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            urlResponse= new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null){
                urlResponse.append(line);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return urlResponse.toString();
    }
    
    /**
     * Generates the url with complete queries
     * @param url - the base url of the remote host
     * @param params - the parameters of the query object
     * @return - the final url string to connect with queries
     */
    public static String urlGenerator(String url, String [] params){
        
        url += "/scholar?hl=en&start="+params[0];         //page number
        url += "&as_q="+params[1];          //need all these words in article
        url += "&as_epq="+params[2];        //need this exact phrase 
        url += "&as_oq="+params[3];         //atleast on of the words
        url += "&as_eq="+params[4];         //except these words
        url += "&as_occt="+params[5];       //occur where
        url += "&as_sauthors="+params[6];   //authors contain any of these
        url += "&as_publication="+params[7];//published in which journals
        url += "&as_ylo="+params[8];        //lower bound of year of publishing
        url += "&as_yhi="+params[9];        //upper bound of year of publishing
        return url;
    }
    
    /**
     * Closes the connection if opened
     */
    public static void disconnect() {
        if (connection != null) {
            connection.disconnect();
        }
    }
}
