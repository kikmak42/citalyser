package citalyser.api;

import java.util.*;

class CompareYear implements Comparator<Paper> {

    @Override
    public int compare(Paper p1, Paper p2) {
        return p1.getYear() - p2.getYear();
    }
}

class CompareCite implements Comparator<Paper>{
    @Override
	public int compare(Paper p1,Paper p2){
		return p1.getNumCites()-p2.getNumCites();
	}
}

/**
 *
 * @author rajkumar
 */
class Query{
    public int flag;
    public String name;
    public int max_year;
    public int min_year;
    public int start_page;
    public int end_page;
}
/**
 * Sets System Proxy
 * @param host=proxy host
 *        port=proxy port
 *        user=username
 *        password
 * 
 * @return null
 * 
 * @author vikassearchPanel.getSearchString()
 */
class ProxyUtilities {

    public void setProxy(String host, String port, String user, String password) {
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", port);
        System.setProperty("http.proxyUser", user);
        System.setProperty("http.proxyPassword", password);
    }
}
