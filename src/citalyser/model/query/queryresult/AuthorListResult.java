/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model.query.queryresult;

import citalyser.model.query.QueryResult;
import citalyser.model.Author;
import java.io.Serializable;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author KRISHNA
 */
public class AuthorListResult extends QueryResult<ArrayList<Author>> implements Serializable {
    static Logger logger= Logger.getLogger(AuthorListResult.class);
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
    public int getNumContents(){
        try{
            return this.authorList.size();
        }catch(Exception ex){
            logger.debug("Error getting Size of result" + ex.getMessage());
            return 0;
        }
    }
    @Override
    public void appendContents(ArrayList<Author> a){
        for(Author author : a)
            this.authorList.add(author);
    }
}