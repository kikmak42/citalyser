/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryresult;

import citalyser.model.PaperCollection;

/**
 *
 * @author KRISHNA
 */
public class PaperCollectionResult extends QueryResult<PaperCollection> {
    private PaperCollection paper_collection;
    
    

//    @Override
//    void setContents(PaperCollectionResult e) {
//        this.paper_collection=e;
//    }
//
//    @Override
//    PaperCollectionResult getContents() {
//        return this.paper_collection;
//    }

    @Override
    public void setContents(PaperCollection e) {
        this.paper_collection=e;
    }

    @Override
    public PaperCollection getContents() {
        return this.paper_collection;
    }
}
