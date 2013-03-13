/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.api;
import java.util.ArrayList;

/**
 *
 * @author sahil
 */
public class PageResultPaperCollection {
    private PaperCollection paperCollection;
    private ArrayList<String> citedByList;
    
    public ArrayList<String> getCitedbylist(){
        return citedByList;
    }
    public PaperCollection getPapercollection(){
        return paperCollection;
    }
    public void setCitedbylist(ArrayList<String> string){
        this.citedByList = new ArrayList<>(string);
    }
    public void setPaperCollection(PaperCollection papercollection){
        this.paperCollection = papercollection;
    }
    
    
}
