package citalyser.networking;

import citalyser.Config;
import citalyser.Constants;
import citalyser.Main;
import citalyser.util.CProxy;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author aravind
 */

public class HttpConnection {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HttpConnection.class.getName());
    //@constants
    
    //@variables
    private static HttpURLConnection connection;
    
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
    public static void connectUrl(String requestURL,CProxy cproxy,String agentname)
        throws IOException, URISyntaxException {
        URL url = new URI(requestURL).toURL();
        Proxy proxy;
        if(!cproxy.getnoProxy())
            proxy = new Proxy(Proxy.Type.HTTP, 
                                new InetSocketAddress(cproxy.getHostName(),cproxy.getPort()));
        else
            proxy = Proxy.NO_PROXY;
        connection = (HttpURLConnection) (url.openConnection(proxy)); 
        //connection.setInstanceFollowRedirects(true);
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.setConnectTimeout(Constants.SERVER_READOUT_TIME);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "text/html; charset=ISO-8859-1");
        connection.setRequestProperty("User-Agent", agentname);        
    }

    /**
     *  Fetches the html page content of the url using set of proxies
     * @param url The url from which content to be fetched
     * @return The html response content of the connected url
     */
    public static String getUrlText(String url) {
        
        logger.info("Getting URL Text for : " + url);
        int responseCode = 0;
        StringBuffer urlResponse = null;
        
        //Get proxies from file and add them
        List<CProxy> proxies = Config.getProxylist();
        if(proxies == null)
        {
            proxies = new ArrayList<CProxy>();
            proxies.add(new CProxy());
        }
        logger.debug("No of Proxies : " + proxies.size());
        for(int i = 0;i<proxies.size(); i++)
        {
            for(int j = 0;j<Constants.userAgents.length; j++)
            {
                logger.debug("Proxy : " + proxies.get(i).toString() +" UserAgent : "+Constants.userAgents[j]);
                try{
                    connectUrl(url,proxies.get(i),Constants.userAgents[j]);
                    responseCode = connection.getResponseCode();
                    if(responseCode == Constants.OK_Response_Code)
                    {
                        /* update ProxyList */
                        updateProxyList(proxies,i);
                        /* Saving the html content */
                        DataInputStream response = new DataInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                        urlResponse= new StringBuffer();
                        String line;
                        while ((line = reader.readLine()) != null){
                            urlResponse.append(line);
                        }
                        return urlResponse.toString();
                        
                    }
                }catch(ConnectException | UnknownHostException ex){
                    //ex.printStackTrace();
                    logger.error("Proxy : " + proxies.get(i) + " not working");
                    break;
                }catch(SocketTimeoutException ex){
                    logger.error("Connection Timeout Connecting to  : " + proxies.get(i).toString());
                    break;
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    logger.error("Error fetching content : " + ex.getMessage());
                }
            }
        }
        Main.getDisplayController().displayErrorMessage("We could not connect to Google Scholar from any of the Proxies. "
                    + "Please check your ProxyList or Try again Later.");
        logger.error("We could not connect to Google Scholar from any of the Proxies. "
                    + "Please check your ProxyList or Try again Later.");
        Main.getDisplayController().displayErrorMessage("We could not connect to Google Scholar from any of the Proxies. "
                    + "Please check your ProxyList or Try again Later.");
        return null;
    }
    
    public static void updateProxyList(List<CProxy> proxies,int index)
    {
        CProxy cproxy = proxies.get(index);
        if(!cproxy.getnoProxy())
        {    
            logger.debug("Shifting proxy to top : " + cproxy.toString());
            proxies.remove(index);
            proxies.add(0,cproxy);
            Config.setProxyList(proxies);
        }
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
