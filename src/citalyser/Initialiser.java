/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/
package citalyser;

import citalyser.dataextraction.cache.CacheCleaner;
import citalyser.util.Config;
import java.io.File;
import java.util.Timer;
import org.apache.log4j.Logger;

public class Initialiser {
    
    static Logger logger = Logger.getLogger(Initialiser.class.getName());
    
    public static void init()
    {
        initAppDirectory();
        initCache();
        //initCacheCleaner();
    }
    
    /* Initialise the .citalyser folder in the User Home directory*/
    public static void initAppDirectory()
    {
        logger.info("Initialising App directory");
        String userHome = System.getProperty("user.home");
        if(userHome == null)
            userHome = ".";
        File home = new File(userHome);
        Main.settingsDirectory = new File(home,".citalyser");
        if(!Main.settingsDirectory.exists())
        {
            if(!Main.settingsDirectory.mkdir())
            {
                logger.error("Failed to create Settings Directory. Exiting..");
                //System.exit(0);
                return;
            }
            else
                logger.info("Created app directory at : " + Main.settingsDirectory.getAbsolutePath());
        }
        else
            logger.info("App Directory already present at  " + Main.settingsDirectory.getAbsolutePath());
        
        
    }
    
    /* Initialise the Cache Directory inside the .citalyser folder*/
    public static void initCache()
    {
        Main.CacheDirectory = new File(Main.settingsDirectory,"Cache");
        if(!Main.CacheDirectory.exists())
        {
            if(!Main.CacheDirectory.mkdir())
            {
                logger.error("Failed to create Cache Directory. Exiting..");
                //System.exit(0);
                return;
            }
            else
                logger.info("Created Cache directory at : " + Main.settingsDirectory.getAbsolutePath());
        }
        else
            logger.info("Cache Directory already present at  " + Main.settingsDirectory.getAbsolutePath());
        
    }
    
    /* Start the Cache Cleaner process */
    public static void initCacheCleaner()
    {
        Timer cacheCleanSchedule = new Timer("CacheCleanScheduler");
        cacheCleanSchedule.schedule(new CacheCleaner(),5000, Config.CacheCleanerPeriod);
    }
    
}
