package citalyser.dataextraction.cache;

import citalyser.Main;
import citalyser.util.Config;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.TimerTask;
import org.apache.log4j.Logger;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KRISHNA
 */
public class CacheCleaner extends TimerTask
{
    static Logger logger = Logger.getLogger(CacheCleaner.class.getName());
    
    @Override
    public void run() {
        logger.info("cacheCleaner invoked");
        File folder = Main.CacheDirectory;
        File[] list = folder.listFiles();
        for(File file : list){
            try {
                logger.debug("Filename :" +file.getAbsolutePath());
                Path filePath = file.toPath();
                BasicFileAttributes attr = Files.readAttributes(filePath,BasicFileAttributes.class);
                if((new Date().getTime() - attr.lastAccessTime().toMillis() ) > (long)Config.CachePersistentTime*24*60*60*1000) {
                   if(file.delete()) {
                       logger.info("cache file deleted: "+file.getName());
                    } else {
                       logger.info("cache file deletion failed: "+file.getName());
                    }
                }
            }
            catch (Exception ex) {
                logger.info("Exception while cleaning cache.");
                //Logger.getLogger(cacheCleaner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void deleteAllCacheFiles(){
        logger.info("deleteAllCacheFiles function invoked");
        File folder = Main.CacheDirectory;
        File[] list = folder.listFiles();
        
        for(File file : list){
                   if(file.delete()) {
                       logger.info("deleteAllCacheFiles: cache file deleted: "+file.getName());
                    } else {
                       logger.info("deleteAllCacheFiles: cache file deletion failed: "+file.getName());
                    }
        }
    }
}
