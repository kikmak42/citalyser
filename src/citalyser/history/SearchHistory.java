package citalyser.history;

import citalyser.Main;
import citalyser.model.query.Query;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class SearchHistory {
    
    private HashMap<String, Query> historyMap;
    static Logger logger = Logger.getLogger(SearchHistory.class.getName());

    public SearchHistory() {
        historyMap = loadHistory();
    }

    public void addQuery(Query query) {
        logger.debug("Adding query : " + query.toString());
        query.timestamp = System.currentTimeMillis()/1000;
        historyMap.put(query.name, query);
        savehistory();
    }

    public void clearHistory() {
        //TODO: Clear from file
        historyMap = new HashMap<>();
        savehistory();
    }

    public String[] getSuggestions(String typedText) {
        return historyMap.keySet().toArray(new String[0]);
    }

    public HashMap<String,Query> getHistory() {
        return historyMap;
    }
    
    private HashMap<String,Query> loadHistory() {
        HashMap<String,Query> hm;  
        File f = new File(Main.settingsDirectory,"history");
        if(!f.exists())
            return new HashMap<String,Query>();
        try
        {
            try(ObjectInputStream s = new ObjectInputStream(new FileInputStream(f))) {
                historyMap = (HashMap<String,Query>)s.readObject();
            }
            if(historyMap == null)
                return new HashMap<String,Query>();
            return historyMap;
        }
        catch(IOException | ClassNotFoundException ex){
            logger.error("Error getting history : " + ex.getMessage());
            f.delete();
            return new HashMap<String,Query>();
        }
    }
    
    private void savehistory() {
        
        final HashMap<String,Query> hm = this.historyMap;
        Thread thread = new Thread() {
            
            public void run(){
                logger.debug("Saving the history to a file");
            File file = new File(Main.settingsDirectory,"history");
            if(!file.exists())
            {
                try{
                  logger.debug("Creating new history file.");
                  file.createNewFile();
                }catch(Exception ex){
                    logger.error("Failed to create history file");
                }
            }
            try {
                try (ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(file))) {
                    s.writeObject(hm);
                    s.flush();
                }
                logger.info("Dumped the history to file.");
            } catch (IOException ex) {
                //ex.printStackTrace();
                logger.error("Error writing Proxy hashmap to file : " + ex.getMessage());
            }
            }
            
        };
        thread.start();
   }

}
