/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.model.Journal;
import java.util.ArrayList;

/**
 *
 * @author KRISHNA
 */
public class JournalListResult extends QueryResult<JournalListResult> {
    private ArrayList<Journal>journalList;
    //private int hasauthourList;
    
    public void setJournalList(ArrayList<Journal> journallist){
        this.journalList = new ArrayList<>(journallist);
    }
    public ArrayList<Journal> getJournalList(){
        return this.journalList;
    }
    /*public int getHasAuthorList(){
        return this.hasauthourList;
    }
    public void setHasAuthorList(int i){
        this.hasauthourList = i;
    }*/
}
