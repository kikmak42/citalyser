package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.griddisplaypanel.gridentitypanel;

import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.ImageResult;
import citalyser.model.query.QueryResult;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class ImageHandler {
    
    static Logger logger = Logger.getLogger(ImageHandler.class.getName());
    
    public static void displayImage(javax.swing.JLabel jLabel, String imageSource,final int width, final int height) {
        final JLabel myLabel = jLabel;
        final String myImgSource = imageSource;
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
                        BufferedImage img1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = img1.createGraphics();
                        g.drawImage(img.getImage(),0,0,width,height, myLabel);
                        g.dispose();
                        myLabel.setIcon(new ImageIcon(img1));
                    }
               }
                
            }.start();
       /*     QueryResult<?> q = (QueryResult<?>) QueryHandler.getInstance().getQueryResult(
                  new  Query.Builder("").flag(QueryType.IMAGE_FROM_LINK).Url(myImgSource).build());
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
            logger.debug("Error creating thread for getting image... : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
