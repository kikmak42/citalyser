
package citalyser.communication.networking;
import citalyser.Constants;
import citalyser.Main;
import citalyser.util.CProxy;
import citalyser.util.Config;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author abhishek
 */
public class HttpClient {
    private static DefaultHttpClient client;
	private static HttpContext localContext;
	private static CookieStore cookiestore;
	private static HttpHost proxy;
	//Constructor
	public static void init()
	{
		client = new DefaultHttpClient();
		localContext = new BasicHttpContext();
		cookiestore = new BasicCookieStore();
                //localContext.setAttribute(ClientContext.COOKIE_STORE, cookiestore);
		client.getParams().setParameter("http.useragent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:12.0) Gecko/20100101 Firefox/12.0");
		client.getParams().setParameter("http.protocol.handle-redirects", true);
		client.getParams().setParameter("http.protocol.max-redirects", 1);
	}
	
	public static String getUrlText(String url)
	{
		HttpGet request;
		HttpResponse response;
		HttpEntity entity;
		String content = "";
		try
		{
                    List<CProxy> proxies = Config.getProxylist();
                    for(int i = 0;i<proxies.size(); i++)
                    {          
                        CProxy cproxy = proxies.get(i);
                        Main.getDisplayController().displayStatusMessage("Trying Proxy " + proxies.get(i).toString());
                        proxy = new HttpHost(cproxy.getHostName(),cproxy.getPort());
        		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

			request = new HttpGet(url);
			//System.out.println("Fetching " + url);
			response = client.execute(request,localContext);
			int rcode = response.getStatusLine().getStatusCode();
                        if(rcode == Constants.OK_Response_Code)
                        {
			
                            //System.out.println("Initial set of cookies:");
                            List<Cookie> cookies = client.getCookieStore().getCookies();
                            /*if (cookies.isEmpty()) {
                                System.out.println("None");
                            } else {
                                for (int i = 0; i < cookies.size(); i++) {
                                    System.out.println("- " + cookies.get(i).toString());
                                }
                            }*/
                            return EntityUtils.toString(response.getEntity());
                        }
                    }
                    return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static String loginToGoogle(String html,String username,String password)
	{
            Main.getDisplayController().displayStatusMessage("Logging Into Google.");
		HttpPost httpost = new HttpPost("https://accounts.google.com/ServiceLoginAuth");
		HttpResponse response;
		HttpEntity entity;
		String content = "";
		try
		{
			List<NameValuePair> nvps = getFormElements(html, username, password);
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = client.execute(httpost,localContext);
	        entity = response.getEntity();
	
	        List<Cookie> cookies = client.getCookieStore().getCookies();
		    cookies = client.getCookieStore().getCookies();
	        if (cookies.isEmpty()) {
	            System.out.println("None");
	        } else {
	            for (int i = 0; i < cookies.size(); i++) {
	                System.out.println("- " + cookies.get(i).toString());
	            }
	        }
                Main.getDisplayController().displayStatusMessage("Logging Done");
	        return EntityUtils.toString(response.getEntity());
	    	
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
                
	}
	
        public static BufferedImage getImageFromUrl(String url)
	{
                return null;
//		HttpGet request;
//		HttpResponse response;
//		HttpEntity entity;
//		String content = "";
//		try
//		{
//			request = new HttpGet(url);
//			//System.out.println("Fetching " + url);
//			response = client.execute(request,localContext);
//			entity = response.getEntity();
//			//System.out.println("Initial set of cookies:");
//	        List<Cookie> cookies = client.getCookieStore().getCookies();
//	        /*if (cookies.isEmpty()) {
//	            System.out.println("None");
//	        } else {
//	            for (int i = 0; i < cookies.size(); i++) {
//	                System.out.println("- " + cookies.get(i).toString());
//	            }
//	        }*/
//	        return EntityUtils.toString(response.getEntity());
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			return null;
//		}
	}
	
	public static List<NameValuePair> getFormElements(String html,String username, String password)
	{
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Element loginform = doc.getElementById("gaia_loginform");
		Elements inputElements = loginform.getElementsByTag("input");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		for(Element inputElement : inputElements)
		{
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");
			//System.out.println(key+" : " + value);
			if(key.equals("Email"))
				value = username;
			else if(key.equals("Passwd"))
				value = password;
			else if(key.equals("PersistentCookie"))
				value = "no";
			nvps.add(new BasicNameValuePair(key,value));
		}
		//System.out.println(nvps.toString());
		return nvps;
	}
	
        
        //Dispose the client
	public static void close()
	{
		client.getConnectionManager().shutdown();
	}
}
