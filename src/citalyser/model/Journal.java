/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model;

/**
 *
 * @author rohan
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Journal implements Serializable {
        private String description;
	private String name;
        private String id;
        private double cites_per_author;
        private double papers_per_author;
        private PaperCollection paper_collection;
	public Journal(String name){
            this.description = null;
            this.id = null;
            this.cites_per_author = 0.0;
            this.papers_per_author = 0.0;
            this.paper_collection = null;
            this.name = name;
	}

        public String getDescription(){
            return this.description;
	}
	public void setDescription(String s){
            description = s;
	}
        
        public String getId(){
            return this.id;
        }
        public void setId(String s){
            this.id = s;
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