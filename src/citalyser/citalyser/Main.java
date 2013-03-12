package citalyser;

//import citalyser.networking.hall;
import citalyser.api.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {


    static Logger logger = Logger.getLogger(Main.class.getName());
    public static File settingsDirectory;
    
    public static void main(String[] args) {
        
        /* Set Logger Settings*/
        PropertyConfigurator.configure("log4j.properties");
        
        /* initialise the software */
        Initialiser.init();
        
        /* Load the Config File*/
        Config.init(settingsDirectory);
        
    }
    

}
