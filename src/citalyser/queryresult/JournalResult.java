/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.api.Journal;

/**
 *
 * @author KRISHNA
 */
public class JournalResult  extends QueryResult<JournalResult>{
    private Journal journal;
    public Journal getJournal(){
       return this.journal;
    }
    public void setJournal(Journal j){
        this.journal = j;
    }
}
