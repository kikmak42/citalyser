/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.griddisplaypanel.gridentitypanel;

import citalyser.CommonUtils;
import citalyser.Constants;
import citalyser.queryhandler.Query;
import citalyser.queryhandler.QueryHandler;
import citalyser.queryhandler.QueryType;
import citalyser.queryresult.ImageResult;
import citalyser.queryresult.QueryResult;
import citalyser.ui.visualization.MainFrame;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class ImageHandler {
    
    static Logger logger = Logger.getLogger(ImageHandler.class.getName());
    
    public static void displayImage(javax.swing.JLabel jLabel, String imageSource) {
        final JLabel myLabel = jLabel;
        final String myImgSource = imageSource;
        try {
            new Thread() {
                @Override
                public void run() {
                    QueryResult<?> q = (QueryResult<?>) QueryHandler.getInstance().getQueryResult(
                                new Query.Builder("").flag(QueryType.IMAGE_FROM_LINK).Url(myImgSource).build());
                    if(q instanceof ImageResult)
                    {
                        ImageIcon img = (ImageIcon)q.getContents();
                        if(img == null)
                        {    
                            //img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(
                            //                                Constants.RESOURCE_FOLDER_PATH + "user.jpg")));
                            img = new ImageIcon("user.jpg");                        
                        }
                        else
                            myLabel.setIcon(img);
                    }
                }
                
            }.start();
       /*     QueryResult<?> q = (QueryResult<?>) QueryHandler.getInstance().getQueryResult(
                  new Query.Builder("").flag(QueryType.IMAGE_FROM_LINK).Url(myImgSource).build());
            logger.debug("Getting Image.....");
            if(q instanceof ImageResult)
            {
                ImageIcon img = (ImageIcon)q.getContents();
                if(img == null)
                {    
                    //img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(
                    //                                Constants.RESOURCE_FOLDER_PATH + "user.jpg")));
                    img = new ImageIcon("user.jpg");                        
                }
                else
                    myLabel.setIcon(img);
            } */
//            //URL url = new URL(imageSource);
//            //URLConnection conn = url.openConnection(CommonUtils.getBestJavaProxy());
//            //InputStream inStream = conn.getInputStream();
//            BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D graphics2D = bufferedImage.createGraphics();
//            //BufferedImage image = ImageIO.read(new File("loading.gif")); 
//            graphics2D.drawImage(ImageIO.read(new File("user,jpg")), 0, 0, 100, 100, jLabel);
//            //graphics2D.drawImage(ImageIO.read(new URL("http://cse.iitkgp.ac.in/~animeshm/mypic.jpg")), 0, 0, 100, 100, jLabel);
//            //graphics2D.dispose();
//            jLabel.setIcon(new ImageIcon(bufferedImage));
        } catch (Exception ex) {
            logger.debug("Error creating thread for getting image....");
            ex.printStackTrace();
        }
    }

}
