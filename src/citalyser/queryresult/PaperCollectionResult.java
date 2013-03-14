/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.api.PaperCollection;

/**
 *
 * @author KRISHNA
 */
public class PaperCollectionResult extends QueryResult<PaperCollectionResult> {
    private PaperCollection paper_collection;
    
    public void setPaperCollection(PaperCollection p){
        this.paper_collection = p;
    }
    public PaperCollection getPaperCollection(){
        return this.paper_collection;
    }
}
