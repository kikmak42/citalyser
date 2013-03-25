/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.utils;

import citalyser.Main;
import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryResult;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.ImageResult;
import citalyser.ui.model.ContentRenderer;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author abhishek
 */
public class UiUtils {
    
    static Logger logger = Logger.getLogger(UiUtils.class);
    /* jLabel : Label to display the message
     * message : Message to display
     * time : Time to display the message in milliseconds.
     */
    public static void displayLabelMessageTemp(JLabel jLabel, String message, int time) {
        final JLabel label = jLabel;
        final String m = message;
        final int t = time;
        try{
            new Thread(){
                public void run()
                {
                    label.setText(m);
                    try {
                        Thread.sleep(t);
                        label.setText("");
                    } catch (InterruptedException ex) {
                        logger.debug("Error in thread while sleeping : " + ex.getMessage());
                    }
                }
            }.start();
        }catch(Exception ex){
            logger.debug("Error in thread for displaying Message : " + ex.getMessage());
        }
    }
    
    public static void displayImage(final javax.swing.JLabel myLabel,final String myImgSource,final int myWidth,final int myHeight) {
    
        try {
            new Thread() {
                @Override
                public void run() {
                    QueryResult<?> q = (QueryResult<?>) QueryHandler.getInstance().getQueryResult(
                                new Query.Builder("").flag(QueryType.IMAGE_FROM_LINK).Url(myImgSource).build());
                    ImageIcon img;
                    if(q instanceof ImageResult)
                    {
                        img = (ImageIcon)q.getContents();
                        if(img == null)
                        {    
                            //img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(
                            //                                Constants.RESOURCE_FOLDER_PATH + "user.jpg")));
                            img = new ImageIcon("user.jpg");                        
                        }
                        BufferedImage img1 = new BufferedImage(myWidth, myHeight, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = img1.createGraphics();
                        g.drawImage(img.getImage(), 0, 0, myWidth, myHeight, myLabel);
                        g.dispose();
                        logger.debug("Setting image url : " + myImgSource + " at Label : " + myLabel.getToolTipText());
                        myLabel.setIcon(new ImageIcon(img1));
                    }
               }
           }.start();
        } catch (Exception ex) {
            logger.error("Error creating thread for getting image... : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void displayQueryCompleteInfoMessage(QueryType qtype,int count, String searchQuery)
    {
        String result;
        switch(qtype)
        {
            case GEN_AUTH: 
                result =  "Displaying "+count+" papers with authors matching '"+searchQuery+"' from Google Scholar...";
                break;
            case GEN_JOURN:
                result = "Displaying "+count+" papers with publications matching '"+searchQuery+"' from Google Scholar...";
                break;
            case MET_AUTH:
                result = "Displaying "+count+" results for Authors matching '"+searchQuery+"'...";
                break;
            case MET_JOURN:
                 result = "Displaying Top "+count + " publications matching '" + searchQuery + " '...";
                 break;
            case AUTH_PROF:
                result = "Displaying "+count+" papers of Author : " + searchQuery  +"...";
                break;
            case JOURN_PROF:
                result = "Displaying "+count+" papers of Journal : " + searchQuery + "...";
                break;
            default : 
                result = "";
        }
        Main.getDisplayController().displayInfoMessage(result);
    }
    
    public static void displayQueryStartInfoMessage(QueryType qtype,String searchQuery)
    {
        String result;
        switch(qtype)
        {
            case GEN_AUTH: 
                result = "Fetching results for papers of Authors with name matching '"+searchQuery+"' from Google Scholar";
                break;
            case GEN_JOURN:
                result =  "Fetching results for papers in publications with name matching '"+searchQuery+"'...";
                break;
            case MET_AUTH:
                result =  "Fetching results for Authors' name matching '"+searchQuery+"'...";
                break;
            case MET_JOURN:
                result = "Fetching results for publications with name matching '"+searchQuery+"'...";
                break;
            case AUTH_PROF:
                result =  "Fetching profile of Author : " + searchQuery  +"...";
                break;
            case JOURN_PROF:
                 result = "Fetching papers of Journal : " + searchQuery + "...";
                 break;
            default : 
                result = "";
        }
        Main.getDisplayController().displayInfoMessage(result);
    }
    
    public static void displayQueryEmptyMessage(ContentRenderer contentRenderer,QueryType qtype,String searchQuery)
    {
        String result;
        switch(qtype)
        {
            case GEN_AUTH: 
                result = "We did not find any results for papers with Authors matching '"+searchQuery+"' from Google Scholar";
                break;
            case GEN_JOURN:
                result =  "We did not find any results for papers in publications with name matching '"+searchQuery+"'...";
                break;
            case MET_AUTH:
                result = "We did not find any authors matching '" + searchQuery+"'";
                break;
            case MET_JOURN:
                result = "We did not find any journals matching '" + searchQuery + "'";
                break;
            case AUTH_PROF:
                result = "There was some error fetching profile of Author  : '"+searchQuery+"'...";
                break;
            case JOURN_PROF:
                 result = "There was some error fetching publications of the journal '"+searchQuery+"'...";
                 break;
            default : 
                return;
        }
        contentRenderer.displayMessage(result);
    }
}
