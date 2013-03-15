/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.model.Author;

/**
 *
 * @author KRISHNA
 */
public class AuthorResult extends QueryResult<Author> {

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
