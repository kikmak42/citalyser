/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/
package citalyser;

import java.io.Serializable;

/**
 *
 * @author abhishek
 */
public class Proxy implements Serializable{
    public String host;
    public int port;
    public String username;
    public String password;
    
    public Proxy(String host, int port, String username, String password)
    {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    public Proxy(String host, int port)
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
}
