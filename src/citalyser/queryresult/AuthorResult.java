/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.api.Author;

/**
 *
 * @author KRISHNA
 */
public class AuthorResult  extends QueryResult<AuthorResult>{
    private Author author;
    public Author getAuthor(){
       return this.author;
    }
    public void setAuthor(Author a){
        this.author = a;
    }
}
