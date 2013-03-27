package citalyser;

//import citalyser.networking.hall;
import citalyser.graph.CreateGraph;
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
        /*Paper paper = new Paper();
        paper.setTitle("Universal messaging service using single voice grade telephone line within a client/server architecture");
        Author a = new Author("AK Golder");
        ArrayList<Author> arr = new ArrayList<>();
        arr.add(a);
        paper.setAuthors(arr);
        paper.setInfo("AK Golder");
        paper.setCitedByUrl("http://scholar.google.co.in/scholar?oi=bibs&hl=en&cites=10153773069743616474&start=0&num=20&as_vis=1&as_sdt=1%2C5");

        getDisplayController().getMainFrame().getRegularDisplayPanel().getDataVisualizationPanel().getGraphViewPanel().setPaper(paper);
        */
    }
    

}
