/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.model.Author;
import java.io.Serializable;

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
}
