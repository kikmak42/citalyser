/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.api;

/**
 *
 * @author sahil
 */
import java.util.ArrayList;

public class QueryResult {
    private int hasauthourList;
    private ArrayList<Author> authorList;
    private PaperCollection paperCollection;
    public void setHasAuthorList(int i){
        this.hasauthourList = i;
    }
    public void setAuthorList(ArrayList<Author> authorlist){
        this.authorList = new ArrayList<>(authorlist);
    }
    public void setPaperCollection(PaperCollection papercollection){
        this.paperCollection = papercollection;
    }
    public int getHasAuthorList(){
        return this.hasauthourList;
    }
    public ArrayList<Author> getAuthorList(){
        return this.authorList;
    }
    public PaperCollection getPaperCollection(){
        return this.paperCollection;
    }
}
