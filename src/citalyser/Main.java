package citalyser;

//import citalyser.networking.hall;
import citalyser.graph.CreateGraph;
import citalyser.history.SearchHistory;
import citalyser.model.Author;
import citalyser.model.Paper;
import citalyser.util.Config;
import citalyser.ui.DisplayController;
import citalyser.ui.control.DisplayControllerImpl;
import java.io.File;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {


    private static Logger logger = Logger.getLogger(Main.class.getName());
    public static File settingsDirectory;
    public static File CacheDirectory;
    private static DisplayController displayController;
    public static SearchHistory historyHandler;

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
        
         /* Load Search History in memory*/
        historyHandler = new SearchHistory();
      
    }
    

}
