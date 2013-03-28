/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model.query.queryresult;

import citalyser.model.query.QueryResult;
import citalyser.model.Journal;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author KRISHNA
 */
public class JournalResult extends QueryResult<Journal> implements Serializable {

    private Journal journal;

    @Override
    public void setContents(Journal e) {
        this.journal = e;
    }

    @Override
    public Journal getContents() {
        return this.journal;
    }
    
    @Override
    public void appendContents(Journal j){
        ArrayList<Paper> p = this.journal.getPaperCollection().getPapers();
        ArrayList<Paper> temp = j.getPaperCollection().getPapers();
        int i;
        for(i=0; i<temp.size(); i++){
            p.add(temp.get(i));
        }
    }
    @Override
    public int getNumContents(){
        try{
            return this.journal.getPaperCollection().getPapers().size();
        }catch(Exception ex){
            return 0;
        }
    }

    @Override
    public PaperCollection getPaperCollection() {
        return this.journal.getPaperCollection();
    }
}
