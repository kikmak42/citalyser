/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/

package citalyser.util;

import citalyser.Main;
import citalyser.util.CProxy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
public class Config
{
    static final Logger logger = Logger.getLogger(Config.class.getName());
    
    private static Properties properties;
    private static List<CProxy> proxyList;
    public static int CachePersistentTime = 7;
    public static int CacheCleanerPeriod = 7200000;
    private static File configFile = null;
     
    public static void init(File settingsDir)
    {
        properties = new Properties();
        configFile = new File(Main.settingsDirectory,"Settings.txt");
        if(configFile.exists())
        {
            /*Settings Directory exists. Read the config info. */
            logger.debug("Config file exists");
            readConfigFile();
        }
        if(!configFile.exists())
        {
            try
            {
                configFile.createNewFile();
                writeConfigFile();
            }
            catch(Exception ex)
            {
                logger.error("Error creating Settings File.Using Default Settings..");
                configFile = null;
            }
            
        }
        /* Load the proxy Settings*/
        loadProxylist();
    }
    
    private static int readConfigFile()
    {
        try
        {
            properties.load(new FileInputStream(configFile));
            CachePersistentTime = getInteger(CachePersistentTime,properties.getProperty("CachePersistentTime"));
            CacheCleanerPeriod = getInteger(CacheCleanerPeriod,properties.getProperty("CacheCleanerPeriod"));
            return 0;
        }
        catch(Exception ex)
        {
            logger.debug("Error Reading properties from Settings File. Rewriting properties file.");
            writeConfigFile();
            return -1;
        }
    }
    
    private static int writeConfigFile()
    {
        try
        { 
            properties.setProperty("CachePersistentTime", Integer.toString(CachePersistentTime));
            properties.setProperty("CacheCleanerPeriod", Integer.toString(CacheCleanerPeriod));
            
            properties.store(new FileOutputStream(configFile), null);
            return 0;
        }
        catch(Exception ex)
        {
            logger.error("Error writing Properties to file.");
            return -1;
        }
    }
    
    public static int updateConfigFile()
    {
        int wresult = writeConfigFile();
        int rresult = readConfigFile();
        if(wresult > 0 && rresult > 0) {
            return 0;
        }
        else {
            return -1;
        }
    }
 /* 
  * PROXY Configuration as a hashmap
  */   
    public static int setProxyList(List<CProxy> list)
    {
        logger.debug("Writing the proxies to a file");
        File file = new File(Main.settingsDirectory,"proxies");
        if(!file.exists())
        {
            try{
              logger.debug("Creating new proxies file.");
              file.createNewFile();
            }catch(Exception ex){
                logger.error("Failed to create proxies file");
                return -2;
            }
        }
        try {
            HashMap<Integer,CProxy> hm = new HashMap<>();
            for(int i = 0;i<list.size();i++)
                hm.put(i, list.get(i));
            try (ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(file))) {
                s.writeObject(hm);
                s.flush();
            }
            logger.info("Dumped the proxyList to file.");
            loadProxylist();
            return 0;
        } catch (IOException ex) {
            //ex.printStackTrace();
            logger.error("Error writing Proxy hashmap to file : " + ex.getMessage());
            return -1;
        }
    }
    
    public static List<CProxy> getProxylist()
    {
        return proxyList;
    }
    
    public static void loadProxylist()
    {
        List<CProxy> pl = new ArrayList<>();
        HashMap<Integer,CProxy> hm;  
        File f = new File(Main.settingsDirectory,"proxies");
        if(!f.exists())
            return;
        try{
            try (ObjectInputStream s = new ObjectInputStream(new FileInputStream(f))) {
                hm = (HashMap<Integer,CProxy>)s.readObject();
            }
            for (CProxy proxy : hm.values()) {
                pl.add(proxy);
            }
            Config.proxyList = pl;
        }catch(IOException | ClassNotFoundException ex){
            logger.error("Error getting proxies.");
            f.delete();
            Config.proxyList = null;
        }
    }
    
    /* The methods below are util functions not used now. Might be used in the future.*/
    private static int getInteger(int var, String val)
    {
        try{
            return Integer.parseInt(val); 
        }catch(Exception ex)
        {
            return var;
        }
    }
    
    private static boolean getBoolean(boolean var,String val)
    {
        switch (val.toLowerCase()) {
            case "true":
                return true;
            case "false":
                return false;
            default:
                return var;
        }
    }
    
    private static long getLong(long var, String val)
    {
        try{
            return Long.parseLong(val); 
        }catch(Exception ex)
        {
            return var;
        }
    }
    
}