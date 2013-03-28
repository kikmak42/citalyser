/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model.query.queryresult;

import citalyser.model.PaperCollection;
import citalyser.model.query.QueryResult;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;

/**
 *
 * @author Abhishek
 */
public class ImageResult extends QueryResult<ImageIcon> implements Serializable {
    static Logger logger = Logger.getLogger(ImageResult.class.getName());
    private ImageIcon image;

    @Override
    public void setContents(ImageIcon img) {
        this.image = img;
    }

    @Override
    public ImageIcon getContents() {
        return this.image;
    }
     @Override
    public int getNumContents(){
        return 1;
    }
    @Override
    public void appendContents(ImageIcon t) {
        logger.debug("Not Supported yet.");
    }

    @Override
    public PaperCollection getPaperCollection() {
        return null;
    }
}
