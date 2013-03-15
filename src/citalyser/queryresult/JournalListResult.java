/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.model.*;
import java.util.ArrayList;

/**
 *
 * @author KRISHNA
 */
public class JournalListResult extends QueryResult<ArrayList<Journal>> {

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
}
