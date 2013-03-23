package citalyser.communication.networking;

import citalyser.util.CommonUtils;
import citalyser.util.Config;
import citalyser.Constants;
import citalyser.Main;
import citalyser.util.CProxy;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

/**
 *
 * @author aravind
 */

public class HttpConnection {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HttpConnection.class.getName());
    //@constants
    
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
    public static HttpURLConnection connectUrl(String requestURL,CProxy cproxy,String agentname)
        throws IOException, URISyntaxException {
        
        HttpURLConnection connection;
        //requestURL = URLEncoder.encode(requestURL,"UTF-8");
        logger.debug("Request Url : " + requestURL);
        URL url = new URL(requestURL);
        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(),null);
        //URL url = new URI(requestURL).toURL();
        Proxy proxy = CommonUtils.getJavaProxyFromCProxy(cproxy);
        
        connection = (HttpURLConnection) (uri.toURL().openConnection(proxy)); 
        //connection.setInstanceFollowRedirects(true);
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.setConnectTimeout(Constants.SERVER_READOUT_TIME);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "text/html; charset=ISO-8859-1");
        connection.setRequestProperty("User-Agent", agentname);
        return connection;
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
        if(proxies == null || proxies.size() == 0)
        {
            proxies = new ArrayList<>();
            proxies.add(new CProxy());
        }
        logger.debug("No of Proxies : " + proxies.size());
        for(int i = 0;i<proxies.size(); i++)
        {
            for(int j = 0;j<Constants.userAgents.length; j++)
            {
                logger.debug("Proxy : " + proxies.get(i).toString() +" UserAgent : "+Constants.userAgents[j]);
                Main.getDisplayController().displayStatusMessage("Trying Proxy " + proxies.get(i).toString());
                try{
                    HttpURLConnection connection = connectUrl(url,proxies.get(i),Constants.userAgents[j]);
                    responseCode = connection.getResponseCode();
                    logger.debug("Response Code: " + responseCode);
                    if(responseCode == Constants.OK_Response_Code)
                    {
                        /* update ProxyList */
                        if(i!=0)
                            updateProxyList(proxies,i);
                        /* Saving the html content */
                        DataInputStream response = new DataInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                        urlResponse= new StringBuffer();
                        String line;
                        while ((line = reader.readLine()) != null){
                            urlResponse.append(line);
                        }
                        connection.disconnect();
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
        logger.error("We could not connect to Google Scholar from any of the Proxies. "
                    + "Please check your ProxyList or Try again Later.");
        Main.getDisplayController().displayErrorMessage("We could not connect to Google Scholar from any of the Proxies. "
                    + "Please check your ProxyList or Try again Later.");
        return null;
    }
    
    public static BufferedImage getImageFromUrl(String url)
    {
        logger.info("Getting Image from : " + url);
        int responseCode = 0;
        BufferedImage image = null;
        List<CProxy> proxies = Config.getProxylist();
        if(proxies == null || proxies.size() ==0)
        {
            proxies = new ArrayList<>();
            proxies.add(new CProxy());
        }
        logger.debug("No of Proxies : " + proxies.size());
        for(int i = 0;i<proxies.size(); i++)
        {
            logger.debug("Proxy : " + proxies.get(i).toString() +" UserAgent : "+Constants.userAgents[0]);
            try
            {
                HttpURLConnection connection = connectUrl(url,proxies.get(i),Constants.userAgents[0]);
                responseCode = connection.getResponseCode();
                logger.debug("Response Code : " + responseCode);
                if(responseCode == Constants.OK_Response_Code)
                {
                    /* Get the image content */
                    InputStream inStream = connection.getInputStream();
                    image = ImageIO.read(inStream);
                    logger.debug("Returning image.");
                    connection.disconnect();
                    return image;
                }
                else if(responseCode == Constants.NOT_FOUND_Code)
                {
                    return null;
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
                logger.error("Error fetching Image : " + ex.getMessage());
            }
        }
        //logger.debug("Returning null as image.");
        return null;
    }
    
    public static void updateProxyList(List<CProxy> proxies,int index)
    {
        CProxy cproxy = proxies.get(index);
        //CommonUtils.setSystemProxy(cproxy);
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
//    public static void disconnect() {
//        if (connection != null) {
//            connection.disconnect();
//        }
//    }
}
