package hall;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HttpConnection {

    private static HttpURLConnection connection;
    
    public HttpConnection() {
        
    }
    
    /**
     * Makes an HTTP request to the specified URL.
     * 
     * @param requestURL
     *            the URL of the remote server
     * @return An HttpURLConnection object
     * @throws IOException
     *             thrown if any I/O error occurred
     */
    public static HttpURLConnection connectUrl(String requestURL)
            throws IOException {
        URL url = new URL(requestURL);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.3.100.212", 8080));
        connection = (HttpURLConnection) url.openConnection(proxy); 
        connection.setRequestMethod("GET");
        HttpURLConnection.setFollowRedirects(true);
        connection.setReadTimeout(13000);
        //connection.addRequestProperty("User-Agent", "Foo?");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");
        return connection;
    }

    public void getUrlText(String url) {
        
        try {
            HttpURLConnection connection = connectUrl(url);    
             /* Check for errors in response codes*/
            //int responseCode = ((HttpURLConnection)connection).getResponseCode();
            //if(responseCode < 0 || responseCode >=400)
            //{
                //logger.error("Response Code : " + responseCode);
                //badDataList.add(url);
                //return null;
            //}
            /* Check if content type of page is text*
            contentType = connection.getContentType();
            if(contentType != null)
            {
                if(contentType.contains("application") || contentType.contains("audio") || 
                        contentType.contains("video") || contentType.contains("image"))
                    {
                        //logger.info("Error: File of type "+contentType+" : url");
                        //badDataList.add(url);
                        //return null;
                    }
                }
            */
            DataInputStream response = new DataInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            //System.out.println(sb.toString());
        }
        catch (IOException ex) {
            Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getCookies(String fullyQualifiedURL){
        //try {            
            // Get all cookies from the server.
            // Note: The first call to getHeaderFieldKey() will implicit send the HTTP request to the server.
            for (int i=0; ; i++) {
                String headerName = connection.getHeaderFieldKey(i);
                String headerValue = connection.getHeaderField(i);

                // No more headers
                if (headerName == null || headerValue == null) {
                    System.out.println("Null\n");
                    break;
                }

                System.out.println("Header name: " + headerName + "Header value:" + headerValue);

                if ("Set-Cookie".equalsIgnoreCase(headerName)) {
                    // Parse cookie. 
                    //TODO: Splits the chain at every semicolon (;) followed by a tab, space and so on..
                    String[] fields = headerValue.split(";\\s*");

                    String cookieValue = fields[0];
                    String expires = null;
                    String path = null;
                    String domain = null;
                    boolean secure = false;

                    // Parse each field
                    for (int j=1; j<fields.length; j++) {
                        if ("secure".equalsIgnoreCase(fields[j])) {
                            secure = true;
                        } else if (fields[j].indexOf('=') > 0) {
                            String[] f = fields[j].split("=");
                            if ("expires".equalsIgnoreCase(f[0])) {
                                expires = f[1];
                            } else if ("domain".equalsIgnoreCase(f[0])) {
                                domain = f[1];
                            } else if ("path".equalsIgnoreCase(f[0])) {
                                path = f[1];
                            }
                        }
                    }
                    // Save the cookie..
                    //add(cookieValue);                
                }
            }
          //}//dcatch (MalformedURLException e) {
	//} catch (IOException e) {
	//}
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
