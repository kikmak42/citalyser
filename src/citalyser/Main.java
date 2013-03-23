package citalyser;

//import citalyser.networking.hall;
import citalyser.communication.networking.HttpClient;
import citalyser.util.Config;
import citalyser.ui.DisplayController;
import citalyser.ui.control.DisplayControllerImpl;
import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {


    private static Logger logger = Logger.getLogger(Main.class.getName());
    public static File settingsDirectory;
    public static File CacheDirectory;
    private static DisplayController displayController;

    public static DisplayController getDisplayController() {
        return displayController;
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
        
          /* Login to Google*/
        HttpClient.init();
        //String html = HttpClient.getUrlText("https://accounts.google.com/ServiceLogin?hl=en&service=alerts&continue=http://www.google.com/alerts/manage");
        //HttpClient.loginToGoogle(html, "alcalucent123", "alcalucent123");
       
    }
    

}
