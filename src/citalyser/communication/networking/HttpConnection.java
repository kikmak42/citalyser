package citalyser.communication.networking;

import citalyser.util.CommonUtils;
import citalyser.util.Config;
import citalyser.Constants;
import citalyser.Main;
import citalyser.util.CProxy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpHost;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.conn.params.ConnRoutePNames;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.util.EntityUtils;

/**
 *
 * @author aravind
 */

public class HttpConnection {
    private static HttpURLConnection connection;
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
        //requestURL = URLEncoder.encode(requestURL,"UTF-8");
        logger.debug("Request Url : " + requestURL);
        URL url = new URL(requestURL);
        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(),null);
        //URL url = new URI(requestURL).toURL();
        Proxy proxy = CommonUtils.getJavaProxyFromCProxy(cproxy);
        
        connection = (HttpURLConnection) (uri.toURL().openConnection(proxy)); 
        //connection.setInstanceFollowRedirects(true);
        //connection.setDoInput(true);
        //connection.setDoOutput(false);
        connection.setConnectTimeout(Constants.SERVER_READOUT_TIME);
        connection.setRequestMethod("GET");
        //connection.setRequestProperty("Content-Type", "text/html; charset=ISO-8859-1");
        connection.setRequestProperty("User-Agent", agentname);
        return connection;
    }

    /**
     *  Fetches the html page content of the url using set of proxies
     * @param url The url from which content to be fetched
     * @return The html response content of the connected url
     */
    public static String getUrlText(String url) {
        HttpURLConnection connection = null;
        logger.info("Getting URL Text for : " + url);
        int responseCode = 0;
        
        //Get proxies from file and add them
        List<CProxy> proxies = HttpConnectionUtils.getProxyList();
        logger.debug("No of Proxies : " + proxies.size());
        for(int i = 0;i<proxies.size(); i++)
        {
            for(int j = 0;j<Constants.userAgents.length; j++)
            {
                logger.debug("Proxy : " + proxies.get(i).toString() +" UserAgent : "+Constants.userAgents[j]);
                Main.getDisplayController().displayStatusMessage("Trying Proxy " + proxies.get(i).toString());
                try{
                    connection = connectUrl(url,proxies.get(i),Constants.userAgents[j]);
                    responseCode = connection.getResponseCode();
                    logger.debug("Response Code: " + responseCode);
                    if(responseCode == Constants.OK_Response_Code)
                    {
                        /* update ProxyList */
                        if(i!=0)
                            updateProxyList(proxies,i);
                        /* Saving the html content */
                        DataInputStream response = new DataInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(response),15000);
                        StringBuffer urlResponse= new StringBuffer();
                        String line;
                        while ((line = reader.readLine()) != null){
                            urlResponse.append(line);
                        }
                        reader.close();
                        Main.getDisplayController().displayStatusMessage("");
                        logger.debug("Returning String");
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
    
    public static String getUrlPage(String urlstr)
    {
    	HttpResponse response;
        HttpEntity entity;
        DefaultHttpClient client = null;
        String content = "";
        int responseCode;
        logger.info("Getting URL Text for : " + urlstr);
        
        List<CProxy> proxies = HttpConnectionUtils.getProxyList();
        logger.debug("No of Proxies : " + proxies.size());
        for(int i = 0;i<proxies.size(); i++)
        {
            for(int j = 0;j<Constants.userAgents.length; j++)
            {
                logger.debug("Proxy : " + proxies.get(i).toString() +" UserAgent : "+Constants.userAgents[j]);
                Main.getDisplayController().displayStatusMessage("Trying Proxy " + proxies.get(i).toString());
                try{
                    client = getHttpClient(proxies.get(i),Constants.userAgents[j]);
                    URL url = new URL(urlstr);
                    URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(),null);
                    HttpGet request = new HttpGet(uri);
                    response = client.execute(request);
                    entity = response.getEntity();
                    responseCode = response.getStatusLine().getStatusCode();
                    logger.debug("Response Code: " + responseCode);
                    if(responseCode == Constants.OK_Response_Code)
                    {
                        /* update ProxyList */
                        if(i!=0)
                            updateProxyList(proxies,i);
                        /* Saving the html content */
                        Main.getDisplayController().displayStatusMessage("");
                        return EntityUtils.toString(response.getEntity());
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
                }finally{
                     //client.getConnectionManager().shutdown();
                }
            }
        }
        logger.error("We could not connect to Google Scholar from any of the Proxies. "
                    + "Please check your ProxyList or Try again Later.");
        Main.getDisplayController().displayErrorMessage("We could not connect to Google Scholar from any of the Proxies. "
                    + "Please check your ProxyList or Try again Later.");
        return null;
    }
    
    private static DefaultHttpClient getHttpClient(CProxy cproxy,String agentname)
    {
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter("http.useragent",agentname);
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);
        //HttpConnectionParams.setSoTimeout(params, 10000);
        if(!cproxy.getnoProxy())
        {
            HttpHost proxy = new HttpHost(cproxy.getHostName(),cproxy.getPort());
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
    	return client;
    }
    public static BufferedImage getImageFromUrl(String url)
    {
        //logger.info("Getting Image from : " + url);
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
            //logger.debug("Proxy : " + proxies.get(i).toString() +" UserAgent : "+Constants.userAgents[0]);
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
                    //logger.debug("Returning image.");
                    inStream.close();
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
