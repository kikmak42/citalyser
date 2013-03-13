package citalyser;

//import citalyser.networking.hall;
import citalyser.api.*;
import citalyser.ui.DisplayController;
import citalyser.ui.control.DisplayControllerImpl;
import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {


    private static Logger logger = Logger.getLogger(Main.class.getName());
    private static File settingsDirectory;
    private static DisplayController displayController;

    public static DisplayController getDisplayController() {
        return displayController;
    }

    public static File getSettingsDirectory() {
        return settingsDirectory;
    }

    public static void setSettingsDirectory(File settingsDirectory) {
        Main.settingsDirectory = settingsDirectory;
    }
    
    public static void main(String[] args) {
        
        /* Set Logger Settings*/
        PropertyConfigurator.configure("log4j.properties");
        
        /* initialise the software */
        Initialiser.init();
        
        /* Load the Config File*/
        Config.init(settingsDirectory);
        
        displayController = new DisplayControllerImpl();
        displayController.initializeDisplay();
        
    }
    

}
