package citalyser;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {


    static Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        /* Set Logger Settings*/
        PropertyConfigurator.configure("log4j.properties");
        
        logger.debug("RK Opensoft 2013");
    }
    
}
