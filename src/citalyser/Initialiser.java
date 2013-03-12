/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/
package citalyser;

import java.io.File;
import org.apache.log4j.Logger;

public class Initialiser {
    
    static Logger logger = Logger.getLogger(Initialiser.class.getName());
    
    public static void init()
    {
        logger.info("Initialising App directory");
        String userHome = System.getProperty("user.home");
        if(userHome == null)
            userHome = ".";
        File home = new File(userHome);
        Main.setSettingsDirectory(new File(home,".citalyser"));
        if(!Main.getSettingsDirectory().exists())
        {
            if(!Main.getSettingsDirectory().mkdir())
            {
                logger.error("Failed to create Settings Directory. Exiting..");
                //System.exit(0);
            }
            else
                logger.info("Created app directory at : " + Main.getSettingsDirectory().getAbsolutePath());
        }
        else
            logger.info("App Directory already present at  " + Main.getSettingsDirectory().getAbsolutePath());
    }
    
}
