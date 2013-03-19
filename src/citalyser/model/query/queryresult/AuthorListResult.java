/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model.query.queryresult;

import citalyser.model.query.QueryResult;
import citalyser.model.Author;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author KRISHNA
 */
public class AuthorListResult extends QueryResult<ArrayList<Author>> implements Serializable {
    private ArrayList<Author> authorList;
    
//    public void setAuthorList(ArrayList<Author> authorlist){
//        this.authorList = new ArrayList<>(authorlist);
//    }
//    public ArrayList<Author> getAuthorList(){
//        return this.authorList;
//    }
    

    @Override
    public void setContents(ArrayList<Author> e) {
        this.authorList = e;
    }

    @Override
    public ArrayList<Author> getContents() {
        return this.authorList;
    }
    
    @Override
    public void appendContents(ArrayList<Author> a){
        
    }
}