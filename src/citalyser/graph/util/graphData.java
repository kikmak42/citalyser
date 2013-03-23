/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph.util;

import citalyser.dataextraction.parsing.Parser;
import citalyser.model.query.queryresult.PaperCollectionResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author milindtahalani1
 */
public class graphData {
    
    static Logger logger = Logger.getLogger(graphData.class.getName());
    static String returnValue;
    public static void main(String args[]){
        PropertyConfigurator.configure("log4j.properties");

        returnValue = "";
        FileReader file = null;

        try {
            file = new FileReader("C:/Users/milindtahalani1/Documents/input.html");                 //          give complete file path to the source file
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            while ((line = reader.readLine()) != null) {
                returnValue += line + "\n";
            logger.debug("String : " + returnValue);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    // Ignore issues during closing 
                }
            }
        }
        PaperCollectionResult p= new PaperCollectionResult();
       
         
        
    }
}

