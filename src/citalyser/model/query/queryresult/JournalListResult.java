/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model.query.queryresult;

import citalyser.model.query.QueryResult;
import citalyser.model.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author KRISHNA
 */
public class JournalListResult extends QueryResult<ArrayList<Journal>> implements Serializable{

    private ArrayList<Journal> journalList;
    //private int hasauthourList;

    @Override
    public void setContents(ArrayList<Journal> e) {
        this.journalList = e;
    }

    @Override
    public ArrayList<Journal> getContents() {
        return this.journalList;
    }
    
    /**
     *
     * @param j
     */
    @Override
    public void appendContents(ArrayList<Journal> j){
        
    }
}
