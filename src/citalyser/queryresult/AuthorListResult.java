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
public class AuthorListResult extends QueryResult<ArrayList<Author>>{
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
}