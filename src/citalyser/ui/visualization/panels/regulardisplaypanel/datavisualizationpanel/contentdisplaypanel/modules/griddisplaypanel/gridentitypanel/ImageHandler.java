/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.griddisplaypanel.gridentitypanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Tanmay Patil
 */
public class ImageHandler {
    
    public static void displayImage(javax.swing.JLabel jLabel, String imageSource) {
        try {
            BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = bufferedImage.createGraphics();
            graphics2D.drawImage(ImageIO.read(new URL("http://cse.iitkgp.ac.in/~animeshm/mypic.jpg")), 0, 0, 100, 100, jLabel);
            graphics2D.dispose();
            jLabel.setIcon(new ImageIcon(bufferedImage));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
