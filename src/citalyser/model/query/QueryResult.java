/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model.query;

import java.io.Serializable;

/**
 *
 * @author sahil
 */

public abstract class QueryResult<T> implements Serializable{

    abstract public void setContents(T t);
    abstract public T getContents();
    abstract public void appendContents(T t);
    abstract public int getNumContents();

}
