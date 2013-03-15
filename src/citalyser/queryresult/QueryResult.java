/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

/**
 *
 * @author sahil
 */

public abstract class QueryResult<T> {

    abstract public void setContents(T t);
    abstract public T getContents();

}
