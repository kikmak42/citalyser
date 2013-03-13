package citalyser.api;
import citalyser.parsing.*;
import citalyser.networking.HttpConnection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sony
 */


public class Apibackend {
    public static PaperCollection getDetails(Query q){
        String URL = new String();
        String html ;//= new String();
        TableModel retval = new DefaultTableModel();
        PaperCollection p;
        switch(q.flag){
            case 1: 
                URL =  "http://scholar.google.co.in/scholar?";
                URL +=  "start="+ q.start_page +"&";
                URL +=  "as_q=" + "" + "&" ;
                URL +=  "as_epq=" + "" + "&";
                URL +=  "as_oq=" + "" + "&";
                URL += "as_eq=" + "" + "&";
                URL +="as_occt=" + "" + "&";
                URL +="as_sauthors=" + q.name + "&" ;
                URL +="as_publication=" + "" + "&";
                URL +="as_ylo=" + q.min_year + "&";
                URL +="as_yhi=" + q.max_year + "&";
                URL +="btnG=&hl=en&as_sdt=0%2C5";
                break;
                
            case 2:
                URL =  "http://scholar.google.co.in/scholar?";
                URL  +=  "start="+ q.start_page +"&";
                URL  +=  "as_q=" + "" + "&" ;
                URL  +=  "as_epq=" + "" + "&";
                URL  +=  "as_oq=" + "" + "&";
                URL += "as_eq=" + "" + "&";
                URL +="as_occt=" + "" + "&";
                URL +="as_sauthors=" + ""+ "&" ;
                URL +="as_publication=" + q.name + "&";
                URL +="as_ylo=" + q.min_year + "&";
                URL +="as_yhi=" + q.max_year + "&";
                URL +="btnG=&hl=en&as_sdt=0%2C5";
                break;
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