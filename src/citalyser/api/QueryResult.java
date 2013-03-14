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

public class QueryResult<T extends QueryResult<T>> {
    private int hasauthourList;
    private ArrayList<Author> authorList;
    private PageResultPaperCollection pageResultpaperCollection;
    public void setHasAuthorList(int i){
        this.hasauthourList = i;
    }
    public void setAuthorList(ArrayList<Author> authorlist){
        this.authorList = new ArrayList<>(authorlist);
    }
    public void setPaperCollection(PageResultPaperCollection papercollection){
        this.pageResultpaperCollection = papercollection;
    }
    public int getHasAuthorList(){
        return this.hasauthourList;
    }
    public ArrayList<Author> getAuthorList(){
        return this.authorList;
    }
   /* public PageResultPaperCollection getPaperCollection(){
        return this.pageResultpaperCollection;
    }*/
}
