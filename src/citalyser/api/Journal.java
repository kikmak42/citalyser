/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.api;

/**
 *
 * @author rohan
 */
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Journal extends QueryResult<Journal>{
	private String name;
        private double cites_per_author;
        private double papers_per_author;
        private PaperCollection paper_collection;
	public Journal(String name){
            this.paper_collection = null;
            this.name = name;
	}

	public String getName(){
            return this.name;
	}
	public void setName(String s){
            name = s;
	}
        public double getCitesPerAuthor(){
            showstats();
            return cites_per_author;
        }
        public double getPapersPerAuthor(){
            showstats();
            return papers_per_author;
        }
        public PaperCollection getPaperCollection(){
            return this.paper_collection;
        }
        public void setPaperCollection(PaperCollection p){
            this.paper_collection = p;
        }
        private void showstats(){
            ArrayList<Paper> paperList = this.paper_collection.getPapers();
            Set<Author> diff_authors = new TreeSet<>();
            int total_cites = 0;
            for (Paper paper : paperList) {
                ArrayList<Author> authors = paper.getAuthors();
                diff_authors.addAll(authors);
                total_cites += paper.getNumCites();
            }
            cites_per_author = total_cites/diff_authors.size();
            papers_per_author = paperList.size()/diff_authors.size();
        }
}