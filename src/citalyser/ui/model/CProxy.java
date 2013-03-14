/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/
package citalyser.ui.model;

import java.io.Serializable;

/**
 *
 * @author abhishek
 */
public class CProxy implements Serializable{
    public String host;
    public int port;
    public String username;
    public String password;
    
    public CProxy(String host, int port, String username, String password)
    {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    public CProxy(String host, int port)
    {
        this.host = host;
        this.port = port;
        this.username = null;
        this.password = null;
    }
    public String toString()
    {
        return host + ":"+port;
    }
    
    public String getHostName()
    {
        return host;
    }
}
