/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/
package citalyser.util;

import java.io.Serializable;

/**
 *
 * @author abhishek
 */
public class CProxy implements Serializable{
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean noProxy;
            
    public CProxy(String host, int port, String username, String password)
    {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        noProxy = false;
    }
    public CProxy(String host, int port)
    {
        this.host = host;
        this.port = port;
        this.username = null;
        this.password = null;
        noProxy = false;
    }
    public CProxy()
    {
        noProxy = true;
    }
    
    public String toString()
    {
        if(noProxy)
            return "No_Proxy";
        else
            return host + ":"+port;
    }
    
    public String getHostName()
    {
        return host;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public boolean getnoProxy()
    {
        return noProxy;
    }
    
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
}
