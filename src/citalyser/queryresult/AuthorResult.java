/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.model.Author;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author KRISHNA
 */
public class AuthorResult extends QueryResult<Author> implements Serializable{

    private Author author;

    @Override
    public void setContents(Author e) {
        this.author = e;
    }

    @Override
    public Author getContents() {
        return this.author;
    }
    
    @Override
    public void appendContents(Author a){
        ArrayList<Paper> p = this.author.getPaperCollection().getPapers();
        ArrayList<Paper> temp = a.getPaperCollection().getPapers();
        int i;
        for(i=0; i<temp.size(); i++){
            p.add(temp.get(i));
        }
    }
}
