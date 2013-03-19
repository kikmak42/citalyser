/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model.query;

/**
 *
 * @author Tanmay Patil
 */
public enum QueryType {  
    GEN_AUTH,               // Author papers from Google Scholar --PaperCollectionResult
    GEN_JOURN,              // Journal Papers from Google Scholar --PaperCollectionResult
    MET_AUTH,               // All Authors matching a particular string from Google Author Search --AuthorListResult
    MET_JOURN,              // All Journals matching a particular string from Google Metrics    -- JournalListResult
    AUTH_PROF,              // Get Complete Author Details from Google Author Search --AuthorResult
    JOURN_PROF,             // Get Complete Journal Details from Google Metrics Search --JournalResult
    IMAGE_FROM_LINK,         // Get BufferedImage from specified link --ImageResult
    CITATIONS_LIST          // Get Citations list of a Paper -- PaperCollectionResult
}
