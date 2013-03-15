/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.model.Author;
import java.util.ArrayList;

/**
 *
 * @author KRISHNA
 */
public class AuthorListResult extends QueryResult<AuthorListResult>{
    private ArrayList<Author> authorList;
    private int hasauthourList;
    
    public void setAuthorList(ArrayList<Author> authorlist){
        this.authorList = new ArrayList<>(authorlist);
    }
    public ArrayList<Author> getAuthorList(){
        return this.authorList;
    }
    public int getHasAuthorList(){
        return this.hasauthourList;
    }
    public void setHasAuthorList(int i){
        this.hasauthourList = i;
    }
}