/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.communication.networking;

import citalyser.util.CProxy;
import citalyser.util.Config;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abhishek
 */
public class HttpConnectionUtils {
    public static List<CProxy> getProxyList()
    {
        List<CProxy> proxies = Config.getProxylist();
        if(proxies == null || proxies.size() == 0)
        {
            proxies = new ArrayList<>();
            proxies.add(new CProxy());
        }
        return proxies;
    }
}
