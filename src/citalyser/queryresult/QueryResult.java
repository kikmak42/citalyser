/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

/**
 *
 * @author sahil
 */

public abstract class QueryResult<E> {
    abstract public void setContents(E e);
    abstract public E getContents();

}
