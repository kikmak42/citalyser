package citalyser.networking;

import citalyser.Config;
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
    private static ArrayList<String> agents = new ArrayList<String>(3);
    
    public static void saveProxy(String proxy, String userAgent) {
        try {
            System.out.print("Saving to proxy.config.....");
            File file = new File("proxy.txt");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                    file.createNewFile();
            }

            String content = "";
            content += new java.util.Date() +"\n";
            content += proxy+"\n";
            content += userAgent+"\n";
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Completed");

        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
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
    public static void connectUrl(String requestURL, String hostname, String agentname)
        throws IOException {

        URL url = new URL(requestURL);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, 8080));
        connection = (HttpURLConnection) url.openConnection(proxy); 
        connection.setInstanceFollowRedirects(true);
        connection.setFollowRedirects(true);
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.setReadTimeout(10000);
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
        
        StringBuffer urlResponse = null;
        
        List proxies = Config.getProxylist();
        if(HttpConnection.getProxylistSize() == 0) {
            Iterator proxyIterator = proxies.iterator();
            while (proxyIterator.hasNext()) {
                hostnames.add(proxyIterator.next().toString());
            }
        }
        
        if(agents.size() == 0) {
            agents.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201");
            agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.60 Safari/537.17");
            agents.add("Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25");
            agents.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13+ (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2");
            agents.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) Version/5.1.3 Safari/534.53.10");
            agents.add("Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko ) Version/5.1 Mobile/9B176 Safari/7534.48.3");
            agents.add("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; de-at) AppleWebKit/533.21.1 (KHTML, like Gecko) Version/5.0.5 Safari/533.21.1");
            agents.add("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_7; da-dk) AppleWebKit/533.21.1 (KHTML, like Gecko) Version/5.0.5 Safari/533.21.1");
            agents.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; tr-TR) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27");
            agents.add("Opera/9.80 (Windows NT 6.0) Presto/2.12.388 Version/12.14");
            agents.add("Mozilla/5.0 (Windows NT 6.0; rv:2.0) Gecko/20100101 Firefox/4.0 Opera 12.14");
            agents.add("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.0) Opera 12.14");
            agents.add("Opera/12.80 (Windows NT 5.1; U; en) Presto/2.10.289 Version/12.02");
           
        }
        
        String hostname;
        try {
             
            /* Check for errors in response codes*/
            int responseCode = 0;
            
            while(responseCode != 200)
            {
                if(hostnames.size() != 0){
                    hostname = hostnames.remove(0);
                    
                    for(int j=0; j< agents.size() ; j++) {
                        System.out.println(hostname + " - " + agents.get(j));
                        System.out.println();
                        connectUrl(url,hostname,agents.get(j));
                        responseCode = connection.getResponseCode();
                        System.out.println("response = "+responseCode);
                        if(responseCode== 200) break;
                        else saveProxy(hostname, agents.get(j));
                    }
                    if(responseCode == 200)hostnames.add(hostname);
                }
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
    
    public static int getProxylistSize()
    {
        return hostnames.size();
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
