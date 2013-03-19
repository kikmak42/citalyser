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
    GEN_AUTH,               // Author papers from Google Scholar 
    GEN_JOURN,              // Journal Papers from Google Scholar
    MET_AUTH,               // All Authors matching a particular string from Google Author Search
    MET_JOURN,              // All Journals matching a particular string from Google Metrics
    AUTH_PROF,              // Get Complete Author Details from Google Author Search
    JOURN_PROF,             // Get Complete Journal Details from Google Metrics Search
    IMAGE_FROM_LINK,        // Get BufferedImage from specified link
    CITATIONS_LIST          // Get Citations list of a Paper
}
