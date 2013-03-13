package citalyser.api;
import citalyser.parsing.*;
import hall.HttpConnection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sony
 */

import javax.swing.table.*;

public class Apibackend {
    public static PaperCollection getDetails(Query q){
        String URL = new String();
        String html = new String();
        TableModel retval = new DefaultTableModel();
        PaperCollection p;
        switch(q.flag){
            case 1: 
                URL =  "http://scholar.google.co.in/scholar?"+"start="+ q.start_page +"&" + "as_q=" + "" + "&" + "as_epq=" + "" + "&" + "as_oq=" + "" + "&" + "as_eq=" + "" + "&" + "as_occt=" + "" + "&" + "as_sauthors=" + q.name + "&" + "as_publication=" + "" + "&" + "as_ylo=" + q.min_year + "&" + "as_yhi=" + q.max_year + "&" + "btnG=&hl=en&as_sdt=0%2C5";
           
            case 2:
                URL =  "http://scholar.google.co.in/scholar?"+"start="+ q.start_page +"&" + "as_q=" + "" + "&" + "as_epq=" + "" + "&" + "as_oq=" + "" + "&" + "as_eq=" + "" + "&" + "as_occt=" + "" + "&" + "as_sauthors=" + "" + "&" + "as_publication=" + q.name + "&" + "as_ylo=" + q.min_year + "&" + "as_yhi=" + q.max_year + "&" + "btnG=&hl=en&as_sdt=0%2C5";
        }
        
        //NETWORK FUNCTION CALLED HERE
        
        html = HttpConnection.getUrlText(URL);
        
        p = Extractdata.extractInfo(html);
        
        return p;
        
       // return retval;
      }
    
    private static String getDummyHtml() {
        String returnValue = "";
        FileReader file = null;

        try {
            file = new FileReader("C:/Users/sony/Desktop/the 'present'/opensoft 2013/html.html");
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            while ((line = reader.readLine()) != null) {
                returnValue += line + "\n";
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
        return returnValue;
    }
}