/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.model.Journal;

/**
 *
 * @author KRISHNA
 */
public class JournalResult extends QueryResult<Journal> {

    private Journal journal;

    @Override
    public void setContents(Journal e) {
        this.journal = e;
    }

    @Override
    public Journal getContents() {
        return this.journal;
    }
}
