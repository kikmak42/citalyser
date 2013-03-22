/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.utils;

import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryResult;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.ImageResult;
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
                        logger.debug("Thread going to sleep : ");
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
    
    public static void displayImage(javax.swing.JLabel jLabel, String imageSource, int width, int height) {
        final JLabel myLabel = jLabel;
        final String myImgSource = imageSource;
        final int myWidth = width;
        final int myHeight = height;
        try {
            new Thread() {
                @Override
                public void run() {
                    QueryResult<?> q = (QueryResult<?>) QueryHandler.getInstance().getQueryResult(
                                new Query.Builder("").flag(QueryType.IMAGE_FROM_LINK).Url(myImgSource).build());
                    ImageIcon img;
                    if(q instanceof ImageResult)
                    {
                        logger.debug("Rendering Image");
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
                        myLabel.setIcon(new ImageIcon(img1));
                    }
               }
                
            }.start();
        } catch (Exception ex) {
            logger.error("Error creating thread for getting image... : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
